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
   client.post_twoway("coap://127.0.0.1:9082", "test", "{\"arg0\":\"17.419255,78.3502029,17z\", \"arg1\":\"17.4248596,78.3330046\"}", 0);

//    String msg = "{\"ts\": 1388179812427,\"type\": \"message\",\"data\": {\"onFire\":false,\"temperature\":50}} ";
//    client.post_oneway("coaps://coaps-api.artik.cloud/v1.1/messages/", "8ead85643cb0406e9fce0f537b13f93f", msg, 0);
  }
}
