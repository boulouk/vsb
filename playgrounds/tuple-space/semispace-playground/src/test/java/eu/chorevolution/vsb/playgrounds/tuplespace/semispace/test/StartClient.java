package eu.chorevolution.vsb.playgrounds.tuplespace.semispace.test;

import java.util.UUID;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.tuplespace.semispace.TSpaceClient;

public class StartClient {
  @Test
  public void startClient() {
    TSpaceClient client = new TSpaceClient("127.0.0.1", 47555, "client");
    client.write("key","value", 1000*60);
    String s = client.read("key");
    System.out.println(s);
    s = client.take("a template");
    System.out.println(s);
    s = client.read("key");
    System.out.println(s);
  }
}
