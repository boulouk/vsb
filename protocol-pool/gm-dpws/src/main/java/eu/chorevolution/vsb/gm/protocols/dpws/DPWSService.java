package eu.chorevolution.vsb.gm.protocols.dpws;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.URI;

/**
 * Implementation of the service described in "An Introduction to WS4D and to
 * JMEDS, a framework for distributed communication between devices in a domotic
 * environment" by Pierre-Alexandre GagnÈ
 * 
 * @author ajordan
 */
public class DPWSService extends DefaultService {

	public final static URI	DOCU_EXAMPLE_SERVICE_ID	= new URI(DPWSDevice.DOCU_NAMESPACE + "/DocuExampleService");

	/**
	 * Standard Constructor
	 */
	public DPWSService() {
		super();

		this.setServiceId(DOCU_EXAMPLE_SERVICE_ID);

		DPWSOperation simpleOp = new DPWSOperation();
		addOperation(simpleOp);

	}

}
