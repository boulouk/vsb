package eu.chorevolution.vsb.playgrounds.tuplespace.semispace.test;

import java.io.IOException;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.tuplespace.semispace.TSpaceServer;

public class StartServer {
  @Test
  public void startServer() {
    int port = 47555;
    try {
      TSpaceServer server = new TSpaceServer(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
