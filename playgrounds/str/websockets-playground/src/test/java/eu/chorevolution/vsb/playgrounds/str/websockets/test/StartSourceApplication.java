package eu.chorevolution.vsb.playgrounds.str.websockets.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.chorevolution.vsb.playgrounds.str.websockets.WsServer;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.Parameters;
import eu.chorevolution.vsb.playgrounds.str.websockets.test.utils.ToggleByExpDist;

public class StartSourceApplication implements Runnable {

	private WsServer wsServer = null;
	public static Long counter = 0L; 
	
	public StartSourceApplication(WsServer wsServer) {
		this.wsServer = wsServer;
	}

	void sendMsg() {
		String msg = "Msg " + counter;
		StartExperiment.startTimeMap.put(counter, System.nanoTime());
		wsServer.send(msg);
		counter++;
	}

	public void run() {

		Exp waitDuration = new Exp(Parameters.tGet);
		
		while (StartExperiment.experimentRunning) {
				sendMsg();
				try {
//					Thread.sleep((long)waitDuration.next());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

	}

}
