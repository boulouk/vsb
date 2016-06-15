package eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.test;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.gui.PublisherGUI;

public class StartPublisherGUI {
  @Test
  public void startPublisherGUI() {
    PublisherGUI pub = null;
    pub = new PublisherGUI("localhost",61616, "publisher");

  }
}
