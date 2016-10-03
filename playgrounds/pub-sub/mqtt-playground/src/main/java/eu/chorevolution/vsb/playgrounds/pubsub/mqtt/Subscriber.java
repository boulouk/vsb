/**
 * Subscriber.java
 * Created on: 29 févr. 2016
 */
package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber {

  public String clientId;
  private String ip;
  private int port;
  public Queue<Message> msgQueue;

  public Subscriber() {
    this.ip = "localhost";
    this.port = 1883;
    this.clientId = "subscriber";
    msgQueue = new ConcurrentLinkedQueue<Message>();
  }

  public Subscriber(String ip, int port, String clientId) {
    this.ip = ip;
    this.port = port;
    this.clientId = clientId;
    msgQueue = new ConcurrentLinkedQueue<Message>();
  }

  public void create(String topicName) throws JMSException {
    MqttClient client = null;
    try {
      client = new MqttClient("tcp://"+ip+":"+port, clientId);
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
      System.out.println("sending " + topic + ":" + msg.toString());
      synchronized(msgQueue) {
    	  System.out.println("added message to queue");
        msgQueue.add(new Message(topic, msg.toString()));
        msgQueue.notify();
      }
    }

    @Override
    public void connectionLost(Throwable arg0) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {

    }
  }
}
