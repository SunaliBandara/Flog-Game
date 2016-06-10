package com.nsbm.view;

import static com.nsbm.common.CommonUtil.setModelData;
import com.nsbm.common.PlayerStatus;
import com.nsbm.common.CommonData;
import com.nsbm.entity.Player;
import static com.nsbm.service.PlayerServiceHandler.getAllPlayers;
import static com.nsbm.service.PlayerServiceHandler.listendToJoinEvent;
import static com.nsbm.service.PlayerServiceHandler.notifyPlayerJoin;
import static com.nsbm.service.PlayerServiceHandler.setModelReference;
import javax.swing.DefaultListModel;

/**
 *
 * @author Lakshitha
 */
public class MainMenu extends javax.swing.JFrame {

    String[] playerNames;
    Player[] allPlayers;
    DefaultListModel<String> model = new DefaultListModel<>();

    public MainMenu() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startGame = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JList();
        user = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        startGame.setText("Start Game");
        startGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(playerList);

        user.setText("User");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startGame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startGame, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(user)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameActionPerformed
        CommonData.playerStatus = PlayerStatus.PLAYING;
        this.dispose();
        Game game = new Game();
        game.setVisible(true);
    }//GEN-LAST:event_startGameActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        user.setText(CommonData.username);
        allPlayers = getAllPlayers();
        playerList.setModel(model);
        setModelReference(model);
        new Thread(new Runnable() {
            @Override
            public void run() {
                setModelData(allPlayers,model);
                notifyPlayerJoin();
                listendToJoinEvent();
            }
        }).start();

    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList playerList;
    private javax.swing.JButton startGame;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}
