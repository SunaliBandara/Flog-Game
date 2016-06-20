/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.controller;

import com.nsbm.common.CommonData;
import static com.nsbm.service.PlayerServiceHandler.addPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class StartUpController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Button exitButton;
    @FXML
    private Button startButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        String playerName = username.getText();
        CommonData.username = playerName;
        String password = "";
        String result = addPlayer(playerName, password);
        if (result.equals("success")) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            stage = (Stage) startButton.getScene().getWindow();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Player Already Exist");
        }
    }
}
