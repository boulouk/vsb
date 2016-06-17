package eu.chorevolution.vsb.gm.protocols.mqtt;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.ws.Endpoint;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;

import org.apache.activemq.broker.BrokerService;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class BcMQTTSubcomponent extends BcGmSubcomponent {

  private Endpoint endpoint;
  private MqttClient client;
  public BcMQTTSubcomponent(BcConfiguration bcConfiguration) {
    super(bcConfiguration);
  }

  @Override
  public void start() {
    switch (this.bcConfiguration.getSubcomponentRole()) {
    case SERVER:      
      BrokerService broker;
      BlockingConnection connection;
      broker = new BrokerService();
      try {
        broker.addConnector("mqtt://" + this.bcConfiguration.getSubcomponentAddress() + ":" + this.bcConfiguration.getSubcomponentPort());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      broker.setDataDirectory("target");
      try {
        broker.start();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      Runtime.getRuntime().addShutdownHook(new Thread(){
        @Override
        public void run() {
          try {
            broker.stop();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      MqttClient localclient;
      try {
        localclient = new MqttClient("tcp://"+this.bcConfiguration.getSubcomponentAddress()+":"+this.bcConfiguration.getSubcomponentPort(), "client");
        localclient.setCallback(new LocalSubscriberCallback());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        localclient.connect(options);
      } catch (MqttException e) {
        e.printStackTrace();
      }

      break;
    case CLIENT:  
      try {
        client = new MqttClient("tcp://"+this.bcConfiguration.getServiceAddress()+":"+this.bcConfiguration.getServicePort(), "client");
        client.setCallback(new SubscriberCallback());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        client.connect(options);
      } catch (MqttException e) {
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
      break;
    default:
      break;
    }
  }

  @Override
  public void postOneway(final String destination, final String scope, final List<Data<?>> data, final long lease) {
    // TODO Auto-generated method stub
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

  private final class LocalSubscriberCallback implements MqttCallback {

    public LocalSubscriberCallback() {
      super();
    }

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
      mgetOneway(topic, msg);
    }

    @Override
    public void connectionLost(Throwable arg0) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {

    }
  }
  
  private final class SubscriberCallback implements MqttCallback {

    public SubscriberCallback() {
      super();
    }

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
      mgetOneway(topic, msg);
    }

    @Override
    public void connectionLost(Throwable arg0) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {

    }
  }
}