package eu.chorevolution.vsb.playgrounds.pubsub.mqtt.gui;

public class StartSubscriberGUI {
  public static void main(String[] args) {
    SubscriberGUI sub = new SubscriberGUI("localhost",1883, "subscriber");
  }
}
