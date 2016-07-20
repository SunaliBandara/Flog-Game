package com.nsbm.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.nsbm.common.CommonData;
import com.nsbm.common.Mouse;
import static com.nsbm.common.ResponseResult.SUCCESS;
import com.nsbm.common.Validator;
import static com.nsbm.service.PlayerServiceHandler.RegisterPlayer;
import static com.nsbm.service.PlayerServiceHandler.removePlayer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private Validator validate = new Validator();
    @FXML
    private Button exitButton;
    @FXML 
    private Button backButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Pane signUpPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private Alert alert;
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
        alert = new Alert(AlertType.CONFIRMATION,"Do You Want To Exit The Game?");
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-confirm.png"));
        alert.getDialogPane().setPrefSize(390,95);
        alert.initStyle(StageStyle.UNDECORATED);       
        alert.initOwner(exitButton.getScene().getWindow());
        if (alert.showAndWait().get() == ButtonType.OK){
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
            removePlayer(CommonData.username);
            System.exit(0);
        }
    }
    
    @FXML
    private void registerAction(ActionEvent event){
        if(validate.isInputEmpty(usernameField) && validate.isInputEmpty(emailField) && validate.isInputEmpty(passwordField)){
            String name = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String result = RegisterPlayer(name,password,email);   
            if(result.equals(SUCCESS)){
                alert = new Alert(AlertType.INFORMATION,"Successfully Registered");
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-information.png"));
                alert.getDialogPane().setPrefSize(390,95);
                alert.initStyle(StageStyle.UNDECORATED);       
                alert.initOwner(signUpButton.getScene().getWindow());
                if (alert.showAndWait().get() == ButtonType.OK){
                    usernameField.clear();
                    passwordField.clear();
                    emailField.clear();
                }
            }
            else{
                alert = new Alert(AlertType.ERROR,"Error");
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-error.png"));
                alert.getDialogPane().setPrefSize(390,95);
                alert.initStyle(StageStyle.UNDECORATED);       
                alert.initOwner(signUpButton.getScene().getWindow());
                alert.showAndWait();
            }
        }
        else{
            alert = new Alert(AlertType.ERROR,"Fields cannot be empty");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-error.png"));
            alert.getDialogPane().setPrefSize(390,95);
            alert.initStyle(StageStyle.UNDECORATED);       
            alert.initOwner(signUpButton.getScene().getWindow());
            alert.showAndWait();
        }
    }   
}
