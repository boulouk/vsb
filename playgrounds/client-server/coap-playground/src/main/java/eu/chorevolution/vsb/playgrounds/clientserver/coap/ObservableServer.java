package eu.chorevolution.vsb.playgrounds.clientserver.coap;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;

import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.StartExperiment;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Exp;
import eu.chorevolution.vsb.playgrounds.clientserver.coap.test.utils.Parameters;

public class ObservableServer extends CoapResource {

	public static Long counter = 0L; 
	public static Exp waitDuration = new Exp(Parameters.msgPostParam);
	
	boolean firstCall = true;
	
	public ObservableServer(String name) {
		super(name);
		setObservable(true); // enable observing
		setObserveType(Type.NON); // configure the notification type to CONs
		getAttributes().setObservable(); // mark observable in the Link-Format
	}
	
	void sendMsg(CoapExchange exchange) {
		String msg = "Msg " + counter;
		if(StartExperiment.DEBUG) {
			synchronized (StartExperiment.startTimeMap) {
				StartExperiment.startTimeMap.put(counter, System.nanoTime());				
			}
		}
		else {
			StartExperiment.startTimeMap.put(counter, System.nanoTime());
		}
		exchange.respond(msg);
		counter++;
	}

	private class UpdateTask implements Runnable {
		@Override
		public void run() {
			while (StartExperiment.experimentRunning) {
				try {
					Thread.sleep((long)waitDuration.next() * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				sendMsg(false, null);
				changed();
			}
		}
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.setMaxAge(1000); // the Max-Age value should match the update interval
		sendMsg(exchange);
		if(firstCall) {
			firstCall = false;
			new Thread(new UpdateTask()).run();
		}
	}

	@Override
	public void handleDELETE(CoapExchange exchange) {
		delete(); // will also call clearAndNotifyObserveRelations(ResponseCode.NOT_FOUND)
		exchange.respond(DELETED);
	}

	@Override
	public void handlePUT(CoapExchange exchange) {
		exchange.respond(CHANGED);
	}

	public static void main(String[] args) {
		CoapServer server = new CoapServer();
		server.add(new ObservableServer("hello"));
		server.start();
	}

}