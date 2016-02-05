/**
 * 
 */
package eu.chorevolution.vsb.bcs.dtsgoogle;

import eu.chorevolution.vsb.bcs.dtsgoogle.bc.RestServer;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 *
 * StarterMain.java Created: 5 févr. 2016
 * Description:
 */
public class StarterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RestServer rs = new RestServer();
		rs.start();
		
		
		BCStarter st = new BCStarter();
		st.start();
		
	}

}
