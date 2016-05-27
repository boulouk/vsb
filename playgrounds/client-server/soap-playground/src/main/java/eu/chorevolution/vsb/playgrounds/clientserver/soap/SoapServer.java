package eu.chorevolution.vsb.playgrounds.clientserver.soap;

import javax.xml.ws.Endpoint;

public class SoapServer {
  private String url;
  
  public SoapServer(String url) {
    this.url = url;
  }

  public void publish(String uri, Object implementor) {
    Endpoint.publish(url+uri, implementor);  
  }
}
