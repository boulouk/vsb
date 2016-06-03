package eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.PublisherGUI;

public class startPublisherGUI {
  public static void main(String[] args) {
    PublisherGUI pub = null;
    pub = new PublisherGUI("localhost",61616, "publisher");

  }
}
