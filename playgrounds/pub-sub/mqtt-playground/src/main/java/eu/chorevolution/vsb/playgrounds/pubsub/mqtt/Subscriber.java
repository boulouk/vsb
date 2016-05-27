/**
 * Subscriber.java
 * Created on: 29 févr. 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

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
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class Subscriber {

	private String clientId;
		
	public void create(String clientId, String topicName) throws JMSException {
		this.clientId = clientId;

	  MqttClient client = null;
	  try {
	    client = new MqttClient("tcp://localhost:1883", clientId);
	    client.setCallback(new SubscriberCallback());
	    MqttConnectOptions options = new MqttConnectOptions();
	    options.setCleanSession(false);
	    client.connect(options);
	  } catch (MqttException e) {
	    e.printStackTrace();
	  }
	  try {
	    client.subscribe(topicName);
	  } catch (MqttException e) {
	    e.printStackTrace();
	  }
	}

	 private final class SubscriberCallback implements MqttCallback {
	    
	    public SubscriberCallback() {
	      super();
	    }

	    @Override
	    public void messageArrived(String topic, MqttMessage msg) throws Exception {
	    }

	    @Override
	    public void connectionLost(Throwable arg0) {
	    }

	    @Override
	    public void deliveryComplete(IMqttDeliveryToken arg0) {
	      
	    }
	  }

}
