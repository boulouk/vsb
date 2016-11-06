package eu.chorevolution.vsb.playgrounds.str.websockets;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartClient;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartExperiment;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartSourceApplication;

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
		System.err.println("an error occured " + ex.getStackTrace() + " " + ex.getMessage());
		System.err.println();
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

	public void send(String msg) {
		if(connection!=null) {
			connection.send(msg);
		}
	}

}