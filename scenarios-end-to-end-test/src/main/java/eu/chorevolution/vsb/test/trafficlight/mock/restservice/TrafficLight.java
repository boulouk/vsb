package eu.chorevolution.vsb.test.trafficlight.mock.restservice;

public class TrafficLight {
  private int id;
  private String address;
  private String status;

  public TrafficLight() {}

  public Integer getId() {
    return id;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
  
  public String getAddress() {
    return address;
  }
}