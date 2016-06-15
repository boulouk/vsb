package eu.chorevolution.vsb.bc.manager;

import eu.chorevolution.vsb.gm.protocols.Manageable;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

public class BcManager implements Manageable {
  private final BcConfiguration configuration;
  private BcGmSubcomponent endpointApi;
  private BcGmSubcomponent clientApi;
  
  public BcManager(BcConfiguration configuration) {
    this.configuration = configuration;
    // start the Manager REST interface
  }
  
  //@GET
  public void start() {    
    // TODO instantiate each with reflection according to configuration
    //server endpoint for connectorA
    this.endpointApi = new BcSoapSubcomponent(this.configuration);
    //connectorB 
    this.clientApi = new BcRestSubcomponent(this.configuration);
    
    
    //set the reference other protocol connector
    this.endpointApi.setNextComponent(clientApi);
    this.clientApi.setNextComponent(endpointApi);
    
    this.endpointApi.start();
    this.clientApi.start();
  }
  
  //@GET
  public void stop() {
    this.endpointApi.stop();
    this.clientApi.stop();
  }

  //@POST
  public void setEndpointAddress(String endpointAddress) {
    this.configuration.setSubcomponentAddress(endpointAddress);    
  }
}