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
   // private String username;

    public void initialize(){

    }

    public void LoginBtnOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtName.getText();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/client.fxml"))));
        stage.setTitle(txtName.getText());
        stage.show();
        txtName.clear();
       /* String userName = txtName.getText().trim();
        Stage newStage = new Stage();
        newStage.setTitle(userName);
        // Add content to the new window
        BorderPane newRoot = new BorderPane();
        newStage.setScene(new Scene(newRoot, 600, 600));
        txtName.clear();

        // Show the new window
        newStage.show();*/

     /*  if (!txtName.getText().isEmpty()&&txtName.getText().matches("[A-Za-z0-9]+")){
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/client.fxml"));

            ClientFormController controller = new ClientFormController();
            controller.setClientName(txtName.getText()); // Set the parameter
            //fxmlLoader.setController(controller);

            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle(txtName.getText());
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.setOnCloseRequest(windowEvent -> {
                //controller.shutdown();
            });
            primaryStage.show();

            txtName.clear();
        }else{
            new Alert(Alert.AlertType.ERROR, "Please enter your name").show();
        }*/

    }



    }

