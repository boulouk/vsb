package eu.chorevolution.vsb.playgrounds.str.websockets.test.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.chorevolution.vsb.playgrounds.str.websockets.test.StartExperiment;

public class ToggleByExpDist implements Runnable {

	public volatile boolean indicator = false;

	private final Thread thread;
	
	Exp onParameter = null;
	Exp offParameter = null;

	long addition = 0;

	public ToggleByExpDist(Exp onParameter, Exp offParameter) {
		Runnable runnable = new ToggleByExpDist(onParameter, offParameter); // init Runnable
		thread = new Thread(runnable);// init thread object, but haven't started yet
		this.onParameter = onParameter;
		this.offParameter = offParameter;
	}

	public void start() {
		thread.start();
	}
	
	public void run() {
		toggleIndicator();
		while (StartExperiment.experimentRunning) {
			if (getIndicatorVal() == true) {
				try {
					System.out.println("hi ON");
					Thread.sleep(((long) onParameter.next()) * 1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(ToggleByExpDist.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else {
				try {
					System.out.println("hi OFF");
					long trand = (long) (offParameter.next());
					Thread.sleep(trand * 1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(ToggleByExpDist.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			toggleIndicator();
		}
		System.err.println("Average ON: " + onParameter.average());
		System.err.println("Average OFF: " + offParameter.average());
	}

	public Boolean getIndicatorVal() {
		return indicator;
	}

	public void toggleIndicator() {
		indicator = !indicator;
	}

}
