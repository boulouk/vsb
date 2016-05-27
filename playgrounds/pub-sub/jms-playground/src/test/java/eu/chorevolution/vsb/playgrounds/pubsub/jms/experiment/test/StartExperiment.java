/**
 * StartExperiment.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.experiment.test;

import javax.jms.JMSException;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Broker1;
import eu.chorevolution.vsb.playgrounds.pubsub.jms.Broker2;
import eu.chorevolution.vsb.playgrounds.pubsub.jms.DurableSubscriber;
import eu.chorevolution.vsb.playgrounds.pubsub.jms.Publisher;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class StartExperiment {
	public static void main(String[] args) {

//		Broker2 broker2 = new Broker2();
//		broker2.start();
//		Broker1 broker1 = new Broker1();
//		broker1.start();
//
		DurableSubscriber subscriber = new DurableSubscriber();

		try {
			subscriber.create("subscriber-durablesubscriber", "durablesubscriber.t", "durablesubscriber1");
		} catch (JMSException e) {
			e.printStackTrace();
		}

		Publisher pub = new Publisher();

		try {
			pub.create("publisher-durablesubscriber", "durablesubscriber.t");
		} catch (JMSException e) {
			e.printStackTrace();
		}

		String forSent = "blaa";
		
		try {
			pub.send(forSent);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
