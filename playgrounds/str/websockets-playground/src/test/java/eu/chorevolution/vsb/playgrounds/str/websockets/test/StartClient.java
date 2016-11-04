package eu.chorevolution.vsb.playgrounds.str.websockets.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.str.websockets.WebSocketClient.WsClient;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.Parameters;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.ToggleByExpDist;

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
				System.out.println("UP!!");
				localFlag = false;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ipconfig lo0 up");
					}
					else {
						// https://github.com/tylertreat/comcast
						String[] cmd = {"/bin/bash","-c","echo pwd| sudo -Sk /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --stop"};
//						
//						Runtime.getRuntime ().exec ("sudo /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device=eth0 --latency=250 --target-bw=1000 --default-bw=1000000 --packet-loss=100% --target-addr=192.168.0.101 --target-proto=tcp,udp,icmp --target-port=8090");
						Runtime.getRuntime ().exec (cmd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("here");
				try {
					Thread.sleep(((long) 10) * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("DOWN!!");
				localFlag = true;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ipconfig lo0 down");
					}
					else {
						// https://github.com/tylertreat/comcast
						String[] cmd = {"/bin/bash","-c","echo pwd| sudo -Sk /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device=eth0 --latency=250 --target-bw=1000 --default-bw=1000000 --packet-loss=100% --target-addr=127.0.0.1 --target-proto=tcp,udp,icmp --target-port=8090"};
//						Runtime.getRuntime ().exec ("sudo /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --stop");
						Runtime.getRuntime ().exec (cmd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("here");
				try {
					Thread.sleep(((long) 10) * 1000);
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
				Long msgNum = Long.valueOf(msgParts[1]);
				System.out.println("Msg " + msgNum + " --> " + (StartExperiment.endTimeMap.get(msgNum) - StartExperiment.startTimeMap.get(msgNum)));
//				System.out.println(recvdMsg + " " + Long.valueOf(msgParts[4]) + " " + (Long.valueOf(msgParts[4]) - Long.valueOf(msgParts[2])));
			}
		}

	}

	public static void main(String[] args) {
		// create a client
		WsClient client = null;
		try {
			client = new WsClient(new URI("http://127.0.0.1:8787"));
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
