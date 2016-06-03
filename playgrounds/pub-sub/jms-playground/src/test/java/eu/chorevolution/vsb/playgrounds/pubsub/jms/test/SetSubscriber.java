/**
 * SetSubscriber.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import javax.jms.JMSException;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.DurableSubscriber;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class SetSubscriber {
	
	public static void main(String[] args) throws JMSException {
		DurableSubscriber subscriber = new DurableSubscriber();
		
		subscriber.create("subscriber-durablesubscriber", "durablesubscriber.t", "durablesubscriber1", "localhost", 61616);
		
		//For sync interaction
//		subscriber.getNext(5000);
//		subscriber.closeConnection();
		
	}

}
