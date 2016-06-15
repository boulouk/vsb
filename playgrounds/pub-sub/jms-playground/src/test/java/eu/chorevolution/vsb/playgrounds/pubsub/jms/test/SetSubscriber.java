/**
 * SetSubscriber.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import javax.jms.JMSException;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.DurableSubscriber;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class SetSubscriber {
  @Test
  public void setSubscriber() {
		DurableSubscriber subscriber = new DurableSubscriber();
		
		try {
      subscriber.create("subscriber-durablesubscriber", "durablesubscriber.t", "durablesubscriber1", "localhost", 61616);
    } catch (JMSException e) {
      e.printStackTrace();
    }
		
		//For sync interaction
//		subscriber.getNext(5000);
//		subscriber.closeConnection();
		
	}

}
