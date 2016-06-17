package eu.chorevolution.vsb.playgrounds.clientserver.soap.test;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.clientserver.soap.SoapServer;

public class StartServer {
  @Test
  public void StartServer() {
    SoapServer ss = new SoapServer("http://localhost:8080/");
    ss.publish("bookEndpoint",new BookService());  
  
  }
  
  public static void main(String[] args) {
    SoapServer ss = new SoapServer("http://localhost:8484/");
    ss.publish("bookEndpoint",new BookService());  
  }
}
