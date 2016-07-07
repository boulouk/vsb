package eu.chorevolution.vsb.gm.protocols.coap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;

public class BcCoapSubcomponent extends BcGmSubcomponent {
  private CoapClient client;
  private CoapServer server;  
  private CoapResource resource;
  
  public BcCoapSubcomponent(BcConfiguration bcConfiguration) {
    super(bcConfiguration);
    switch (this.bcConfiguration.getSubcomponentRole()) {
    case SERVER:
      server = new CoapServer(9090);
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
    Request request = CoapRequestBuilder.buildCoapGetRequest(destination, scope, datas);
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
    
    Request request = RestRequestBuilder.buildRestGetRequest(destination, scope, datas);
    Response response = this.client.handle(request);
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

}
