package eu.chorevolution.vsb.bcs.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConversionWindow extends JPanel { 

  public static JLabel left = null;
  public static JLabel right = null;
  public static JLabel center = null;

  public class bc extends JPanel {
    public bc() {
      
      final JLabel text = new JLabel("Binding Component has started");
      
      text.setVisible(false);
      text.setBounds(26, 30, 300, 80);
      text.setText("Binding Component has started");
      JButton startbtn = new JButton("Start");
      JButton stopbtn = new JButton("Stop");
      
      startbtn.setBounds(30, 10, 80, 30);
      stopbtn.setBounds(170, 10, 80, 30);
      
      left = new JLabel("");
      right = new JLabel("");
//      left.setText(String.format("<html><div WIDTH=%d>%s</div><html>", 80, "aggggggggg"));
//      right.setText(String.format("<html><div WIDTH=%d>%s</div><div WIDTH=%d>%s</div><html>", 83, "{\"id\":\"23\",",90,"\"value\":\"23\"}"));
      left.setText("");
      right.setText("");
      
      ImageIcon image = new ImageIcon("/home/siddhartha/Downloads/arrow-right-c.png");
      center = new JLabel(image);   
      
      left.setBounds(30, 90, 80, 30);      
      center.setBounds(97, 90, 80, 30);
      right.setBounds(170, 90, 90, 45);
      
      //center.setIcon(get);
      left.setVisible(true);
      center.setVisible(false);
      right.setVisible(true);
      setLayout(null);
//      add(new JPanel());
      add(startbtn);
      add(stopbtn);
      add(text);
      add(right);
      add(left);
      add(center);
      add(right);
//      add(new JPanel());
      
      startbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          text.setVisible(true);
        }
      });

      
    }
  }
  
  public ConversionWindow() {
    JFrame frame1 = new JFrame("Binding Component");
    frame1.getContentPane().add(new bc());
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(310, 130);
    frame1.setVisible(true);
  }
  public static void main(String[] args) {
    new ConversionWindow();
  }
}


