package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.mqttPublisher;

public class StartPublishing {
	@Test
	public void startPublishing() {
		mqttPublisher pub = null;
		pub = new mqttPublisher("localhost",1882, "publisher");
		pub.publish("operation_1", "{\"origin\":\"17.4781737,78.2975116,15.51z\",\"destination\":\"17.470432,78.3102378,15z\",\"key\": \"AIzaSyBhfNR1PHo8EsuxjLr3EO-sNnfoUDh4ilw\"}");
	}
	public static void main(String[] args) {
		mqttPublisher pub = null;
		pub = new mqttPublisher("localhost",1883, "publisher4");
		pub.publish("operation_1", "{\"origin\":\"17.4781747,78.2975116,15.51z\",\"destination\":\"17.470432,78.3102378,15z\",\"key\": \"AIzaSyBhfNR1PHo8EsuxjLr3EO-sNnfoUDh4ilw\"}");
//		pub.publish("operation_1", "{\"id\":\"fdfd\"}");
	}
}
