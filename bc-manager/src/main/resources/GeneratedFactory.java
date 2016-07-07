
package eu.chorevolution.vsb.bindingcomponent.generated;

import java.io.File;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;

import eu.chorevolution.vsb.bc.manager.BcManagerRestService;
import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Constants;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;

public class GeneratedFactory {


  GmServiceRepresentation gmServiceRepresentation = null;
  BcGmSubcomponent[][] component = null;
  
  
  public void run() {
    java.lang.Integer intFive;
    intFive = Integer.parseInt("5");
    java.lang.Integer intOne;
    intOne = Integer.parseInt("1");
    java.lang.Integer intNine;
    intNine = Integer.parseInt("9");

    String interfaceDescriptionPath;
    interfaceDescriptionPath = ((((new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(intNine)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath()+ File.separator)+ new String("config"))+ File.separator)+ new String("gidl.gidl"));
    String extension = ""; 
    String[] interfaceDescPieces = interfaceDescriptionPath.split("\\.");
    extension = interfaceDescPieces[interfaceDescPieces.length-1];
    JInvocation getInterfaceRepresentation = null;
    switch(extension) {
    case "gmdl":
      gmServiceRepresentation = ServiceDescriptionParser.getRepresentationFromGMDL(interfaceDescriptionPath); 
    case "gidl":
      gmServiceRepresentation = ServiceDescriptionParser.getRepresentationFromGIDL(interfaceDescriptionPath);
    }
    
    
    
    component = new BcGmSubcomponent[gmServiceRepresentation.getInterfaces().size()][2];
    
    for (int i = 0; (i<gmServiceRepresentation.getInterfaces().size()); i += 1) {
      Interface inter = null;
      inter = gmServiceRepresentation.getInterfaces().get(i);
      RoleType busRole;
      if (inter.getRole() == RoleType.SERVER) {
        busRole = RoleType.CLIENT;
      } else {
        busRole = RoleType.SERVER;
      }
      BcConfiguration bcConfiguration1 = new BcConfiguration();
      BcConfiguration bcConfiguration2 = new BcConfiguration();
      bcConfiguration1 .setSubcomponentRole(inter.getRole());
      bcConfiguration2 .setSubcomponentRole(busRole);
      bcConfiguration1 .parseFromJSON((((((new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(intNine)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath()+ File.separator)+ new String("config"))+ File.separator)+ new String("config_block1_interface_"))+ String.valueOf((i + intOne))));
      bcConfiguration2 .parseFromJSON((((((new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(intNine)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath()+ File.separator)+ new String("config"))+ File.separator)+ new String("config_block2_interface_"))+ String.valueOf((i + intOne))));
     
      component[i][0] = new BcSoapSubcomponent(bcConfiguration1);
      
      switch(gmServiceRepresentation.getProtocol()) {
      case REST:
        component[i][1] = new BcRestSubcomponent(bcConfiguration2);
        break;
      case SOAP:
        component[i][1] = new BcSoapSubcomponent(bcConfiguration2);
        break;
      case MQTT:
        component[i][1] = new BcMQTTSubcomponent(bcConfiguration2);
        break;
      }
      
      component[i][0].setNextComponent(component[i][1]);
      component[i][1].setNextComponent(component[i][0]);
      component[i][0].start();
      component[i][1].start();
    }
  }

  public void pause() {
    for (int i = 0; (i<gmServiceRepresentation.getInterfaces().size()); i += 1) {
      component[i][0].stop();
      component[i][1].stop();
    }
  }
}
