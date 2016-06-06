package org.rest.bc;

import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTComponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestComponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

public class BCStarter {
  public static void main(String[] args) {
    BcConfiguration configuration = new BcConfiguration();
    configuration.setComponentRole("SERVER");
    configuration.setServiceAddress("https://maps.googleapis.com");
    configuration.setServiceName("BindingComponent");
    configuration.setTargetNamespace("eu.chorevolution.vsb.bcs.dtsgoogle.bc");
    BcMQTTComponent bc = new BcMQTTComponent(configuration);
    BcRestComponent rc = new BcRestComponent(configuration);
    rc.start();
    bc.setNextComponent(rc);
    bc.start();
  }
}
