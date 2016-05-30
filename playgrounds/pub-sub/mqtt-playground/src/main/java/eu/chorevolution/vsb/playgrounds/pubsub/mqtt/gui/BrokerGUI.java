package eu.chorevolution.vsb.playgrounds.pubsub.mqtt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.Broker;
import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.mqttPublisher;
import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.gui.mqttPublisherGUI.Gui;

public class BrokerGUI {
  static mqttPublisher pub = null;
  
  Broker broker = null;
  

  class Gui extends JPanel {
    public Gui() {

      JButton startbtn = new JButton("Start");
      JButton stopbtn = new JButton("Stop");
      
      startbtn.setBounds(30, 10, 80, 30);
      stopbtn.setBounds(170, 10, 80, 30);
      
      add(startbtn);
      add(stopbtn);
      
      final JLabel text = new JLabel("Binding Component has started");
      
      text.setVisible(false);
      text.setBounds(26, 30, 300, 80);
      
      add(text);
      
      startbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          broker.start();
          text.setText("Binding Component has started");
          text.setVisible(true);
        }
      });
      
      stopbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          broker.stop();
          text.setText("Binding Component has stopped");
          text.setVisible(true);
        }
      });
      
    }
  }

  public BrokerGUI() {
    
    broker = new Broker();

    JFrame frame1 = new JFrame("MQTT Broker");
    frame1.getContentPane().add(new Gui());

    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(310, 70);
    frame1.setVisible(true);
    
  }
  
  public BrokerGUI(String ip, int port) {
    
    broker = new Broker(ip, port);

    JFrame frame1 = new JFrame("MQTT Broker");
    frame1.getContentPane().add(new Gui());

    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(310, 130);
    frame1.setVisible(true);
    
  }

}
