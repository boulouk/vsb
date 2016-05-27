/**
 * StartBroker.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Broker1;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class StartBroker1 {
	
	public static void main(String[] args) {
		Broker1 broker = new Broker1();
		broker.start();
		
		
	}

}
