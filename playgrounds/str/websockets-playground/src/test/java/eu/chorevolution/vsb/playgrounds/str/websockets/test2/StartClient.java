package eu.chorevolution.vsb.playgrounds.str.websockets.test2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import eu.chorevolution.vsb.playgrounds.str.websockets.WebSocketClient.WsClient;
import eu.chorevolution.vsb.playgrounds.str.websockets.WebSocketClient2;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.Parameters;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.RangeExp;

public class StartClient implements Runnable {

	public WebSocketClient2 client2 = null;
	public static RangeExp onParameter = new RangeExp(Parameters.onParam);
	public static RangeExp offParameter = new RangeExp(Parameters.offParam);

	public StartClient() {
			client2 = new WebSocketClient2();
	}

//	public void connect() {
//		try {
//			client.connectBlocking();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		if(StartExperiment.DEBUG) { 
//			new Thread(new MessageReader()).start();
//		}
//	}

	public void run() {
		boolean localFlag = true;
		while (StartExperiment.experimentRunning) {
			if(localFlag == true) {
				System.err.println("UP!!");
				localFlag = false;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ipconfig lo0 up");
					}
					else {
						// https://github.com/tylertreat/comcast
						String[] cmd = {"/bin/bash","-c","echo qqq_04| sudo -Sk /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --stop"};
//						
//						Runtime.getRuntime ().exec ("sudo /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device=eth0 --latency=250 --target-bw=1000 --default-bw=1000000 --packet-loss=100% --target-addr=192.168.0.101 --target-proto=tcp,udp,icmp --target-port=8090");
						Runtime.getRuntime ().exec (cmd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				long param = (long) onParameter.next();
				System.err.println("here " + param);
				try {
					Thread.sleep(7 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				System.err.println("DOWN!! " + System.nanoTime());
				localFlag = true;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ipconfig lo0 down");
					}
					else {
						// https://github.com/tylertreat/comcast
						String[] cmd = {"/bin/bash","-c","echo qqq_04| sudo -Sk /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device=eth0 --latency=250 --target-bw=1000 --default-bw=1000000 --packet-loss=100% --target-addr=127.0.0.1 --target-proto=tcp,udp,icmp --target-port=8090"};
//						Runtime.getRuntime ().exec ("sudo /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --stop");
						Runtime.getRuntime ().exec (cmd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				long param = (long) offParameter.next();
				System.err.println("here " + param);
				try {
					Thread.sleep(param * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
