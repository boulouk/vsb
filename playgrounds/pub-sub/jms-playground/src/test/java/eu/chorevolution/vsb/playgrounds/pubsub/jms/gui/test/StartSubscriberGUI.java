package eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.test;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.SubscriberGUI;

public class StartSubscriberGUI {
  @Test
  public void startSubscriberGUI() {
    SubscriberGUI sub = new SubscriberGUI("localhost",61616, "subscriber");
  }
}
