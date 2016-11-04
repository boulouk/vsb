package eu.chorevolution.vsb.playgrounds.str.websockets.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.str.websockets.WsServer;


public class StartServer {
	
	public WsServer server = null;
	
	public StartServer() {
		// create a server listening on port 8090
		server = new WsServer(new InetSocketAddress(8090));
	}
	
	void start() {
		server.start();
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
