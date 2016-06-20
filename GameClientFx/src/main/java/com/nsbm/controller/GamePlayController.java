/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.controller;

import com.nsbm.common.CommonData;
import static com.nsbm.common.CommonData.currentRound;
import static com.nsbm.common.ResponseResult.ADALA_NA;
import static com.nsbm.common.ResponseResult.SUCCESS;
import static com.nsbm.service.WordServiceHandler.addWord;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class GamePlayController implements Initializable {

    private String letters;
    @FXML
    private AnchorPane letterPane;
    @FXML
    private TextField wordField;
    @FXML
    private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        letters = CommonData.letters;
        int count = 0;
        for (Node node : letterPane.getChildren()) {
            if (count == letters.length()) {
                break;
            }
            if (node instanceof TextField) {
                ((TextField) node).setText(String.valueOf(letters.charAt(count)).toUpperCase());
            }
            count++;
        }
    }

    public void sendWord() {
        Stage stage = new Stage();
        String word = wordField.getText();
        String response = addWord(word);
        if (response.equals(SUCCESS)) {
            JOptionPane.showMessageDialog(null, "Correct Word");
        } else if (response.equals(ADALA_NA)) {
            JOptionPane.showMessageDialog(null, "Adala Nane");
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Word");
            //Action ad = Dialogs.create().owner(stage).title("Information Dialog").masthead(null).message("I have a great message for you!").showInformation();
        }
        currentRound++;
        
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/RoundComplete.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
