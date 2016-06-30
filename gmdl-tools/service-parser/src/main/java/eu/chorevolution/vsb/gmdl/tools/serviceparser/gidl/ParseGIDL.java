package eu.chorevolution.vsb.gmdl.tools.serviceparser.gidl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.modelingnotations.gidl.ContextType;
import eu.chorevolution.modelingnotations.gidl.DataType;
import eu.chorevolution.modelingnotations.gidl.Definition;
import eu.chorevolution.modelingnotations.gidl.GIDLModel;
import eu.chorevolution.modelingnotations.gidl.InterfaceDescription;
import eu.chorevolution.modelingnotations.gidl.Property;
import eu.chorevolution.modelingnotations.gidl.impl.GidlPackageImpl;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;
import eu.chorevolution.vsb.gmdl.utils.enums.Verb;

public class ParseGIDL {

  static class Utils {
    public static GIDLModel loadGIDLModel(URI cltsURI) throws Exception {
      GidlPackageImpl.init();
      Resource resource = new XMIResourceFactoryImpl().createResource(cltsURI);
      try {
        // load the resource
        resource.load(null);
      } catch (IOException e) {
        throw new Exception("Error to load the resource: " + resource.getURI().toFileString());
      }
      GIDLModel gidlModel = (GIDLModel) resource.getContents().get(0);
      return gidlModel;
    }
  }

  public GmServiceRepresentation parse(String gidl) {
    GIDLModel  model = null;
    try {
      model = Utils.loadGIDLModel(URI.createURI(new File(gidl).toURI().toString()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return parse_gidl(model);
  }

  private static String GIDLDataTypeToJavaType(DataType data_type) {
    String data_type_str = "";
    switch(data_type) {
    case BOOLEAN:
      data_type_str = "boolean";
      break;
    case INTEGER:
      data_type_str = "int";
      break;
    case DATE:
      data_type_str = "Date";
      break;
    case DECIMAL:
      data_type_str = "double";
      break;
    case OBJECT:
      data_type_str = "object";
      break;
    case STRING:
      data_type_str = "String";
      break;
    case TIME:
      data_type_str = "Time";
      break;
    default:
      System.out.println("Unknown datta type while parsing GIDL");
    }
    return data_type_str;
  }
  
  private static Data<?> getDataObject(eu.chorevolution.modelingnotations.gidl.Data getData, Map<String, Data<?>> definitonMap) {
    String data_name = getData.getName();
    DataType data_type = getData.getType();
    ContextType contextType = getData.getContext();
    Definition $ref = getData.getHasDefinition();

    Context con = null;
    switch(contextType) {
    case BODY:
      con = Context.BODY;
      break;
    case FORM:
      con = Context.FORM;
      break;
    case HEADER:
      con = Context.HEADER;
      break;
    case PATH:
      con = Context.PATH;
      break;
    case QUERY:
      con = Context.QUERY;
      break;
    }

    String data_type_str = GIDLDataTypeToJavaType(data_type);

    Data<?> data = null;
    if("object".equals(data_type_str)) {
      data = new Data(definitonMap.get($ref.getName()));
      data.setName(data_name);
      data.setContext(con);
    } 
    else {

      data = new Data(data_name, data_type_str, true, "application/json", con, true);
    }
    return data;
  }



  public GmServiceRepresentation parse_gidl(GIDLModel gidlModel) {
    JSONParser parser = new JSONParser();
    Map<String, Data<?>> definitonMap = new HashMap<String, Data<?>>();
    GmServiceRepresentation serviceDefinition = new GmServiceRepresentation();


    String host_address = gidlModel.getHostAddress();
    eu.chorevolution.modelingnotations.gidl.ProtocolType protocol = gidlModel.getProtocol();

    //      BcConfiguration compConfServer = new BcConfiguration();
    //      compConfServer.setServiceAddress(host_address);
    //      compConfServer.setGeneratedCodePath("src/test/generated");
    //      compConfServer.setTargetNamespace("");

    serviceDefinition.setHostAddress(host_address);

    switch(protocol) {
    case REST:
      serviceDefinition.setProtocol(ProtocolType.REST);
      break;
    case SOAP:
      serviceDefinition.setProtocol(ProtocolType.SOAP);
      break;
    }

    EList<InterfaceDescription> interfaces = gidlModel.getHasInterfaces();
    //			JSONArray operations = (JSONArray) jsonObject.get("operations");
    EList<Definition> definitions = gidlModel.getHasDefinitions();

    for(Definition definition: definitions) {
      String defintionName = definition.getName();
      //				String defintionType = (String)definition.get("definition_type");
      Context con = Context.PATH;

      Data<?> data = null;
      //				if(defintionType.equals("object")) {
      data = new Data<>(defintionName, defintionName, false, "application/json", con, false);
      //				} 
      //				else {
      //					data = new Data<>(defintionName, defintionType, true, "application/json", con, false);
      //				}
      definitonMap.put(defintionName, data);
    }

    for(Definition definition: definitions) {
      String definitionName = definition.getName();
      Data<?> parentData = definitonMap.get(definitionName);

      for(Property property: definition.getHasProperties()) {
        String propertyName = property.getName();
        DataType propertyTypeEnum = property.getType();
        String propertyType = GIDLDataTypeToJavaType(propertyTypeEnum);
        boolean req = property.isRequired();
        if(propertyType.equals("object")) {
          Definition referenceDef = property.getReferenceDefinition();
          Data<?> data = new Data(definitonMap.get(referenceDef.getName()));
          data.setName(propertyName);
          if(req)
            data.setIsRequired(true);
          parentData.addAttribute(data);
        }
        else {
          Data<?> data = new Data(propertyName, propertyType, true, "application/json", Context.PATH, req);
          parentData.addAttribute(data);
        }
      }

      serviceDefinition.addTypeDefinition(parentData);
    }

    //			Map<String, Operation> operationMap = new HashMap<String, Operation>();

    for(InterfaceDescription inter: interfaces) {
      eu.chorevolution.modelingnotations.gidl.RoleType roleNameEnum = inter.getRole();
      EList<eu.chorevolution.modelingnotations.gidl.Operation> ops = inter.getHasOperations();
      Interface interfaceObj = null;
      switch(roleNameEnum) {
      case PROVIDER:
        interfaceObj = new Interface(RoleType.SERVER);
        break;
      case CONSUMER:	
        interfaceObj = new Interface(RoleType.CLIENT);
        break;
      }

      for(eu.chorevolution.modelingnotations.gidl.Operation opGidl: ops) {
        String operation_name = opGidl.getName();
        eu.chorevolution.modelingnotations.gidl.OperationType operation_type = opGidl.getType();  
        OperationType type = null;
        switch(operation_type) {
        case ONE_WAY:
          type = OperationType.ONE_WAY;
          break;
        case STREAM:
          type = OperationType.STREAM;
          break;
        case TWO_WAY_ASYNC:
          type = OperationType.TWO_WAY_ASYNC;
          break;
        case TWO_WAY_SYNC:
          type = OperationType.TWO_WAY_SYNC;
          break;
        }

        //        String role = (String) operation.get("role");  
        //        if(role.equalsIgnoreCase("SERVER")) {
        //          compConfServer.setSubcomponentRole(RoleType.SERVER);
        //        }
        //        else if(role.equalsIgnoreCase("CLIENT")) {
        //          compConfServer.setSubcomponentRole(RoleType.CLIENT);
        //        }

        eu.chorevolution.modelingnotations.gidl.Scope scopeGidl = opGidl.getHasScope();

        Scope scope = new Scope();

        if(scopeGidl!=null) {
          scope.setName(scopeGidl.getName());
          switch(scopeGidl.getVerb()) {
          case "GET":
            scope.setVerb(Verb.GET);            
            break;
          case "POST":
            scope.setVerb(Verb.POST);            
            break;
          case "PUT":
            scope.setVerb(Verb.PUT);            
            break;
          case "PATCH":
            scope.setVerb(Verb.PATCH);            
            break;
          case "DELETE":
            scope.setVerb(Verb.DELETE);            
            break;
          default:
            System.out.println("Error: Unknown verb encountered during parsing scope block.");
          }
          scope.setUri(scopeGidl.getUri());
        } 

        Operation op = new Operation(operation_name, scope, type);      

        EList<eu.chorevolution.modelingnotations.gidl.Data> get_data = opGidl.getInputData();

        for(eu.chorevolution.modelingnotations.gidl.Data getData: get_data) {
          Data<?> data = getDataObject(getData, definitonMap); 
          op.addGetData(data);
        }

        EList<eu.chorevolution.modelingnotations.gidl.Data> post_data = opGidl.getOutputData();

        for(eu.chorevolution.modelingnotations.gidl.Data postData: post_data) {
          Data<?> data = getDataObject(postData, definitonMap); 
          op.setPostData(data);
        }

        serviceDefinition.addOperation(op);
        interfaceObj.addOperation(op);
      }
      //			
      serviceDefinition.addInterface(interfaceObj);
    }

    return serviceDefinition;
  }
}
