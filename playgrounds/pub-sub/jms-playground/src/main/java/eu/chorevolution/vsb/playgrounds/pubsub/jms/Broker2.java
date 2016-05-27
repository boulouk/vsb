/**
 * Broker.java
 * Created on: 29 févr. 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms;

import org.apache.activemq.broker.*;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class Broker2 {
	private BrokerService broker;
	
	public Broker2() {
		this.broker = new BrokerService();
	}
	
	public void start() {
		try {
			broker.addConnector("tcp://localhost:61626");
			broker.setBrokerName("mango");
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
