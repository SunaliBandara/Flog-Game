package com.nsbm.view;

import static com.nsbm.common.CommonData.currentRound;
import static com.nsbm.common.CommonData.username;
import static com.nsbm.common.ResponseResult.ADALA_NA;
import static com.nsbm.common.ResponseResult.SUCCESS;
import static com.nsbm.service.WordServiceHandler.addWord;
import static com.nsbm.service.WordServiceHandler.getInitialLetters;
import static com.nsbm.service.WordServiceHandler.getLetters;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game extends javax.swing.JFrame {

    private String initialLetters = null;
    private static int counter = 180;
    private Timer timer;

    public Game() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lettersLabel = new javax.swing.JLabel();
        wordTextField = new javax.swing.JTextField();
        sendWord = new javax.swing.JButton();
        consonantsTextField = new javax.swing.JTextField();
        getButton = new javax.swing.JButton();
        vowelsTextField = new javax.swing.JTextField();
        timeerLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lettersLabel.setText("NO LETTERS FOUND");

        sendWord.setText("Send");
        sendWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendWordActionPerformed(evt);
            }
        });

        getButton.setText("Get");
        getButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getButtonActionPerformed(evt);
            }
        });

        timeerLabel.setText("timer");

        jLabel2.setText("Vowels");

        jLabel3.setText("Consonants");

        nameTextField.setText("name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lettersLabel)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(wordTextField)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(vowelsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(consonantsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(31, 31, 31)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(getButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sendWord, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameTextField)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lettersLabel)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consonantsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getButton)
                    .addComponent(vowelsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendWord))
                .addGap(35, 35, 35)
                .addComponent(timeerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(nameTextField)
                .addContainerGap())
        );

        lettersLabel.getAccessibleContext().setAccessibleName("serverLetterLabel");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        nameTextField.setText(username);
        initialLetters = getInitialLetters();
        lettersLabel.setText(initialLetters);
        sendWord.setEnabled(false);
        wordTextField.setEditable(false);
    }//GEN-LAST:event_formWindowOpened

    private void sendWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendWordActionPerformed
        String word = wordTextField.getText();
        String response = addWord(word);
        if (response.equals(SUCCESS)) {
            JOptionPane.showMessageDialog(rootPane, "Correct Word");
        } else if(response.equals(ADALA_NA)){
            JOptionPane.showMessageDialog(rootPane, "Adala Nane");
        } else{
            JOptionPane.showMessageDialog(rootPane, "Incorrect Word");
        }
        currentRound++;
        this.dispose();
        RoundComplete roundComplete = new RoundComplete();
        roundComplete.setVisible(true);
    }//GEN-LAST:event_sendWordActionPerformed

    private void getButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getButtonActionPerformed
        lettersLabel.setText("");
        
        int consonantsRequired = Integer.parseInt(consonantsTextField.getText());
        int vowelsRequired = Integer.parseInt(vowelsTextField.getText());
        if (consonantsRequired + vowelsRequired > 10) {
            JOptionPane.showMessageDialog(rootPane, "You can not select more than 10");
        } else {
            String letters = getLetters(vowelsRequired, consonantsRequired);
            //String[] letterParts = letters.split("@");
            lettersLabel.setText(letters);
            
            consonantsTextField.setEditable(false);
            vowelsTextField.setEditable(false);
            getButton.setEnabled(false);            
            sendWord.setEnabled(true);
            wordTextField.setEditable(true);
            
            counter = 180;
            ActionListener action = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (counter == 0) {
                        timer.stop();
                        timeerLabel.setText("Time is Up");
                    } else {
                        timeerLabel.setText(String.valueOf(counter));
                        counter--;
                    }
                }
            };

            timer = new Timer(1000, action);
            timer.setInitialDelay(0);
            timer.start();
        }
    }//GEN-LAST:event_getButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField consonantsTextField;
    private javax.swing.JButton getButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lettersLabel;
    private javax.swing.JLabel nameTextField;
    private javax.swing.JButton sendWord;
    private javax.swing.JLabel timeerLabel;
    private javax.swing.JTextField vowelsTextField;
    private javax.swing.JTextField wordTextField;
    // End of variables declaration//GEN-END:variables
}
