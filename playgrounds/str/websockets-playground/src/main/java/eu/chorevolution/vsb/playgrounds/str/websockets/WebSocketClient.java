package eu.chorevolution.vsb.playgrounds.str.websockets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.handshake.ServerHandshake;

import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartClient;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartExperiment;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartSourceApplication;

public class WebSocketClient {

	public static class WsClient extends org.java_websocket.client.WebSocketClient {

		public BlockingQueue<String> msgQueue;

		public WsClient(URI serverURI) {
			super(serverURI);
			msgQueue = new LinkedBlockingQueue<String>();
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
			System.out.println("Client opens a stream");
		}

		@Override
		public void onMessage(String message) {
			//			message += " at " + System.nanoTime();
			//			System.out.println("Client receives : " + message);
			Long recvdTime = System.nanoTime();
			String msgParts[] = message.split(" ");
			if(StartExperiment.DEBUG) {
				synchronized (StartExperiment.endTimeMap) {
					StartExperiment.endTimeMap.put(Long.valueOf(msgParts[1]), recvdTime);					
				}
			}
			else {
				StartExperiment.endTimeMap.put(Long.valueOf(msgParts[1]), recvdTime);
			}
			StartExperiment.messagesReceived++;
			if(StartExperiment.DEBUG) {
				try {
					msgQueue.put(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
			System.err.println("stream closed");
		}

		@Override
		public void onError(Exception ex) {
			System.err.println("an error occured " + ex.getStackTrace() + " " + ex.getMessage());
			System.err.println("Packets Sent: " + StartSourceApplication.counter);
			System.err.println("Packets Received: " + StartExperiment.messagesReceived);
			System.err.println("Packet Loss: " + (StartSourceApplication.counter - StartExperiment.messagesReceived));

			Long dur = 0L;

			if(StartExperiment.endTimeMap.containsKey(0L))
				StartExperiment.endTimeMap.remove(0L);
			
			for(java.util.Map.Entry<Long, Long> e: StartExperiment.endTimeMap.entrySet()) {
				dur += (e.getValue() - StartExperiment.startTimeMap.get(e.getKey()));
			}

			System.err.println("Average time: " + dur.doubleValue()/StartExperiment.endTimeMap.size());

			System.err.println("on duration: " + StartClient.onParameter.average());
			System.err.println("off duration: " + StartClient.offParameter.average());
			System.err.println("msgs: " + StartSourceApplication.waitDuration.average());
		}

	}

}