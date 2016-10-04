package eu.chorevolution.vsb.playgrounds.clientserver.websockets.gui.test;

import org.junit.Test;

import eu.chorevolution.vsb.playgrounds.clientserver.websockets.gui.WebsocketsGUI;


public class WebsocketsGUITest {
	@Test
	public void websocketsGUITest() {
		new WebsocketsGUI().setVisible(true);
	}

	public static void main(String[] args) {
		new WebsocketsGUI().setVisible(true);
	}
}
