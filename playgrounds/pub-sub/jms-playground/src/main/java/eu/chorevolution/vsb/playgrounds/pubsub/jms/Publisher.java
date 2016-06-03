/**
 * Publisher.java
 * Created on: 1 mars 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

	private String clientId;
	private Connection connection;
	private Session session;
	private MessageProducer messageProducer;

	public void create(String clientId, String topicName, String ip, int port) throws JMSException {
		this.clientId = clientId;

		// create a Connection Factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+ip+":"+port);

		// create a Connection
		connection = connectionFactory.createConnection();
		connection.setClientID(clientId);

		// create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// create the Topic to which messages will be sent
		Topic topic = session.createTopic(topicName);

		// create a MessageProducer for sending messages
		messageProducer = session.createProducer(topic);
	}

	public void closeConnection() throws JMSException {
		connection.close();
	}

	public void send(String message) throws JMSException {

		// create a JMS TextMessage
		TextMessage textMessage = session.createTextMessage(message);

		// send the message to the topic destination
		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());
		messageProducer.send(textMessage);
		
		// LOGGER.debug(clientId + ": sent message with text='{}'", message);
	}

}
