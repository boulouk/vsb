package eu.chorevolution.vsb.playgrounds.clientserver.websockets.test;

import java.net.InetSocketAddress;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WebSocketServer.WsServer;

public class StartServer {
  public static void main(String[] args) {
    // create a server listening on port 8090
    WsServer server = new WsServer(new InetSocketAddress(8090));
    server.start();
  }
}
