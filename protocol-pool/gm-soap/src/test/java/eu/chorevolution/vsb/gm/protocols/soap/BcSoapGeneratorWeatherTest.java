package eu.chorevolution.vsb.gm.protocols.soap;

import org.junit.Before;
import org.junit.Test;

import pl.ncdc.differentia.DifferentiaAssert;
import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;
import eu.chorevolution.vsb.gmdl.utils.enums.Verb;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 * BcSoapGeneratorWeatherTest.java Created: 1 févr. 2016
 * Description:
 */
public class BcSoapGeneratorWeatherTest {

	private BcSubcomponentGenerator soapGenerator;

	@Before
	public void initGenerator() {
		BcConfiguration compConfServer = new BcConfiguration();
		compConfServer.setSubcomponentRole("SERVER");
		compConfServer.setServiceAddress("http://93.62.202.227");
		compConfServer.setGeneratedCodePath("src/test/resources/generated/weather");
		compConfServer.setTargetNamespace("");

		GmServiceRepresentation serviceDefinition = new GmServiceRepresentation();
		serviceDefinition.setProtocol(Protocol.REST);

		/* Types Definitions */
		Data<?> rootClass = new Data<>("rootClass", "RootClass", false, "application/json", Context.BODY, true);

		Data<?> status = new Data<>("status", "String", true, "application/json", Context.PATH, true);

		// TODO fix the lists
		// Data<?> docs = new Data<>("docs", "List<WeatherType>", false, "application/json", Context.BODY, true);
		Data<?> docs = new Data<>("docs", "WeatherType", false, "application/json", Context.BODY, true);

		Data<?> latitude = new Data<>("latitude", "Double", true, "application/json", Context.PATH, true);
		Data<?> longitude = new Data<>("longitude", "Double", true, "application/json", Context.PATH, true);
		Data<?> temperature = new Data<>("temperature", "Double", true, "application/json", Context.PATH, true);
		Data<?> pressure = new Data<>("pressure", "Double", true, "application/json", Context.PATH, true);
		Data<?> observation_time = new Data<>("observation_time", "String", true, "application/json", Context.PATH, true);
		Data<?> tag = new Data<>("tag", "String", true, "application/json", Context.PATH, true);
		Data<?> __CLASS__ = new Data<>("__CLASS__", "String", true, "application/json", Context.PATH, true);
		Data<?> collection = new Data<>("collection", "String", true, "application/json", Context.PATH, true);
		Data<?> id = new Data<>("id", "String", true, "application/json", Context.PATH, true);
		Data<?> _id = new Data<>("_id", "String", true, "application/json", Context.PATH, true);
		Data<?> location = new Data<>("location", "Location", false, "application/json", Context.BODY, true);
		Data<?> status_double = new Data<>("status", "Double", true, "application/json", Context.PATH, true);
		Data<?> humidity = new Data<>("humidity", "Integer", true, "application/json", Context.PATH, true);
		Data<?> creation_date = new Data<>("creation_date", "String", true, "application/json", Context.PATH, true);
		Data<?> elevation = new Data<>("elevation", "Double", true, "application/json", Context.PATH, true);
		Data<?> rain = new Data<>("rain", "String", true, "application/json", Context.PATH, true);

		Data<?> type = new Data<>("type", "String", true, "application/json", Context.PATH, true);

		// TODO fix the lists
		// Data<?> coordinates = new Data<>("coordinates", "List<Double>", true, "application/json", Context.PATH, true);
		Data<?> coordinates = new Data<>("coordinates", "Double", true, "application/json", Context.PATH, true);

		docs.addAttribute(latitude);
		docs.addAttribute(longitude);
		docs.addAttribute(temperature);
		docs.addAttribute(pressure);
		docs.addAttribute(observation_time);
		docs.addAttribute(tag);
		docs.addAttribute(__CLASS__);
		docs.addAttribute(collection);
		docs.addAttribute(id);
		docs.addAttribute(_id);
		docs.addAttribute(location);
		docs.addAttribute(status_double);
		docs.addAttribute(humidity);
		docs.addAttribute(creation_date);
		docs.addAttribute(elevation);
		docs.addAttribute(rain);

		location.addAttribute(type);
		location.addAttribute(coordinates);

		rootClass.addAttribute(status);
		rootClass.addAttribute(docs);

		serviceDefinition.addTypeDefinition(rootClass);

		// Data for the requests
		Data<?> period = new Data<>("period", "Integer", true, "application/json", Context.PATH, true);
		Data<?> radius = new Data<>("radius", "Double", true, "application/json", Context.PATH, true);

		/* -- TWOWAY OPERATION 1 -- */

		Scope scope1 = new Scope();
		scope1.setName("getMeteoInfo");
		scope1.setVerb(Verb.GET);
		scope1.setUri("/mes/get_metadata_in_area?collection=weather&{period}");

		Operation twoWayOperation1 = new Operation("operation_1", scope1, OperationType.TWO_WAY_SYNC);
		twoWayOperation1.addGetData(period);
		twoWayOperation1.setPostData(rootClass);

		serviceDefinition.addOperation(twoWayOperation1);

		/* ------------------------ */

		/* -- TWOWAY OPERATION 2 -- */

		Scope scope2 = new Scope();
		scope2.setName("getMeteoInfoByArea");
		scope2.setVerb(Verb.GET);
		scope2.setUri("/mes/get_metadata_in_area?collection=weather&{period}&{lat}&{lon}&{radius}");

		Operation twoWayOperation2 = new Operation("operation_2", scope2, OperationType.TWO_WAY_SYNC);
		twoWayOperation2.addGetData(period);
		twoWayOperation2.addGetData(latitude);
		twoWayOperation2.addGetData(longitude);
		twoWayOperation2.addGetData(radius);
		twoWayOperation2.setPostData(rootClass);

		serviceDefinition.addOperation(twoWayOperation2);

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