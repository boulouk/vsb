package eu.chorevolution.vsb.playgrounds.clientserver.coap.test;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.CoapClientPart;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.CoapServerPart;

public class CoapMain {
  public static void main(String[] args) {
    CoapServerPart server = new CoapServerPart();
    server.startServer();
    server.mgetOneway("operation1");
    server.mgetTwoway("operation1");

    CoapClientPart client = new CoapClientPart();
    client.post_oneway("coap://127.0.0.1:9090", "operation1", "blaa", 0);
    client.post_twoway("coap://127.0.0.1:9090", "operation1", "blaa", 0);
  }
}
