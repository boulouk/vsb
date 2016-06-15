package eu.chorevolution.vsb.gmdl.tools.parser.gmdl;

import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ParseGMDLTest {
  public static void main(String[] args) {
    ParseGMDL parser = new ParseGMDL();
    GmServiceRepresentation def = parser.parse("/home/siddhartha/Documents/gmdl/dts-google.json");// examples/MQTT-sensor-manager.gmdl.json
    System.out.println(def.getProtocol());
    System.out.println(def.getTypeDefinitions());
  }
}
