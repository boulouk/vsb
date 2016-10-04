package eu.chorevolution.vsb.playgrounds.clientserver.rest;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eu.chorevolution.vsb.playgrounds.clientserver.rest.RestServer;

public class StartRestServer {  
  public static void main(String[] args) {
    RestServer rs = new RestServer();
    rs.startServer(1111);
    rs.mget("sensors");
  }
}