package eu.chorevolution.vsb.manager;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import eu.chorevolution.vsb.gm.protocols.Manageable;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

public class BcManagerRestService implements Manageable {

  private Component component;
  private Server server;  
  private Boolean serverOnline = false;

  public BcManagerRestService(final int port) {
    this.server = new Server(Protocol.HTTP, port);
    this.component = new Component();
    this.component.getServers().add(server);
    this.component.getDefaultHost().attach("/getconfiguration", GetConfiguration.class);
    this.component.getDefaultHost().attach("/setconfiguration", SetConfiguration.class);
    try {
      this.component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }      
  }

  static class GetConfiguration extends ServerResource {
    @Override
    protected Representation post(Representation entity) throws ResourceException {
      String receivedText = null;
      try {
        receivedText = entity.getText();
      } catch (IOException e1) {
        e1.printStackTrace();
      }

      JSONParser parser = new JSONParser();
      JSONObject jsonObject = null;

      try {
        jsonObject = (JSONObject) parser.parse(receivedText);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      try (FileWriter file = new FileWriter("")) {
        file.write(jsonObject.toJSONString());
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("rec: " + receivedText);

      String returnMessage = "";
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }
  }

  static class SetConfiguration extends ServerResource {
    @Override
    protected Representation post(Representation entity) throws ResourceException {
      String receivedText = null;
      try {
        receivedText = entity.getText();
      } catch (IOException e1) {
        e1.printStackTrace();
      }

      JSONParser parser = new JSONParser();
      JSONObject jsonObject = null;

      try {
        jsonObject = (JSONObject) parser.parse(receivedText);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      try (FileWriter file = new FileWriter("")) {
        file.write(jsonObject.toJSONString());
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("rec: " + receivedText);

      String returnMessage = "";
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }
  }

  public static void main(String[] args) {
    BcManagerRestService bcmanager = new BcManagerRestService(1111);
  }

  @Override
  public void start() {
    try {
      this.component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    try {
      this.component.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
