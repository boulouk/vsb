/**
 * Broker.java
 * Created on: 29 févr. 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;


import org.apache.activemq.broker.*;
import org.fusesource.mqtt.client.BlockingConnection;

public class Broker {
  
  private BrokerService broker;
  private BlockingConnection connection;  
  
  public Broker() {
    this.broker = new BrokerService();
  }
  
  public void start() {
    try {
      broker.addConnector("mqtt://localhost:1883");
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    broker.setDataDirectory("target");
    try {
      broker.start();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
  
  public void stop() {
    try {
      broker.stop();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
