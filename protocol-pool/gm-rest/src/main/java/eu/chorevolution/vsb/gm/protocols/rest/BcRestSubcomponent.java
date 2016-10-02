package eu.chorevolution.vsb.gm.protocols.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.restlet.Client;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;

public class BcRestSubcomponent extends BcGmSubcomponent {
	private Client client;
	private Server server;  
	private Component component;

	private Component printerComponent;
	private Server printerServer; 
	private static String printermsg = "empty";

	public BcRestSubcomponent(BcConfiguration bcConfiguration, 
			GmServiceRepresentation serviceRepresentation) {
		super(bcConfiguration);
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			this.server = new Server(Protocol.HTTP, this.bcConfiguration.getSubcomponentAddress(), this.bcConfiguration.getSubcomponentPort());
			this.component = new Component();
			this.component.getServers().add(server);
			break;
		case CLIENT:   
			this.client = new Client(Protocol.HTTP);
			this.printerServer = new Server(Protocol.HTTP, 8585);
			this.printerComponent = new Component();
			this.printerComponent.getServers().add(printerServer);
			
			break;
		default:
			break;
		}
	}

	@Override
	public void start() {
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			try {
				this.component.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//      this.component.setDefaultHost(this.bcConfiguration.getSubcomponentAddress());
			this.component.getDefaultHost().attach("/", RestServerResource.class);
			break;
		case CLIENT:   
			try {
				this.client.start();
				this.printerComponent.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.printerComponent.getDefaultHost().attach("/getmessage", PrinterRestServerResource.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void stop() {
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			try {
				this.component.stop();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case CLIENT:   
			try {
				this.client.stop();
				this.printerComponent.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void postOneway(final String destination, final String scope, final List<Data<?>> datas, final long lease) {
		//    String json = null;
		//    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//    try {
		//      json = ow.writeValueAsString(datas.get(0).getObject());
		//    } catch (JsonProcessingException e) {
		//      e.printStackTrace();
		//    }
		//    System.out.println("rec: " + json.toString());
		//    //    Request request = new Request(Method.POST, destination + scope);
		//    json = (String)datas.get(0).getObject();
		//    String[] values = json.split(",");
		//    JSONObject obj = new JSONObject();
		//    if(values.length>1) {
		//      obj.put("id", json.split(",")[0]);
		//      obj.put("value", json.split(",")[1]);
		//    }
		//    System.out.println("rec: " + obj.toJSONString());
		//    //    request.setEntity(json, new StringRepresentation((String)obj.toJSONString());
		//
		//    Request request = new Request(Method.POST, destination + scope, new StringRepresentation((String)obj.toJSONString()));
		Request request = RestRequestBuilder.buildRestGetRequest(destination, scope, datas);
		this.client.handle(request);
	}

	@Override
	public void mgetOneway(final String scope, final Object exchange) {
		this.nextComponent.postOneway(this.bcConfiguration.getServiceAddress(), scope, (List<Data<?>>)exchange, 0);
	}

	@Override
	public void xmgetOneway(final String source, final String scope, final Object exchange) {
		this.nextComponent.postOneway(this.bcConfiguration.getServiceAddress(), scope, (List<Data<?>>)exchange, 0);
	}

	@Override
	public <T> T postTwowaySync(final String destination, final String scope, final List<Data<?>> datas, final long lease) {
		System.out.println("Destination: " + destination);
		System.out.println("Scope: " + scope);
		System.out.println("Datas: " + datas);
		Request request = RestRequestBuilder.buildRestGetRequest(destination, scope, datas);
		printermsg = (request.toString());
		System.out.println(request.toString());
		Response response = this.client.handle(request);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	    printermsg = (response.getEntityAsText());
		System.out.println(response.getEntityAsText());
		return (T) response.getEntityAsText();
	}

	@Override
	public void xtgetTwowaySync(final String destination, final String scope, final long timeout, final Object response) {
		// TODO Auto-generated method stub
	}

	@Override
	public <T> T mgetTwowaySync(final String scope, final Object exchange) {
		return this.nextComponent.postTwowaySync(this.bcConfiguration.getServiceAddress(), scope, (List<Data<?>>)exchange, 0);
	}

	@Override
	public void postTwowayAsync(final String destination, final String scope, final List<Data<?>> data, final long lease) {
		// TODO Auto-generated method stub
	}

	@Override
	public void xgetTwowayAsync(final String destination, final String scope, final Object response) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mgetTwowayAsync(final String scope, final Object exchange) {
		this.nextComponent.postTwowayAsync(this.bcConfiguration.getServiceAddress(), scope, (List<Data<?>>)exchange, 0);
	}

	@Override
	public void postBackTwowayAsync(final String source, final String scope, final Data<?> data, final long lease, final Object exchange) {
		// TODO Auto-generated method stub
	}

	public static class PrinterRestServerResource extends ServerResource {
		@Override
		protected Representation post(Representation entity) throws ResourceException {
			return new StringRepresentation("empty");
		}

		@Override
		protected Representation get() throws ResourceException {
			return new StringRepresentation(BcRestSubcomponent.printermsg);
		}

	}

	public static class RestServerResource extends ServerResource {
		//    @Override
		//    protected Representation post(Representation entity) throws ResourceException {
		//      String receivedText = null;
		//      try {
		//        receivedText = entity.getText();
		//      } catch (IOException e1) {
		//        e1.printStackTrace();
		//      }
		//      System.out.println("Server received: " + receivedText);
		////      System.err.println("Server responded: ss");
		//      return new StringRepresentation("ss");
		//    }

		@Get
		public org.restlet.Response receiveGet() {
			String remainingPart = getReference().getRemainingPart();
			Request req = getRequest();

			//TODO: Remove source. Not needed
			String source = req.getHostRef().getHostDomain();
			int port = req.getHostRef().getHostPort();

			Request request = getRequest();
			Map<String, Object> map = request.getAttributes();
			Object headers = map.get(HeaderConstants.ATTRIBUTE_HEADERS);
			int connectionTimeout = 60000;//Default value
			String callback = null;
			try {
				@SuppressWarnings("unchecked")
				Series<Header> headerSeries = (Series<Header>) headers;
				Header timeoutHeader = headerSeries.getFirst("connectionTimeout");
				if (timeoutHeader != null) {
					connectionTimeout = Integer.valueOf(timeoutHeader.getValue());
				}
				Header callbackHeader = headerSeries.getFirst("callbackURL");
				if (callbackHeader != null) {
					callback = callbackHeader.getValue();
				}
			} catch (ClassCastException e) {
				//            GLog.log.e(TAG, "Class cast exeption: " + e);
			} catch (NumberFormatException nfe) {
				//            GLog.log.e(TAG, "Class cast exeption: " + nfe);
			}

			//        Message_CS msg = new Message_CS("", 0, source, port, "GET", remainingPart, "", connectionTimeout, "", 0);
			//
			//        fr.inria.arles.lsb.commons.Response lsbResponse = null;
			//
			//        if (callback == null) {
			//            lsbResponse = connectorRef.invokeSync(msg);
			//        } else {
			//            lsbResponse = connectorRef.invokeAsync(msg);
			//        }

			org.restlet.Response restletResponse = null;
			//        restletResponse = getResponse();getResponse();
			//        restletResponse.setStatus(lsbResponse.get_respStatus());
			//        restletResponse.setEntity(new StringRepresentation(lsbResponse.getDataString()));
			return restletResponse;
		}


	}

}
