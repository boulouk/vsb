/**
 * 
 */
package eu.chorevolution.vsb.gm.protocols.mqtt;


import org.junit.Before;
import org.junit.Test;

import pl.ncdc.differentia.DifferentiaAssert;
import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gm.protocols.mqtt.BcSoapGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.gmdl.utils.enums.Verb;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 * BcSoapGeneratorDTSGoogleTest.java Created: 5 févr. 2016
 * Description:
 */
public class BcSoapGeneratorDTSGoogleTest {
	
	private BcSubcomponentGenerator soapGenerator;
	
	@Before
	public void initGenerator() {
		BcConfiguration compConfServer = new BcConfiguration();
		compConfServer.setSubcomponentRole("SERVER");
		compConfServer.setServiceAddress("https://maps.googleapis.com");
		compConfServer.setGeneratedCodePath("src/test/resources/generated/dtsgoogle");
		compConfServer.setTargetNamespace("");

		GmServiceRepresentation serviceDefinition = new GmServiceRepresentation();
		serviceDefinition.setProtocol(ProtocolType.REST);

		/* Types Definitions */
		Data<?> rootClass = new Data<>("rootClass", "RootClass", false, "application/json", Context.BODY, true);

		Data<?> geocoded_waypoints = new Data<>("geocoded_waypoints", "GeocodedWayPoints", false, "application/json", Context.PATH, true);
		Data<?> routes = new Data<>("routes", "Routes", false, "application/json", Context.PATH, true);
		Data<?> status = new Data<>("status", "String", true, "application/json", Context.PATH, true);

		rootClass.addAttribute(geocoded_waypoints);
		rootClass.addAttribute(routes);
		rootClass.addAttribute(status);
		
		Data<?> geocoder_status = new Data<>("geocoder_status", "String", true, "application/json", Context.PATH, true);
		Data<?> place_id = new Data<>("place_id", "String", true, "application/json", Context.PATH, true);
		Data<?> types = new Data<>("types", "String", true, "application/json", Context.PATH, true);
		
		geocoded_waypoints.addAttribute(geocoder_status);
		geocoded_waypoints.addAttribute(place_id);
		geocoded_waypoints.addAttribute(types);
		
		Data<?> bounds = new Data<>("bounds", "Bounds", false, "application/json", Context.PATH, true);
		Data<?> copyrights = new Data<>("copyrights", "String", true, "application/json", Context.PATH, true);
		Data<?> legs = new Data<>("legs", "Legs", false, "application/json", Context.PATH, true);
		Data<?> overview_polyline = new Data<>("overview_polyline", "OverviewPolyline", false, "application/json", Context.PATH, true);
		Data<?> summary = new Data<>("summary", "String", true, "application/json", Context.PATH, true);
		Data<?> warnings = new Data<>("warnings", "Warnings", false, "application/json", Context.PATH, true);
		Data<?> waypoint_order = new Data<>("waypoint_order", "WaypointOrder", false, "application/json", Context.PATH, true);
		
		routes.addAttribute(bounds);
		routes.addAttribute(copyrights);
		routes.addAttribute(legs);
		routes.addAttribute(overview_polyline);
		routes.addAttribute(summary);
		routes.addAttribute(warnings);
		routes.addAttribute(waypoint_order);
		
		Data<?> northeast = new Data<>("northeast", "Northeast", false, "application/json", Context.PATH, true);
		Data<?> southwest = new Data<>("southwest", "Southwest", false, "application/json", Context.PATH, true);
		
		bounds.addAttribute(northeast);
		bounds.addAttribute(southwest);
		
		Data<?> lat = new Data<>("lat", "Double", true, "application/json", Context.PATH, true);
		Data<?> lng = new Data<>("lng", "Double", true, "application/json", Context.PATH, true);
		
		northeast.addAttribute(lat);
		northeast.addAttribute(lng);
		
		southwest.addAttribute(lat);
		southwest.addAttribute(lng);
		
		Data<?> distance = new Data<>("distance", "Distance", false, "application/json", Context.PATH, true);
		Data<?> duration = new Data<>("duration", "Duration", false, "application/json", Context.PATH, true);
		Data<?> end_address = new Data<>("end_address", "String", true, "application/json", Context.PATH, true);
		Data<?> end_location = new Data<>("end_location", "EndLocation", false, "application/json", Context.PATH, true);
		Data<?> start_address = new Data<>("start_address", "String", true, "application/json", Context.PATH, true);
		Data<?> start_location = new Data<>("start_location", "StartLocation", false, "application/json", Context.PATH, true);
		Data<?> steps = new Data<>("steps", "Steps", false, "application/json", Context.PATH, true);
		Data<?> via_waypoint = new Data<>("via_waypoint", "ViaWaypoint", false, "application/json", Context.PATH, true);
		
		legs.addAttribute(distance);
		legs.addAttribute(duration);
		legs.addAttribute(end_address);
		legs.addAttribute(end_location);
		legs.addAttribute(start_address);
		legs.addAttribute(start_location);
		legs.addAttribute(steps);
		legs.addAttribute(via_waypoint);
		
		Data<?> text = new Data<>("text", "String", true, "application/json", Context.PATH, true);
		Data<?> value = new Data<>("value", "Double", true, "application/json", Context.PATH, true);
		
		distance.addAttribute(text);
		distance.addAttribute(value);
		
		duration.addAttribute(text);
		duration.addAttribute(value);
		
		end_location.addAttribute(lat);
		end_location.addAttribute(lng);
		
		start_location.addAttribute(lat);
		start_location.addAttribute(lng);
		
		Data<?> maneuver = new Data<>("maneuver", "String", true, "application/json", Context.PATH, false);
		Data<?> html_instructions = new Data<>("html_instructions", "String", true, "application/json", Context.PATH, true);
		Data<?> polyline = new Data<>("polyline", "Polyline", false, "application/json", Context.PATH, true);
		Data<?> travel_mode = new Data<>("travel_mode", "String", true, "application/json", Context.PATH, true);
		
		steps.addAttribute(distance);
		steps.addAttribute(duration);
		steps.addAttribute(end_location);
		steps.addAttribute(maneuver);
		steps.addAttribute(html_instructions);
		steps.addAttribute(polyline);
		steps.addAttribute(start_location);
		steps.addAttribute(travel_mode);
		
		Data<?> points = new Data<>("points", "String", true, "application/json", Context.PATH, true);
		polyline.addAttribute(points);
		
		overview_polyline.addAttribute(points);
		
		serviceDefinition.addTypeDefinition(rootClass);

		
		
		// Data for the requests
		Data<?> origin = new Data<>("origin", "String", true, "application/json", Context.PATH, true);
		Data<?> destination = new Data<>("destination", "String", true, "application/json", Context.PATH, true);
		Data<?> key = new Data<>("key", "String", true, "application/json", Context.PATH, true);

		/* -- TWOWAY OPERATION 1 -- */

		Scope scope1 = new Scope();
		scope1.setName("routeRequest");
		scope1.setVerb(Verb.GET);
//		scope1.setUri("/mes/get_metadata_in_area?collection=weather&{period}");
		scope1.setUri("/maps/api/directions/json?origin={origin}&destination={destination}&key={key}");

		Operation twoWayOperation1 = new Operation("operation_1", scope1, OperationType.TWO_WAY_SYNC);
		twoWayOperation1.addGetData(origin);
		twoWayOperation1.addGetData(destination);
		twoWayOperation1.addGetData(key);
		twoWayOperation1.setPostData(rootClass);

		serviceDefinition.addOperation(twoWayOperation1);

		/* ------------------------ */

		this.soapGenerator = new BcSoapGenerator(serviceDefinition, compConfServer).setDebug(true);
	}

	@Test
	public void testEndpointGeneration() {
		this.soapGenerator.generateBc();
		// DifferentiaAssert.assertSourcesEqual("src/test/resources/expected/BindingComponent.java",
		// "src/test/resources/generated/BindingComponent.java");
	}
	//
	// @Test
	// public void testPojoGeneration() {
	// this.soapGenerator.generateBc();
	// DifferentiaAssert.assertSourcesEqual("src/test/resources/expected/TrafficLight.java",
	// "src/test/resources/generated/TrafficLight.java");
	// }
	
	

}
