package eu.chorevolution.vsb.playgrounds.pubsub.mqtt.gui;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.mqttPublisher;

public class StartMqttPublisherGUI {
  @Test
  public void startMqttPublisherGUI() {
    mqttPublisherGUI pub = null;
    pub = new mqttPublisherGUI("localhost",1883, "publisher");

  }
}
