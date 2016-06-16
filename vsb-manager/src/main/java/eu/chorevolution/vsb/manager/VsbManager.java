package eu.chorevolution.vsb.manager;

//import eu.chorevolution.vsb.bc.generators.JarGenerator;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;

    String configPath = BcManager.class.getClassLoader().getResource("config.json").toExternalForm();

    try {
      jsonObject = (JSONObject) parser.parse(new FileReader(configPath));//"/home/siddhartha/Downloads/chor/evolution-service-bus/bc-manager/src/main/resources/config.json"));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
   
    for(Interface inter: gmComponentRepresentation.getInterfaces()) {
     
      BcGmSubcomponent block1Component = null;
      BcGmSubcomponent block2Component = null;
      
      RoleType busRole = null;
      if(inter.getRole() == RoleType.SERVER) {
        busRole = RoleType.CLIENT;
      }
      else if(inter.getRole() == RoleType.CLIENT) {
        busRole = RoleType.SERVER;
      }
      
      bcConfiguration = new BcConfiguration();
      bcConfiguration.setSubcomponentRole(inter.getRole().toString());
      bcConfiguration.setServiceAddress(gmComponentRepresentation.getHostAddress());
      bcConfiguration.setServiceName((String) jsonObject.get("service_name"));
      bcConfiguration.setTargetNamespace((String) jsonObject.get("target_namespace"));
      
      switch(busProtocol) {
      case REST:
        block1Component = new BcRestSubcomponent(bcConfiguration); 
        break;
      case SOAP:
        block1Component = new BcSoapSubcomponent(bcConfiguration); 
        break;
      case MQTT:
        block1Component = new BcMQTTSubcomponent(bcConfiguration); 
        break;
      }

      bcConfiguration = new BcConfiguration();
      bcConfiguration.setSubcomponentRole(busRole.toString());
      bcConfiguration.setServiceAddress(gmComponentRepresentation.getHostAddress());
      bcConfiguration.setServiceName((String) jsonObject.get("service_name"));
      bcConfiguration.setTargetNamespace((String) jsonObject.get("target_namespace"));
      
      switch(gmComponentRepresentation.getProtocol()) {
      case REST:
        block2Component = new BcRestSubcomponent(bcConfiguration); 
        break;
      case SOAP:
        block2Component = new BcSoapSubcomponent(bcConfiguration); 
        break;
      case MQTT:
        block2Component = new BcMQTTSubcomponent(bcConfiguration); 
        break;
      }

      block1Component.setNextComponent(block2Component);
      block2Component.setNextComponent(block1Component);
      
      block1Component.start();
      block2Component.start();
      
    }


    // TODO: instantiate the right generator based on the bcConfig
    // could use JAVA Service Provider Interface (SPI) for a clean and clear implementation
    //    JarGenerator.generateBc(new BcSoapGenerator(gmComponentDescription, new BcConfiguration(bcConfiguration)));
  }
}
