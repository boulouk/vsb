package eu.chorevolution.vsb.gm.protocols.mqtt;

import org.junit.Before;
import org.junit.Test;

import pl.ncdc.differentia.DifferentiaAssert;
import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gm.protocols.mqtt.BcMQTTGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.Data.Context;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.Scope;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;
import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;
import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;
import eu.chorevolution.vsb.gmdl.utils.enums.Verb;

public class BcSoapGeneratorTest {
  
  private BcSubcomponentGenerator soapGenerator;
  
  @Before
  public void initGenerator() {
    BcConfiguration compConfServer = new BcConfiguration();
    compConfServer.setSubcomponentRole(RoleType.SERVER);
    compConfServer.setServiceAddress("http://127.0.0.1:8282");
    compConfServer.setGeneratedCodePath("src/test/resources/generated/traffic");
    compConfServer.setTargetNamespace("");
    
    GmServiceRepresentation serviceDefinition = new GmServiceRepresentation();
    serviceDefinition.setProtocol(ProtocolType.REST);
    
    /*Types Definitions*/
    Data<?> light = new Data<>("light", "TrafficLight", false, "application/json", Context.BODY, true);
    Data<?> id = new Data<>("id", "Integer", true, "application/json", Context.PATH, true);
    Data<?> status = new Data<>("status", "String", true, "application/json", Context.PATH, true);
    Data<?> address = new Data<>("address", "String", true, "application/json", Context.PATH, true);
    light.addAttribute(id);
    light.addAttribute(status);
    light.addAttribute(address);
    serviceDefinition.addTypeDefinition(light);
    
    /*ONEWAY OP*/    
    Scope scope1 = new Scope();
    scope1.setName("postTrafficLight");
    scope1.setVerb(Verb.POST);
    scope1.setUri("/traffic-lights");
    Operation oneWayOperation = new Operation("operation_1", scope1,OperationType.ONE_WAY);
    Data<?> getData1 = light;
    oneWayOperation.addGetData(getData1);
    serviceDefinition.addOperation(oneWayOperation);
    
    /*TWOWAY OP*/
    Scope scope2 = new Scope();
    scope2.setName("getTrafficLight");
    scope2.setVerb(Verb.GET);
    scope2.setUri("/traffic-lights/{id}");
    Operation twoWayOperation = new Operation("operation_2",scope2,OperationType.TWO_WAY_SYNC);
    Data<?> getData2 = id;
    twoWayOperation.addGetData(getData2);
    Data<?> postData = light;
    twoWayOperation.setPostData(postData);
    serviceDefinition.addOperation(twoWayOperation);
    
    this.soapGenerator = new BcMQTTGenerator(serviceDefinition, compConfServer).setDebug(true);
  }
  
  @Test
  public void testEndpointGeneration() {
    this.soapGenerator.generatePOJOAndEndpoint();
    DifferentiaAssert.assertSourcesEqual("src/test/resources/expected/BindingComponent.java", "src/test/resources/generated/traffic/BindingComponent.java");
  }
  
  @Test
  public void testPojoGeneration() {
    this.soapGenerator.generatePOJOAndEndpoint();
    DifferentiaAssert.assertSourcesEqual("src/test/resources/expected/TrafficLight.java", "src/test/resources/generated/traffic/TrafficLight.java");
  }
}