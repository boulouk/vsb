package eu.chorevolution.vsb.playgrounds.tuplespace.semispace.test;

import java.io.IOException;

import eu.chorevolution.vsb.playgrounds.tuplespace.semispace.TSpaceServer;

public class StartServer {
  public static void main(String[] args) {
    int port = 47555;
    try {
      TSpaceServer server = new TSpaceServer(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
