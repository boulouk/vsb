/**
 * PublishMessages.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import javax.jms.JMSException;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Publisher;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class PublishMessages {
  @Test
  public void startBrokerGUI() {
		Publisher pub = new Publisher();

		try {
      pub.create("publisher-durablesubscriber", "durablesubscriber.t", "localhost", 61616);
    } catch (JMSException e) {
      e.printStackTrace();
    }

		String forSent = "blaa";
		//while(true) {
			try {
        pub.send(forSent);
      } catch (JMSException e) {
        e.printStackTrace();
      }
		//}
	}

}
