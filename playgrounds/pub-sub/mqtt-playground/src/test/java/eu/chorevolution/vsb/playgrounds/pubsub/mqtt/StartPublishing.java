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

import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.mqttPublisher;


public class StartPublishing extends JPanel {

  static mqttPublisher pub = null;

  
  public StartPublishing() {

    
    
    final JLabel topicLabel = new JLabel("Topic:");
    topicLabel.setBounds(3, 5, 50, 30);
    
    final JTextField txt = new JTextField("");
    txt.setBounds(55, 5, 250, 30);
    
    final JLabel idLabel = new JLabel("Id:");
    idLabel.setBounds(3, 44, 50, 30);

    final JTextField txt2 = new JTextField("12");
    txt2.setBounds(55, 44, 250, 30);
    txt2.setEditable(false);
    
    final JLabel valueLabel = new JLabel("Value:");
    valueLabel.setBounds(3, 83, 50, 30);

    final JTextField txt3 = new JTextField("");
    txt3.setBounds(55, 83, 250, 30);
    
    JButton button = new JButton("Publish");
    button.setBounds(60, 122, 200, 40);

    setLayout(null);
    add(topicLabel);
    add(txt);
    add(idLabel);
    add(txt2);
    add(valueLabel);
    add(txt3);
    add(button);

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        //System.out.println("You clicked the button");

        String topic = txt.getText();
        String id = txt2.getText();
        String value = txt3.getText();
//        int messageType = JOptionPane.PLAIN_MESSAGE;
        pub.publish(topic, id + "," + value);
        //        JOptionPane.showMessageDialog(null, "{\"" + getTxt + "\"}", "REST Server", messageType);
      }
    });

  }

 

  public static void main(String[] args) {

    pub = new mqttPublisher("localhost",1883, "publisher");

    JFrame frame1 = new JFrame("MQTT Publisher");
    frame1.getContentPane().add(new StartPublishing());

    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(310, 170);
    frame1.setVisible(true);

  }

}
