/**
 * StartBroker.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Broker2;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class StartBroker2 {
	
	public static void main(String[] args) {
		Broker2 broker = new Broker2();
		broker.start();
		
		
	}

}
