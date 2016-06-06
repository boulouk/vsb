package eu.chorevolution.vsb.manager;

//import eu.chorevolution.vsb.bc.generators.JarGenerator;
import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTComponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmComponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestComponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapComponent;
import eu.chorevolution.vsb.gmdl.tools.parser.ComponentDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;
import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;

public class VsbManager {
  public void generateBindingComponent(final String gmdlDescription, final Protocol choreographyProtocol) {
    GmComponentRepresentation gmComponentRepresentation = null;
    BcGmComponent serverComponent = null;
    BcGmComponent clientComponent = null;
    BcConfiguration bcConfiguration = null;
    
    gmComponentRepresentation = ComponentDescriptionParser.getReprensentationFromGMDL(gmdlDescription);
    bcConfiguration = new BcConfiguration();
    
    bcConfiguration.setServiceAddress("https://maps.googleapis.com");
    bcConfiguration.setServiceName("BindingComponent");
    bcConfiguration.setTargetNamespace("eu.chorevolution.vsb.bcs.dtsgoogle.bc");
 
    bcConfiguration.setComponentRole("SERVER");
    
    switch(choreographyProtocol) {
    case REST:
      serverComponent = new BcRestComponent(bcConfiguration); 
      break;
    case SOAP:
      serverComponent = new BcSoapComponent(bcConfiguration); 
      break;
    case MQTT:
      serverComponent = new BcMQTTComponent(bcConfiguration); 
      break;
    }
    
    bcConfiguration.setComponentRole("CLIENT");
    
    switch(gmComponentRepresentation.getProtocol()) {
    case REST:
      clientComponent = new BcRestComponent(bcConfiguration); 
      break;
    case SOAP:
      clientComponent = new BcSoapComponent(bcConfiguration); 
      break;
    case MQTT:
      clientComponent = new BcMQTTComponent(bcConfiguration); 
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
