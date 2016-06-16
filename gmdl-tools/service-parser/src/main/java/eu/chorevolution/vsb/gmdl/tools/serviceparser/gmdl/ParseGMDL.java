package eu.chorevolution.vsb.gmdl.tools.serviceparser.gmdl;

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

import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;
import eu.chorevolution.vsb.gmdl.utils.enums.Verb;

public class ParseGMDL {

  private static Data<?> getDataObject(JSONObject getData, Map<String, Data<?>> definitonMap) {
    String data_name = (String) getData.get("data_name");
    String data_type = (String) getData.get("data_type");   
    String context = (String) getData.get("context");
    String $ref = (String) getData.get("$ref");

    Context con = null;
    if("body".equals(context)) {
      con = Context.BODY;
    }
    else if("path".equals(context)) {
      con = Context.PATH;
    } 
    else {
      System.out.println("unknown context");
    }

    Data<?> data = null;
    if("object".equals(data_type)) {
      data = new Data(definitonMap.get($ref));
      data.setName(data_name);
    } 
    else {
      data = new Data(data_name, data_type, true, "application/json", con, true);
    }
    return data;
  }

  public GmServiceRepresentation parse(String gmdlPath) {
    JSONParser parser = new JSONParser();
    Map<String, Data<?>> definitonMap = new HashMap<String, Data<?>>();
    GmServiceRepresentation serviceDefinition = new GmServiceRepresentation();
    try {
      JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(gmdlPath));
      String host_address = (String) jsonObject.get("host_address");
      String protocol = (String) jsonObject.get("protocol");

      BcConfiguration compConfServer = new BcConfiguration();

      compConfServer.setServiceAddress(host_address);
      compConfServer.setGeneratedCodePath("src/test/generated");
      compConfServer.setTargetNamespace("");

      serviceDefinition.setHostAddress(host_address);

      switch(protocol) {
      case "REST":
        serviceDefinition.setProtocol(Protocol.REST);
        break;
      }

      JSONArray operations = (JSONArray) jsonObject.get("operations");
      JSONArray definitions = (JSONArray) jsonObject.get("definitions");

      if(operations == null) {
        operations = new JSONArray();
      }

      if(definitions == null) {
        definitions = new JSONArray();
      }

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
        String definitionName = (String)definition.get("definition_name");
        Data<?> parentData = definitonMap.get(definitionName); 

        Set<String> requiredProperties = new HashSet<String>();
        JSONArray required = (JSONArray) definition.get("required");
        Iterator<String> requiredIterator = required.iterator();
        while(requiredIterator.hasNext()) {
          String property = (String) requiredIterator.next();
          requiredProperties.add(property);
        }

        JSONArray properties = (JSONArray) definition.get("properties");
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

            Data<?> data = new Data(definitonMap.get($ref));
            data.setName(propertyName);
            if(req)
              data.setIsRequired(true);
            parentData.addAttribute(data);
          }
          else {
            boolean req = false;
            if(requiredProperties.contains(propertyName)) 
              req = true;
            Data<?> data = new Data(propertyName, propertyType, true, "application/json", Context.PATH, req);
            parentData.addAttribute(data);
          }
        }
        serviceDefinition.addTypeDefinition(parentData);
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
        compConfServer.setSubcomponentRole(role.toUpperCase());

        JSONObject scopeJSONObj = null;
        Object scopeObj = operation.get("scope");

        Scope scope = new Scope();
        try {
          if(scopeObj.getClass() == Class.forName("java.lang.String")) {
            String uri = (String) scopeObj;     
            scope.setUri(uri);
          }
          else {
            scopeJSONObj = (JSONObject) scopeObj;
            String verb = (String) scopeJSONObj.get("verb");
            String name = (String) scopeJSONObj.get("name");
            String uri = (String) scopeJSONObj.get("uri");     

            scope.setName(name);
            switch(verb) {
            case "GET":
              scope.setVerb(Verb.GET);            
              break;
            }
            scope.setUri(uri);
          }
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }


        Operation op = new Operation(operation_name, scope, type);      

        JSONArray get_data = (JSONArray) operation.get("get_data");

        Iterator<JSONObject> getDataIterator = get_data.iterator();
        while(getDataIterator.hasNext()) {
          JSONObject getData = (JSONObject) getDataIterator.next();
          Data<?> data = getDataObject(getData, definitonMap); 
          op.addGetData(data);
        }

        JSONArray post_data = (JSONArray) operation.get("post_data");

        Iterator<JSONObject> postDataIterator = post_data.iterator();
        while(postDataIterator.hasNext()) {
          JSONObject postData = (JSONObject) postDataIterator.next();
          Data<?> data = getDataObject(postData, definitonMap); 
          op.setPostData(data);
        }

        serviceDefinition.addOperation(op);       

      }
      
      if(serviceDefinition.getProtocol() == Protocol.REST) {
        BcSubcomponentGenerator soapGenerator = new BcSoapGenerator(serviceDefinition, compConfServer).setDebug(true); 
        soapGenerator.generateBc();
      }
      
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
    return serviceDefinition;
  }

}
