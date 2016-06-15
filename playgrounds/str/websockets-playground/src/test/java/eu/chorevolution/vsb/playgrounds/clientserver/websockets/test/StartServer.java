package eu.chorevolution.vsb.playgrounds.clientserver.websockets.test;

import java.net.InetSocketAddress;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WsServer;


public class StartServer {
  @Test
  public void startServer() {
    // create a server listening on port 8090
    WsServer server = new WsServer(new InetSocketAddress(8090));
    server.start();
  }
}
