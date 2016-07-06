
package eu.chorevolution.vsb.bindingcomponent.copy.generated;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import eu.chorevolution.vsb.gm.protocols.builders.ResponseBuilder;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmSubcomponent;
import eu.chorevolution.vsb.gmdl.utils.Data;


/**
 * This class was generated by the CHOReVOLUTION BindingComponent Generator using com.sun.codemodel 2.6
 * 
 */
@WebService(serviceName = "BindingComponent", targetNamespace = "eu.chorevolution.vsb.bindingcomponent.generated")
public class BindingComponent {

    private final BcGmSubcomponent apiRef;

    public BindingComponent(BcGmSubcomponent apiRef) {
        this.apiRef = apiRef;
    }

    @WebMethod
    public Itinerary routeRequest(String arg0, String arg1) {
        List<Data<?>> datas = new ArrayList<Data<?>>();
        datas.add(new Data<String>("arg0", "String", true, arg0, "PATH"));
        datas.add(new Data<String>("arg1", "String", true, arg1, "PATH"));
        java.lang.String serializedroute = this.apiRef.mgetTwowaySync("/maps/api/directions/json?origin={arg0}&destination={arg1}&key=AIzaSyBhfNR1PHo8EsuxjLr3EO-sNnfoUDh4ilw", datas);
        return ResponseBuilder.unmarshalObject("application/json", serializedroute, Itinerary.class);
    }

}
