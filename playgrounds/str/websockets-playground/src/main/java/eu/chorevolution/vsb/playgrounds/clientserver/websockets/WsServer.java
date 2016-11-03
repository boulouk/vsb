package eu.chorevolution.vsb.playgrounds.clientserver.websockets;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public class WsServer extends org.java_websocket.server.WebSocketServer {

  public WebSocket connection;

  public WsServer(InetSocketAddress address) {
    super(address);
    System.out.println("server is started on " + address.toString());
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
	  connection = conn;
    System.out.println("Server opens a stream");
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason,
      boolean remote) {
    System.err.println("close a stream");
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
//    System.out.println("Receives message : " + message);
	  conn.send("hey received " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    System.err.println("an error occured");
  }
  
  public void send(String msg) {
	  if(connection!=null) {
		  connection.send(msg);
	  }
  }

}