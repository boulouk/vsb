package eu.chorevolution.vsb.playgrounds.str.websockets;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.api.WebSocketServerListener;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.instance.JWebSocketInstance;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.server.TokenServer;

class Listener implements WebSocketServerListener{

	@Override
	public void processClosed(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processOpened(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Connection opened");
	}

	@Override
	public void processPacket(WebSocketServerEvent event, WebSocketPacket packet) {
		// TODO Auto-generated method stub
		System.out.println("Server recvd: " + packet.getString());
		packet.setString("The client says "+ packet.getString());
		event.sendPacket(packet);

	}

}

public class WsServer2 {

	public static void main(String[] args){
		//		JWebSocketFactory.printCopyrightToConsole();
		JWebSocketConfig.initForConsoleApp(new String[]{"-home", "/Users/Siddhartha/Documents/Academics/7th Sem/placements/apac/jWebSocket-1.0/"});
//		JWebSocketConfig.initForConsoleApp(new String[]{"-home", "/Users/Siddhartha/Documents/Academics/8thSem/inria/git/evolution-service-bus/playgrounds/str/websockets-playground/"});
		JWebSocketFactory.start();
		TokenServer server = (TokenServer) JWebSocketFactory.getServer("ts0");
		if(server!=null)
			server.addListener(new Listener());
		while (JWebSocketInstance.getStatus()!=JWebSocketInstance.SHUTTING_DOWN)
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}