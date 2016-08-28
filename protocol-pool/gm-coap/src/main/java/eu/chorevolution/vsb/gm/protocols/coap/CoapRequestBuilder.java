//package eu.chorevolution.vsb.gm.protocols.coap;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//
//import javax.ws.rs.core.UriBuilder;
//
//import eu.chorevolution.vsb.gm.protocols.builders.RequestBuilder;
//import eu.chorevolution.vsb.gmdl.utils.Data;
//import eu.chorevolution.vsb.gmdl.utils.Data.Context;
//
//public class CoapRequestBuilder implements RequestBuilder {
//	
//	String destination;
//	String scope;
//	List<Data<?>> datas;
//	
//	public CoapRequestBuilder(String destination, String scope, List<Data<?>> datas) {
//		this.destination = destination;
//		this.scope = scope;
//		this.datas = datas;
//	}
//	
//	public static Request buildCoapPostRequest(final String destination, final String scope, final List<Data<?>> datas) {
//		return buildCoapRequest(Method.POST, destination, scope, datas);
//	}
//
//	public static Request buildCoapGetRequest(final String destination, final String scope, final List<Data<?>> datas) {
//		return buildCoapRequest(Method.GET, destination, scope, datas);
//	}
//
//	private static Request buildCoapRequest(final Method method, final String destination, final String scope, final List<Data<?>> datas) {
//		Request request = new Request();
//
//
//		buildRequestHeaders(request, datas);
//		buildRequestQuery(request, datas);
//		buildRequestForm(request, datas);
//		buildRequestBody(request, datas);
//
//		request.setMethod(method);
//
//		return request;
//	}
//
//	private static void buildRequestBody(final Request request, final List<Data<?>> datas) {
//		for (Data<?> data : datas) {
//			if(data.getContext() == Context.BODY) {
//				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//				String json = null;
//				try {
//					json = ow.writeValueAsString(data.getObject());
//				} catch (JsonProcessingException e) {
//					e.printStackTrace();
//				}
//				request.setEntity(json, new MediaType(data.getMediaTypeAsString()));
//			}
//		}
//	}
//
//	private void buildRequestForm() {
//		for (Data<?> data : datas) {
//			if(data.getContext() == Context.FORM) {
//				// TODO
//			}
//		}
//	}
//
//	private void buildRequestQuery() {
//		System.out.println(datas.size());
//		for (Data<?> data : datas) {
//			System.out.println(data.getName() + " " + data.getObject().toString());
//			if(data.getContext() == Context.QUERY) {
//				System.out.println(data.getName() + " " + data.getObject().toString());
//				// TODO
//			}
//		}
//	}
//
//	String buildRequestPath() {
//		UriBuilder builder = UriBuilder.fromPath(destination+scope);
//		for (Data<?> data : datas) {
//			if(data.getContext() == Context.PATH) {
//				builder.resolveTemplate(data.getName(), data.getObject());
//			}
//		}
//		String decodedPath = "";
//		try {
//			decodedPath = java.net.URLDecoder.decode(builder.toString(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return decodedPath;
//	}
//
//	private void buildRequestHeaders() {
//		for (Data<?> data : datas) {
//			if(data.getContext() == Context.HEADER) {
//				// TODO
//			}
//		}
//	}
//}