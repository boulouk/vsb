package eu.chorevolution.vsb.playgrounds.clientserver.dpws;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;

public class DocuExampleDevice extends DefaultDevice {

	public final static String	DOCU_NAMESPACE	= "http://ws4d.org/jmeds";

	/**
	 * Constructor of our device.
	 */
	public DocuExampleDevice() {
		super(DPWSCommunicationManager.COMMUNICATION_MANAGER_ID);

		/*
		 * The following lines add metadata information to the device to
		 * illustrate how it works. As default values are defined for all of the
		 * fields, you CAN set new values here but you do NOT have to.
		 */

		// set PortType
		this.setPortTypes(new QNameSet(new QName("DocuExampleDevice", DOCU_NAMESPACE)));
		// add device name (name is language specific)
		this.addFriendlyName("en-US", "DocuDevice");
		this.addFriendlyName(LocalizedString.LANGUAGE_DE, "DokuGeraet");

		// add device manufacturer (manufacturer is language specific)
		this.addManufacturer(LocalizedString.LANGUAGE_EN, "Test Inc.");
		this.addManufacturer("de-DE", "Test GmbH");

		this.addModelName(LocalizedString.LANGUAGE_EN, "DocuModel");

		// add binding (optional!)
		/*
		 * add discovery binding or change the ip (127.0.0.1) with an ip of a
		 * non loopback interface like eth1 or eth3
		 */
		// NetworkInterface iface =
		// IPNetworkDetection.getInstance().getNetworkInterface("eth3");
		// IPDiscoveryDomain domain =
		// IPNetworkDetection.getInstance().getIPDiscoveryDomainForInterface(iface,
		// false);
		// this.addBinding(new
		// IPDiscoveryBinding(DPWSCommunicationManager.COMMUNICATION_MANAGER_ID,
		// domain));

		// this.addBinding(new
		// HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1",
		// false), 0, "docuDevice",
		// DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));
	}
}
