package eu.chorevolution.vsb.gm.protocols.mqtt;

import java.util.Random;

import org.apache.activemq.broker.BrokerService;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

public class MQTTPublisher {

  private static BlockingConnection connection = null;

  public static void startPublisher() throws Exception {
    MQTT client = new MQTT();
    client.setHost("tcp://localhost:1883");
    client.setClientId("MqttBrokerPublisher");
    connection = client.blockingConnection();

    Runtime.getRuntime().addShutdownHook(new Thread(){
      @Override
      public void run() {
        try {
          connection.disconnect();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    connection.connect();
  }

  public static void publish() throws Exception {
    String topic = "apple";
    Random rand = new Random();

    while(true) {
      int temp = rand.nextInt(100);
      int hum = rand.nextInt(100);
      String payload = temp + "/" + hum;

      connection.publish(topic, payload.getBytes(), QoS.AT_LEAST_ONCE, false);
      Thread.sleep(500);
    }
  }

  public static void main(String[] args) throws Exception{
    startPublisher();
    publish();
  }
}
