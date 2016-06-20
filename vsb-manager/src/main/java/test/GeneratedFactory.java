
package test;

import java.io.FileReader;
import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Interface;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GeneratedFactory {


    public static void run() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        String configFilePath = BcManager.getClassLoader().getResource("config.json").toExternalForm();
        try {
            FileReader fileReader = new FileReader(configFilePath);
            jsonObject = ((JSONObject) parser.parse(fileReader));
        } catch (Exception _x) {
        }
        GmServiceRepresentation gmComponentRepresentation;
        for (int i = 0; (i<gmComponentRepresentation.getInterfaces().size()); i += 1) {
            Interface inter;
            inter = gmComponentRepresentation.getInterfaces().get(i);
            BcGmSubcomponent block1Component;
            BcGmSubcomponent block2Component;
            eu.chorevolution.vsb.gmdl.utils.enums.RoleType busRole;
            if (inter.getRole() == eu.chorevolution.vsb.gmdl.utils.enums.RoleType.RoleType.SERVER) {
                busRole = eu.chorevolution.vsb.gmdl.utils.enums.RoleType.RoleType.CLIENT;
            } else {
                busRole = eu.chorevolution.vsb.gmdl.utils.enums.RoleType.RoleType.SERVER;
            }
            block1Component.setNextComponent(block2Component);
            block2Component.setNextComponent(block1Component);
            block1Component.start();
            block2Component.start();
        }
    }

}
