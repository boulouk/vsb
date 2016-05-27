package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class mqttPublisher {
  private MqttClient client;

  public mqttPublisher(String brokerURI) {
    try {
      this.client = new MqttClient(brokerURI, UUID.randomUUID().toString().substring(0, 23), new MqttDefaultFilePersistence());
      this.client.setCallback(new PublisherCallback(this));
      this.client.connect();
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  public mqttPublisher(String ip, int port, String cliendID) {
    this("tcp://"+ip+":"+port, cliendID);
  }

  public mqttPublisher(String brokerURI, String clientID) {
    try {
      this.client = new MqttClient(brokerURI, clientID);
      this.client.setCallback(new mqttPublisher.PublisherCallback(this));
      MqttConnectOptions options = new MqttConnectOptions();
      options.setCleanSession(false);
      this.client.connect(options);

    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {
    try {
      this.client.disconnect();
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  public void publish(String topic, String message) {
    try {
      long sentTimestamp = System.nanoTime();
      message = message; 
      this.client.publish(topic, message.getBytes(), 0, true);

    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  protected void onDeliveryComplete(IMqttDeliveryToken token) {
    //System.out.println("Message delivered");

  }

  protected void onLostConnection(Throwable t) {
    System.out.println("Connection lost!");
    //t.printStackTrace();
  }

  private final class PublisherCallback implements MqttCallback {
    private final mqttPublisher pub;

    public PublisherCallback(mqttPublisher pub) {
      super();
      this.pub = pub;
    }

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {}

    @Override
    public void connectionLost(Throwable arg0) {
      this.pub.onLostConnection(arg0);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
      this.pub.onDeliveryComplete(arg0);
    }
  }
}