package eu.chorevolution.vsb.playgrounds.tuplespace.semispace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.semispace.SemiSpace;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class TSpaceServer {
	private final Server server;
	private final TupleSpace space = new TupleSpace();
	
	public TSpaceServer(int port) throws IOException {
		this.server = new Server();
		Network.register(server);
		
		server.start();
		server.bind(port);
		
		final ObjectSpace objectSpace = new ObjectSpace();
		objectSpace.register(Network.SPACE,this.space);
		
		this.server.addListener(new TSServerListener(objectSpace));
	}
	
	public void stop() {
		this.server.stop();
		this.space.close();
	}
	
	private final class TSServerListener extends Listener {
		private final HashMap<String, TSpaceClient> observers = new HashMap<>();
		private final ObjectSpace objectSpace;
		
		public TSServerListener(ObjectSpace objectSpace) {
			this.objectSpace = objectSpace;
		}
		
		@Override
		public void connected(final Connection connection) {
			this.objectSpace.addConnection(connection);
			System.err.println("new connection " + connection.getID() +" "+ connection.getRemoteAddressTCP().getHostString());
		}
		
		@Override
		public void received(Connection connection, Object object) {
			if(object instanceof ObjectSpace.InvokeMethod) {
				ObjectSpace.InvokeMethod invokedMethod = (ObjectSpace.InvokeMethod) object;
				//System.err.println(method.method.getName());
				// check remote address if there is an observer registred for it.
				if(observers.containsKey(connection.getRemoteAddressTCP().getHostString())) {
					switch (invokedMethod.method.getName()) {
					case "read": 
						System.err.println("a");
						observers.get(connection.getRemoteAddressTCP().getHostString()).read((String)invokedMethod.args[0]);
						break;
					case "take":
						System.err.println("b");
						observers.get(connection.getRemoteAddressTCP().getHostString()).take((String)invokedMethod.args[0]);
						break;
					case "write":
						System.err.println("c");
						observers.get(connection.getRemoteAddressTCP().getHostString()).write((String)invokedMethod.args[0],(String)invokedMethod.args[1],(int)invokedMethod.args[2]);
						break;	
					default:
						break;
					}
				}
				else if (invokedMethod.method.getName() == "register") {
					TSpaceClient c = new TSpaceClient((String)invokedMethod.args[0], (int)invokedMethod.args[1], "callback-"+(String)invokedMethod.args[0]);
					this.observers.put((String)invokedMethod.args[0], c);
					c.take("debug");
				}
			}
		}
	}
	
	private final class TupleSpace extends Connection implements ISpace {
		private final SemiSpace space = (SemiSpace) SemiSpace.retrieveSpace();
		
		public TupleSpace() {	
			new ObjectSpace(this).register(Network.SPACE, this);
		}
		
		public void close() {
			System.exit(0);
		}
		
		@Override
		public String read(String template) {
			Object templateKey = this.space.processTemplate(new Element().setTemplate(template));
			Element elt = (Element) this.space.readIfExists(templateKey);
			return (elt == null) ? "" : (elt.getValue() == null) ? "" : elt.getValue();
		}

		@Override
		public String take(String template) {
			Object templateKey = this.space.processTemplate(new Element().setTemplate(template));
			Element elt = (Element) this.space.takeIfExists(templateKey);
			return (elt == null) ? "" : (elt.getValue() == null) ? "" : elt.getValue();
		}

		@Override
		public void write(String template, String value, int leaseTime) {
			Element elt = new Element().setTemplate(template).setValue(value);
			this.space.write(elt, leaseTime);
		}

		@Override
		public void register(String callbackIP, int callbackPort) {
			System.err.println("registration of " + callbackIP + ":" + callbackPort);
		}
	}
}
