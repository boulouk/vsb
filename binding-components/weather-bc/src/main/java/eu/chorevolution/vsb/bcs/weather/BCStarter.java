package eu.chorevolution.vsb.bcs.weather;

import eu.chorevolution.vsb.bc.manager.BcManager;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * 
 *         BCManager.java Created: 27 janv. 2016 Description:
 */
public class BCStarter {

	public static void main(String[] args) {

		// Should parse the local config file and instantiate the
		BcConfiguration configuration = new BcConfiguration(/* path/to/local/config/file */);

		// test purpose: should be extract when parsing the config file
		configuration.setComponentRole("SERVER");
		configuration.setServiceAddress("http://93.62.202.227");
		configuration.setServiceName("BindingComponent");
		configuration.setTargetNamespace("eu.chorevolution.vsb.bcs.weather.bc");
		// END test purpose

		BcManager manager = new BcManager(configuration);

		// should be called remotely via Manager REST interface
		
		manager.setEndpointAddress("http://localhost:8888/BindingComponent"); 
		manager.start();

		/*
		 * try { Thread.sleep(10000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * manager.stop();
		 */
	}

}
