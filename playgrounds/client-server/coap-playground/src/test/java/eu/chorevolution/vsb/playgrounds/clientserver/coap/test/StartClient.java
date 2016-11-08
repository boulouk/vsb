package eu.chorevolution.vsb.playgrounds.clientserver.coap.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.ObserverClient;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Parameters;


public class StartClient {

	private ObserverClient client;

	public StartClient(HashMap<Long, Long> endTimeMap) {
		client = new ObserverClient(endTimeMap);
		System.out.println("fewfc");
		if(StartExperiment.DEBUG) { 
			new Thread(new MessageReader(endTimeMap)).start();
		}
	}
	
	class MessageReader implements Runnable {

		HashMap<Long, Long> endTimeMap = null;
		
		public MessageReader(HashMap<Long, Long> endTimeMap) {
			this.endTimeMap = endTimeMap;
		}
		
		@Override
		public void run() {
			String recvdMsg = null;
			while(StartExperiment.experimentRunning) {
				try {
					recvdMsg = client.msgQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String[] msgParts = recvdMsg.split(" ");
				Long msgNum = Long.valueOf(msgParts[1]);
				assert(msgNum != null);
				Long endTime;
				Long startTime;

				synchronized (endTimeMap) {
					endTime = endTimeMap.get(msgNum);	
				}

				synchronized (StartExperiment.startTimeMap) {
					startTime = StartExperiment.startTimeMap.get(msgNum);	
				}
				
//				System.out.println(msgNum);
//				System.out.println(endTimeMap);
//				System.out.println(endTime);
//				System.out.println(startTime);
//				if(endTime!=null && startTime!=null)
				System.out.println("Msg " + msgNum + " --> " + (endTime - startTime));
				//				System.out.println(recvdMsg + " " + Long.valueOf(msgParts[4]) + " " + (Long.valueOf(msgParts[4]) - Long.valueOf(msgParts[2])));
			}
		}

	}
	
}
