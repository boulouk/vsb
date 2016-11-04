package eu.chorevolution.vsb.playgrounds.str.websockets.gui;
//package eu.chorevolution.vsb.playgrounds.clientserver.websockets.gui;
//
//import java.awt.Color;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.UUID;
//
//import javax.swing.JFrame;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyledDocument;
//
//import org.java_websocket.server.WebSocketServer;
//
//import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WebSocketClient;
//import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WebSocketClient.WsClient;
//import eu.chorevolution.vsb.playgrounds.clientserver.websockets.WsServer;
//
//
//@SuppressWarnings("serial")
//public class WebsocketsGUI extends JFrame {
//
//  private WsClient client;
//  private WsServer server;
//
//  public WebsocketsGUI() {
//    initComponents();
//  }
//
//  private void initComponents() {
//
//    _P_Client = new javax.swing.JPanel();
//    _P_ClientLog = new javax.swing.JScrollPane();
//    _TP_ClientLog = new javax.swing.JTextPane();
//    _P_ClientConfig = new javax.swing.JPanel();
//
//    _TF_DestIp = new javax.swing.JTextField();
//    jLabelIP = new javax.swing.JLabel();
//
//    _TF_DestPort = new javax.swing.JTextField();
//    jLabelPort = new javax.swing.JLabel();
//
//    _P_ClientWrite = new javax.swing.JPanel();
//
//    jLabelKey = new javax.swing.JLabel();
//    //		jLabelLease = new javax.swing.JLabel();
//    //		jLabelValue = new javax.swing.JLabel();
//
//    _TF_Template = new javax.swing.JTextField();
//    //		_TF_Value = new javax.swing.JTextField();
//    //		_TF_Lease = new javax.swing.JTextField();
//
//    _BT_Read = new javax.swing.JButton();
//    //		_BT_Take = new javax.swing.JButton();
//    //		_BT_Write = new javax.swing.JButton();
//
//    _P_TupleSpace = new javax.swing.JPanel();
//    _P_TSConfig = new javax.swing.JPanel();
//    _TF_TSServerPort = new javax.swing.JTextField();
//    _LB_TSServerPort = new javax.swing.JLabel();
//    _BT_StartTupleSpace = new javax.swing.JToggleButton();
//    _LB_ServerEnabled = new javax.swing.JLabel();
//    _P_ClientOperation = new javax.swing.JPanel();
//
//    jScrollPane1 = new javax.swing.JScrollPane();
//    _TP_ServerLog = new javax.swing.JTextPane();
//
//    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//    setTitle("SemiSpace Playground");
//
//    _P_Client.setBorder(javax.swing.BorderFactory.createTitledBorder("Client"));
//
//    _P_ClientLog.setViewportView(_TP_ClientLog);
//
//    _P_ClientConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));
//
//    _TF_DestIp.setText("127.0.0.1");
//    jLabelIP.setText("IP:");
//
//    _TF_DestPort.setText("8090");
//    jLabelPort.setText("Port:");
//
//    _P_ClientWrite.setBackground(new java.awt.Color(204, 204, 204));
//
//    jLabelKey.setText("Message:");
//    _TF_Template.setText("message");
//    //		jLabelLease.setText("Lease:");
//    //		_TF_Lease.setText("60000");
//    //		jLabelValue.setText("Value:");
//    //		_TF_Value.setText("a value");
//
//    _BT_Read.setText("Send");
//    _BT_Read.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(java.awt.event.ActionEvent evt) {
//        _BT_ReadActionPerformed(evt);
//      }
//    });
//
//    //		_BT_Take.setText("Take");
//    //		_BT_Take.addActionListener(new java.awt.event.ActionListener() {
//    //			public void actionPerformed(java.awt.event.ActionEvent evt) {
//    //				_BT_TakeActionPerformed(evt);
//    //			}
//    //		});
//    //
//    //		_BT_Write.setText("Write");
//    //		_BT_Write.addActionListener(new java.awt.event.ActionListener() {
//    //			public void actionPerformed(java.awt.event.ActionEvent evt) {
//    //				_BT_WriteActionPerformed(evt);
//    //			}
//    //		});
//
//    javax.swing.GroupLayout _P_ClientPublishLayout = new javax.swing.GroupLayout(_P_ClientWrite);
//
//    _P_ClientWrite.setLayout(_P_ClientPublishLayout);
//
//    _P_ClientPublishLayout.setHorizontalGroup(
//        _P_ClientPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_ClientPublishLayout.createSequentialGroup()
//            .addContainerGap()
//            .addComponent(jLabelKey)
//            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//            .addComponent(_TF_Template)
//            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//            .addContainerGap()
//            )
//            .addGroup(_P_ClientPublishLayout.createSequentialGroup()
//                .addContainerGap()
//                //								.addComponent(jLabelLease)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                //								.addComponent(_TF_Lease)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addContainerGap()
//                )    
//                .addGroup(_P_ClientPublishLayout.createSequentialGroup()
//                    .addContainerGap()
//                    //										.addComponent(jLabelValue)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                    //										.addComponent(_TF_Value)
//                    )    
//                    .addGroup(_P_ClientPublishLayout.createSequentialGroup()
//                        .addContainerGap()
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addComponent(_BT_Read, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        //												.addComponent(_BT_Take, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        //												.addComponent(_BT_Write, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addContainerGap()
//                        )
//        );
//    _P_ClientPublishLayout.setVerticalGroup(
//        _P_ClientPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_ClientPublishLayout.createSequentialGroup()
//            .addContainerGap()
//            .addGroup(_P_ClientPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                .addComponent(jLabelKey)
//                .addComponent(_TF_Template, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                )
//                .addContainerGap()
//                .addGroup(_P_ClientPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    //										.addComponent(jLabelLease)
//                    //										.addComponent(_TF_Lease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    )
//                    .addContainerGap()
//                    .addGroup(_P_ClientPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                        //												.addComponent(jLabelValue)
//                        //												.addComponent(_TF_Value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        )
//                        .addContainerGap()
//                        .addGroup(_P_ClientPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                            .addComponent(_BT_Read)
//                            //														.addComponent(_BT_Take)
//                            //														.addComponent(_BT_Write)
//                            )
//
//                            .addContainerGap(6, Short.MAX_VALUE))
//        );
//
//
//
//    javax.swing.GroupLayout _P_ClientConfigLayout = new javax.swing.GroupLayout(_P_ClientConfig);
//    _P_ClientConfig.setLayout(_P_ClientConfigLayout);
//    _P_ClientConfigLayout.setHorizontalGroup(
//        _P_ClientConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_ClientConfigLayout.createSequentialGroup()
//            .addGroup(_P_ClientConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addGroup(_P_ClientConfigLayout.createSequentialGroup()
//                    .addContainerGap()
//                    .addGroup(_P_ClientConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _P_ClientConfigLayout.createSequentialGroup()
//                            .addComponent(jLabelIP)
//                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                            .addComponent(_TF_DestIp, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
//                            .addComponent(jLabelPort)
//                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                            .addComponent(_TF_DestPort, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))))
//                            .addComponent(_P_ClientWrite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                            .addContainerGap())
//        );
//    _P_ClientConfigLayout.setVerticalGroup(
//        _P_ClientConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_ClientConfigLayout.createSequentialGroup()
//            .addGroup(_P_ClientConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                .addComponent(_TF_DestIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addComponent(jLabelIP)
//                .addComponent(_TF_DestPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addComponent(jLabelPort))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(_P_ClientWrite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addContainerGap())
//        );
//
//    javax.swing.GroupLayout _P_ClientLayout = new javax.swing.GroupLayout(_P_Client);
//    _P_Client.setLayout(_P_ClientLayout);
//    _P_ClientLayout.setHorizontalGroup(
//        _P_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_ClientLayout.createSequentialGroup()
//            .addContainerGap()
//            .addGroup(_P_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addComponent(_P_ClientLog, javax.swing.GroupLayout.Alignment.TRAILING)
//                .addComponent(_P_ClientConfig, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addContainerGap())
//        );
//    _P_ClientLayout.setVerticalGroup(
//        _P_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_ClientLayout.createSequentialGroup()
//            .addContainerGap()
//            .addComponent(_P_ClientConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//            .addComponent(_P_ClientLog)
//            .addContainerGap())
//        );
//
//    _P_TupleSpace.setBorder(javax.swing.BorderFactory.createTitledBorder("SemiSpace"));
//
//    _P_TSConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));
//
//    _TF_TSServerPort.setText("8090");
//
//    _LB_TSServerPort.setText("Port:");
//
//    _BT_StartTupleSpace.setText("Start");
//    _BT_StartTupleSpace.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(java.awt.event.ActionEvent evt) {
//        _BT_ServerStartActionPerformed(evt);
//      }
//    });
//
//    _LB_ServerEnabled.setBackground(java.awt.Color.red);
//    _LB_ServerEnabled.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
//    _LB_ServerEnabled.setForeground(new java.awt.Color(255, 255, 255));
//    _LB_ServerEnabled.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//    _LB_ServerEnabled.setText("Disabled");
//    _LB_ServerEnabled.setOpaque(true);
//
//    javax.swing.GroupLayout _P_BrokerConfigLayout = new javax.swing.GroupLayout(_P_TSConfig);
//    _P_TSConfig.setLayout(_P_BrokerConfigLayout);
//    _P_BrokerConfigLayout.setHorizontalGroup(
//        _P_BrokerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_BrokerConfigLayout.createSequentialGroup()
//            .addContainerGap()
//            .addGroup(_P_BrokerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addComponent(_BT_StartTupleSpace, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _P_BrokerConfigLayout.createSequentialGroup()
//                    .addComponent(_LB_TSServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                    .addComponent(_TF_TSServerPort, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                    .addComponent(_LB_ServerEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addComponent(_P_ClientOperation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                    .addContainerGap())
//        );
//    _P_BrokerConfigLayout.setVerticalGroup(
//        _P_BrokerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_BrokerConfigLayout.createSequentialGroup()
//            .addGroup(_P_BrokerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                .addComponent(_TF_TSServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addComponent(_LB_TSServerPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(_LB_ServerEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
//                .addComponent(_BT_StartTupleSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(5, 5, 5)
//                .addComponent(_P_ClientOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(7, 7, 7))
//        );
//
//    jScrollPane1.setViewportView(_TP_ServerLog);
//
//    javax.swing.GroupLayout _P_BrokerLayout = new javax.swing.GroupLayout(_P_TupleSpace);
//    _P_TupleSpace.setLayout(_P_BrokerLayout);
//    _P_BrokerLayout.setHorizontalGroup(
//        _P_BrokerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _P_BrokerLayout.createSequentialGroup()
//            .addContainerGap()
//            .addGroup(_P_BrokerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                .addComponent(jScrollPane1)
//                .addComponent(_P_TSConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addContainerGap())
//        );
//    _P_BrokerLayout.setVerticalGroup(
//        _P_BrokerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(_P_BrokerLayout.createSequentialGroup()
//            .addContainerGap()
//            .addComponent(_P_TSConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
//            .addContainerGap())
//        );
//
//    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//    getContentPane().setLayout(layout);
//    layout.setHorizontalGroup(
//        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(layout.createSequentialGroup()
//            .addContainerGap()
//            .addComponent(_P_Client, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//            .addComponent(_P_TupleSpace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//    layout.setVerticalGroup(
//        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                .addComponent(_P_Client, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(_P_TupleSpace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
//        );
//
//    pack();
//  }// </editor-fold>//GEN-END:initComponents
//
//  private void _BT_ServerStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__BT_ServerStartActionPerformed
//    if (_BT_StartTupleSpace.isSelected()) {
//      if (_TF_TSServerPort.getText().length() == 0) {
//        return;
//      }
//
//      int port = Integer.parseInt(_TF_TSServerPort.getText());
//      server = new WsServer(new InetSocketAddress(port));
//      server.start();
//      writeToLog("server", "d", "DEBUG", "Starting Server on port: " + port);
//
//      _LB_ServerEnabled.setBackground(Color.green);
//      _LB_ServerEnabled.setForeground(Color.black);
//      _LB_ServerEnabled.setText("Enabled");
//      _BT_StartTupleSpace.setText("Stop");
//
//      new Thread(new Runnable() {
//
//        @Override
//        public void run() {
//          while(true) {
//            try {
//              synchronized(server.msgQueue) {
//                if(server.msgQueue.size()>0) {
//                  String msg = server.msgQueue.poll();
//
//                  writeToLog("server", "d", "DEBUG", "Received: " + msg);
//
//                }
//                else {
//                  server.msgQueue.wait();
//                }
//              }
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//            }          
//          }
//        }
//      }).start();
//
//    } else {
//      //            server.stop();
//      _LB_ServerEnabled.setBackground(Color.red);
//      _LB_ServerEnabled.setForeground(Color.white);
//      _LB_ServerEnabled.setText("Disabled");
//      _BT_StartTupleSpace.setText("Start");
//    }
//
//  }//GEN-LAST:event__BT_ServerStartActionPerformed
//
//  private void _BT_ReadActionPerformed(java.awt.event.ActionEvent evt) {
//    if (_TF_Template.getText().length() == 0) {
//      return;
//    }
//    else if (client == null) {
//      try {
//        client = new WsClient(new URI("http://"+_TF_DestIp.getText()+":"+_TF_DestPort.getText()));
//        try {
//          client.connectBlocking();
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//
//      } catch (URISyntaxException e1) {
//        e1.printStackTrace();
//      }
//
//    }
//    client.send(_TF_Template.getText());
//    //        String s = client.read(_TF_Template.getText());
//    writeToLog("client", "d", "DEBUG", "sent \"" + _TF_Template.getText() + "\"");
//  }
//
//  private void _BT_TakeActionPerformed(java.awt.event.ActionEvent evt) {
//    if (_TF_Template.getText().length() == 0) {
//      return;
//    }
//    else if (client == null) {
//      try {
//        client = new WsClient(new URI("http://"+_TF_DestIp.getText()+":"+_TF_DestPort.getText()));
//      } catch (URISyntaxException e1) {
//        e1.printStackTrace();
//      }
//      try {
//        client.connectBlocking();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
//
//    //        String s = client.take(_TF_Template.getText());
//    //        writeToLog("client", "d", "DEBUG", s);
//  }
//
//  private void _BT_WriteActionPerformed(java.awt.event.ActionEvent evt) {
//    if (_TF_Template.getText().length() == 0) {
//      return;
//    }
//    else if (client == null) {
//      try {
//        client = new WsClient(new URI("http://"+_TF_DestIp.getText()+":"+_TF_DestPort.getText()));
//      } catch (URISyntaxException e1) {
//        e1.printStackTrace();
//      }
//      try {
//        client.connectBlocking();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
//
//    //        client.write(_TF_Template.getText(),_TF_Value.getText(), Integer.parseInt(_TF_Lease.getText()));
//    //        writeToLog("client", "d", "DEBUG", "Client writes in TS");
//  }
//
//  private javax.swing.JButton _BT_Read;
//  //	private javax.swing.JButton _BT_Take;
//  //	private javax.swing.JButton _BT_Write;
//
//  private javax.swing.JToggleButton _BT_StartTupleSpace;
//  private javax.swing.JLabel _LB_TSServerPort;
//  private javax.swing.JLabel _LB_ServerEnabled;
//  private javax.swing.JPanel _P_TupleSpace;
//  private javax.swing.JPanel _P_TSConfig;
//  private javax.swing.JPanel _P_Client;
//  private javax.swing.JPanel _P_ClientConfig;
//  private javax.swing.JScrollPane _P_ClientLog;
//  private javax.swing.JPanel _P_ClientWrite;
//  private javax.swing.JPanel _P_ClientOperation;
//  private javax.swing.JTextField _TF_DestIp;
//  private javax.swing.JTextField _TF_DestPort;
//  private javax.swing.JTextField _TF_Template;
//  //	private javax.swing.JTextField _TF_Value;
//  //	private javax.swing.JTextField _TF_Lease;   
//  private javax.swing.JTextField _TF_TSServerPort;
//
//  private javax.swing.JTextPane _TP_ClientLog;
//  private javax.swing.JTextPane _TP_ServerLog;
//  private javax.swing.JLabel jLabelIP;
//  private javax.swing.JLabel jLabelPort;
//  private javax.swing.JLabel jLabelKey;
//  //	private javax.swing.JLabel jLabelLease;
//  //	private javax.swing.JLabel jLabelValue;
//
//  private javax.swing.JScrollPane jScrollPane1;
//  // End of variables declaration//GEN-END:variables
//
//  public void writeToLog(String dest, String type, String tag, String msg) {
//    SimpleAttributeSet keyWord = new SimpleAttributeSet();
//    StyleConstants.setForeground(keyWord, Color.WHITE);
//
//    switch (type) {
//    case "e":
//      StyleConstants.setBackground(keyWord, Color.RED);
//      break;
//    case "d":
//      StyleConstants.setBackground(keyWord, Color.BLUE);
//      break;
//    case "i":
//      StyleConstants.setBackground(keyWord, Color.GREEN);
//      break;
//    default:
//      StyleConstants.setBackground(keyWord, Color.BLUE);
//      break;
//    }
//
//    StyleConstants.setBold(keyWord, true);
//    StyledDocument document;
//
//    //Choose the write log panel
//    if (dest.equals("client")) {
//      document = (StyledDocument) _TP_ClientLog.getDocument();
//    } else {
//      document = (StyledDocument) _TP_ServerLog.getDocument();
//    }
//
//    try {
//      document.insertString(document.getLength(), "[" + tag + "]", keyWord);
//      document.insertString(document.getLength(), " - " + msg + "\n", null);
//    } catch (BadLocationException ex) {
//      System.out.println(ex.getMessage());
//    }
//  }
//}
