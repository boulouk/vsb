
package test;

import java.io.FileReader;
import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.tools.serviceparser.ServiceDescriptionParser;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GeneratedFactory {


    public static void run() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        java.lang.Integer intFive;
        intFive = Integer.parseInt("5");
        java.lang.Integer intOne;
        intOne = Integer.parseInt("1");
        String configFilePath = BcManager.class.getClassLoader().getResource("config.json").toExternalForm().substring(intFive);
        try {
            FileReader fileReader = new FileReader(configFilePath);
            jsonObject = ((JSONObject) parser.parse(fileReader));
        } catch (Exception _x) {
        }
        GmServiceRepresentation gmComponentRepresentation = null;
        String interfaceDescFilePath = BcManager.class.getClassLoader().getResource("dts-google1.json").toExternalForm().substring(intFive);
        gmComponentRepresentation = ServiceDescriptionParser.getRepresentationFromGMDL(interfaceDescFilePath);
        for (int i = 0; (i<gmComponentRepresentation.getInterfaces().size()); i += 1) {
            Interface inter = null;
            inter = gmComponentRepresentation.getInterfaces().get(i);
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
            bcConfiguration1 .parseFromJSON((new String("src/main/java/test/config_block1_interface_")+ String.valueOf((i + intOne))));
            bcConfiguration2 .parseFromJSON((new String("src/main/java/test/config_block2_interface_")+ String.valueOf((i + intOne))));
            BcGmSubcomponent block1Component = new BcSoapSubcomponent(bcConfiguration1);
            BcGmSubcomponent block2Component = new BcRestSubcomponent(bcConfiguration2);
            block1Component.setNextComponent(block2Component);
            block2Component.setNextComponent(block1Component);
            block1Component.start();
            block2Component.start();
        }
    }

}
