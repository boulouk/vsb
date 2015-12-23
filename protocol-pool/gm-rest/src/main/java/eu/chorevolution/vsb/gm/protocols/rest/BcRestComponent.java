package eu.chorevolution.vsb.gm.protocols.rest;

import java.util.List;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmComponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;

public class BcRestComponent extends BcGmComponent {
  private Client client;
  
  public BcRestComponent(BcConfiguration bcConfiguration) {
    super(bcConfiguration);
  }

  @Override
  public void start() {
    this.client = new Client(Protocol.HTTP);
    try {
      this.client.start();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
  
  @Override
  public void stop() {
    try {
      this.client.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void postOneway(final String destination, final String scope, final List<Data<?>> datas, final long lease) {
    Request request = RestRequestBuilder.buildRestPostRequest(destination, scope, datas);
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