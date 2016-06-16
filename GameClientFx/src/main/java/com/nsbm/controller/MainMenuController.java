/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.controller;

import com.nsbm.common.CommonData;
import static com.nsbm.common.CommonData.currentRound;
import static com.nsbm.common.CommonUtil.setModelData;
import com.nsbm.entity.Player;
import static com.nsbm.service.PlayerServiceHandler.getAllPlayers;
import static com.nsbm.service.PlayerServiceHandler.listenToJoinEvent;
import static com.nsbm.service.PlayerServiceHandler.notifyPlayerJoin;
import static com.nsbm.service.PlayerServiceHandler.setModelReference;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class MainMenuController implements Initializable {

    final ObservableList<String> model = FXCollections.observableArrayList();

    private String[] playerNames;
    private Player[] allPlayers;

    @FXML
    private ListView<String> listBox;
    @FXML
    private Label userNameLabel;
    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameLabel.setText(CommonData.username);
        allPlayers = getAllPlayers();
        setModelData(allPlayers, model);
        listBox.setItems(model);
        setModelReference(model);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                notifyPlayerJoin();
                if (currentRound == 0) {
                    listenToJoinEvent();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void startGame(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GameWindow.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage = (Stage) startButton.getScene().getWindow();
        stage.close();
    }
}
