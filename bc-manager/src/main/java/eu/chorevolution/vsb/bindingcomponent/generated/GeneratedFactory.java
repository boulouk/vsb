
package eu.chorevolution.vsb.bindingcomponent.generated;

import java.io.File;
import eu.chorevolution.vsb.bc.manager.BcManagerRestService;
import eu.chorevolution.vsb.gm.protocols.coap.BcCoapSubcomponent;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;

public class GeneratedFactory {

    BcGmSubcomponent[][] subcomponent;
    GmServiceRepresentation gmServiceRepresentation = null;

    public void run() {
        java.lang.Integer intFive;
        intFive = Integer.parseInt("5");
        java.lang.Integer intOne;
        intOne = Integer.parseInt("1");
        java.lang.Integer intNine;
        intNine = Integer.parseInt("9");
        String interfaceDescFilePath;
        interfaceDescFilePath = ((((new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(intNine)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath()+ File.separator)+ new String("config"))+ File.separator)+ new String("gidl.gidl"));
        gmServiceRepresentation = ServiceDescriptionParser.getRepresentationFromGIDL(interfaceDescFilePath);
        int num_interfaces = gmServiceRepresentation.getInterfaces().size();
        subcomponent = new BcGmSubcomponent[num_interfaces][2];
        for (int i = 0; (i<gmServiceRepresentation.getInterfaces().size()); i += 1) {
            Interface inter = null;
            inter = gmServiceRepresentation.getInterfaces().get(i);
            System.out.println("genfac start iteration");
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
            subcomponent[i][0] = new BcCoapSubcomponent(bcConfiguration1, gmServiceRepresentation);
            subcomponent[i][1] = new BcRestSubcomponent(bcConfiguration2, gmServiceRepresentation);
            BcGmSubcomponent block1Component = subcomponent[i][0];
            BcGmSubcomponent block2Component = subcomponent[i][1];
            block1Component.setNextComponent(block2Component);
            block2Component.setNextComponent(block1Component);
            block1Component.start();
            block2Component.start();
        }
    }

    public void pause() {
        for (int i = 0; (i<gmServiceRepresentation.getInterfaces().size()); i += 1) {
            System.out.println("genfac stop iteration");
            BcGmSubcomponent block1Component = subcomponent[i][0];
            BcGmSubcomponent block2Component = subcomponent[i][1];
            block1Component.stop();
            block2Component.stop();
        }
    }

}
