package eu.chorevolution.vsb.gm.protocols.dpws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.types.QName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import eu.chorevolution.vsb.gm.protocols.Manageable;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;

public class BcDPWSSubcomponent extends BcGmSubcomponent {

	private DPWSDevice device = null;
	private DPWSClient client = null;

	public BcDPWSSubcomponent(BcConfiguration bcConfiguration, GmServiceRepresentation serviceRepresentation) {
		super(bcConfiguration);
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:
			JMEDSFramework.start(null);
			device = new DPWSDevice();
			final DPWSService service = new DPWSService(this);
			device.addService(service);
			break;
		case CLIENT:   
			client = new DPWSClient();
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
				device.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case CLIENT:   
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
				device.stop();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case CLIENT:   
			break;
		default:
			break;
		}
	}

	@Override
	public void postOneway(final String destination, final String scope, final List<Data<?>> datas, final long lease) {
		client.sendRequest(scope, datas);
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
		return (T) client.sendRequest(scope, datas);
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
