package eu.chorevolution.vsb.playgrounds.clientserver.soap;

import javax.xml.soap.*;

public class SoapSAAJClient {

  public static void main(String args[]) throws Exception {
    // Create SOAP Connection
    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
    SOAPConnection soapConnection = soapConnectionFactory.createConnection();

    // Send SOAP Message to SOAP Server
    String url = "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx";//http://localhost:8484/bookEndpoint
    SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

    // print SOAP Response
    System.out.print("Response SOAP Message:");
    soapResponse.writeTo(System.out);

    soapConnection.close();
  }

  private static SOAPMessage createSOAPRequest() throws Exception {
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    SOAPPart soapPart = soapMessage.getSOAPPart();

    String serverURI = "http://ws.cdyne.com/";//http://test.soap.clientserver.playgrounds.vsb.chorevolution.eu/

    // SOAP Envelope
    SOAPEnvelope envelope = soapPart.getEnvelope();
    envelope.addNamespaceDeclaration("example", serverURI);

    // SOAP Body
    SOAPBody soapBody = envelope.getBody();
    
    SOAPElement soapBodyElem = soapBody.addChildElement("VerifyEmail", "example");
    SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email", "example");
    soapBodyElem1.addTextNode("mutantninja@gmail.com");
    SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "example");
    soapBodyElem2.addTextNode("123");
    
//    MimeHeaders headers = soapMessage.getMimeHeaders();
//    headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");
    
//    SOAPElement soapBodyElem1 = soapBody.addChildElement("getBook", "example");
//    SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("arg0");
//    soapBodyElem2.addTextNode("2");

    
    soapMessage.saveChanges();

    /* Print the request message */
    System.out.print("Request SOAP Message:");
    soapMessage.writeTo(System.out);
    System.out.println();

    return soapMessage;
  }

}
