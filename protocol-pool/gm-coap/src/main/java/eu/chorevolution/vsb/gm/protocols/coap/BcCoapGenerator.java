package eu.chorevolution.vsb.gm.protocols.coap;

import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;

public class BcCoapGenerator extends BcSubcomponentGenerator {

  public BcCoapGenerator(GmServiceRepresentation componentDescription, BcConfiguration bcConfiguration) {
    super(componentDescription, bcConfiguration);
  }

  @Override
  public void generateEndpoint() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void generatePojo(Data<?> definition) {
    // TODO Auto-generated method stub 
  }
}