/**
 * PublishMessages.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import javax.jms.JMSException;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class PublishMessages {

	public static void main(String[] args) throws JMSException {
		Publisher pub = new Publisher();

		pub.create("publisher-durablesubscriber", "durablesubscriber.t");

		String forSent = "blaa";
		pub.send(forSent);

	}

}
