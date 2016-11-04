package eu.chorevolution.vsb.playgrounds.str.websockets;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jwebsocket.api.WebSocketClientEvent;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.client.token.BaseTokenClient;
import org.jwebsocket.api.WebSocketClientListener;
import org.jwebsocket.kit.RawPacket;
import org.jwebsocket.kit.WebSocketException;
import org.jwebsocket.packetProcessors.JSONProcessor;
import org.jwebsocket.token.Token;

public class WebSocketClient2 implements WebSocketClientListener {

	public static void main(String[] args) {
		WebSocketClient2 c = new WebSocketClient2();
		BaseTokenClient client = new BaseTokenClient();
		client.addListener(c);
		try {
			client.open("ws://localhost:8787");
		} catch (WebSocketException ex) {
			Logger.getLogger(WebSocketClient2.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			client.login(null, null);
		} catch (WebSocketException ex) {
			Logger.getLogger(WebSocketClient2.class.getName()).log(Level.SEVERE, null, ex);
		}
		for (int i = 0; i < 100; i++) {
			try {
				// wait for 3 seconds, then move to next slide
				Thread.sleep(3000);
				c.sendPacket(client, i); // slides 1..5, then back to 1
			} catch (InterruptedException ex) {
			}
		}
	}
	
	public void sendPacket(BaseTokenClient client, int slideNumber) {
	    String json = "{'action':'slide','uniqueId':123543,'slideNumber':" + slideNumber + "}";
	    try {
	        client.send(json.getBytes());
	    } catch (WebSocketException ex) {
	        Logger.getLogger(WebSocketClient2.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

	@Override
	public void processClosed(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("*******processClosed*******");
	}

	@Override
	public void processOpened(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("*******processOpened*******");
	}

	@Override
	public void processOpening(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("*******processOpening*******");
	}

	@Override
	public void processPacket(WebSocketClientEvent arg0, WebSocketPacket arg1) {
		// TODO Auto-generated method stub
		System.out.println("Client recvd: " + arg1.getString());
	}

	@Override
	public void processReconnecting(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("*******trying reconnect*******");
	}
}