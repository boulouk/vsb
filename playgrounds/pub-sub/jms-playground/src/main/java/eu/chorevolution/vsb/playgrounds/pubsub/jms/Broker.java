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
public class Broker {
	private BrokerService broker;
	
	public Broker(String ip, int port, String name) {
		this.broker = new BrokerService();
		try {
			broker.addConnector("tcp://"+ip+":"+port);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			broker.addNetworkConnector("static:(tcp://localhost:61626)");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		broker.setBrokerName(name);
	}
	
	public void start() {
		try {
			broker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			broker.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
