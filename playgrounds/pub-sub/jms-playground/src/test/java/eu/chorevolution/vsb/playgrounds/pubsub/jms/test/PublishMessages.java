/**
 * PublishMessages.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import javax.jms.JMSException;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Publisher;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class PublishMessages {

	public static void main(String[] args) throws JMSException {
		Publisher pub = new Publisher();

		pub.create("publisher-durablesubscriber", "durablesubscriber.t", "localhost", 61616);

		String forSent = "blaa";
		//while(true) {
			pub.send(forSent);
		//}
	}

}
