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
import static com.nsbm.service.PlayerServiceHandler.setModelReference;
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
        StartNextRound = new javax.swing.JButton();
        playerNameField = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane1.setViewportView(playerList);

        StartNextRound.setText("Next Round");

        playerNameField.setText("name");

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
                        .addGap(108, 108, 108)
                        .addComponent(StartNextRound, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(playerNameField)))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(StartNextRound, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(playerNameField)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        playerNameField.setText(username);
        allCompletedPlayer = getRoundCompletedPlayers();
        setModelReference(model);
        playerList.setModel(model);
        new Thread(new Runnable() {
            public void run() {
                setRoundCompletedModelData(allCompletedPlayer, model);
                notifyRoundCompletion();
                listenToRoundCompletionEvent();
            }
        }).start();
    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton StartNextRound;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList playerList;
    private javax.swing.JLabel playerNameField;
    // End of variables declaration//GEN-END:variables
}
