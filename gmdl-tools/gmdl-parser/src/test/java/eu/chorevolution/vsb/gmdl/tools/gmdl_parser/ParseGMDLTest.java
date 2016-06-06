package eu.chorevolution.vsb.gmdl.tools.gmdl_parser;

import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ParseGMDLTest {
  public static void main(String[] args) {
    ParseGMDL parser = new ParseGMDL();
    GmComponentRepresentation def = parser.parse("/home/siddhartha/Documents/gmdl/dts-google.json");// examples/MQTT-sensor-manager.gmdl.json
    System.out.println(def.getProtocol());
    System.out.println(def.getTypeDefinitions());
  }
}
