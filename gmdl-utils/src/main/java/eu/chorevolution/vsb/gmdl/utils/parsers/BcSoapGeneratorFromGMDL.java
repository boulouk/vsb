package eu.chorevolution.vsb.gmdl.utils.parsers;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;
import eu.chorevolution.vsb.gmdl.utils.enums.Verb;

public class BcSoapGeneratorFromGMDL {

  private static Data<?> getDataObject(JSONObject getData, Map<String, Data<?>> definitonMap) {
    String data_name = (String) getData.get("verb");
    String data_type = (String) getData.get("name");   
    String context = (String) getData.get("uri");
    String $ref = (String) getData.get("$ref");

    Context con = null;
    if(context.equals("body")) {
      con = Context.BODY;
    }
    else {
      con = Context.PATH;
    }

    Data<?> data = null;
    if(data_type.equals("object")) {
      data = definitonMap.get($ref);
    } 
    else {
      data = new Data<>(data_name, data_type, true, "application/json", con, true);
    }
    return data;
  }

  public static void main(String[] args) {
    JSONParser parser = new JSONParser();
    Map<String, Data<?>> definitonMap = new HashMap<String, Data<?>>();
    try {
      JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("/home/siddhartha/Documents/gmdl/dts-google.json"));
      String host_address = (String) jsonObject.get("host_address");
      String protocol = (String) jsonObject.get("protocol");

      JSONArray operations = (JSONArray) jsonObject.get("operations");
      JSONArray definitions = (JSONArray) jsonObject.get("definitions");

      Iterator<JSONObject> definitionsIterator = definitions.iterator();
      while(definitionsIterator.hasNext()) {
        JSONObject definition = (JSONObject) definitionsIterator.next();
        String defintionName = (String)definition.get("definition_name");
        String defintionType = (String)definition.get("definition_type");
        Context con = Context.PATH;

        Data<?> data = null;
        if(defintionType.equals("object")) {
          data = new Data<>(defintionName, defintionName, false, "application/json", con, false);
        } 
        else {
          data = new Data<>(defintionName, defintionType, true, "application/json", con, false);
        }

        definitonMap.put(defintionName, data);
      }
      
      
      definitionsIterator = definitions.iterator();
      while(definitionsIterator.hasNext()) {
        JSONObject definition = (JSONObject) definitionsIterator.next();
        String defintionName = (String)definition.get("definition_name");
        Data<?> parentData = definitonMap.get(defintionName); 
      
        Set<String> requiredProperties = new HashSet<String>();
        JSONArray required = (JSONArray) jsonObject.get("required");
        Iterator<String> requiredIterator = required.iterator();
        while(requiredIterator.hasNext()) {
          String property = (String) requiredIterator.next();
          requiredProperties.add(property);
        }
        
        JSONArray properties = (JSONArray) jsonObject.get("properties");
        Iterator<JSONObject> propertiesIterator = properties.iterator();
        while(propertiesIterator.hasNext()) {
          JSONObject property = (JSONObject) propertiesIterator.next();
          String propertyName = (String)property.get("property_name");
          String propertyType = (String)property.get("property_type");
          if(propertyType.equals("object")) {
            String $ref = (String)property.get("$ref");
            boolean req = false;
            if(requiredProperties.contains(propertyName)) 
              req = true;
            Data<?> data = definitonMap.get($ref);
            data.setName(propertyName);
            if(req)
              data.setIsRequired(true);
            parentData.addAttribute(data);
          }
          else {
            boolean req = false;
            if(requiredProperties.contains(propertyName)) 
              req = true;
            Data<?> data = new Data<>(propertyName, propertyType, true, "application/json", Context.PATH, req);
            parentData.addAttribute(data);
          }
        }
      }
      
      BcConfiguration compConfServer = new BcConfiguration();
      
      compConfServer.setServiceAddress(host_address);
      compConfServer.setGeneratedCodePath("src/test/resources/generated/dtsgoogle");
      compConfServer.setTargetNamespace("");

      GmComponentRepresentation serviceDefinition = new GmComponentRepresentation();
      switch(protocol) {
      case "REST":
        serviceDefinition.setProtocol(Protocol.REST);
        break;
      }
      Iterator<JSONObject> operationsIterator = operations.iterator();
      while(operationsIterator.hasNext()) {
        JSONObject operation = (JSONObject) operationsIterator.next();

        String operation_name = (String) operation.get("operation_name");
        String operation_type = (String) operation.get("operation_type");                 
        OperationType type = null; 
        switch(operation_type) {
        case "two_way":
          type = OperationType.TWO_WAY_SYNC;
        }
        String role = (String) operation.get("role");  
        compConfServer.setComponentRole(role.toUpperCase());
        
        JSONObject scopeObj = (JSONObject) operation.get("scope");
        String verb = (String) scopeObj.get("verb");
        String name = (String) scopeObj.get("name");
        String uri = (String) scopeObj.get("uri");     
        Scope scope = new Scope();
        scope.setName(name);
        switch(verb) {
        case "GET":
          scope.setVerb(Verb.GET);            
          break;
        }
        scope.setUri(uri);

        Operation op = new Operation(operation_name, scope, type);      

        JSONArray get_data = (JSONArray) operation.get("get_data");

        Iterator<JSONObject> getDataIterator = get_data.iterator();
        while(getDataIterator.hasNext()) {
          JSONObject getData = (JSONObject) getDataIterator.next();
          Data<?> data = getDataObject(getData, definitonMap); 
          op.addGetData(data);
        }

        JSONArray post_data = (JSONArray) operation.get("post_data");

        Iterator<JSONObject> postDataIterator = get_data.iterator();
        while(postDataIterator.hasNext()) {
          JSONObject postData = (JSONObject) postDataIterator.next();
          Data<?> data = getDataObject(postData, definitonMap); 
          op.setPostData(data);
        }

        serviceDefinition.addOperation(op);       

      }

      //        this.soapGenerator = new BcSoapGenerator(serviceDefinition, compConfServer).setDebug(true); 

    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
}

