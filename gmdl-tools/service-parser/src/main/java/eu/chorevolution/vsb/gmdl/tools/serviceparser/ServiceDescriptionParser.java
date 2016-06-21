package eu.chorevolution.vsb.gmdl.tools.serviceparser;

import eu.chorevolution.vsb.gmdl.tools.serviceparser.gmdl.ParseGMDL;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
// TODO NOTE: could be refactor with a Factory+Strategy Pattern
// OR, at least, one interface+concretes implementation for each description format
public class ServiceDescriptionParser {

  public static final GmServiceRepresentation getRepresentationFromGMDL(String gmdl) {
    ParseGMDL gmdlParser = new ParseGMDL();
    return gmdlParser.parse(gmdl);
  }
  
  public static final GmServiceRepresentation getRepresentationFromGIDL(String gidl) {
 // TODO
    return null;
  }

  
  public static final GmServiceRepresentation getRepresentationFromWSDL(String wsdl) {
    // TODO
    return null;
  }
  
  public static final GmServiceRepresentation getRepresentationFromWADL(String wadl) {
    // TODO
    return null;
  }
  
  public static final GmServiceRepresentation getRepresentationFromSwaggerJson(String swaggerJson) {
    // TODO
    return null;
  }
  
  public static final GmServiceRepresentation getRepresentationFromSwaggerYaml(String swaggerYaml) {
    // TODO
    return null;
  }
}