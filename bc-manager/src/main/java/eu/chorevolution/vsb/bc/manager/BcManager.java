package eu.chorevolution.vsb.bc.manager;

import eu.chorevolution.vsb.gm.protocols.Manageable;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmComponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestComponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapComponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

public class BcManager implements Manageable {
  private final BcConfiguration configuration;
  private BcGmComponent endpointApi;
  private BcGmComponent clientApi;
  
  public BcManager(BcConfiguration configuration) {
    this.configuration = configuration;
    // start the Manager REST interface
  }
  
  //@GET
  public void start() {    
    // TODO instantiate each with reflection according to configuration
    //server endpoint for connectorA
    this.endpointApi = new BcSoapComponent(this.configuration);
    //connectorB 
    this.clientApi = new BcRestComponent(this.configuration);
    
    
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
    this.configuration.setComponentAddress(endpointAddress);    
  }
}