package eu.chorevolution.vsb.playgrounds.str.websockets;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.codehaus.jackson.map.ser.std.TokenBufferSerializer;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.api.WebSocketServerListener;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.instance.JWebSocketInstance;
import org.jwebsocket.kit.WebSocketException;
import org.jwebsocket.kit.WebSocketFrameType;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;

import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartExperiment;

class Listener implements WebSocketServerListener{

	public BlockingQueue<String> msgQueue = null;
	HashMap<Long, Long> endTimeMap = null;
	public WebSocketConnector connector;

	public Listener(BlockingQueue<String> msgQueue, HashMap<Long, Long> endTimeMap) {
		this.msgQueue = msgQueue;
		this.endTimeMap = endTimeMap;
	}

	@Override
	public void processClosed(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Server Connection closed");
	}

	@Override
	public void processOpened(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub
		connector = arg0.getConnector();
		System.out.println("Server Connection opened");
	}
	
	public WebSocketConnector getConnector() {
		return connector;
	}

	@Override
	public void processPacket(WebSocketServerEvent event, WebSocketPacket packet) {
		// TODO Auto-generated method stub
		//		System.out.println("Server recvd: " + packet.getString());
		//		packet.setString("The client says "+ packet.getString());
		//		event.sendPacket(packet);
		String message = packet.getString();
//		System.out.println("recv " + message);
		if(message.startsWith("M")) {
//			System.out.println("here");
			Long recvdTime = System.nanoTime();
			String msgParts[] = message.split(" ");
			if(StartExperiment.DEBUG) {
				synchronized (endTimeMap) {
//					System.out.println("put " + Long.valueOf(msgParts[1]) + " " + recvdTime);
//					System.out.println(endTimeMap);
					endTimeMap.put(Long.valueOf(msgParts[1]), recvdTime);	
//					Long endTime = endTimeMap.get(Long.valueOf(msgParts[1]));
//					Long endTime2 = null;
//					if(Long.valueOf(msgParts[1])>0)
//						endTime2 = endTimeMap.get(Long.valueOf(msgParts[1])-1);	
//					System.out.println(Long.valueOf(msgParts[1]) + " " + endTime + " " + endTime2);
				}
			}
			else {
				endTimeMap.put(Long.valueOf(msgParts[1]), recvdTime);
			}
			StartExperiment.messagesReceived++;
			if(StartExperiment.DEBUG) {
				try {
					msgQueue.put(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

public class WsServer2 {

	public BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();

	public WsServer2(HashMap<Long, Long> endTimeMap) {
		JWebSocketConfig.initForConsoleApp(new String[]{"-home", "/home/siddhartha/Documents/jWebSocket-1.0/"});
		//		JWebSocketConfig.initForConsoleApp(new String[]{"-home", "/Users/Siddhartha/Documents/Academics/8thSem/inria/git/evolution-service-bus/playgrounds/str/websockets-playground/"});
		JWebSocketFactory.start();
		TokenServer server = (TokenServer) JWebSocketFactory.getServer("ts0");
		Listener listener = new Listener(msgQueue, endTimeMap);
		if(server!=null)
			server.addListener(listener);
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(6000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				while (true) {
//					try {
//						server.sendPacket(listener.getConnector(), new WebSocketPacket() {
//							
//							@Override
//							public void setUTF8(String aString) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setTimeout(long aMilliseconds) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setString(String aString, String aEncoding)
//									throws UnsupportedEncodingException {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setString(String aString) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setFrameType(WebSocketFrameType aFrameType) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setFragment(String aString, int aIdx) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setCreationDate(Date aDate) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setByteArray(byte[] aByteArray) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void setASCII(String aString) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public void packFragments() {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public boolean isTimedOut() {
//								// TODO Auto-generated method stub
//								return false;
//							}
//							
//							@Override
//							public boolean isFragmented() {
//								// TODO Auto-generated method stub
//								return false;
//							}
//							
//							@Override
//							public boolean isComplete() {
//								// TODO Auto-generated method stub
//								return true;
//							}
//							
//							@Override
//							public void initFragmented(int aTotal) {
//								// TODO Auto-generated method stub
//								
//							}
//							
//							@Override
//							public String getUTF8() {
//								// TODO Auto-generated method stub
//								return "apple";
//							}
//							
//							@Override
//							public String getString(String aEncoding)
//									throws UnsupportedEncodingException {
//								// TODO Auto-generated method stub
//								return "apple";
//							}
//							
//							@Override
//							public String getString() {
//								// TODO Auto-generated method stub
//								return "apple";
//							}
//							
//							@Override
//							public WebSocketFrameType getFrameType() {
//								// TODO Auto-generated method stub
//								return WebSocketFrameType.TEXT;
//							}
//							
//							@Override
//							public Date getCreationDate() {
//								// TODO Auto-generated method stub
//								return null;
//							}
//							
//							@Override
//							public byte[] getByteArray() {
//								// TODO Auto-generated method stub
//								return "apple".getBytes();
//							}
//							
//							@Override
//							public String getASCII() {
//								// TODO Auto-generated method stub
//								return "apple";
//							}
//						});
//						Thread.sleep(250);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			};}).start();
		//		while (JWebSocketInstance.getStatus()!=JWebSocketInstance.SHUTTING_DOWN)
		//			try {
		//				Thread.sleep(250);
		//			} catch (InterruptedException e) {
		//				e.printStackTrace();
		//			}
	}

	public static void main(String[] args){
		//		JWebSocketFactory.printCopyrightToConsole();
		JWebSocketConfig.initForConsoleApp(new String[]{"-home", "/home/siddhartha/Documents/jWebSocket-1.0/"});
		//		JWebSocketConfig.initForConsoleApp(new String[]{"-home", "/Users/Siddhartha/Documents/Academics/8thSem/inria/git/evolution-service-bus/playgrounds/str/websockets-playground/"});
		JWebSocketFactory.start();
		TokenServer server = (TokenServer) JWebSocketFactory.getServer("ts0");
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();
		if(server!=null)
			server.addListener(new Listener(msgQueue, null));
		while (JWebSocketInstance.getStatus()!=JWebSocketInstance.SHUTTING_DOWN)
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	}
}