/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.controller;

import com.nsbm.common.CommonData;
import com.nsbm.common.Mouse;
import com.nsbm.common.Validator;
import static com.nsbm.service.PlayerServiceHandler.LoginPlayer;
import static com.nsbm.service.PlayerServiceHandler.removePlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
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
 * @author Muthu
 */
public class StartUpController implements Initializable {

    private Mouse mouse = new Mouse();
    private Validator validate = new Validator();
    @FXML
    private TextField username;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button exitButton;
    @FXML
    private Button startButton;
    @FXML
    private Hyperlink signUpLink;
    @FXML
    private Pane startPane;
    @FXML
    private Alert alert;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.setX(event.getX());
                mouse.setY(event.getY());
            }

        });
        startPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startPane.getScene().getWindow().setX(event.getScreenX() - mouse.getX() - 14);
                startPane.getScene().getWindow().setY(event.getScreenY() - mouse.getY() - 14);
            }

        });
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want To Exit The Game?");
        alert.setHeaderText(null);
        alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-confirm.png"));
        alert.getDialogPane().setPrefSize(350,95);
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
    private void handleStart(ActionEvent event) throws IOException {
        if(validate.isInputEmpty(username) && validate.isInputEmpty(passwordField)){
            String playerName = username.getText();
            CommonData.username = playerName;
            String password = passwordField.getText();
            String result = LoginPlayer(playerName, password);
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
            } else if (result.equals("invalid login")) {
                alert = new Alert(Alert.AlertType.ERROR,"Your User name or Password is Incorrect");
                alert.setHeaderText(null);
                alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-error.png"));
                alert.getDialogPane().setPrefSize(350,95);
                alert.initStyle(StageStyle.UNDECORATED);       
                alert.initOwner(startButton.getScene().getWindow());
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.WARNING,"Player Doesn't exists");
                alert.setHeaderText(null);
                alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-warning.png"));
                alert.getDialogPane().setPrefSize(350,95);
                alert.initStyle(StageStyle.UNDECORATED);       
                alert.initOwner(startButton.getScene().getWindow());
                alert.showAndWait();
            }
        }
        else{
            alert = new Alert(Alert.AlertType.WARNING,"Please Enter your username and Password");
            alert.setHeaderText(null);
            alert.setGraphic(new ImageView("com/sun/javafx/scene/control/skin/modena/dialog-warning.png"));
            alert.getDialogPane().setPrefSize(350,95);
            alert.initStyle(StageStyle.UNDECORATED);       
            alert.initOwner(startButton.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleSignUpAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage = (Stage) signUpLink.getScene().getWindow();
        stage.close();
    }
}
