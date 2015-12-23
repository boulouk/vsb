package eu.chorevolution.vsb.gm.protocols.builders;

import java.io.IOException;

import javax.activation.UnsupportedDataTypeException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: instead of one class with multiple methods:
// could use a Factory + an interface ResponseBuilder  + concretes impl. as JsonResponseBuilder, XmlResponseBuilder etc.
public class ResponseBuilder {
  public static <T> T unmarshalObject(final String mediaType, final String serializedObject, final Class<T> responseClass) {
    switch (mediaType) {
    case "application/json":
      return ResponseBuilder.unmarshalObjectFromJson(serializedObject, responseClass);
    default: 
      // TODO find, or create, a better and more explicit exception, similar to 415 HTTP error ?
      // throw new UnsupportedDataTypeException();
      return null;
    }
  }
  
  private static <T> T unmarshalObjectFromJson(final String serializedObject, final Class<T> responseClass) {
    ObjectMapper mapper = new ObjectMapper();
    try {
        return (T) mapper.readValue(serializedObject, responseClass);
    } 
    catch (JsonParseException e) {
      e.printStackTrace();
    }
    catch (JsonMappingException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null; // TODO: handle exceptions 
  }
}