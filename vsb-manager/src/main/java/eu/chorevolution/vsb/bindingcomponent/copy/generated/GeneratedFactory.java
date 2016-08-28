
package eu.chorevolution.vsb.bindingcomponent.copy.generated;

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


    public static void run() {
        java.lang.Integer intOne;
        intOne = Integer.parseInt("1");
        GmServiceRepresentation gmServiceRepresentation = null;
        String interfaceDescFilePath;
        interfaceDescFilePath = "/home/siddhartha/Downloads/chor/evolution-service-bus/bc-manager/target/classes/DtsGoogle.gidl";
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
            bcConfiguration1 .parseFromJSON((new String("/home/siddhartha/Downloads/chor/evolution-service-bus/vsb-manager/src/main/java/eu/chorevolution/vsb/bindingcomponent/generated/config_block1_interface_")+ String.valueOf((i + intOne))));
            bcConfiguration2 .parseFromJSON((new String("/home/siddhartha/Downloads/chor/evolution-service-bus/vsb-manager/src/main/java/eu/chorevolution/vsb/bindingcomponent/generated/config_block2_interface_")+ String.valueOf((i + intOne))));
            BcGmSubcomponent block1Component = new BcSoapSubcomponent(bcConfiguration1, gmServiceRepresentation);
            BcGmSubcomponent block2Component = new BcRestSubcomponent(bcConfiguration2, gmServiceRepresentation);
            block1Component.setNextComponent(block2Component);
            block2Component.setNextComponent(block1Component);
            block1Component.start();
            block2Component.start();
        }
    }

}
