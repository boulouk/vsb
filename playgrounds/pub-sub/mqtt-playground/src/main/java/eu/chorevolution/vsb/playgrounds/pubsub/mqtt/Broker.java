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
  
  private String ip;
  private int port;
  
  public Broker() {
    this.broker = new BrokerService();
    this.ip = "localhost";
    this.port = 1883;
  }
  
  public Broker(String ip, int port) {
    this.broker = new BrokerService();
    this.ip = ip;
    this.port = port;
  }
  
  public void start() {
    try {
      broker.addConnector("mqtt://"+ ip + ":" + port);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    broker.setDataDirectory("target2");
    try {
      broker.start();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    Runtime.getRuntime().addShutdownHook(new Thread(){
		@Override
		public void run() {
			try {
				broker.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
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
