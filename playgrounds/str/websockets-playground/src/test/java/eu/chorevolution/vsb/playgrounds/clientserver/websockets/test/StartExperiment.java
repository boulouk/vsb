/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.chorevolution.vsb.playgrounds.clientserver.websockets.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.Parameters;
import eu.chorevolution.vsb.playgrounds.clientserver.websockets.utils.ToggleByExpDist;

public class StartExperiment {

	public static boolean experimentRunning = true;
	
	public static void main(String[] args) throws Exception {

		StartServer server = new StartServer();
		server.start();

		StartSourceApplication source = new StartSourceApplication(server.server);
		new Thread((Runnable)source).start();
		
		StartClient client = new StartClient();
		client.connect();
		new Thread((Runnable)client).start();
		
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				StartExperiment.experimentRunning = false;

				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex) {
					Logger.getLogger(StartExperiment.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}, Parameters.experimentDuration);

	}

}