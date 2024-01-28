package org.example.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientFormController extends Thread {

    @FXML
    private TextField txtMsg;
    public Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    @FXML
    private Label lblClientName;
    @FXML
    private VBox VBox;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public Text txtLabel;

    public void initialize(){
        lblClientName.setText(LoginController.userName);
        //txtLabel.setText(LoginController.userName);

     new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                socket = new Socket("localhost", 3000);
                System.out.println("Socket is connected with server!");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                // this.start();
                while (socket.isConnected()){
                    String receivingMsg = dataInputStream.readUTF();
                    receiveMessage(receivingMsg, ClientFormController.this.VBox);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }).start();

        }

    @FXML
    void sendBtnOnAction(ActionEvent event) throws IOException {
       //send();
        sendMsg(txtMsg.getText());
    }
    @FXML
    void txtClientOnAction(ActionEvent event) {
        //send();
    }

    private void sendMsg(String msgToSend) throws IOException {
       /* if (!msgToSend.isEmpty()){
            if (!msgToSend.matches(".*\\.(png|jpe?g|gif)$")){

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 0, 10));

                Text text = new Text(msgToSend);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

//              #0693e3 #37d67a #40bf75
                textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(1, 1, 1));

                hBox.getChildren().add(textFlow);

                HBox hBoxTime = new HBox();
                hBoxTime.setAlignment(Pos.CENTER_RIGHT);
                hBoxTime.setPadding(new Insets(0, 5, 5, 10));
                String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                Text time = new Text(stringTime);
                time.setStyle("-fx-font-size: 8");

                hBoxTime.getChildren().add(time);

                VBox.getChildren().add(hBox);
                VBox.getChildren().add(hBoxTime);


                dataOutputStream.writeUTF(lblClientName + "-" + msgToSend);
                dataOutputStream.flush();

                txtMsg.clear();
            }
        }*/


            if (!msgToSend.isEmpty()) {
                if (!msgToSend.matches(".*\\.(png|jpe?g|gif)$")) {

                    // Construct the message including the sender's name
                    String fullMessage = lblClientName.getText() + "-" + msgToSend;

                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 0, 10));

                    Text text = new Text(msgToSend);
                    text.setStyle("-fx-font-size: 14");
                    TextFlow textFlow = new TextFlow(text);

                    // Setting style for the textFlow
                    textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(1, 1, 1));

                    hBox.getChildren().add(textFlow);

                    HBox hBoxTime = new HBox();
                    hBoxTime.setAlignment(Pos.CENTER_RIGHT);
                    hBoxTime.setPadding(new Insets(0, 5, 5, 10));
                    String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                    Text time = new Text(stringTime);
                    time.setStyle("-fx-font-size: 8");

                    hBoxTime.getChildren().add(time);

                    VBox.getChildren().add(hBox);
                    VBox.getChildren().add(hBoxTime);

                    // Sending the message
                    dataOutputStream.writeUTF(fullMessage);
                    dataOutputStream.flush();

                    txtMsg.clear();
                }
            }
        }

    public static void receiveMessage(String msg, VBox vBox) throws IOException {
        if (msg.matches(".*\\.(png|jpe?g|gif)$")) {
            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_LEFT);
            Text textName = new Text(msg.split("[-]")[0]);
            TextFlow textFlowName = new TextFlow(textName);
            hBoxName.getChildren().add(textFlowName);

            Image image = new Image(msg.split("[-]")[1]);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            hBox.getChildren().add(imageView);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                }
            });

        } else {
            String name = msg.split("-")[0];
            String msgFromServer = msg.split("-")[1];

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_LEFT);
            Text textName = new Text(name);
            TextFlow textFlowName = new TextFlow(textName);
            hBoxName.getChildren().add(textFlowName);

            Text text = new Text(msgFromServer);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0, 0, 0));


            hBox.getChildren().add(textFlow);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                }
            });
        }
    }
}






