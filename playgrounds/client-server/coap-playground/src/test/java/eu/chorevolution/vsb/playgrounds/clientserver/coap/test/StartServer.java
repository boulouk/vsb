package eu.chorevolution.vsb.playgrounds.clientserver.coap.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import org.eclipse.californium.core.CoapServer;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.ObservableServer;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Parameters;


public class StartServer {

	public StartServer() {
		CoapServer server = new CoapServer();
		server.add(new ObservableServer("hello"));
		server.start();
	}

}
