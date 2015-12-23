package eu.chorevolution.vsb.test.trafficlight.mock.restservice;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/traffic-lights")
public class JaxRsTrafficLightResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postTrafficLight(TrafficLight light) {
    LightPersistence.getInstance().put(light.getId(), light);
    System.out.println("Resource created");
    try {
      return Response
          .status(Status.CREATED)
          .contentLocation(new URI("http://localhost:8282/traffic-lights/"+light.getId()))
          .build();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getTrafficLight(@PathParam("id") Integer id) {
    try {
      if (LightPersistence.getInstance().containsKey(id)) {
        GenericEntity<TrafficLight> light = new GenericEntity<TrafficLight>(LightPersistence.getInstance().get(id)) {};
        return Response.ok(light).build();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Response.status(Status.NO_CONTENT).build();
  }
}
