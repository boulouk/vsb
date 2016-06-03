package eu.chorevolution.vsb.playgrounds.pubsub.jms.gui;

import javax.jms.JMSException;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.DurableSubscriber;
import eu.chorevolution.vsb.playgrounds.pubsub.jms.Subscriber;

public class SubscriberGUI {

	private DurableSubscriber sub = null;
	private JLabel messageLabel = null;
	private JLabel msgLabel = null;

	class Gui extends JPanel {
		public Gui() {

			final JLabel clientLabel = new JLabel("Client Id:");
			clientLabel.setBounds(3, 5, 80, 30);

			final JTextField txt = new JTextField("");
			txt.setBounds(80, 5, 225, 30);

			final JLabel topicLabel = new JLabel("Topic:");
			topicLabel.setBounds(3, 44, 50, 30);

			final JTextField txt2 = new JTextField("topic");
			txt2.setBounds(80, 44, 225, 30);

			messageLabel = new JLabel("Msg:");
			messageLabel.setBounds(3, 83, 50, 30);
			messageLabel.setVisible(false);

			msgLabel = new JLabel("msg");
			msgLabel.setBounds(80, 83, 225, 30);
			msgLabel.setVisible(false);

			JButton button = new JButton("Subscribe");
			button.setBounds(60, 122, 200, 40);

			setLayout(null);
			add(clientLabel);
			add(txt);
			add(topicLabel);
			add(txt2);
			add(messageLabel);
			add(msgLabel);

			add(button);

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String clientId = txt.getText();
					String topic = txt2.getText();

					try {
						sub.create(clientId, topic, "durablesubscriber1", "localhost", 61616);
					} catch (JMSException e3) {
						e3.printStackTrace();
					}
					new Thread(new Runnable() {

						@Override
						public void run() {
							startReceiving();              
						}
					}).start();

				}
			});
		}
	}

	public SubscriberGUI() {
		//    sub = new Subscriber("localhost",1883, "subscriber");
		sub = new DurableSubscriber();

		JFrame frame1 = new JFrame("MQTT Subscriber");
		frame1.getContentPane().add(new Gui());

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(310, 170);
		frame1.setVisible(true);
	}

	public SubscriberGUI(String ip, int port, String cliendID) {
		sub = new DurableSubscriber();

		JFrame frame1 = new JFrame("JMS Subscriber");
		frame1.getContentPane().add(new Gui());

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(310, 170);
		frame1.setVisible(true);
	}

	void startReceiving() {
		    while(true) {
		      try {
		        synchronized(sub.msgQueue) {
		          if(sub.msgQueue.size()>0) {
		            String msg = sub.msgQueue.poll();
		            messageLabel.setVisible(true);
		            msgLabel.setText(msg);
		            msgLabel.setVisible(true);
//		            System.out.println("gui : " + msg.getMsg());
		          }
		          else {
		            sub.msgQueue.wait();
		          }
		        }
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		    }
	}

}
