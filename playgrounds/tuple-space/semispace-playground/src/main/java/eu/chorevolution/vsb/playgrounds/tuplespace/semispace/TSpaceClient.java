package eu.chorevolution.vsb.playgrounds.tuplespace.semispace;

import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class TSpaceClient implements ISpace {
	private final Client client;
	private final ISpace space;
	
	public TSpaceClient(final String ip, final int port, String name) {
		this.client = new Client();
		Network.register(client);
		client.start();
		
		new Thread(name) {
			public void run() {
				try {
					client.connect(5000, InetAddress.getByName(ip), port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		this.space = ObjectSpace.getRemoteObject(client, Network.SPACE, ISpace.class);
	}
	
	public void stop() {
		this.client.close();
	}
	
	@Override
	public String read(String template) {
		return this.space.read(template);
	}

	@Override
	public String take(String template) {
		return this.space.take(template);
	}

	@Override
	public void write(String template, String value, int leaseTime) {
		this.space.write(template, value, leaseTime);
	}

	@Override
	public void register(String callbackIP, int callbackPort) {
		this.space.register(callbackIP, callbackPort);		
	}
}
