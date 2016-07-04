package eu.chorevolution.vsb.bc.manager;

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

public class BcManagerRestService {
  
//  private BcGmSubcomponent endpointApi;
//  private BcGmSubcomponent clientApi;

  private Component component;
  private Server server;  
  private Boolean serverOnline = false;

  public BcManagerRestService(final int port) {
    this.server = new Server(Protocol.HTTP, port);
    this.component = new Component();
    this.component.getServers().add(server);
//    this.component.getDefaultHost().attach("/configure", SetConfiguration.class);
//    this.component.getDefaultHost().attach("/power/" + "{power}", ToggleBC.class);
    this.component.getDefaultHost().attach("/interface/" + "{interface}", InterfaceDesc.class);
    try {
      this.component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void startServer() {
    if (!this.serverOnline) {
      try {
        this.component.start();
      } catch (Exception e) {
        e.printStackTrace();
      }      
    }
  }
  
  public static class ToggleBC extends ServerResource {
    @Override
    protected Representation get() throws ResourceException {
      String power = (String) this.getRequestAttributes().get("power");
      String returnMessage = "";
      switch(power) {
      case "on":
        returnMessage = "Binding component turned on successfully!";
        break;
      case "off":
        returnMessage = "Binding component turned off successfully!";
        break;
      }
      return new StringRepresentation(returnMessage);
    }
  }

  public static class SetConfiguration extends ServerResource {
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
    bcmanager.startServer();
  }

 
}