
package test;

import java.io.FileReader;
import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gm.protocols.rest.BcRestSubcomponent;
import eu.chorevolution.vsb.gm.protocols.soap.BcSoapSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GeneratedFactory {


    public static void run() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        java.lang.Integer intFive;
        intFive = Integer.parseInt("5");
        String configFilePath = BcManager.class.getClassLoader().getResource("config.json").toExternalForm().substring(intFive);
        try {
            FileReader fileReader = new FileReader(configFilePath);
            jsonObject = ((JSONObject) parser.parse(fileReader));
        } catch (Exception _x) {
        }
        GmServiceRepresentation gmComponentRepresentation = null;
        for (int i = 0; (i<gmComponentRepresentation.getInterfaces().size()); i += 1) {
            Interface inter;
            inter = gmComponentRepresentation.getInterfaces().get(i);
            eu.chorevolution.vsb.gmdl.utils.enums.RoleType busRole;
            if (inter.getRole() == eu.chorevolution.vsb.gmdl.utils.enums.RoleType.SERVER) {
                busRole = eu.chorevolution.vsb.gmdl.utils.enums.RoleType.CLIENT;
            } else {
                busRole = eu.chorevolution.vsb.gmdl.utils.enums.RoleType.SERVER;
            }
            BcConfiguration bcConfiguration1 = new BcConfiguration();
            BcGmSubcomponent block1Component = new BcSoapSubcomponent(bcConfiguration1);
            BcGmSubcomponent block2Component = new BcRestSubcomponent(bcConfiguration1);
            block1Component.setNextComponent(block2Component);
            block2Component.setNextComponent(block1Component);
            block1Component.start();
            block2Component.start();
        }
    }

}
