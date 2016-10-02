
package eu.chorevolution.vsb.bindingcomponent.copy.generated;

import java.io.File;
import java.io.PrintWriter;

import eu.chorevolution.vsb.bc.manager.BcManagerRestService;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;

public class GeneratedFactory {


    public static void run(PrintWriter writer) {
        java.lang.Integer intFive;
        intFive = Integer.parseInt("5");
        java.lang.Integer intOne;
        intOne = Integer.parseInt("1");
        java.lang.Integer intNine;
        intNine = Integer.parseInt("9");
        GmServiceRepresentation gmServiceRepresentation = null;
        String interfaceDescFilePath;
        interfaceDescFilePath = "/home/siddhartha/Downloads/chor/evolution-service-bus/bc-manager/src/main/resources/DtsGoogle.gidl";
        gmServiceRepresentation = ServiceDescriptionParser.getRepresentationFromGIDL(interfaceDescFilePath);
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
            bcConfiguration1 .parseFromJSON(gmServiceRepresentation, (((((new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(intNine)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath()+ File.separator)+ new String("config"))+ File.separator)+ new String("config_block1_interface_"))+ String.valueOf((i + intOne))));
            bcConfiguration2 .parseFromJSON(gmServiceRepresentation, (((((new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(intNine)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath()+ File.separator)+ new String("config"))+ File.separator)+ new String("config_block2_interface_"))+ String.valueOf((i + intOne))));
            BcGmSubcomponent block1Component = new BcSoapSubcomponent(bcConfiguration1, gmServiceRepresentation);
            BcGmSubcomponent block2Component = new BcRestSubcomponent(bcConfiguration2, gmServiceRepresentation);
            block1Component.setNextComponent(block2Component);
            block2Component.setNextComponent(block1Component);
            block1Component.start();
            block2Component.start();
        }
    }

}
