package eu.chorevolution.vsb.gm.protocols.dpws;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.DPWSProtocolVersion;
import org.ws4d.java.configuration.DPWSProperties;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;

public class SendRequest extends DefaultClient {
	final static String	namespace	= DPWSDevice.DOCU_NAMESPACE;

	final static QName	service		= new QName("BasicServices", namespace);

	public static void main(String[] args) {
		// mandatory starting of DPWS framework
		JMEDSFramework.start(args);

		DPWSProperties.getInstance().removeSupportedDPWSVersion(DPWSProtocolVersion.DPWS_VERSION_2006);

		// create client
		SendRequest client = new SendRequest();

		// Use discovery, define what you are searching for and start search
		SearchParameter search = new SearchParameter();
		search.setDeviceTypes(new QNameSet(new QName("DocuExampleDevice", namespace)), DPWSCommunicationManager.COMMUNICATION_MANAGER_ID);
		SearchManager.searchDevice(search, client, null);

		// search services
		search = new SearchParameter();
		search.setServiceTypes(new QNameSet(service), DPWSCommunicationManager.COMMUNICATION_MANAGER_ID);
		SearchManager.searchService(search, client, null);

	}
	
	public void serviceFound(ServiceReference servRef, SearchParameter search, String comManId) {
		try {
				// get operation
				Operation op = servRef.getService().getOperation(service, "operation_1", null, null);

				// create input value and set string values
				ParameterValue request = op.createInputValue();
				ParameterValueManagement.setString(request, "arg0", "17.4781737,78.2975116,15.51z");
				ParameterValueManagement.setString(request, "arg1", "17.470432,78.3102378,15z");

				System.err.println("Invoking HelloWorldOp...");

				// invoke operation with prepared input
				ParameterValue result = op.invoke(request, CredentialInfo.EMPTY_CREDENTIAL_INFO);

				System.err.println("Finished invoking HelloWorldOp...");
				// get string value from answer
				String reply = ParameterValueManagement.getString(result, "reply");

				System.out.println(reply);
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (InvocationException e) {
			e.printStackTrace();
		}
	}

	public void deviceFound(DeviceReference devRef, SearchParameter search, String comManId) {
		try {
			Device device = devRef.getDevice();
			System.out.println("Found DocuExampleDevice: " + device.getFriendlyName(LocalizedString.LANGUAGE_EN));
		} catch (CommunicationException e) {
			e.printStackTrace();
		}
	}

}
