package eu.chorevolution.vsb.playgrounds.clientserver.websockets;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.handshake.ServerHandshake;

public class WebSocketClient {
	
	public static class WsClient extends org.java_websocket.client.WebSocketClient {

		public WsClient(URI serverURI) {
			super(serverURI);
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
			System.out.println("Client opens a stream");
		}

		@Override
		public void onMessage(String message) {
			System.out.println("Client receives : " + message);
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
			System.err.println("stream closed");
		}

		@Override
		public void onError(Exception ex) {
			System.err.println("an error occured");
		}
	}

}
