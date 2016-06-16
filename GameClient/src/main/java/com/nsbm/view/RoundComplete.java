/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.view;

import static com.nsbm.common.CommonData.username;
import static com.nsbm.common.CommonUtil.setRoundCompletedModelData;
import static com.nsbm.service.PlayerServiceHandler.getRoundCompletedPlayers;
import static com.nsbm.service.PlayerServiceHandler.listenToRoundCompletionEvent;
import static com.nsbm.service.PlayerServiceHandler.notifyRoundCompletion;
import static com.nsbm.service.PlayerServiceHandler.setFrameReference;
import static com.nsbm.service.PlayerServiceHandler.setLabelReference;
import static com.nsbm.service.PlayerServiceHandler.setModelReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Lakshitha
 */
public class RoundComplete extends javax.swing.JFrame {

    DefaultListModel<String> model = new DefaultListModel<>();
    String[] allCompletedPlayer;

    public RoundComplete() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JList();
        playerNameField = new javax.swing.JLabel();
        nextRoundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane1.setViewportView(playerList);

        playerNameField.setText("name");

        nextRoundLabel.setText("nextRound");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(playerNameField))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(nextRoundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(nextRoundLabel)
                .addGap(25, 25, 25)
                .addComponent(playerNameField)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        playerNameField.setText(username);
        allCompletedPlayer = getRoundCompletedPlayers();
        setModelReference(model);
        setLabelReference(nextRoundLabel);
        setFrameReference(this);
        playerList.setModel(model);
        new Thread(new Runnable() {
            public void run() {
                setRoundCompletedModelData(allCompletedPlayer, model);
                listenToRoundCompletionEvent();              
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RoundComplete.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyRoundCompletion();
    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nextRoundLabel;
    private javax.swing.JList playerList;
    private javax.swing.JLabel playerNameField;
    // End of variables declaration//GEN-END:variables
}
