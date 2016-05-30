package eu.chorevolution.vsb.playgrounds.pubsub.mqtt.gui;

import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.mqttPublisher;

public class startMqttPublisherGUI {
  public static void main(String[] args) {
    mqttPublisherGUI pub = null;
    pub = new mqttPublisherGUI("localhost",1883, "publisher");

  }
}
