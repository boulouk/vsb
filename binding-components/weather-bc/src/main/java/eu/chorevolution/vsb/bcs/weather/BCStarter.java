package eu.chorevolution.vsb.bcs.weather;

import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * 
 *         BCStarter.java Created: 27 janv. 2016 Description:
 */
public class BCStarter {

	private String messageStart = "";
	private String messageStop = "";
	private BcManager manager;

	public BCStarter() {
		manager = null;

		// Should parse the local config file and instantiate the
		BcConfiguration configuration = new BcConfiguration(/**
		 * 
		 * path/to/local/config/file
		 */
		);

		// test purpose: should be extract when parsing the config file
		configuration.setSubcomponentRole(RoleType.SERVER);
		configuration.setServiceAddress("http://93.62.202.227");
		configuration.setServiceName("BindingComponent");
		configuration.setTargetNamespace("eu.chorevolution.vsb.bcs.weather.bc");
		// END test purpose

		manager = new BcManager(configuration);
		manager.setEndpointAddress("http://localhost:8888/BindingComponent");
	}

	public String start() {
		try {

			// should be called remotely via Manager REST interface

			manager.start();
			messageStart = "http://localhost:8888/BindingComponent";

		} catch (Exception ex) {
			// TODO handle custom exceptions here
			messageStart = "The BC is not started. Check:" + ex;
		}

		return messageStart;
	}

	public String stop() {

		try {
			manager.stop();
			messageStop = "The BC is shut down";

		} catch (Exception ex) {
			// TODO handle custom exceptions here
			messageStop = "The BC is not shut down. Check:" + ex;
		}

		return messageStop;
	}

}
