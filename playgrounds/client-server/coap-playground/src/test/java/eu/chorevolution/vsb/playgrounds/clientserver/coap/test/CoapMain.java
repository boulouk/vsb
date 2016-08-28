package eu.chorevolution.vsb.playgrounds.clientserver.coap.test;

import org.eclipse.californium.core.coap.Response;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.CoapClientPart;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.CoapServerPart;

public class CoapMain {
  public static void main(String[] args) {
//    CoapServerPart server = new CoapServerPart();
//    server.startServer();
//    server.mgetOneway("operation1");
//    server.mgetTwoway("test");
	  
    CoapClientPart client = new CoapClientPart();
//    client.post_oneway("coap://127.0.0.1:9090", "operation1", "blaa", 0);
   client.post_twoway("coap://127.0.0.1:8071", "listener", "{\"arg0\":\"17.4280139,78.3332974,16z\", \"arg1\":\"17.4280139,78.3332974,16z\"}", 0);

//    String msg = "{\"ts\": 1388179812427,\"type\": \"message\",\"data\": {\"onFire\":false,\"temperature\":50}} ";
//    client.post_oneway("coaps://coaps-api.artik.cloud/v1.1/messages/", "8ead85643cb0406e9fce0f537b13f93f", msg, 0);
  }
}
