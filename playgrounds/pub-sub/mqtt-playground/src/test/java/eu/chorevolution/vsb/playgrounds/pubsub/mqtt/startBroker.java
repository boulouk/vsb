package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

public class startBroker {
  public static void main(String[] args) {
    Broker broker = new Broker();
    broker.start();
  }
}
