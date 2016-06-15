package eu.chorevolution.vsb.playgrounds.clientserver.websockets.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WebSocketClient.WsClient;

public class StartClient {
  
  @Test
  public void startClient() {
    // create a client
    WsClient client = null;
    try {
      client = new WsClient(new URI("http://127.0.0.1:8090"));
      client.connectBlocking();
    } catch (URISyntaxException | InterruptedException e) {
      e.printStackTrace();
    }
    
    // Send a message
    String msg = "Hello!";
    client.send("Hello!");
    System.out.println("Send message: " + msg );
  }
}
