package com.nsbm.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.nsbm.common.Mouse;
import static com.nsbm.common.ResponseResult.SUCCESS;
import static com.nsbm.service.PlayerServiceHandler.RegisterPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Muthu Devendra
 */
public class SignUpController implements Initializable {
    private Mouse mouse = new Mouse();
    @FXML
    private Button exitButton;
    @FXML 
    private Button backButton;
    @FXML
    private Pane signUpPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        signUpPane.setOnMousePressed((MouseEvent event) -> {
            mouse.setX(event.getX());
            mouse.setY(event.getY());
        });
        signUpPane.setOnMouseDragged((MouseEvent event) -> {
            signUpPane.getScene().getWindow().setX(event.getScreenX() - mouse.getX() - 14);
            signUpPane.getScene().getWindow().setY(event.getScreenY() - mouse.getY() - 14);
        });
    }    
    
    @FXML
    private void backAction(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartUp.fxml"));
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
    
    @FXML
    private void registerAction(ActionEvent event){
        String name = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String result = RegisterPlayer(name,password,email);   
        if(result.equals(SUCCESS)){
            JOptionPane.showMessageDialog(null, "Success");
        }
        else{
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    
}
