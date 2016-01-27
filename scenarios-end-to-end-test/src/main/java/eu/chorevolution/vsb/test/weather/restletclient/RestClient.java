/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.chorevolution.vsb.test.weather.restletclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.UriBuilder;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author boulouka
 */
public class RestClient {

	private Client client;
	// private String uri = "http://localhost:8282/traffic-lights/{id}";

	// private String uri =
	// "http://93.62.202.227/mes/get_metadata_in_area?collection={collection}&period={period}&lat={latitude}&lon={longitude}&radius={radius}";

	private String uri = "http://93.62.202.227/mes/get_metadata_in_area?collection={collection}&period={period}";

	public static void main(String[] args) throws JsonMappingException,
			JsonParseException {
		RestClient rest = new RestClient();
		rest.getResponse();
	}

	public Response getResponse() throws JsonMappingException, JsonParseException {

		this.client = new Client(Protocol.HTTP);
		try {
			this.client.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Request request = new Request();

		UriBuilder builder = UriBuilder.fromPath(this.uri);

		// builder.resolveTemplate("id", "1");

		builder.resolveTemplate("collection", "weather");
		builder.resolveTemplate("period", "3600");
		// builder.resolveTemplate("latitude", "44.41016");
		// builder.resolveTemplate("longitude", "8.92637");
		// builder.resolveTemplate("radius", "1.5");

		// builder.buildFromEncoded("%20");

		// request.setResourceRef(builder.toString());

		try {
			request.setResourceRef(java.net.URLDecoder.decode(builder.toString(),
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setMethod(Method.GET);

		Response response = this.client.handle(request);
		System.out.println(response.getEntityAsText());

		String serializedObject = response.getEntityAsText();
		ObjectMapper mapper = new ObjectMapper();

		try {

			// Convert JSON string to Object
			ComplexType Ti = mapper.readValue(serializedObject, ComplexType.class);
			System.out.println(Ti.getstatus());

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

}
