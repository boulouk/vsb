package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.pubsub.mqtt.mqttPublisher;


public class StartSubscriber {

  @Test
  public void startSubscriber() {
    Subscriber sub= null;
    sub = new Subscriber("localhost",1883, "subscriber");
    try {
      sub.create("topic_name");
    } catch (JMSException e) {
      e.printStackTrace();
    }

    while(true) {
      synchronized (sub.msgQueue) {
        if(sub.msgQueue.size()>0) {
          Message msg = sub.msgQueue.poll();
          System.out.println(msg.getMsg());
        } 
        else {
          try {
            sub.msgQueue.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

}
