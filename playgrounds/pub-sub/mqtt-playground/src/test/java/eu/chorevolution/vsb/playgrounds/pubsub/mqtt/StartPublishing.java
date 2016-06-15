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
    pub = new mqttPublisher("localhost",1883, "publisher");
    pub.publish("topic_name", "message");
  }
}
