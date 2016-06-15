package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Broker;

public class StartBroker {
  @Test
  public void startBroker() {
		Broker broker = new Broker("localhost", 61616, "broker");
		broker.start();
	}

}
