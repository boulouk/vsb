package eu.chorevolution.vsb.playgrounds.str.websockets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.handshake.ServerHandshake;

import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartExperiment;

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
		}

	}

}