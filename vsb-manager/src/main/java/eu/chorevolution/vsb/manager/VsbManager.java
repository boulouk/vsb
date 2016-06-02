package eu.chorevolution.vsb.manager;

//import eu.chorevolution.vsb.bc.generators.JarGenerator;
import eu.chorevolution.vsb.gmdl.tools.parser.ComponentDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;

public class VsbManager {
  public void generateBindingComponent(final String gmdlDescription, final String choreographyProtocol, final String bcConfiguration) {
    GmComponentRepresentation gmComponentDescription = ComponentDescriptionParser.getReprensentationFromGMDL(gmdlDescription);
    
    // TODO: instantiate the right generator based on the bcConfig
    // could use JAVA Service Provider Interface (SPI) for a clean and clear implementation
//    JarGenerator.generateBc(new BcSoapGenerator(gmComponentDescription, new BcConfiguration(bcConfiguration)));
  }
}
