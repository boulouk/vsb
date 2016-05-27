package eu.chorevolution.vsb.playgrounds.clientserver.soap.test;

import eu.chorevolution.vsb.playgrounds.clientserver.soap.SoapClient;

public class StartClient {
  public static void main(String[] args) {
    SoapClient sc = new SoapClient("http://localhost:8080/bookEndpoint", "eu.chorevolution.vsb.playgrounds.clientserver.soap.test", 
        "BookServiceInt", "http://test.soap.clientserver.playgrounds.vsb.chorevolution.eu/", "bookServiceInt");  
    BookServiceInt bsi = (BookServiceInt)sc.start();
    System.out.println(bsi.getAuthor(5));
  }
}
