/**
 * 
 */
package eu.chorevolution.vsb.bcs.dtsgoogle.bc;

import java.io.IOException;
import java.util.ArrayList;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * 
 *         RestServer.java Created: 3 févr. 2016 Description:
 */
public class RestServer {

	private Component component;
	private Server server;
	private static ArrayList<String> list = null;

	public void start() {

		list = new ArrayList<String>();

		//TODO fix the ports
		this.server = new Server(Protocol.HTTP, 8585);
		this.component = new Component();
		this.component.getServers().add(server);

		try {
			this.component.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.component.getDefaultHost().attach("/getmessage", RestServerResource.class);
	}
	
	public void stop() {
		try {
			this.component.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class RestServerResource extends ServerResource {
		@Override
		protected Representation post(Representation entity) throws ResourceException {
			try {
				String message = entity.getText();
				System.out.println("Rest Server received: " + message);
				list.add(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new StringRepresentation("OK");
		}

		@Override
		protected Representation get() throws ResourceException {
			//TODO catch the exception: java.lang.IndexOutOfBoundsException
			if(list.isEmpty()) {
				return new StringRepresentation("empty");
			} else {
				String message = list.get(0);
				System.out.println(message);
				list.remove(0);
				return new StringRepresentation(message);
			}	
		}
	}
}
