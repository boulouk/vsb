package eu.chorevolution.vsb.gm.protocols.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
    String json = null;
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      json = ow.writeValueAsString(datas.get(0).getObject());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println("rec: " + json.toString());
//    Request request = new Request(Method.POST, destination + scope);
    json = (String)datas.get(0).getObject();
    String[] values = json.split(",");
    JSONObject obj = new JSONObject();
    if(values.length>1) {
      obj.put("id", json.split(",")[0]);
      obj.put("value", json.split(",")[1]);
//      ConversionWindow.center.setVisible(true);
//      ConversionWindow.right.setText(String.format("<html><div WIDTH=%d>%s</div><div WIDTH=%d>%s</div><html>", 83, obj.toJSONString().substring(0, 13),90,obj.toJSONString().substring(13)));
      
    }
    System.out.println("rec: " + obj.toJSONString());
//    request.setEntity(json, new StringRepresentation((String)obj.toJSONString());
  
    Request request = new Request(Method.POST, destination + scope, new StringRepresentation((String)obj.toJSONString()));
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