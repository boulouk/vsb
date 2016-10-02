package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import org.junit.Test;

public class StartBroker {
	@Test
	public void startBrokerGUI() {

		Broker broker = new Broker();
		broker.start();
	}
	public static void main(String[] args) {
		Broker broker = new Broker();
		broker.start();
	}
}
