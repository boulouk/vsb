package eu.chorevolution.vsb.playgrounds.clientserver.coap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.StartExperiment;


public class ObserverClient {

	static CoapClient client = null;
	HashMap<Long, Long> endTimeMap = null;
	public BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();;
	
	public ObserverClient(HashMap<Long, Long> endTimeMap) {
		this.endTimeMap = endTimeMap;
		client = new CoapClient("coap://127.0.0.1:5683/hello");
		new Thread(new ServerListener(endTimeMap)).start();
	}

	public static void main(String[] args) {
		HashMap<Long, Long> h = new HashMap<Long, Long>();
		ObserverClient o = new ObserverClient(h);
	}
	
	class ServerListener implements Runnable {
		HashMap<Long, Long> endTimeMap = null;
		public ServerListener(HashMap<Long, Long> endTimeMap) {
			this.endTimeMap = endTimeMap;
		}
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("OBSERVE CLIENT (press enter to exit)");

			CoapObserveRelation relation = client.observe(
					new CoapHandler() {
						@Override public void onLoad(CoapResponse response) {
							String content = response.getResponseText();
							if(content.startsWith("M")) {
//								System.out.println("here");
								Long recvdTime = System.nanoTime();
								String msgParts[] = content.split(" ");
								if(StartExperiment.DEBUG) {
									synchronized (endTimeMap) {
//										System.out.println("put " + Long.valueOf(msgParts[1]) + " " + recvdTime);
//										System.out.println(endTimeMap);
										endTimeMap.put(Long.valueOf(msgParts[1]), recvdTime);	
//										Long endTime = endTimeMap.get(Long.valueOf(msgParts[1]));
//										Long endTime2 = null;
//										if(Long.valueOf(msgParts[1])>0)
//											endTime2 = endTimeMap.get(Long.valueOf(msgParts[1])-1);	
//										System.out.println(Long.valueOf(msgParts[1]) + " " + endTime + " " + endTime2);
									}
								}
								else {
									endTimeMap.put(Long.valueOf(msgParts[1]), recvdTime);
								}
								StartExperiment.messagesReceived++;
								if(StartExperiment.DEBUG) {
									try {
										msgQueue.put(content);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}

						@Override public void onError() {
							System.err.println("OBSERVING FAILED (press enter to exit)");
						}
					});

			// wait for user
			try { br.readLine(); } catch (IOException e) { }

			System.out.println("CANCELLATION");

			relation.proactiveCancel();
		}
	}
}