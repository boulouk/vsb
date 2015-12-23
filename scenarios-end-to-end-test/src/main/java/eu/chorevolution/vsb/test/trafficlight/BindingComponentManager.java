package eu.chorevolution.vsb.test.trafficlight;

import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

public class BindingComponentManager {
  public static void main(String[] args) {
    
    // Should parse the local config file and instantiate the
    BcConfiguration configuration = new BcConfiguration(/*path/to/local/config/file*/);
    
    // test purpose: should be extract when parsing the config file
    configuration.setComponentRole("SERVER");
    configuration.setServiceAddress("http://localhost:8282");
    configuration.setServiceName("BindingComponent");
    configuration.setTargetNamespace("eu.chorevolution.vsb.test.trafficlight.bc");
    // END test purpose
    
    BcManager manager = new BcManager(configuration);
     
    manager.setEndpointAddress("http://localhost:8888/BindingComponent"); // should be called remotely via Manager REST interface
    manager.start();
    
    /*try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    manager.stop();*/
  }
}