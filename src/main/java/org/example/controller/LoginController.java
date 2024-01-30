package org.example.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    static String userName;
    @FXML
    private Button login;
    @FXML
    private TextField txtName;

    public void initialize(){

    }

    public void LoginBtnOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtName.getText();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/client.fxml"))));
        stage.setTitle(txtName.getText());
        stage.show();
        txtName.clear();

    }



    }

