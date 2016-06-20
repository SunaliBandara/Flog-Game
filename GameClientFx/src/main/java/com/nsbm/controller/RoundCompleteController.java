/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.controller;

import static com.nsbm.common.CommonUtil.setRoundCompletedModelData;
import static com.nsbm.service.PlayerServiceHandler.getRoundCompletedPlayers;
import static com.nsbm.service.PlayerServiceHandler.listenToRoundCompletionEvent;
import static com.nsbm.service.PlayerServiceHandler.notifyRoundCompletion;
import static com.nsbm.service.PlayerServiceHandler.setLabelReference;
import static com.nsbm.service.PlayerServiceHandler.setModelReference;
import static com.nsbm.service.PlayerServiceHandler.setSpecialPointsLabel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author Lakshitha
 */
public class RoundCompleteController implements Initializable {

    private String[] allCompletedPlayer;
    final ObservableList<String> model = FXCollections.observableArrayList();
    
    @FXML
    private Label nextRoundTime;
    @FXML
    private ListView<String> completeList;
    @FXML
    private Label specialPoints;
    @FXML
    private Button exitButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allCompletedPlayer = getRoundCompletedPlayers();
        setSpecialPointsLabel(specialPoints);
        setModelReference(model);
        setLabelReference(nextRoundTime);
        completeList.setItems(model);
        new Thread(new Runnable() {
            public void run() {
                setRoundCompletedModelData(allCompletedPlayer, model);
                listenToRoundCompletionEvent();
            }
        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScoringMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyRoundCompletion();
    }
    @FXML
    private void exitAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
