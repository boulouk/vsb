package eu.chorevolution.vsb.playgrounds.clientserver.websockets.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WsServer;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.Parameters;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.ToggleByExpDist;

public class StartSourceApplication implements Runnable {

	private WsServer wsServer = null;
	private long counter = 0; 
	
	public StartSourceApplication(WsServer wsServer) {
		this.wsServer = wsServer;
	}

	void sendMsg() {
		long currentTime = System.nanoTime();
		wsServer.send("Msg " + counter + " " + String.valueOf(currentTime));
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
