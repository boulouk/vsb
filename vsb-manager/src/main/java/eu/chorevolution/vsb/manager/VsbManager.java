package eu.chorevolution.vsb.manager;

//import eu.chorevolution.vsb.bc.generators.JarGenerator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCatchBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JForLoop;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JVar;

import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;

public class VsbManager {
  public void generateBindingComponent(final String interfaceDescription, final Protocol busProtocol) {
    GmServiceRepresentation gmComponentRepresentation = null;
    
    BcConfiguration bcConfiguration = null;

    gmComponentRepresentation = ServiceDescriptionParser.getRepresentationFromGMDL(interfaceDescription);
    
    generateClass(gmComponentRepresentation, busProtocol);
    
//    JSONParser parser = new JSONParser();
//    JSONObject jsonObject = null;
//
//    String configPath = BcManager.class.getClassLoader().getResource("config.json").toExternalForm();
//
//    try {
//      jsonObject = (JSONObject) parser.parse(new FileReader(configPath));//"/home/siddhartha/Downloads/chor/evolution-service-bus/bc-manager/src/main/resources/config.json"));
//    } catch (IOException | ParseException e) {
//      e.printStackTrace();
//    }
//   
//    for(Interface inter: gmComponentRepresentation.getInterfaces()) {
//     
//      BcGmSubcomponent block1Component = null;
//      BcGmSubcomponent block2Component = null;
//      
//      RoleType busRole = null;
//      if(inter.getRole() == RoleType.SERVER) {
//        busRole = RoleType.CLIENT;
//      }
//      else if(inter.getRole() == RoleType.CLIENT) {
//        busRole = RoleType.SERVER;
//      }
//      
//      bcConfiguration = new BcConfiguration();
//      bcConfiguration.setSubcomponentRole(inter.getRole());
//      bcConfiguration.setServiceAddress(gmComponentRepresentation.getHostAddress());
//      bcConfiguration.setServiceName((String) jsonObject.get("service_name"));
//      bcConfiguration.setTargetNamespace((String) jsonObject.get("target_namespace"));
//      
//      switch(busProtocol) {
//      case REST:
//        block1Component = new BcRestSubcomponent(bcConfiguration); 
//        break;
//      case SOAP:
//        block1Component = new BcSoapSubcomponent(bcConfiguration); 
//        break;
//      case MQTT:
//        block1Component = new BcMQTTSubcomponent(bcConfiguration); 
//        break;
//      }
//
//      bcConfiguration = new BcConfiguration();
//      bcConfiguration.setSubcomponentRole(busRole);
//      bcConfiguration.setServiceAddress(gmComponentRepresentation.getHostAddress());
//      bcConfiguration.setServiceName((String) jsonObject.get("service_name"));
//      bcConfiguration.setTargetNamespace((String) jsonObject.get("target_namespace"));
//      
//      switch(gmComponentRepresentation.getProtocol()) {
//      case REST:
//        block2Component = new BcRestSubcomponent(bcConfiguration); 
//        break;
//      case SOAP:
//        block2Component = new BcSoapSubcomponent(bcConfiguration); 
//        break;
//      case MQTT:
//        block2Component = new BcMQTTSubcomponent(bcConfiguration); 
//        break;
//      }
//
//      block1Component.setNextComponent(block2Component);
//      block2Component.setNextComponent(block1Component);
//      
//      block1Component.start();
//      block2Component.start();
//      
//    }


    // TODO: instantiate the right generator based on the bcConfig
    // could use JAVA Service Provider Interface (SPI) for a clean and clear implementation
    //    JarGenerator.generateBc(new BcSoapGenerator(gmComponentDescription, new BcConfiguration(bcConfiguration)));
  }
  
  
  public static void generateClass(GmServiceRepresentation gmComponentRepresentation, Protocol busProtocol) {
    
    /* Creating java code model classes */
    JCodeModel jCodeModel = new JCodeModel();
    /* Adding package here */
    JPackage jp = jCodeModel._package("test");
  
    /* Giving Class Name to Generate */
    JDefinedClass jc = null;
    try {
      jc = jp._class("GeneratedFactory");
    } catch (JClassAlreadyExistsException e) {
      e.printStackTrace();
    }
    
    JMethod jmCreate = jc.method(JMod.PUBLIC | JMod.STATIC, void.class, "run");
    
    /* Adding method body */
    JBlock jBlock = jmCreate.body();

    JClass JSONParserClass = jCodeModel.ref(org.json.simple.parser.JSONParser.class);
    JVar JSONParserVar = jBlock.decl(JSONParserClass, "parser");
    JSONParserVar.init(JExpr._new(JSONParserClass));
    
    JClass JSONObjectClass = jCodeModel.ref(org.json.simple.JSONObject.class);
    JVar JSONObjectVar = jBlock.decl(JSONObjectClass, "jsonObject");
    JSONObjectVar.init(JExpr._new(JSONObjectClass));

    JClass StringClass = jCodeModel.ref(String.class);
    JVar StringObjectVar = jBlock.decl(StringClass, "configFilePath", jCodeModel.ref(BcManager.class).staticInvoke("getClassLoader").invoke("getResource").arg("config.json").invoke("toExternalForm"));

    JClass ExceptionClass = jCodeModel.ref(java.lang.Exception.class);
   
    JTryBlock parseTryBlock = jBlock._try();
    JBlock parseBlock = parseTryBlock.body();
    
    JClass FileReaderClass = jCodeModel.ref(java.io.FileReader.class);
    JVar FileReaderVar = parseBlock.decl(FileReaderClass, "fileReader");
    FileReaderVar.init(JExpr._new(FileReaderClass).arg(StringObjectVar));
    
    JInvocation parserInvocation = JSONParserVar.invoke("parse");
    parserInvocation.arg(FileReaderVar);
    
    parseBlock.assign(JExpr.ref(JSONObjectVar.name()),JExpr.cast(JSONObjectClass, parserInvocation));
    
    JCatchBlock parseCatchBlock = parseTryBlock._catch(ExceptionClass);
   
    JClass GmComponentRepresentationClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation.class);
    JVar GmComponentRepresentationVar = jBlock.decl(GmComponentRepresentationClass, "gmComponentRepresentation");
    
    JForLoop forLoop = jBlock._for();
    JVar ivar = forLoop.init(jCodeModel.INT, "i", JExpr.lit(0));
    forLoop.test(ivar.lt( GmComponentRepresentationVar.invoke("getInterfaces").invoke("size") ));
    forLoop.update(ivar.assignPlus(JExpr.lit(1)));

    JBlock forBlock = forLoop.body();
    
    JClass InterfaceClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.Interface.class);
    JVar InterfaceVar = forBlock.decl(InterfaceClass, "inter");
    
    forBlock.assign(JExpr.ref(InterfaceVar.name()), GmComponentRepresentationVar.invoke("getInterfaces").invoke("get").arg(ivar));
    
    JClass BcGmSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent.class);
    JVar BcGmSubcomponentVar1 = forBlock.decl(BcGmSubcomponentClass, "block1Component");
    JVar BcGmSubcomponentVar2 = forBlock.decl(BcGmSubcomponentClass, "block2Component");
    
    
    JClass RoleTypeClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.enums.RoleType.class);
    JClass BcRestSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent.class);
    JClass BcSoapSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent.class);
    JClass BcMQTTSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTSubcomponent.class);
    JClass BcConfigurationClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.BcConfiguration.class);
    
    JEnumConstant RoleTypeEnum = null;
    try {
      RoleTypeEnum = jCodeModel._class(JMod.PUBLIC, "eu.chorevolution.vsb.gmdl.utils.enums.RoleType", ClassType.ENUM).enumConstant("RoleType");
    } catch (JClassAlreadyExistsException e1) {
      e1.printStackTrace();
    }

    JVar RoleTypeClassVar = forBlock.decl(RoleTypeClass, "busRole");
    
    JConditional roleCondition = forBlock._if(InterfaceVar.invoke("getRole").eq(RoleTypeEnum.ref("SERVER")) );
    roleCondition._then().assign(JExpr.ref(RoleTypeClassVar.name()), RoleTypeEnum.ref("CLIENT"));
    roleCondition._else().assign(JExpr.ref(RoleTypeClassVar.name()), RoleTypeEnum.ref("SERVER"));
    
    switch(busProtocol) {
    case REST:
      BcGmSubcomponentVar1.init(JExpr._new(BcRestSubcomponentClass));
      break;
    case SOAP:
      BcGmSubcomponentVar1.init(JExpr._new(BcSoapSubcomponentClass));
      break;
    case MQTT:
      BcGmSubcomponentVar1.init(JExpr._new(BcMQTTSubcomponentClass));
      break;
    }
    
    switch(gmComponentRepresentation.getProtocol()) {
    case REST:
      BcGmSubcomponentVar1.init(JExpr._new(BcRestSubcomponentClass));
      break;
    case SOAP:
      BcGmSubcomponentVar1.init(JExpr._new(BcSoapSubcomponentClass));
      break;
    case MQTT:
      BcGmSubcomponentVar1.init(JExpr._new(BcMQTTSubcomponentClass));
      break;
    }
    
    forBlock.add(BcGmSubcomponentVar1.invoke("setNextComponent").arg(BcGmSubcomponentVar2));
    forBlock.add(BcGmSubcomponentVar2.invoke("setNextComponent").arg(BcGmSubcomponentVar1));
    
    forBlock.add(BcGmSubcomponentVar1.invoke("start"));
    forBlock.add(BcGmSubcomponentVar2.invoke("start"));
    
    try {
      jCodeModel.build(new File("src/main/java"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
}
