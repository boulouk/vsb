package eu.chorevolution.vsb.playgrounds.clientserver.coap.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import org.eclipse.californium.core.CoapServer;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.ObservableServer;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Parameters;


public class NetworkToggle implements Runnable {

	public static Exp onParameter = new Exp(Parameters.onParam);
	public static Exp offParameter = new Exp(Parameters.offParam);

	public void run() {
		boolean localFlag = true;
		while (StartExperiment.experimentRunning) {
			System.out.println("ge");
			if(localFlag == true) {
				System.err.println("UP!!");
				localFlag = false;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ipconfig lo0 up");
					}
					else {
						// https://github.com/tylertreat/comcast
						String[] cmd = {"/bin/bash","-c","echo qqq_04| sudo -Sk /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device lo --stop"};
//						
//						Runtime.getRuntime ().exec ("sudo /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device=eth0 --latency=250 --target-bw=1000 --default-bw=1000000 --packet-loss=100% --target-addr=192.168.0.101 --target-proto=tcp,udp,icmp --target-port=8090");
						Runtime.getRuntime ().exec (cmd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				long param = (long) onParameter.next();
				System.err.println("here " + param);
				try {
					Thread.sleep(param * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				System.err.println("DOWN!! " + System.nanoTime());
				localFlag = true;
				try {
					if(System.getProperty("os.name").startsWith("Windows")) {
						Runtime.getRuntime ().exec ("ipconfig lo0 down");
					}
					else {
						// https://github.com/tylertreat/comcast
						String[] cmd = {"/bin/bash","-c","echo qqq_04| sudo -Sk /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --device=lo --latency=250 --target-bw=1000 --default-bw=1000000 --packet-loss=100% --target-addr=127.0.0.1 --target-proto=tcp,udp,icmp --target-port=8787"};
//						Runtime.getRuntime ().exec ("sudo /Users/Siddhartha/Documents/Academics/8thSem/go/bin/comcast --stop");
						Runtime.getRuntime ().exec (cmd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				long param = (long) offParameter.next();
				System.err.println("here " + param);
				try {
					Thread.sleep(param * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
