package eu.chorevolution.vsb.playgrounds.str.websockets.test2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.str.websockets.WsServer;
import eu.chorevolution.vsb.playgrounds.str.websockets.WsServer2;


public class StartServer {

	public WsServer2 server2 = null;

	public StartServer(HashMap<Long, Long> endTimeMap) {
		// create a server listening on port 8090
		server2 = new WsServer2(endTimeMap);
		if(StartExperiment.DEBUG) { 
			new Thread(new MessageReader(endTimeMap)).start();
		}
		
	}

	class MessageReader implements Runnable {

		HashMap<Long, Long> endTimeMap = null;
		
		public MessageReader(HashMap<Long, Long> endTimeMap) {
			this.endTimeMap = endTimeMap;
		}
		
		@Override
		public void run() {
			String recvdMsg = null;
			while(StartExperiment.experimentRunning) {
				try {
					recvdMsg = server2.msgQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String[] msgParts = recvdMsg.split(" ");
				Long msgNum = Long.valueOf(msgParts[1]);
				assert(msgNum != null);
				Long endTime;
				Long startTime;

				synchronized (endTimeMap) {
					endTime = endTimeMap.get(msgNum);	
				}

				synchronized (StartExperiment.startTimeMap) {
					startTime = StartExperiment.startTimeMap.get(msgNum);	
				}
				
//				System.out.println(msgNum);
//				System.out.println(endTimeMap);
//				System.out.println(endTime);
//				System.out.println(startTime);
//				if(endTime!=null && startTime!=null)
				System.out.println("Msg " + msgNum + " --> " + (endTime - startTime));
				//				System.out.println(recvdMsg + " " + Long.valueOf(msgParts[4]) + " " + (Long.valueOf(msgParts[4]) - Long.valueOf(msgParts[2])));
			}
		}

	}

	public static void main(String[] args) {
		WsServer server = new WsServer(new InetSocketAddress(8090));
		server.start();

		int i=0;

		// Send a message
		while(i<100) {
			String msg = "Hello! " + i;
			server.send(msg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

}
