package eu.chorevolution.vsb.playgrounds.clientserver.websockets.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WebSocketClient.WsClient;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.Parameters;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.ToggleByExpDist;

public class StartClient implements Runnable {

	private WsClient client = null;

	public StartClient() {
		try {
			client = new WsClient(new URI("http://127.0.0.1:8090"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			client.connectBlocking();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new MessageReader()).start();
	}

	public void run() {
		boolean localFlag = true;
		Exp onParameter = new Exp(Parameters.tGet);
		Exp offParameter = new Exp(Parameters.timeout);
		while (StartExperiment.experimentRunning) {
			if(localFlag == true) {
				localFlag = false;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ifconfig lo0 up");
					}
					else {
						Runtime.getRuntime ().exec ("ipconfig lo0 up");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(((long) onParameter.next()) * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				localFlag = true;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ifconfig lo0 down");
					}
					else {
						Runtime.getRuntime ().exec ("ipconfig lo0 down");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(((long) offParameter.next()) * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class MessageReader implements Runnable {

		@Override
		public void run() {
			String recvdMsg = null;
			while(StartExperiment.experimentRunning) {
				try {
					recvdMsg = client.msgQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String[] msgParts = recvdMsg.split(" ");
				System.out.println(recvdMsg + " " + (System.nanoTime() - Long.valueOf(msgParts[2])));
			}
		}

	}

	public static void main(String[] args) {
		// create a client
		WsClient client = null;
		try {
			client = new WsClient(new URI("http://127.0.0.1:8090"));
			client.connect();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		boolean closed = false;

		while(true) {

			String msg = null;
			try {
				msg = client.msgQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(msg);
			if("Hello! 10".equals(msg)) {
				closed = true;
				//				client.close();
				try {
					Runtime.getRuntime ().exec ("ifconfig lo0 down");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(closed) {
				try {
					Thread.sleep(25000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				closed = false;
				//				client.connect();
				try {
					Runtime.getRuntime ().exec ("ifconfig lo0 up");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
