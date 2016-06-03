package eu.chorevolution.vsb.playgrounds.pubsub.jms.test;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Broker;

public class StartBroker {
	
	public static void main(String[] args) {
		Broker broker = new Broker("localhost", 61616, "broker");
		broker.start();
	}

}
