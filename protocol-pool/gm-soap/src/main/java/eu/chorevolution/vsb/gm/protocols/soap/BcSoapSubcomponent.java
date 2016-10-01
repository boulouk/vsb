package eu.chorevolution.vsb.gm.protocols.soap;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Endpoint;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;

public class BcSoapSubcomponent extends BcGmSubcomponent {

  private Endpoint endpoint;
  private SOAPConnection soapConnection;
  private GmServiceRepresentation serviceRepresentation;

  public BcSoapSubcomponent(BcConfiguration bcConfiguration,
			GmServiceRepresentation serviceRepresentation) {
    super(bcConfiguration);
    this.serviceRepresentation = serviceRepresentation;
  }

  @Override
  public void start() {
    switch (this.bcConfiguration.getSubcomponentRole()) {
    case SERVER:
      Class<?> bc = null;
      try {
        bc = Class.forName(this.bcConfiguration.getTargetNamespace()+"."+this.bcConfiguration.getServiceName());
      } catch (ClassNotFoundException e1) {
        e1.printStackTrace();
      }
      try {
    	  String endpointAddress = this.bcConfiguration.getSubcomponentAddress()+":"+this.bcConfiguration.getSubcomponentPort()+"/"+this.bcConfiguration.getServiceName();
        this.endpoint = Endpoint.publish(endpointAddress, bc.getDeclaredConstructor(BcGmSubcomponent.class).newInstance(this));
        System.err.println("SOAP endpoint published on " + endpointAddress);
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (SecurityException e) {
        e.printStackTrace();
      }
      break;
    case CLIENT:
      SOAPConnectionFactory soapConnectionFactory = null;
      try {
        soapConnectionFactory = SOAPConnectionFactory.newInstance();
      } catch (UnsupportedOperationException | SOAPException e) {
        e.printStackTrace();
      }
      try {
        soapConnection = soapConnectionFactory.createConnection();
      } catch (SOAPException e) {
        e.printStackTrace();
      }
      break;
    default:
      break;
    }
  }

  @Override
  public void stop() {
    switch (this.bcConfiguration.getSubcomponentRole()) {
    case SERVER:
      if(this.endpoint.isPublished()) {
        this.endpoint.stop();
      }
      break;
    case CLIENT:
      try {
        soapConnection.close();
      } catch (SOAPException e) {
        e.printStackTrace();
      }
      break;
    default:
      break;
    }
  }

  @Override
  public void postOneway(final String destination, final String scope, final List<Data<?>> data, final long lease) {
    // TODO Auto-generated method stub
    String url = "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx";//http://localhost:8484/bookEndpoint
    
    MessageFactory messageFactory = null;
    try {
      messageFactory = MessageFactory.newInstance();
    } catch (SOAPException e) {
      e.printStackTrace();
    }
    SOAPMessage soapMessage = null;
    try {
      soapMessage = messageFactory.createMessage();
    } catch (SOAPException e) {
      e.printStackTrace();
    }
    SOAPPart soapPart = soapMessage.getSOAPPart();

    String serverURI = "http://ws.cdyne.com/";//http://test.soap.clientserver.playgrounds.vsb.chorevolution.eu/

    // SOAP Envelope
    SOAPEnvelope envelope = null;
    try {
      envelope = soapPart.getEnvelope();
    } catch (SOAPException e) {
      e.printStackTrace();
    }
    try {
      envelope.addNamespaceDeclaration("example", serverURI);
    } catch (SOAPException e) {
      e.printStackTrace();
    }

    // SOAP Body
    SOAPBody soapBody = null;
    try {
      soapBody = envelope.getBody();
    } catch (SOAPException e) {
      e.printStackTrace();
    }
    
    SOAPElement soapBodyElem = null;
    SOAPElement soapBodyElem1 = null;
    SOAPElement soapBodyElem2 = null;
    try {
      soapBodyElem = soapBody.addChildElement("VerifyEmail", "example");
      soapBodyElem1 = soapBodyElem.addChildElement("email", "example");
      soapBodyElem1.addTextNode("mutantninja@gmail.com");
      soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "example");
      soapBodyElem2.addTextNode("123");
    } catch (SOAPException e) {
      e.printStackTrace();
    }
    
//    MimeHeaders headers = soapMessage.getMimeHeaders();
//    headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");
    
    try {
      soapMessage.saveChanges();
      /* Print the request message */
      System.out.print("Request SOAP Message:");
      soapMessage.writeTo(System.out);
      System.out.println();

      SOAPMessage soapResponse = soapConnection.call(soapMessage, url);

      System.out.print("Response SOAP Message:");
      soapResponse.writeTo(System.out);
    } catch (SOAPException | IOException e) {
      e.printStackTrace();
    }
    
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
    // TODO Auto-generated method stub
    return null;
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