package eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.SubscriberGUI;

public class StartSubscriberGUI {
  public static void main(String[] args) {
    SubscriberGUI sub = new SubscriberGUI("localhost",61616, "subscriber");
  }
}
