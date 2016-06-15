package eu.chorevolution.vsb.manager;

//import eu.chorevolution.vsb.bc.generators.JarGenerator;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;

public class VsbManager {
  public void generateBindingComponent(final String interfaceDescription, final Protocol choreographyProtocol) {
    GmServiceRepresentation gmComponentRepresentation = null;
    BcGmSubcomponent serverComponent = null;
    BcGmSubcomponent clientComponent = null;
    BcConfiguration bcConfiguration = null;
    
    gmComponentRepresentation = ServiceDescriptionParser.getRepresentationFromGMDL(interfaceDescription);
    bcConfiguration = new BcConfiguration();
    
    bcConfiguration.setServiceAddress(gmComponentRepresentation.getHostAddress());
    
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;
    try {
      jsonObject = (JSONObject) parser.parse(new FileReader("/home/siddhartha/Downloads/chor/evolution-service-bus/bc-manager/src/main/resources/config.json"));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
    bcConfiguration.setServiceName((String) jsonObject.get("service_name"));
    bcConfiguration.setTargetNamespace((String) jsonObject.get("target_namespace"));
 
    bcConfiguration.setSubcomponentRole("SERVER");
    
    switch(choreographyProtocol) {
    case REST:
      serverComponent = new BcRestSubcomponent(bcConfiguration); 
      break;
    case SOAP:
      serverComponent = new BcSoapSubcomponent(bcConfiguration); 
      break;
    case MQTT:
      serverComponent = new BcMQTTSubcomponent(bcConfiguration); 
      break;
    }
    
    bcConfiguration.setSubcomponentRole("CLIENT");
    
    switch(gmComponentRepresentation.getProtocol()) {
    case REST:
      clientComponent = new BcRestSubcomponent(bcConfiguration); 
      break;
    case SOAP:
      clientComponent = new BcSoapSubcomponent(bcConfiguration); 
      break;
    case MQTT:
      clientComponent = new BcMQTTSubcomponent(bcConfiguration); 
      break;
    }
    
    serverComponent.setNextComponent(clientComponent);
    clientComponent.setNextComponent(serverComponent);
    
    serverComponent.start();
    clientComponent.start();
    // TODO: instantiate the right generator based on the bcConfig
    // could use JAVA Service Provider Interface (SPI) for a clean and clear implementation
//    JarGenerator.generateBc(new BcSoapGenerator(gmComponentDescription, new BcConfiguration(bcConfiguration)));
  }
}
