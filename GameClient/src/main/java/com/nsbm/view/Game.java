
package com.nsbm.view;

import static com.nsbm.service.WordServiceHandler.addWord;
import static com.nsbm.service.WordServiceHandler.getInitialLetters;
import static com.nsbm.service.WordServiceHandler.getLetters;
import javax.swing.JOptionPane;

public class Game extends javax.swing.JFrame {
    public Game() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lettersLabel = new javax.swing.JLabel();
        wordTextField = new javax.swing.JTextField();
        sendWord = new javax.swing.JButton();
        requiredTextField = new javax.swing.JTextField();
        getTextField = new javax.swing.JButton();

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

        getTextField.setText("Get");
        getTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(requiredTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(getTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lettersLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(wordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(sendWord)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lettersLabel)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requiredTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendWord))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        lettersLabel.getAccessibleContext().setAccessibleName("serverLetterLabel");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        String letters =  getInitialLetters();
        lettersLabel.setText(letters);
    }//GEN-LAST:event_formWindowOpened

    private void sendWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendWordActionPerformed
        String word = wordTextField.getText();
        String recievedWord = addWord(word);
        JOptionPane.showMessageDialog(rootPane, recievedWord);
    }//GEN-LAST:event_sendWordActionPerformed

    private void getTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getTextFieldActionPerformed
        int required = Integer.parseInt(requiredTextField.getText());
        String letters = getLetters(required);
        lettersLabel.setText(lettersLabel.getText() + "  " + letters);
    }//GEN-LAST:event_getTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton getTextField;
    private javax.swing.JLabel lettersLabel;
    private javax.swing.JTextField requiredTextField;
    private javax.swing.JButton sendWord;
    private javax.swing.JTextField wordTextField;
    // End of variables declaration//GEN-END:variables
}
