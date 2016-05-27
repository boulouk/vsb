package eu.chorevolution.vsb.playgrounds.clientserver.soap.test;

import eu.chorevolution.vsb.playgrounds.clientserver.soap.SoapServer;

public class StartServer {
  public static void main(String[] args) {
    
    SoapServer ss = new SoapServer("http://localhost:8080/");
    ss.publish("bookEndpoint",new BookService());  
  
  }
}
