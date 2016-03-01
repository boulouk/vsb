/**
 * StartBroker.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class StartBroker {
	
	public static void main(String[] args) {
		Broker broker = new Broker();
		broker.start();
		
		
	}

}
