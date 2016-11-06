package eu.chorevolution.vsb.playgrounds.str.websockets;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jwebsocket.api.WebSocketClientEvent;
import org.jwebsocket.api.WebSocketClientListener;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.client.java.ReliabilityOptions;
import org.jwebsocket.client.token.BaseTokenClient;
import org.jwebsocket.kit.WebSocketException;

public class WebSocketClient2 implements WebSocketClientListener {

	BaseTokenClient client;
	
	@SuppressWarnings("deprecation")
	public WebSocketClient2() {
		client = new BaseTokenClient();
		client.addListener(this);
//		client.setConnectionSocketTimeout(10000);
		
		try {
			client.open("ws://localhost:8787");
		} catch (WebSocketException ex) {
			Logger.getLogger(WebSocketClient2.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			client.login("guest","guest");
		} catch (WebSocketException ex) {
			Logger.getLogger(WebSocketClient2.class.getName()).log(Level.SEVERE, null, ex);
		}
//		for (int i = 0; i < 100; i++) {
//			try {
//				// wait for 3 seconds, then move to next slide
//				Thread.sleep(3000);
//				this.sendPacket(client, i); // slides 1..5, then back to 1
//			} catch (InterruptedException ex) {
//			}
//		}
	}

	public void send(String msg) {
//		if(connection!=null) {
		try {
	        client.send(msg.getBytes());
	    } catch (WebSocketException ex) {
	        Logger.getLogger(WebSocketClient2.class.getName()).log(Level.SEVERE, null, ex);
	    }
//		}
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
		System.out.println("*******processClosed*******" + arg0.getName() + "#"+arg0.getData());
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
//		System.out.println("Client recvd: " + arg1.getString());
		System.out.println("revd " + arg1.getASCII());
	}

	@Override
	public void processReconnecting(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("*******trying reconnect*******");
	}

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
			client.login("demo", "demo");
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
}