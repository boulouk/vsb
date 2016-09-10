package eu.chorevolution.vsb.playgrounds.clientserver.dpws;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.ComplexType;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class DocuExampleComplexOperation extends Operation {

	public DocuExampleComplexOperation() {
		super("DocuExampleComplexOperation", new QName("BasicServices", DocuExampleDevice.DOCU_NAMESPACE));

		// create inner elements for complex type
		Element nameElem = new Element(new QName("name", DocuExampleDevice.DOCU_NAMESPACE), SchemaUtil.TYPE_STRING);
		Element addressElem = new Element(new QName("address", DocuExampleDevice.DOCU_NAMESPACE), SchemaUtil.TYPE_STRING);
		addressElem.setMaxOccurs(2);

		// create complex type and add inner elements
		ComplexType userInfo = new ComplexType(new QName("userInfoType", DocuExampleDevice.DOCU_NAMESPACE), ComplexType.CONTAINER_SEQUENCE);
		userInfo.addElement(nameElem);
		userInfo.addElement(addressElem);

		// create element of type userInfo
		Element userInfoElement = new Element(new QName("userInfo", DocuExampleDevice.DOCU_NAMESPACE), userInfo);

		// set the input of the operation
		setInput(userInfoElement);

		// create reply element
		Element reply = new Element(new QName("reply", DocuExampleDevice.DOCU_NAMESPACE), SchemaUtil.TYPE_STRING);

		// set this element as output
		setOutput(reply);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		// get string value from input
		String name = ParameterValueManagement.getString(parameterValue, "name");
		String address = ParameterValueManagement.getString(parameterValue, "address[0]");
		String address2 = ParameterValueManagement.getString(parameterValue, "address[1]");
		System.out.println("Hello World " + name + " from " + address + " or " + address2);

		// create output and set value
		ParameterValue result = createOutputValue();
		ParameterValueManagement.setString(result, "reply", "How are you " + name + " from " + address + " and other places?");

		return result;
	}
}
