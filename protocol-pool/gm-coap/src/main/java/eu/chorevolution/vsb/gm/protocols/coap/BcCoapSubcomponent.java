package eu.chorevolution.vsb.gm.protocols.coap;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.spi.ServiceRegistry;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.Operation;

public class BcCoapSubcomponent extends BcGmSubcomponent {
	private CoapClient client;
	private CoapServer server;  
	private CoapResource resource;
	private GmServiceRepresentation serviceRepresentation;

	public BcCoapSubcomponent(BcConfiguration bcConfiguration, 
			GmServiceRepresentation serviceRepresentation) {
		super(bcConfiguration);
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			server = new CoapServer(this.bcConfiguration.getSubcomponentPort());
			this.serviceRepresentation = serviceRepresentation;
			break;
		case CLIENT:   
			this.client = new CoapClient();
			break;
		default:
			break;
		}
	}

	@Override
	public void start() {
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			server.start();
			server.add(getQueryListenerResource());
			break;
		case CLIENT:   
			break;
		default:
			break;
		}
	}

	CoapResource getQueryListenerResource() {
		CoapResource resource = new CoapResource("listener") {
			@Override
			public void handlePOST(CoapExchange exchange) {
				String receivedText = exchange.getRequestText();
				System.err.println("receivedText: " + exchange.getRequestText());

				JSONParser parser = new JSONParser();
				JSONObject jsonObject = null;

				try {
					jsonObject = (JSONObject) parser.parse(receivedText);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				String op_name = (String)jsonObject.get("op_name");

				for(Entry<String, Operation> en: serviceRepresentation.getInterfaces().get(0).getOperations().entrySet()) {
					if(en.getKey().equals(op_name)) {
						Operation op = en.getValue();
						List<Data<?>> datas = new ArrayList<>();

						for(Data<?> data: op.getGetDatas()) {
							Data d = new Data<String>(data.getName(), "String", true, (String)jsonObject.get(data.getName()), data.getContext());
							datas.add(d);
							System.err.println("Added " + d);
						}
						if(op.getOperationType() == OperationType.TWO_WAY_SYNC) {
							String response = mgetTwowaySync(op.getScope().getUri(), datas);
							exchange.accept();
							exchange.respond(response);	
						}
						else if(op.getOperationType() == OperationType.ONE_WAY) {
							mgetOneway(op.getScope().getUri(), datas);
							exchange.accept();
						}
					}
				}
			}
		};
		return resource;
	}

	@Override
	public void stop() {
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			server.stop();
			break;
		case CLIENT:   
			//      try {
			//        this.client.shutdown();
			//      } catch (Exception e) {
			//        e.printStackTrace();
			//      }
			break;
		default:
			break;
		}
	}

	@Override
	public void postOneway(final String destination, final String scope, final List<Data<?>> datas, final long lease) {
		//		Request request = CoapRequestBuilder.buildCoapGetRequest(destination, scope, datas);
		//		this.client.handle(request);
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

		//		Request request = RestRequestBuilder.buildRestGetRequest(destination, scope, datas);
		//		Response response = this.client.handle(request);
		//		return (T) response.getEntityAsText();
		return null;
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

}
