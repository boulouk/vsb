package eu.chorevolution.vsb.gmdl.utils.parsers;

import eu.chorevolution.vsb.gmdl.utils.GmComponentRepresentation;

// TODO NOTE: could be refactor with a Factory+Strategy Pattern
// OR, at least, one interface+concretes implementation for each description format
public class ComponentDescriptionParser {

  public static final GmComponentRepresentation getReprensentationFromGMDL(String gmdl) {
//    ParseGMDL gmdlParser = new ParseGMDL();
//    return gmdlParser.parse(gmdl);
    return null;
  }
  
  public static final GmComponentRepresentation getReprensentationFromWSDL(String wsdl) {
    // TODO
    return null;
  }
  
  public static final GmComponentRepresentation getReprensentationFromWADL(String wadl) {
    // TODO
    return null;
  }
  
  public static final GmComponentRepresentation getReprensentationFromSwaggerJson(String swaggerJson) {
    // TODO
    return null;
  }
  
  public static final GmComponentRepresentation getReprensentationFromSwaggerYaml(String swaggerYaml) {
    // TODO
    return null;
  }
}