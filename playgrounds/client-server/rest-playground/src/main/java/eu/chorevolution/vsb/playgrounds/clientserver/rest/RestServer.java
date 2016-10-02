package eu.chorevolution.vsb.playgrounds.clientserver.rest;

import java.io.IOException;

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
 */
public class RestServer {

	private Component component;
	private Server server;  
	private Boolean serverOnline = false;

	public void startServer(final int port) {
		if (!this.serverOnline) {
			this.server = new Server(Protocol.HTTP, port);
			this.component = new Component();
			this.component.getServers().add(server);

			try {
				this.component.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void mget(String scope) {
		this.component.getDefaultHost().attach("/"+scope, RestServerResource.class);
		this.component.getDefaultHost().attach("/sensors/" + "{id}", RestServerResource.class);
	}

	public static class RestServerResource extends ServerResource {
		@Override
		protected Representation post(Representation entity) throws ResourceException {
			String receivedText = null;
			try {
				receivedText = entity.getText();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Server received: " + receivedText);
			//      System.err.println("Server responded: ss");
			return new StringRepresentation("ss");
		}

		@Override
		protected Representation get() throws ResourceException {
			String id = (String) this.getRequest().getAttributes().get("id");
			System.out.println("Id: " + id);
			return null;
		}
	}

}
