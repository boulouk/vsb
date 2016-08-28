package eu.chorevolution.vsb.gm.protocols.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.restlet.Request;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import eu.chorevolution.vsb.gm.protocols.builders.RequestBuilder;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;

public class RestRequestBuilder implements RequestBuilder {
  public static Request buildRestPostRequest(final String destination, final String scope, final List<Data<?>> datas) {
    return buildRestRequest(Method.POST, destination, scope, datas);
  }

  public static Request buildRestGetRequest(final String destination, final String scope, final List<Data<?>> datas) {
    return buildRestRequest(Method.GET, destination, scope, datas);
  }

  private static Request buildRestRequest(final Method method, final String destination, final String scope, final List<Data<?>> datas) {
    Request request = new Request();

    UriBuilder builder = UriBuilder.fromPath(destination+scope);
   
    buildRequestHeaders(request, datas);
    buildRequestPath(builder, datas);
    buildRequestQuery(request, datas);
    buildRequestForm(request, datas);
    buildRequestBody(request, datas);

    try {
      request.setResourceRef(java.net.URLDecoder.decode(builder.toString(), "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setMethod(method);

    return request;
  }

  private static void buildRequestBody(final Request request, final List<Data<?>> datas) {
    for (Data<?> data : datas) {
      if(data.getContext() == Context.BODY) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
          json = ow.writeValueAsString(data.getObject());
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        request.setEntity(json, new MediaType(data.getMediaTypeAsString()));
      }
    }
  }

  private static void buildRequestForm(final Request request, final List<Data<?>> datas) {
    for (Data<?> data : datas) {
      if(data.getContext() == Context.FORM) {
        // TODO
      }
    }
  }

  private static void buildRequestQuery(final Request request, final List<Data<?>> datas) {
    
    System.out.println(datas.size());
    for (Data<?> data : datas) {
      if(data.getContext() == Context.QUERY) {
        System.out.println(data.getName() + " " + data.getObject().toString() + " " + data.getContext());
      }
    }
    
    System.out.println(datas.size());
    for (Data<?> data : datas) {
      System.out.println(data.getName() + " " + data.getObject().toString());
      if(data.getContext() == Context.QUERY) {
        System.out.println(data.getName() + " " + data.getObject().toString());
        request.getResourceRef().addQueryParameter(data.getName(), data.getObject().toString());
      }
    }
  }

  private static void buildRequestPath(final UriBuilder builder, final List<Data<?>> datas) {
    for (Data<?> data : datas) {
      if(data.getContext() == Context.PATH) {
        builder.resolveTemplate(data.getName(), data.getObject());
      }
    }
  }

  private static void buildRequestHeaders(final Request request, final List<Data<?>> datas) {
    for (Data<?> data : datas) {
      if(data.getContext() == Context.HEADER) {
        // TODO
      }
    }
  }
}
