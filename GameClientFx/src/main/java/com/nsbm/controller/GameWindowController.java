/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.controller;

import com.nsbm.common.CommonData;
import static com.nsbm.service.WordServiceHandler.getInitialLetters;
import static com.nsbm.service.WordServiceHandler.getLetters;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class GameWindowController implements Initializable {

    private String initialLetters = null;
    private static int counter = 10;
    private final Timer timer = new Timer();
    private final TextField[] letterFields = new TextField[10];

    @FXML
    private TextField initialLetterOne, initialLetterTwo;
    @FXML
    private TextField noOfVowels, noOfConsonants;
    @FXML
    private AnchorPane letterPane;
    @FXML
    private Button requestButton;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label timerLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialLetters = getInitialLetters();
        initialLetterOne.setText(String.valueOf(initialLetters.charAt(0)).toUpperCase());
        initialLetterTwo.setText(String.valueOf(initialLetters.charAt(1)).toUpperCase());
    }

    public void getRequiredLetters() {
        int vowelsRequired = Integer.parseInt(noOfVowels.getText());
        int consonantsRequired = Integer.parseInt(noOfConsonants.getText());
        if (consonantsRequired + vowelsRequired > 10) {
            JOptionPane.showMessageDialog(null, "You can not select more than 10");
        } else {
            String letters = getLetters(vowelsRequired, consonantsRequired);
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
            requestButton.setDisable(true);
            CommonData.letters = initialLetters+letters;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (counter == 0) {
                                timer.cancel();
                                Stage stage = new Stage();
                                Parent root = null;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/fxml/GamePlay.fxml"));
                                } catch (IOException ex) {
                                    Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add("/styles/Styles.css");
                                stage.setResizable(false);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.setScene(scene);
                                stage.show();
                                
                                stage = (Stage) requestButton.getScene().getWindow();
                                stage.close();
                            } else {
                                timerLabel.setText(String.valueOf(counter));
                                counter--;
                                if(counter==1){
                                    final URL resource = getClass().getResource("/styles/mp3.mp3");
                                    final Media media = new Media(resource.toString());
                                    final MediaPlayer mediaPlayer = new MediaPlayer(media);
                                    mediaPlayer.play();
                                }
                            }
                        }
                    });
                }
            }, 0, 1000);
        }
    }
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void exitAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
