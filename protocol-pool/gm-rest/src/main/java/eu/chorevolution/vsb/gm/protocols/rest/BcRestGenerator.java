package eu.chorevolution.vsb.gm.protocols.rest;

import eu.chorevolution.vsb.gm.protocols.generators.BcComponentGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;

public class BcRestGenerator extends BcComponentGenerator {

  public BcRestGenerator(GmComponentRepresentation componentDescription, BcConfiguration bcConfiguration) {
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