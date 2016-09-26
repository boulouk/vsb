package eu.chorevolution.vsb.gm.protocols.mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;

public class BcMQTTSubcomponent extends BcGmSubcomponent {

	BrokerService broker = null;
	MqttClient serverClient = null;
	private MqttClient client = null;
	GmServiceRepresentation serviceRepresentation = null;
	
	public BcMQTTSubcomponent(BcConfiguration bcConfiguration, GmServiceRepresentation serviceRepresentation) {
		super(bcConfiguration);
		this.serviceRepresentation = serviceRepresentation;
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:      
			broker = new BrokerService();
			try {
				broker.addConnector("mqtt://" + this.bcConfiguration.getSubcomponentAddress() + ":" + this.bcConfiguration.getSubcomponentPort());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			broker.setDataDirectory("target");
			try {
				serverClient = new MqttClient("tcp://"+this.bcConfiguration.getSubcomponentAddress()+":"+this.bcConfiguration.getSubcomponentPort(), "serverSubscriber");
			} catch (MqttException e1) {
				e1.printStackTrace();
			}
			serverClient.setCallback(new ServerSubscriberCallback());
			
			break;
		case CLIENT:
			try {
				client = new MqttClient("tcp://"+this.bcConfiguration.getServiceAddress()+":"+this.bcConfiguration.getServicePort(), "client");
			} catch (MqttException e) {
				e.printStackTrace();
			}
			client.setCallback(new SubscriberCallback());
			break;
		default:
			break;
		}

	}

	@Override
	public void start() {
		switch (this.bcConfiguration.getSubcomponentRole()) {
		case SERVER:      
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

			try {
				MqttConnectOptions options = new MqttConnectOptions();
				options.setCleanSession(false);
				serverClient.connect(options);
			} catch (MqttException e) {
				e.printStackTrace();
			}
			break;
		case CLIENT:
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(false);
			try {
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
			try {
				serverClient.close();
				broker.stop();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case CLIENT: 
			try {
				client.close();
			} catch (MqttException e) {
				e.printStackTrace();
			}
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

	private final class ServerSubscriberCallback implements MqttCallback {
		public ServerSubscriberCallback() {
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
			String receivedText = msg.toString();

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = null;

			try {
				jsonObject = (JSONObject) parser.parse(receivedText);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			for(Entry<String, Operation> en: serviceRepresentation.getInterfaces().get(0).getOperations().entrySet()) {
				if(en.getKey().equals(topic)) {
					Operation op = en.getValue();
					List<Data<?>> datas = new ArrayList<>();

					for(Data<?> data: op.getGetDatas()) {
						Data d = new Data<String>(data.getName(), "String", true, (String)jsonObject.get(data.getName()), data.getContext());
						datas.add(d);
						System.err.println("Added " + d);
					}
					if(op.getOperationType() == OperationType.TWO_WAY_SYNC) {
						String response = mgetTwowaySync(op.getScope().getUri(), datas);
						serverClient.publish(topic+"Reply", response.getBytes(), 0, true);
					}
					else if(op.getOperationType() == OperationType.ONE_WAY) {
						mgetOneway(op.getScope().getUri(), datas);
					}
				}
			}
		}

		@Override
		public void connectionLost(Throwable arg0) {
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}
	}
}