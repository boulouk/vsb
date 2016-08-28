package eu.chorevolution.vsb.playgrounds.clientserver.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 *
 * @author Georgios Bouloukakis (boulouk@gmail.com) Created: Oct 14, 2015
 * Description:
 */
public class CoapServerPart {

    private CoapServer server;
    private Boolean serverOnline = false;
    private CoapResource resource;

    public CoapServerPart() {
    }

    public void startServer() {
        if (!serverOnline) {
            // Create a server listening on port 9090
            server = new CoapServer(9082);

            // Start the server
            server.start();
        }

    }

    public void mgetOneway(String scope) {
        
        this.resource = new CoapResource(scope) {
            @Override
            public void handlePOST(CoapExchange exchange) {
                System.err.println("Oneway: " + exchange.getRequestText());
                exchange.accept();
            }
        };

        // Add resources to server
        server.add(resource);

    }

    //two-way
    public void mgetTwoway(String scope) {
        this.resource = new CoapResource(scope) {
            @Override
            public void handleGET(CoapExchange exchange) {
                System.err.println("Twoway: " + exchange.getRequestText());
                //exchange.accept();

                System.err.println("Server responded: ss");
                exchange.respond("ss");
            }
            
            @Override
            public void handlePOST(CoapExchange exchange) {
                System.err.println("Oneway: " + exchange.getRequestText());
                exchange.accept();
                exchange.respond("ss");
            }

        };
        // Add resources to server
        server.add(resource);
    }
}
