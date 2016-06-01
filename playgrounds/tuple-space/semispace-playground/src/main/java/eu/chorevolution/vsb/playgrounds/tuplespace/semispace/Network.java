package eu.chorevolution.vsb.playgrounds.tuplespace.semispace;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public final class Network {
	static public final short SPACE = 1;
	
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		ObjectSpace.registerClasses(kryo);
		kryo.register(ISpace.class);
	}
}
