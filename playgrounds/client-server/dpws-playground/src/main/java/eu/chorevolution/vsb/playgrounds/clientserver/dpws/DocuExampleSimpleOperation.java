package eu.chorevolution.vsb.playgrounds.clientserver.dpws;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

/**
 * Implementation of the first operation described in "An Introduction to WS4D
 * and to JMEDS, a framework for distributed communication between devices in a
 * domotic environment" by Pierre-Alexandre GagnÈ
 * 
 * @author ajordan
 */
public class DocuExampleSimpleOperation extends Operation {

	public final static String	NAME	= "name";

	public final static String	REPLY	= "reply";

	/**
	 * Constructor
	 */
	public DocuExampleSimpleOperation() {
		super("DocuExampleSimpleOperation", new QName("BasicServices", DocuExampleDevice.DOCU_NAMESPACE));

		// create new Element called "name" (just a simple one in this case)
		Element nameElem = new Element(new QName(NAME, DocuExampleDevice.DOCU_NAMESPACE), SchemaUtil.TYPE_STRING);

		// set the input of the operation
		setInput(nameElem);

		// create new element called "reply"
		Element reply = new Element(new QName(REPLY, DocuExampleDevice.DOCU_NAMESPACE), SchemaUtil.TYPE_STRING);

		// set this element as output
		setOutput(reply);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		// get string value from input
		String name = ParameterValueManagement.getString(parameterValue, NAME);
		System.out.println("You have the brigde, " + name);

		// create output and set value
		ParameterValue result = createOutputValue();
		ParameterValueManagement.setString(result, REPLY, "You have the brigde, " + name);

		return result;
	}

}
