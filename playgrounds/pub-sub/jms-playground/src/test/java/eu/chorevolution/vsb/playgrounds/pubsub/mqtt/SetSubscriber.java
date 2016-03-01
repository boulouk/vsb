/**
 * SetSubscriber.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import javax.jms.JMSException;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class SetSubscriber {
	
	public static void main(String[] args) throws JMSException {
		DurableSubscriber subscriber = new DurableSubscriber();
		
		subscriber.create("subscriber-durablesubscriber", "durablesubscriber.t", "durablesubscriber1");
		
		//For sync interaction
//		subscriber.getNext(5000);
//		subscriber.closeConnection();
		
	}

}
