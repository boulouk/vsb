package eu.chorevolution.vsb.bcs.weather.bc;

import java.util.ArrayList;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import eu.chorevolution.vsb.gm.protocols.builders.ResponseBuilder;
import eu.chorevolution.vsb.gm.protocols.primitives.BcGmComponent;
import eu.chorevolution.vsb.gmdl.utils.Data;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.StringRepresentation;


/**
 * This class was generated by the CHOReVOLUTION BindingComponent Generator using com.sun.codemodel 2.6
 * 
 */
@WebService(serviceName = "BindingComponent", targetNamespace = "eu.chorevolution.vsb.bcs.weather.bc")
public class BindingComponent {

    private final BcGmComponent apiRef;
    private Client client;
    private String uri;

    public BindingComponent(BcGmComponent apiRef) {
        this.apiRef = apiRef;
        
        this.client = new Client(Protocol.HTTP);
        this.uri = "http://127.0.0.1:8585/getmessage";
    }

    @WebMethod
    public RootClass getMeteoInfoByArea(Integer period, Double latitude, Double longitude, Double radius) {
        List<Data<?>> datas = new ArrayList<Data<?>>();
        datas.add(new Data<Integer>("period", "Integer", true, period, "PATH"));
        datas.add(new Data<Double>("latitude", "Double", true, latitude, "PATH"));
        datas.add(new Data<Double>("longitude", "Double", true, longitude, "PATH"));
        datas.add(new Data<Double>("radius", "Double", true, radius, "PATH"));
        
        String request = "Request to REST for parameters:  " + "period: " + period + ", latitude: " + latitude + ", longitude: " + longitude + " and radius: " + radius;
        
        Request restRequest = new Request(Method.POST, this.uri, new StringRepresentation(request));
        this.client.handle(restRequest);
        System.out.println(request);
        
        String serializedrootClass = this.apiRef.mgetTwowaySync("/mes/get_metadata_in_area?collection=weather&period={period}&lat={latitude}&lon={longitude}&radius={radius}", datas);
        
        String response = "Response from REST: " + serializedrootClass;
        restRequest = new Request(Method.POST, this.uri, new StringRepresentation(response));
        this.client.handle(restRequest);
        System.out.println(response);
        
        return ResponseBuilder.unmarshalObject("application/json", serializedrootClass, RootClass.class);
    }

    @WebMethod
    public RootClass getMeteoInfo(Integer period) {
        List<Data<?>> datas = new ArrayList<Data<?>>();
        datas.add(new Data<Integer>("period", "Integer", true, period, "PATH"));
        
        String request = "Request to REST for parameters:  " + "period: " + period;
        Request restRequest = new Request(Method.POST, this.uri, new StringRepresentation(request));
        this.client.handle(restRequest);
        System.out.println(request);
        
        String serializedrootClass = this.apiRef.mgetTwowaySync("/mes/get_metadata_in_area?collection=weather&period={period}", datas);
        
        String response = "Response from REST: " + serializedrootClass;
        restRequest = new Request(Method.POST, this.uri, new StringRepresentation(response));
        this.client.handle(restRequest);
        System.out.println(response);
        
        return ResponseBuilder.unmarshalObject("application/json", serializedrootClass, RootClass.class);
    }

}
