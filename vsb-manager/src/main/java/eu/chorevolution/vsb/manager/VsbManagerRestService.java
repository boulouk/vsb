package eu.chorevolution.vsb.manager;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

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

import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.gm.protocols.Manageable;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.manager.VsbManager;

public class VsbManagerRestService {

  private Component component;
  private Server server;  
  private Boolean serverOnline = false;

  public VsbManagerRestService(final int port) {
    this.server = new Server(Protocol.HTTP, port);
    this.component = new Component();
    this.component.getServers().add(server);
    this.component.getDefaultHost().attach("/interface", InterfaceDescPost.class);
    this.component.getDefaultHost().attach("/{interface}/{protocol}", InterfaceDescGet.class);
    try {
      this.component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static class InterfaceDescGet extends ServerResource {
    @Override
    protected Representation get() throws ResourceException {
      String interfacePath = (String) this.getRequestAttributes().get("interface");
      String protocol = (String) this.getRequestAttributes().get("protocol");
      String decodedURL = java.net.URLDecoder.decode(interfacePath);
      System.out.println(decodedURL + " " + protocol);

      ProtocolType busProtocol = null;
      switch(protocol.toUpperCase()) {
      case "REST":
        busProtocol = ProtocolType.REST;
        break;
      case "SOAP":
        busProtocol = ProtocolType.SOAP;
        break;
      case "MQTT":
        busProtocol = ProtocolType.MQTT;
        break;
      case "WEBSOCKETS":
        busProtocol = ProtocolType.WEB_SOCKETS;
        break;
      case "SEMI_SPACE":
        busProtocol = ProtocolType.SEMI_SPACE;
        break;
      case "JMS":
        busProtocol = ProtocolType.JMS;
        break;
      case "PUBNUB":
        busProtocol = ProtocolType.PUB_NUB;
        break;
      case "COAP":
        busProtocol = ProtocolType.COAP;
        break;
      case "ZERO_MQ":
        busProtocol = ProtocolType.ZERO_MQ;
        break;
      case "DPWS":
          busProtocol = ProtocolType.DPWS;
          break;
      }
      
      VsbManager vsbm = new VsbManager();
      vsbm.generateWar(decodedURL, busProtocol);
      String returnMessage = "Request forwarded!";
 
      return new StringRepresentation(returnMessage);
    }
  }

  public static class InterfaceDescPost extends ServerResource {
    @Override
    protected Representation post(Representation entity) throws ResourceException {
      String receivedText = null;
      try {
        receivedText = entity.getText();
      } catch (IOException e1) {
        e1.printStackTrace();
      }

      System.out.println("rec: " + receivedText);

      String[] arguments = receivedText.split(",");
      System.out.println(arguments[0]);
      System.out.println(arguments[1]);

      ProtocolType busProtocol = null;
      switch(arguments[1].toUpperCase()) {
      case "REST":
        busProtocol = ProtocolType.REST;
        break;
      case "SOAP":
        busProtocol = ProtocolType.SOAP;
        break;
      case "MQTT":
        busProtocol = ProtocolType.MQTT;
        break;
      case "WEBSOCKETS":
        busProtocol = ProtocolType.WEB_SOCKETS;
        break;
      case "SEMI_SPACE":
        busProtocol = ProtocolType.SEMI_SPACE;
        break;
      case "JMS":
        busProtocol = ProtocolType.JMS;
        break;
      case "PUBNUB":
        busProtocol = ProtocolType.PUB_NUB;
        break;
      case "COAP":
        busProtocol = ProtocolType.COAP;
        break;
      case "ZERO_MQ":
        busProtocol = ProtocolType.ZERO_MQ;
        break;
      case "DPWS":
          busProtocol = ProtocolType.DPWS;
          break;
      }

      VsbManager vsbm = new VsbManager();
      vsbm.generateWar(arguments[0], busProtocol);

      String returnMessage = "";
      returnMessage = "Received!";
      return new StringRepresentation(returnMessage);
    }
  }

  public static void main(String[] args) {
    VsbManagerRestService bcmanager = new VsbManagerRestService(1111);
  }

}