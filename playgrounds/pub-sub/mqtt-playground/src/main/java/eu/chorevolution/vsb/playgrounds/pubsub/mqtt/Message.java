package eu.chorevolution.vsb.playgrounds.pubsub.mqtt;

public class Message {
  String topic;
  String message;
  public Message(String topic, String message) {
    this.topic = topic;
    this.message = message;
  }
  public String getMsg() {
    return message;
  }
}