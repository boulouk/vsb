/**
 * Broker.java
 * Created on: 29 févr. 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import org.apache.activemq.broker.*;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class Broker {
	private BrokerService broker;
	
	public Broker() {
		this.broker = new BrokerService();
	}
	
	public void start() {
		try {
			broker.addConnector("tcp://localhost:61616");
			broker.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
