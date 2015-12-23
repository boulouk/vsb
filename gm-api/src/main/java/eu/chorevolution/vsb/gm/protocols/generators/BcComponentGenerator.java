package eu.chorevolution.vsb.gm.protocols.generators;

import java.util.Collection;

import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;

public abstract class BcComponentGenerator {
  protected final BcConfiguration bcConfiguration;
  protected final GmComponentRepresentation componentDescription;
  protected boolean debug = false;
  
  public BcComponentGenerator(GmComponentRepresentation componentDescription, BcConfiguration bcConfiguration) {
    this.bcConfiguration = bcConfiguration;
    this.componentDescription = componentDescription;
  }
  
  public void generateBc() {
    this.generatePojos();
    this.generateEndpoint();
  }
  
  protected void generatePojos() {
    Collection<Data<?>> definitions = this.componentDescription.getTypeDefinitions();
    for (Data<?> definition : definitions) {
      this.generatePojo(definition);
    }
  }
  
  public BcComponentGenerator setDebug(boolean debug) {
    this.debug = debug;
    return this;
  }
  
  protected abstract void generatePojo(Data<?> definition);
  protected abstract void generateEndpoint();
}