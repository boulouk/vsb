package eu.chorevolution.vsb.playgrounds.clientserver.soap;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class SoapClient {
  
  URL url = null;
  String namespace;
  String scope;
  String interfaceName;
  String link;
  
  public SoapClient(String uri, String namespace, String interfaceName, String link, String scope) {
    try {
      url = new URL(uri);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    this.namespace = namespace;
    this.scope = scope;
    this.interfaceName = interfaceName;
    this.link = link;
  }

  public Object start() {
    Class<?> localProxyInterface = null;
    try {
      localProxyInterface = Class.forName(namespace + "." + interfaceName);
    } catch (ClassNotFoundException ex) {
    }
    
    QName qName = new QName(link, scope);
    Service adapterService = Service.create(url, qName);
    Object bookProxy = adapterService.getPort(localProxyInterface);
    return bookProxy;
  }
  
}
