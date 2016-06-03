package eu.chorevolution.vsb.playgrounds.pubsub.jms.gui;

import javax.jms.JMSException;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;

import eu.chorevolution.vsb.playgrounds.pubsub.jms.Publisher;

public class PublisherGUI {

	static Publisher pub = null;
	String ip;
	int port;
	String cliendID;

	class Gui extends JPanel {
		public Gui() {

			final JLabel topicLabel = new JLabel("Topic:");
			topicLabel.setBounds(3, 5, 50, 30);

			final JTextField txt = new JTextField("");
			txt.setBounds(55, 5, 250, 30);

			final JLabel idLabel = new JLabel("Id:");
			idLabel.setBounds(3, 44, 50, 30);

			final JTextField txt2 = new JTextField("23");
			txt2.setBounds(55, 44, 250, 30);
			txt2.setEditable(false);

			final JLabel valueLabel = new JLabel("Value:");
			valueLabel.setBounds(3, 83, 50, 30);

			final JTextField txt3 = new JTextField("some_message");
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
				boolean connected = false;
				public void actionPerformed(ActionEvent e) {
					if(!connected) {
						connected = true;
						pub = new Publisher();
						
						String topic = txt.getText();
						String id = txt2.getText();
						
						try {
							pub.create(id, topic, "localhost", 61616);
						} catch (JMSException e1) {
							e1.printStackTrace();
						}
					}

					String value = txt3.getText();
				
					try {
						pub.send(value);
					} catch (JMSException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	public PublisherGUI(String ip, int port, String cliendID) {

		this.ip = ip;
		this.port = port;
		this.cliendID = cliendID;

		JFrame frame1 = new JFrame("JMS Publisher");
		frame1.getContentPane().add(new Gui());

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(310, 170);
		frame1.setVisible(true);

	}
}
