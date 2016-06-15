package eu.chorevolution.vsb.gm.protocols.primitives;

import java.util.List;

import eu.chorevolution.vsb.gm.protocols.Manageable;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;

public abstract class BcGmSubcomponent implements Manageable {
  
  protected BcGmSubcomponent nextComponent;
  protected final BcConfiguration bcConfiguration;
  
  public BcGmSubcomponent(BcConfiguration bcConfiguration) {
    this.bcConfiguration = bcConfiguration;
  }
  
  public void setNextComponent(BcGmSubcomponent nextComponent) {
    this.nextComponent = nextComponent; 
  }
  
  /* ------ one-way Sender ------ */
  
  /**
   * Method used for an one-way communication initialized by a sender. 
   * 
   * Using this method a sender sends some data to the peer of the destination 
   * IP address without expecting any response.
   * 
   * @param destination the IP address of the peer we wish to communicate
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param data the data we want to send which are type of class Data
   * @param lease for how long the data sent will be valid
   */
  public abstract void postOneway(final String destination, final String scope, final List<Data<?>> data, final long lease);
  
  /* ------ One-way Receiver ------ */
  
  /**
   * Method used for an one-way communication initialized by a receiver. 
   * 
   * Using this method a receiver is waiting multiple data to a specific scope.
   * 
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param exchange 
   */
  public abstract void mgetOneway(final String scope, final Object exchange);
  
  /**
   * Method used for an one-way communication initialized by a receiver.
   * 
   * Using this method a receiver is waiting multiple data to a specific scope by an exclusive source (sender).
   * 
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param source the IP address of the exclusive sender
   * @param exchange
   */
  public abstract void xmgetOneway(final String source, final String scope, final Object exchange);
  
  /* ------ Two-way Client-Synchronous ------ */
  
  /**
   * Method used for an two-way synchronous communication initialized by a client. 
   * 
   * Using this method a client sends some data to the peer of the destination 
   * IP address and is waiting until receiving a response.
   * 
   * @param destination the IP address of the peer we wish to communicate
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param data the data we want to send which are type of class Data
   * @param lease for how long the data sent will be valid. In this case must be 0
   * @return 
   */
  public abstract <T> T postTwowaySync(final String destination, final String scope, final List<Data<?>> datas, final long lease);
 
  /**
   * Method used for a two-way synchronous communication initialized by a client. (uncorrelated at the middleware layer)
   * 
   * Using this method a client is waiting (response) data for a timeout limited period to a specific scope by the exclusive destination (server).
   * 
   * @param destination the IP address of the exclusive destination (server)
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param timeout the time the server is waiting for the response
   * @param response 
   */
  public abstract void xtgetTwowaySync(final String destination, final String scope, final long timeout, final Object response);
  
  /* ------ Two-way Server-Synchronous ------ */
  
  /**
   * Method used for an two-way synchronous communication initialized by a server. 
   * 
   * Using this method a server is waiting multiple data to a specific scope.
   * 
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param exchange 
   * @return 
   */
  public abstract <T> T mgetTwowaySync(final String scope, final Object exchange);
  
  /**
   * Method used for an two-way synchronous communication initialized by a server. 
   * 
   * Using this method a server sends the response to the source (client) 
   * 
   * @param source the IP address of the peer we wish to communicate
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param data the data we want to send which are type of class Data
   * @param lease for how long the data sent will be valid. In this case must be 0
   * @param exchange 
   */
  //  public void postBackTwowaySync(String source, String scope, Data data, long lease, Object exchange);
  
  /* ------ Two-way Client-Asynchronous ------ */
  
  /**
   * Method used for an two-way asynchronous communication initialized by a client. 
   * 
   * Using this method a client sends some data to the peer of the destination 
   * IP address without waiting for a response. postTwowayAsync can be performed again
   * without a response of the previous. 
   * 
   * @param destination the IP address of the peer we wish to communicate
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param data the data we want to send which are type of class Data
   * @param lease for how long the data sent will be valid
   */
  public abstract void postTwowayAsync(final String destination, final String scope, final List<Data<?>> data, final long lease);
 
  /**
   * Method used for a two-way asynchronous communication initialized by a client.
   * 
   * Using this method a client will receive (response) data asynchronously to a specific scope by the exclusive destination (server).
   * 
   * @param destination the IP address of the exclusive destination (server)
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param response 
   */
  public abstract void xgetTwowayAsync(final String destination, final String scope, final Object response);
  
  /* ------ Two-way Server-Asynchronous ------ */
  
  /**
   * Method used for an two-way asynchronous communication initialized by a server. 
   * 
   * Using this method a server is waiting multiple data to a specific scope.
   * 
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param exchange 
   */
  public abstract void mgetTwowayAsync(final String scope, final Object exchange);
  
  /**
   * Method used for an two-way asynchronous communication initialized by a server. 
   * 
   * Using this method a server sends the response to the source (client) 
   * 
   * @param source the IP address of the peer we wish to communicate
   * @param scope the identifier of the corresponding operation (SOAP), or resource (REST, CoAP), or topic (Pub/Sub), or stream, or template (Tuplespace)
   * @param data the data we want to send which are type of class Data
   * @param lease for how long the data sent will be valid
   * @param exchange 
   */
  public abstract void postBackTwowayAsync(final String source, final String scope, final Data<?> data, final long lease, final Object exchange);
  
  /* ------ Stream Consumer ------ */
  
  /* ------ Stream Producer ------ */
}