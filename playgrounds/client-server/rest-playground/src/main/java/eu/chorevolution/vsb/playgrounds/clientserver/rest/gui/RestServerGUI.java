package eu.chorevolution.vsb.playgrounds.clientserver.rest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import eu.chorevolution.vsb.playgrounds.clientserver.rest.RestServer;

public class RestServerGUI {

	private RestServer rs = null;
	private JLabel messageLabel = null;
	private JTextArea display = null;
	private JScrollPane scroll = null;

	class Gui extends JPanel {
		public Gui() {

			final JLabel portLabel = new JLabel("Port:");
			portLabel.setBounds(3, 44, 50, 30);

			final JTextField portField = new JTextField("");
			portField.setBounds(80, 44, 225, 30);
			
			final JLabel scopeLabel = new JLabel("Scope:");
			scopeLabel.setBounds(3, 83, 50, 30);

			final JTextField scopeField = new JTextField("");
			scopeField.setBounds(80, 83, 225, 30);

			messageLabel = new JLabel("Msg:");
			messageLabel.setBounds(3, 122, 50, 30);
			//      messageLabel.setVisible(false);

			//      msgLabel = new JLabel("msg");
			//      msgLabel.setBounds(80, 83, 225, 30);
			//      msgLabel.setVisible(false);

			display = new JTextArea(16, 122);
			display.setEditable(false); // set textArea non-editable
			display.setText("Start the server first!");
			scroll = new JScrollPane(display);

			JButton startButton = new JButton("Start");
			startButton.setBounds(320, 44, 90, 30);
			
			JButton stopButton = new JButton("Stop");
			stopButton.setBounds(430, 44, 90, 30);
			
			JButton attachButton = new JButton("Attach");
			attachButton.setBounds(320, 83, 90, 30);

			scroll.setBounds(80, 122, 455, 220);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			//      scroll.setVisible(false);

			setLayout(null);
			//      add(clientLabel);
			//      add(txt);
			add(portLabel);
			add(portField);
			add(scopeLabel);
			add(scopeField);
			add(messageLabel);
			//      add(msgLabel);
			add(scroll);
			add(startButton);
			add(stopButton);
			add(attachButton);
			
			startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					display.setText("Server started, waiting for request!");
					rs.startServer(Integer.parseInt(portField.getText()));
				}
			});
			
			stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					display.setText("Server stopped!");
					rs.stopServer();
				}
			});
			
			attachButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rs.addScope(scopeField.getText());
					new Thread(new Runnable() {
						public void run() {
							startReceiving();              
						}
					}).start();
				}
			});
		}
	}

	public RestServerGUI() {
		rs = new RestServer();
//	    rs.startServer(1111);
		
		JFrame frame1 = new JFrame("REST Server");
		frame1.getContentPane().add(new Gui());

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(590, 370);
		frame1.setVisible(true);
	}

//	public RestServerGUI(int port) {
//		rs = new RestServer();
//	    rs.startServer(port);
//		
//		JFrame frame1 = new JFrame("REST Server");
//		frame1.getContentPane().add(new Gui());
//
//		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame1.setSize(590, 370);
//		frame1.setVisible(true);
//	}

	void startReceiving() {
		while(true) {
			try {
				synchronized(rs.msgQueue) {
					
					if(rs.msgQueue.size()>0) {
						System.out.println("read message to queue");
						String msg = rs.msgQueue.poll();
						display.setText(msg);
						System.out.println("gui : " + msg);
					}
					else {
						rs.msgQueue.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}