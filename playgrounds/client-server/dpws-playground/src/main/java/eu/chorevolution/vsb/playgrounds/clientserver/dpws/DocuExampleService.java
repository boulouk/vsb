package eu.chorevolution.vsb.playgrounds.clientserver.dpws;

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
public class DocuExampleService extends DefaultService {

	public final static URI	DOCU_EXAMPLE_SERVICE_ID	= new URI(DocuExampleDevice.DOCU_NAMESPACE + "/DocuExampleService");

	/**
	 * Standard Constructor
	 */
	public DocuExampleService() {
		super();

		this.setServiceId(DOCU_EXAMPLE_SERVICE_ID);

		// (tutorial 2) add Operations from tutorial 2 to the service

		DocuExampleSimpleOperation simpleOp = new DocuExampleSimpleOperation();
		addOperation(simpleOp);

		DocuExampleComplexOperation complexOp = new DocuExampleComplexOperation();
		addOperation(complexOp);

		DocuExampleAttributeOperation attrOp = new DocuExampleAttributeOperation();
		addOperation(attrOp);
	}

}
