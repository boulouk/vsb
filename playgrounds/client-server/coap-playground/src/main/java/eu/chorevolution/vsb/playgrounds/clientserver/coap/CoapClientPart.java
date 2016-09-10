package eu.chorevolution.vsb.playgrounds.clientserver.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 *
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * Created: Oct 14, 2015
 * Description: 
 */
public class CoapClientPart {
    
    private CoapClient client;
    private Boolean clientOnline = false;
    
    public CoapClientPart() {
        
    }
    
    public void startClient (String destination, String resource) {
//        if(!clientOnline) {
            // Create a client
            client = new CoapClient(destination+resource);
//        }
    }
    
    public void post_oneway(String destination, String scope, String dataPost, long lease){
        startClient(destination, scope);
        // Set the URI the client will connect
        client.setURI(destination+"/"+scope);   
        
        
        System.err.println("Client sent:" + dataPost);
        CoapResponse response = client.post(dataPost, MediaTypeRegistry.APPLICATION_JSON);
        xtget(response);
    }
    
    public void post_twoway(String destination, String scope, String dataPost, long lease){
        startClient(destination, scope);
        
        client.setURI(destination+"/"+scope);   
        System.err.println("Client requested:" + dataPost);
        CoapResponse response = client.post(dataPost, MediaTypeRegistry.TEXT_PLAIN);
        
//        CoapResponse response = client.get(MediaTypeRegistry.TEXT_PLAIN);
        xtget(response);
    }
    
    public void xtget(Object response) {
      if (response instanceof CoapResponse)
        System.err.println("Client responded:" + ((CoapResponse)response).getResponseText());
      
    }
		   
}
