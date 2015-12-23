package eu.chorevolution.vsb.test.trafficlight.mock.restservice;

import java.util.HashMap;

public class LightPersistence extends HashMap<Integer, TrafficLight> {
  private static LightPersistence instance = null;

  private LightPersistence() {}

  public static LightPersistence getInstance() {
    if (instance == null) {
      instance = new LightPersistence();
      return instance;
    } else {
      return instance;
    }
  }
}