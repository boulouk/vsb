/**
 * DurableSubscriber.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 */
public class DurableSubscriber {

	public String clientId;
	private Connection connection;
	private Session session;
	private MessageConsumer messageConsumer;
	public Queue<String> msgQueue;

	private String subscriptionName;

	public void create(String clientId, String topicName, String subscriptionName, String ip, int port) throws JMSException {
		this.clientId = clientId;
		this.subscriptionName = subscriptionName;
		
		msgQueue = new ConcurrentLinkedQueue<String>();
		
		// create a Connection Factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+ip+":"+port);

		// create a Connection
		connection = connectionFactory.createConnection();
		connection.setClientID(clientId);

		// create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// create the Topic from which messages will be received
		Topic topic = session.createTopic(topicName);

		// create a MessageConsumer for receiving messages
		messageConsumer = session.createDurableSubscriber(topic, subscriptionName);

		// for async messages:
		JMSMessageListener listener = new JMSMessageListener(msgQueue);
		messageConsumer.setMessageListener(listener);

		// start the connection in order to receive messages
		connection.start();
	}

	public void removeDurableSubscriber() throws JMSException {
		messageConsumer.close();
		session.unsubscribe(subscriptionName);
	}

	public void closeConnection() throws JMSException {
		connection.close();
	}

	public void getNext(int timeout) throws JMSException {

		// read a message from the topic destination
		Message msg = messageConsumer.receive(timeout);

		// check if a message was received
		if (msg != null) {
			try {
				TextMessage textMessage = (TextMessage) msg;
				String message = textMessage.getText();

				// retrieve the message content
				System.out.println("text reseived:" + message);
				;
			} catch (JMSException ex) {
				java.util.logging.Logger.getLogger(JMSMessageListener.class.getName()).log(Level.SEVERE, null, ex);
			}

		} else {
			System.out.println(clientId + ": no message received");
			// LOGGER.debug(clientId + ": no message received");
		}

	}


}

// JMS Message Listener
class JMSMessageListener implements MessageListener {
	
	Queue<String> msgQueue;
	
	public JMSMessageListener(Queue<String> msgQueue) {
		this.msgQueue = msgQueue;
	}

	@Override
	public void onMessage(javax.jms.Message msg) {
		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());
		try {
			TextMessage textMessage = (TextMessage) msg;

			// retrieve the message content
			String message = textMessage.getText();

			 synchronized(msgQueue) {
			        msgQueue.add(message);
			        msgQueue.notify();
			      }

			// System.out.println("FUCK: "+msg.toString());
		} catch (JMSException ex) {
			java.util.logging.Logger.getLogger(JMSMessageListener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}