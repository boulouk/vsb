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

import eu.chorevolution.modelingnotations.gidl.ComplexType;
import eu.chorevolution.modelingnotations.gidl.ContextTypes;
import eu.chorevolution.modelingnotations.gidl.DataType;
import eu.chorevolution.modelingnotations.gidl.GIDLModel;
import eu.chorevolution.modelingnotations.gidl.InterfaceDescription;
import eu.chorevolution.modelingnotations.gidl.OccurrencesTypes;
import eu.chorevolution.modelingnotations.gidl.SimpleType;
import eu.chorevolution.modelingnotations.gidl.SimpleTypes;
import eu.chorevolution.modelingnotations.gidl.impl.GidlPackageImpl;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.gmdl.utils.enums.QosType;
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
				System.out.println(e.getMessage());
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

	private static String GIDLDataTypeToJavaType(SimpleTypes data_type) {
		String data_type_str = "";
		switch(data_type) {
		case BOOLEAN:
			data_type_str = "Boolean";
			break;
		case INTEGER:
			data_type_str = "Integer";
			break;
		case DATE:
			data_type_str = "Date";
			break;
		case DECIMAL:
			data_type_str = "Double";
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

	private static void addDataObject(Operation op, String getOrPost, eu.chorevolution.modelingnotations.gidl.Data getData) {
		String data_name = getData.getName();
		ContextTypes contextType = getData.getContext();

		//		Definition $ref = getData.getHasDefinition();

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

		Data<?> data = null;

		EList<DataType> dataTypeList = getData.getHasDataType();

		for(DataType dataType: dataTypeList) {
			if(dataType instanceof SimpleType) {
				SimpleTypes dataTypeClass = ((SimpleType) dataType).getType();
				String dataTypeClassStr = GIDLDataTypeToJavaType(dataTypeClass);
				if(((SimpleType) dataType).getOccurences() == OccurrencesTypes.UNBOUNDED) {
					dataTypeClassStr = "List<" + dataTypeClassStr +">"; 
				}
				data = new Data(((SimpleType) dataType).getName(), dataTypeClassStr, true, "application/json", con, true);
			}
			else if(dataType instanceof ComplexType) {
				String dataTypeClassStr = ((ComplexType)dataType).getName().toUpperCase();
				if(((ComplexType) dataType).getOccurences() == OccurrencesTypes.UNBOUNDED) {
					dataTypeClassStr = "List<" + dataTypeClassStr +">"; 
				}
				data = new Data(((ComplexType)dataType).getName(), dataTypeClassStr, true, "application/json", con, true);
				parseComplexType(data, ((ComplexType)dataType));
			}
			if(getOrPost.equals("get")) {
				op.addGetData(data);
			}
			else if(getOrPost.equals("post")) {
				op.setPostData(data);
			}
		}
	}

	private static void parseComplexType(Data<?> parentData, ComplexType dataTypeComplex) {
		Data<?> childData = null;
		EList<DataType> dataTypeList = dataTypeComplex.getHasDataType();
		for(DataType dataType: dataTypeList) {
			if(dataType instanceof SimpleType) {
				SimpleTypes dataTypeClass = ((SimpleType) dataType).getType();
				String dataTypeClassStr = GIDLDataTypeToJavaType(dataTypeClass);
				if(((SimpleType) dataType).getOccurences() == OccurrencesTypes.UNBOUNDED) {
					dataTypeClassStr = "List<" + dataTypeClassStr +">"; 
				}
				childData = new Data(((SimpleType) dataType).getName(), dataTypeClassStr, true, "application/json", null, true);
			}
			else if(dataType instanceof ComplexType) {
				String dataTypeClassStr = "object";
				if(((ComplexType) dataType).getOccurences() == OccurrencesTypes.UNBOUNDED) {
					dataTypeClassStr = "List<" + dataTypeClassStr +">"; 
				}
				childData = new Data(((ComplexType)dataType).getName(), dataTypeClassStr, true, "application/json", null, true);
				parseComplexType(childData, ((ComplexType)dataType));
			}
			parentData.addAttribute(childData);
		}
	}

	public GmServiceRepresentation parse_gidl(GIDLModel gidlModel) {

		JSONParser parser = new JSONParser();
		Map<String, Data<?>> definitonMap = new HashMap<String, Data<?>>();
		GmServiceRepresentation serviceRepresentation = new GmServiceRepresentation();

		String host_address = gidlModel.getHostAddress();
		eu.chorevolution.modelingnotations.gidl.ProtocolTypes protocol = gidlModel.getProtocol();

		serviceRepresentation.setHostAddress(host_address);

		setProtocol(serviceRepresentation, protocol);

		//			JSONArray operations = (JSONArray) jsonObject.get("operations");
		//		EList<Definition> definitions = gidlModel.getHasDefinitions();

		//		for(Definition definition: definitions) {
		//			String defintionName = definition.getName();
		//			//				String defintionType = (String)definition.get("definition_type");
		//			Context con = Context.PATH;
		//
		//			Data<?> data = null;
		//			//				if(defintionType.equals("object")) {
		//			data = new Data<>(defintionName, defintionName, false, "application/json", con, false);
		//			//				} 
		//			//				else {
		//			//					data = new Data<>(defintionName, defintionType, true, "application/json", con, false);
		//			//				}
		//			definitonMap.put(defintionName, data);
		//		}
		//
		//		for(Definition definition: definitions) {
		//			String definitionName = definition.getName();
		//			Data<?> parentData = definitonMap.get(definitionName);
		//
		//			for(Property property: definition.getHasProperties()) {
		//				String propertyName = property.getName();
		//				DataType propertyTypeEnum = property.getType();
		//				String propertyType = GIDLDataTypeToJavaType(propertyTypeEnum);
		//				boolean req = property.isRequired();
		//				if(propertyType.equals("object")) {
		//					Definition referenceDef = property.getReferenceDefinition();
		//					Data<?> data = new Data(definitonMap.get(referenceDef.getName()));
		//					data.setName(propertyName);
		//					if(req)
		//						data.setIsRequired(true);
		//					parentData.addAttribute(data);
		//				}
		//				else {
		//					Data<?> data = new Data(propertyName, propertyType, true, "application/json", Context.PATH, req);
		//					parentData.addAttribute(data);
		//				}
		//			}
		//
		//			serviceRepresentation.addTypeDefinition(parentData);
		//		}

		// Looping through all the interfaces and adding our constructed interface object to serviceRepresentation 

		EList<InterfaceDescription> interfaces = gidlModel.getHasInterfaces();

		for(InterfaceDescription inter: interfaces) {

			Interface interfaceObj = null;

			interfaceObj = initializeInterfaceObjectWithRole(inter);

			// Looping through all the operations and adding our constructed operation object to interface object 

			EList<eu.chorevolution.modelingnotations.gidl.Operation> ops = inter.getHasOperations();

			for(eu.chorevolution.modelingnotations.gidl.Operation opGidl: ops) {

				// Getting operation name
				String operation_name = opGidl.getName();

				// Getting operation type
				OperationType type = getOperationType(opGidl);

				// Getting operation QoS
				QosType qosType = getQos(opGidl);

				// Parsing Scope
				Scope scope = getScope(opGidl); 

				// Constructing operation object 
				Operation op = new Operation(operation_name, type, qosType, scope);      

				// Adding Input Data
				EList<eu.chorevolution.modelingnotations.gidl.Data> getDataGidl = opGidl.getInputData();
				for(eu.chorevolution.modelingnotations.gidl.Data getData: getDataGidl) {
					addDataObject(op, "get", getData); 
					//					op.addGetData(data);
				}

				// Adding Output data
				EList<eu.chorevolution.modelingnotations.gidl.Data> postDataGidl = opGidl.getOutputData();
				for(eu.chorevolution.modelingnotations.gidl.Data postData: postDataGidl) {
					addDataObject(op, "post", postData); 
				}

				serviceRepresentation.addOperation(op);
				interfaceObj.addOperation(op);

			}

			serviceRepresentation.addInterface(interfaceObj);

		}

		return serviceRepresentation;
	}

	private void setProtocol(GmServiceRepresentation serviceRepresentation,
			eu.chorevolution.modelingnotations.gidl.ProtocolTypes protocol) {
		switch(protocol) {
		case REST:
			serviceRepresentation.setProtocol(ProtocolType.REST);
			break;
		case SOAP:
			serviceRepresentation.setProtocol(ProtocolType.SOAP);
			break;
		case CO_AP:
			serviceRepresentation.setProtocol(ProtocolType.COAP);
			break;
			//		case JMS:
			//			serviceDefinition.setProtocol(ProtocolType.JMS);
			//			break;
		case MQTT:
			serviceRepresentation.setProtocol(ProtocolType.MQTT);
			break;
			//		case PUB_NUB:
			//			serviceDefinition.setProtocol(ProtocolType.PUB_NUB);
			//			break;
		case SEMI_SPACE:
			serviceRepresentation.setProtocol(ProtocolType.SEMI_SPACE);
			break;
		case WEB_SOCKETS:
			serviceRepresentation.setProtocol(ProtocolType.WEB_SOCKETS);
			break;
		case ZERO_MQ:
			serviceRepresentation.setProtocol(ProtocolType.ZERO_MQ);
			break;
		}
	}

	private Scope getScope(
			eu.chorevolution.modelingnotations.gidl.Operation opGidl) {

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
		return scope;

	}

	private QosType getQos(
			eu.chorevolution.modelingnotations.gidl.Operation opGidl) {

		eu.chorevolution.modelingnotations.gidl.QosTypes qosTypeGidl = opGidl.getQos();
		QosType qosType = null;
		switch(qosTypeGidl) {
		case RELIABLE:
			qosType = QosType.RELIABLE;
			break;
		case UNRELIABLE:
			qosType = QosType.UNRELIABLE;
			break;
		}
		return qosType;

	}

	private OperationType getOperationType(
			eu.chorevolution.modelingnotations.gidl.Operation opGidl) {

		eu.chorevolution.modelingnotations.gidl.OperationTypes operation_type = opGidl.getType();  
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
		return type;

	}

	private Interface initializeInterfaceObjectWithRole(
			InterfaceDescription inter) {

		Interface interfaceObj = null;
		eu.chorevolution.modelingnotations.gidl.RoleTypes roleNameEnum = inter.getRole();
		switch(roleNameEnum) {
		case PROVIDER:
			interfaceObj = new Interface(RoleType.SERVER);
			break;
		case CONSUMER:	
			interfaceObj = new Interface(RoleType.CLIENT);
			break;
		}
		return interfaceObj;

	}
}
