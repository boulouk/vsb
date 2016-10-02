package eu.chorevolution.vsb.playgrounds.pubsub.mqtt.gui;

import org.junit.Test;

public class StartSubscriberGUI {
	@Test
	public void startSubscriberGUI() {
		SubscriberGUI sub = new SubscriberGUI("localhost",1883, "subscriber");
	}
	public static void main(String[] args) {
		SubscriberGUI sub = new SubscriberGUI("localhost",1883, "subscriber");

	}
}
