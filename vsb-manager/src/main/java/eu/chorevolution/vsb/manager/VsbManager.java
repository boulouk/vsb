package eu.chorevolution.vsb.manager;

//import eu.chorevolution.vsb.bc.generators.JarGenerator;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.camel.language.Constant;
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
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JForLoop;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JVar;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import eu.chorevolution.vsb.artifact.generators.WarGenerator;
import eu.chorevolution.vsb.bc.manager.BcManagerRestService;
import eu.chorevolution.vsb.bindingcomponent.copy.generated.BindingComponent;
import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapGenerator;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Constants;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;
import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.java2wsdl.Java2WSDL;

public class VsbManager {

  public static void main(String[] args) {
    VsbManager vsbm = new VsbManager();
    vsbm.generate(BcManagerRestService.class.getClassLoader().getResource("DtsGoogle.gidl").toExternalForm().substring(5), ProtocolType.SOAP);
  }

  private static void setConstants(String interfaceDescriptionPath) {
    Constants.generatedCodePath = new File("src" + File.separator + "main" + File.separator + "java").getAbsolutePath();
    Constants.configFilePath = BcManagerRestService.class.getClassLoader().getResource("config.json").toExternalForm().substring(5);
    //Constants.intefaceDescriptionFilePath = BcManager.class.getClassLoader().getResource("DtsGoogle.gidl").toExternalForm().substring(5);
    Constants.intefaceDescriptionFilePath = interfaceDescriptionPath;
    Constants.webapp_src = new File(".." + File.separator + "bc-generators" + File.separator + "webapp-bc-generator" 
        + File.separator + "src" + File.separator + "main" + File.separator 
        + "webapp").getAbsolutePath();
    Constants.warDestination = new File(".." + File.separator + "bc-generators" + File.separator + "webapp-bc-generator" 
        + File.separator + "src" + File.separator + "main" + File.separator 
        + "webapp" + File.separator + "test.war").getAbsolutePath();

    Constants.target_namespace = "eu.chorevolution.vsb.bindingcomponent.generated";
    Constants.service_name = "BindingComponent";
  }


  public void generate(String interfaceDescriptionPath, ProtocolType busProtocol) {

    setConstants(interfaceDescriptionPath);

    //    String interfaceDescriptionPath = Constants.intefaceDescriptionFilePath;

    generateBindingComponent(interfaceDescriptionPath, busProtocol);

    // TODO: instantiate the right generator based on the bcConfig
    // could use JAVA Service Provider Interface (SPI) for a clean and clear implementation
    //    JarGenerator.generateBc(new BcSoapGenerator(gmComponentDescription, new BcConfiguration(bcConfiguration)));
    WarGenerator warGenerator = new WarGenerator();
    //    warGenerator.addPackage(pack);
    //    System.out.println(VsbManager.class.getClassLoader().getResource("pom.xml").toExternalForm().substring(5));
    warGenerator.addPackage(eu.chorevolution.vsb.manager.VsbManager.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.java2wsdl.Java2WSDL.class.getPackage());
    //    warGenerator.addPackage(eu.chorevolution.vsb.bindingcomponent.generated.GeneratedFactory.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gmdl.tools.serviceparser.gidl.ParseGIDL.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gmdl.tools.serviceparser.gmdl.ParseGMDL.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gmdl.utils.Operation.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gmdl.utils.enums.OperationType.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gm.protocols.soap.BcSoapGenerator.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gm.protocols.rest.BcRestGenerator.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.bc.manager.BcManagerRestService.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.webappbcgenerator.StartBcManagerServlet.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.artifact.generators.WarGenerator.class.getPackage());
    warGenerator.addPackage(eu.chorevolution.vsb.gm.protocols.Manageable.class.getPackage());
    warGenerator.addDependencyFiles("/home/siddhartha/Downloads/chor/evolution-service-bus/vsb-manager/pom.xml");
    warGenerator.generate();
  }

  public static void generateBindingComponent(final String interfaceDescription, final ProtocolType busProtocol) {

    GmServiceRepresentation gmServiceRepresentation = null;

    BcConfiguration bcConfiguration = null;
    bcConfiguration = new BcConfiguration();
    // .substring(5) to remove the "file:" in front of file paths
    String configPath = Constants.configFilePath;

    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;

    try {
      jsonObject = (JSONObject) parser.parse(new FileReader(configPath));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    bcConfiguration.setGeneratedCodePath(Constants.generatedCodePath);
    bcConfiguration.setTargetNamespace(Constants.target_namespace);
    bcConfiguration.setServiceName(Constants.service_name);

    String extension = ""; 
    String[] interfaceDescPieces = interfaceDescription.split("\\.");
    extension = interfaceDescPieces[interfaceDescPieces.length-1];

    switch(extension) {
    case "gmdl":
      gmServiceRepresentation = ServiceDescriptionParser.getRepresentationFromGMDL(interfaceDescription);
    case "gidl":
      gmServiceRepresentation = ServiceDescriptionParser.getRepresentationFromGIDL(interfaceDescription);
    }

    if(busProtocol == ProtocolType.SOAP) {
      BcSoapGenerator soapGenerator = (BcSoapGenerator) new BcSoapGenerator(gmServiceRepresentation, bcConfiguration).setDebug(true); 
      // temporarily disabled
      soapGenerator.generateBc();

      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

      if(compiler == null) {
        System.setProperty("java.home", System.getenv("JAVA_HOME"));
        compiler = ToolProvider.getSystemJavaCompiler();
        if(compiler == null) {
          try {
            throw new Exception("Set JAVA_HOME env variable to point to JDK");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } 

      StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
      File sourceDir = new File("src" + File.separator + "main" + File.separator + "java" + File.separator +
          "eu" + File.separator + "chorevolution" + File.separator + "vsb" + File.separator 
          + "bindingcomponent" + File.separator + "generated");
      List<JavaFileObject> javaObjects = scanRecursivelyForJavaObjects(sourceDir, fileManager);

      if (javaObjects.size() == 0) {
        System.out.println("There are no source files to compile in " + sourceDir.getAbsolutePath());
      }

      File classDir = new File("src" + File.separator + "main" + File.separator + "java");
      String[] compileOptions = new String[]{"-d", classDir.getAbsolutePath()} ;
      Iterable<String> compilationOptions = Arrays.asList(compileOptions);

      CompilationTask compilerTask = compiler.getTask(null, fileManager, null, compilationOptions, null, javaObjects) ;

      if (!compilerTask.call()) {
        System.out.println("Could not compile project");
      }

      soapGenerator.generateWSDL();;
    }

    generateBCClass(gmServiceRepresentation, busProtocol);

  }

  private static List<JavaFileObject> scanRecursivelyForJavaObjects(File dir, StandardJavaFileManager fileManager) { 
    List<JavaFileObject> javaObjects = new LinkedList<JavaFileObject>(); 
    File[] files = dir.listFiles(); 
    for (File file : files) { 
      if (file.isDirectory()) { 
        javaObjects.addAll(scanRecursivelyForJavaObjects(file, fileManager)); 
      } 
      else if (file.isFile() && file.getName().toLowerCase().endsWith(".java")) { 
        javaObjects.add(readJavaObject(file, fileManager)); 
      } 
    } 
    return javaObjects; 
  } 


  private static JavaFileObject readJavaObject(File file, StandardJavaFileManager fileManager) { 
    Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(file); 
    Iterator<? extends JavaFileObject> it = javaFileObjects.iterator(); 
    if (it.hasNext()) { 
      return it.next(); 
    } 
    throw new RuntimeException("Could not load " + file.getAbsolutePath() + " java file object"); 
  } 

  public static void generateBCClass(GmServiceRepresentation gmServiceRepresentation, ProtocolType busProtocol) {

    String configTemplatePath = "";
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;

    configTemplatePath = Constants.configFilePath;

    try {
      jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    /* Creating java code model classes */
    JCodeModel jCodeModel = new JCodeModel();
    /* Adding package here */
    JPackage jp = jCodeModel._package((String) jsonObject.get("target_namespace"));

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

    //    JClass JSONParserClass = jCodeModel.ref(org.json.simple.parser.JSONParser.class);
    //    JVar JSONParserVar = jBlock.decl(JSONParserClass, "parser");
    //    JSONParserVar.init(JExpr._new(JSONParserClass));
    //
    //    JClass JSONObjectClass = jCodeModel.ref(org.json.simple.JSONObject.class);
    //    JVar JSONObjectVar = jBlock.decl(JSONObjectClass, "jsonObject");
    //    JSONObjectVar.init(JExpr._new(JSONObjectClass));


    JClass integerClass = jCodeModel.ref(java.lang.Integer.class);
    JClass StringClass = jCodeModel.ref(String.class);

    JVar intFiveVar = jBlock.decl(integerClass, "intFive");
    jBlock.assign(JExpr.ref(intFiveVar.name()),jCodeModel.ref("Integer").staticInvoke("parseInt").arg("5"));

    JVar intOneVar = jBlock.decl(integerClass, "intOne");
    jBlock.assign(JExpr.ref(intOneVar.name()),jCodeModel.ref("Integer").staticInvoke("parseInt").arg("1"));


    JClass BcManagerClass = null;
    BcManagerClass = jCodeModel.ref(eu.chorevolution.vsb.bc.manager.BcManagerRestService.class);
    //    JVar StringObjectVar = null;
    //    StringObjectVar = jBlock.decl(StringClass, "configFilePath", BcManagerClass.dotclass().invoke("getClassLoader").invoke("getResource").arg("config.json").invoke("toExternalForm").invoke("substring").arg(intFiveVar));
    //    JClass ExceptionClass = jCodeModel.ref(java.lang.Exception.class);
    //
    //    JTryBlock parseTryBlock = jBlock._try();
    //    JBlock parseBlock = parseTryBlock.body();
    //
    //    JClass FileReaderClass = jCodeModel.ref(java.io.FileReader.class);
    //    JVar FileReaderVar = parseBlock.decl(FileReaderClass, "fileReader");
    //    FileReaderVar.init(JExpr._new(FileReaderClass).arg(StringObjectVar));
    //
    //    JInvocation parserInvocation = JSONParserVar.invoke("parse");
    //    parserInvocation.arg(FileReaderVar);
    //
    //    parseBlock.assign(JExpr.ref(JSONObjectVar.name()),JExpr.cast(JSONObjectClass, parserInvocation));
    //
    //    JCatchBlock parseCatchBlock = parseTryBlock._catch(ExceptionClass);

    JClass GmServiceRepresentationClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation.class);
    JVar GmServiceRepresentationVar = jBlock.decl(GmServiceRepresentationClass, "gmServiceRepresentation", JExpr._null());

    JClass ConstantsClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.Constants.class);

    JVar interfaceDescriptionPathVar = null;
    interfaceDescriptionPathVar = jBlock.decl(StringClass, "interfaceDescFilePath");
    jBlock.assign(JExpr.ref(interfaceDescriptionPathVar.name()), JExpr.lit(Constants.intefaceDescriptionFilePath));


    JClass serviceDescriptionClass = jCodeModel.ref(ServiceDescriptionParser.class);

    String interfaceDescriptionPath = Constants.intefaceDescriptionFilePath;
    String extension = ""; 
    String[] interfaceDescPieces = interfaceDescriptionPath.split("\\.");
    extension = interfaceDescPieces[interfaceDescPieces.length-1];
    JInvocation getInterfaceRepresentation = null;
    switch(extension) {
    case "gmdl":
      getInterfaceRepresentation = serviceDescriptionClass.staticInvoke("getRepresentationFromGMDL").arg(interfaceDescriptionPathVar); 
    case "gidl":
      getInterfaceRepresentation = serviceDescriptionClass.staticInvoke("getRepresentationFromGIDL").arg(interfaceDescriptionPathVar); 
    }

    //    jBlock.add(getInterfaceRepresentation);
    jBlock.assign(GmServiceRepresentationVar, getInterfaceRepresentation);

    JForLoop forLoop = jBlock._for();
    JVar ivar = forLoop.init(jCodeModel.INT, "i", JExpr.lit(0));
    forLoop.test(ivar.lt( GmServiceRepresentationVar.invoke("getInterfaces").invoke("size") ));
    forLoop.update(ivar.assignPlus(JExpr.lit(1)));

    JBlock forBlock = forLoop.body();

    JClass InterfaceClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.Interface.class);
    JVar InterfaceVar = forBlock.decl(InterfaceClass, "inter", JExpr._null());

    forBlock.assign(JExpr.ref(InterfaceVar.name()), GmServiceRepresentationVar.invoke("getInterfaces").invoke("get").arg(ivar));

    JClass BcGmSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent.class);

    //    JClass ProtocolClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType.class);
    JClass RoleTypeClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.enums.RoleType.class);
    JClass BcRestSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent.class);
    JClass BcSoapSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent.class);
    JClass BcMQTTSubcomponentClass = jCodeModel.ref(eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTSubcomponent.class);
    JClass BcConfigurationClass = jCodeModel.ref(eu.chorevolution.vsb.gmdl.utils.BcConfiguration.class);

    JClass EnumClass = jCodeModel.ref("eu.chorevolution.vsb.gmdl.utils.enums.RoleType");

    JFieldRef RoleTypeServerEnum = null;
    JFieldRef RoleTypeClientEnum = null;

    RoleTypeServerEnum = EnumClass.staticRef("SERVER");
    RoleTypeClientEnum = EnumClass.staticRef("CLIENT");

    JVar RoleTypeClassVar = forBlock.decl(RoleTypeClass, "busRole");

    JConditional roleCondition = forBlock._if(InterfaceVar.invoke("getRole").eq(RoleTypeServerEnum));
    roleCondition._then().assign(JExpr.ref(RoleTypeClassVar.name()), RoleTypeClientEnum);
    roleCondition._else().assign(JExpr.ref(RoleTypeClassVar.name()), RoleTypeServerEnum);

    JVar bcConfig1Var = forBlock.decl(BcConfigurationClass, "bcConfiguration1", JExpr._new(BcConfigurationClass));
    JVar bcConfig2Var = forBlock.decl(BcConfigurationClass, "bcConfiguration2", JExpr._new(BcConfigurationClass));

    JInvocation setRole1 = bcConfig1Var.invoke("setSubcomponentRole").arg(InterfaceVar.invoke("getRole"));
    forBlock.add(setRole1);

    JInvocation setRole2 = bcConfig2Var.invoke("setSubcomponentRole").arg(RoleTypeClassVar);
    forBlock.add(setRole2);

    String packagePath = Constants.target_namespace;
    packagePath = packagePath.replace(".", File.separator);

    String generatedCodePath = Constants.generatedCodePath;
    generatedCodePath = generatedCodePath + File.separator;

    JInvocation parseInvocation1 = bcConfig1Var.invoke("parseFromJSON").arg(JExpr._new(StringClass).arg(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5) + File.separator + "config_block1_interface_").plus(jCodeModel.ref(java.lang.String.class).staticInvoke("valueOf").arg(ivar.plus(intOneVar))));
    forBlock.add(parseInvocation1);

    JInvocation parseInvocation2 = bcConfig2Var.invoke("parseFromJSON").arg(JExpr._new(StringClass).arg(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5) + File.separator + "config_block2_interface_").plus(jCodeModel.ref(java.lang.String.class).staticInvoke("valueOf").arg(ivar.plus(intOneVar))));
    forBlock.add(parseInvocation2);

    JVar BcGmSubcomponentVar1 = forBlock.decl(BcGmSubcomponentClass, "block1Component", null);
    JVar BcGmSubcomponentVar2 = forBlock.decl(BcGmSubcomponentClass, "block2Component", null);

    switch(busProtocol) {
    case REST:
      for(int i=1; i<=gmServiceRepresentation.getInterfaces().size(); i++)  
        createConfigFile(ProtocolType.REST, new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5)).getParent() + File.separator + "config_block1_interface_" + String.valueOf(i));
      BcGmSubcomponentVar1.init(JExpr._new(BcRestSubcomponentClass).arg(bcConfig1Var));
      break;
    case SOAP:
      for(int i=1; i<=gmServiceRepresentation.getInterfaces().size(); i++)  
        createConfigFile(ProtocolType.SOAP, new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5)).getParent() + File.separator + "config_block1_interface_" + String.valueOf(i));
      BcGmSubcomponentVar1.init(JExpr._new(BcSoapSubcomponentClass).arg(bcConfig1Var));
      break;
    case MQTT:
      for(int i=1; i<=gmServiceRepresentation.getInterfaces().size(); i++)  
        createConfigFile(ProtocolType.MQTT, BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5) + File.separator + "config_block1_interface_" + String.valueOf(i));
      BcGmSubcomponentVar1.init(JExpr._new(BcMQTTSubcomponentClass).arg(bcConfig1Var));
      break;
    }

    switch(gmServiceRepresentation.getProtocol()) {
    case REST:
      for(int i=1; i<=gmServiceRepresentation.getInterfaces().size(); i++)  
        createConfigFile(ProtocolType.REST, new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5)).getParent() + File.separator + "config_block2_interface_" + String.valueOf(i));
      BcGmSubcomponentVar2.init(JExpr._new(BcRestSubcomponentClass).arg(bcConfig2Var));
      break;
    case SOAP:
      for(int i=1; i<=gmServiceRepresentation.getInterfaces().size(); i++)  
        createConfigFile(ProtocolType.SOAP, BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5) + File.separator + "config_block2_interface_" + String.valueOf(i));
      BcGmSubcomponentVar2.init(JExpr._new(BcSoapSubcomponentClass).arg(bcConfig2Var));
      break;
    case MQTT:
      for(int i=1; i<=gmServiceRepresentation.getInterfaces().size(); i++)  
        createConfigFile(ProtocolType.MQTT, BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5) + File.separator + "config_block2_interface_" + String.valueOf(i));
      BcGmSubcomponentVar2.init(JExpr._new(BcMQTTSubcomponentClass).arg(bcConfig2Var));
      break;
    }

    forBlock.add(BcGmSubcomponentVar1.invoke("setNextComponent").arg(BcGmSubcomponentVar2));
    forBlock.add(BcGmSubcomponentVar2.invoke("setNextComponent").arg(BcGmSubcomponentVar1));

    forBlock.add(BcGmSubcomponentVar1.invoke("start"));
    forBlock.add(BcGmSubcomponentVar2.invoke("start"));

    try {
      jCodeModel.build(new File(generatedCodePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createConfigFile(ProtocolType protocol, String filename) {
    String configPath = BcManagerRestService.class.getClassLoader().getResource("config.json").toExternalForm().substring(5);
    JSONParser configParser = new JSONParser();
    JSONObject configJsonObject = null;

    String configTemplatePath = "";
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;

    switch(protocol) {
    case REST:
      configTemplatePath = BcManagerRestService.class.getClassLoader().getResource("rest-config.json").toExternalForm().substring(5);
      break;
    case SOAP:
      configTemplatePath = BcManagerRestService.class.getClassLoader().getResource("soap-config.json").toExternalForm().substring(5);
      break;
    case MQTT:
      configTemplatePath = BcManagerRestService.class.getClassLoader().getResource("mqtt-config.json").toExternalForm().substring(5);
      break;
    }

    try {
      configJsonObject = (JSONObject) configParser.parse(new FileReader(configPath));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    try {
      jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    if(protocol==ProtocolType.SOAP) {
      jsonObject.put("target_namespace", Constants.target_namespace);
      jsonObject.put("service_name", Constants.service_name);
    }

    // temporarily disabled
    try (FileWriter file = new FileWriter(filename)) {
      file.write(jsonObject.toJSONString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}