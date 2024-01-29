package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientFormController extends Thread {
    private final String[] emojis = {
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE07", // 😇
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE13"  // 😓
    };
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
    @FXML
    private AnchorPane emojiAnchorpane;
    @FXML
    private GridPane emojiGridpane;
    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        lblClientName.setText(LoginController.userName);
        //txtLabel.setText(LoginController.userName);

        emojiAnchorpane.setVisible(false);
        int btnIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (btnIndex < emojis.length) {
                    String emoji = emojis[btnIndex];
                    JFXButton emojiButton = createEmojiButton(emoji);
                    emojiGridpane.add(emojiButton, column, row);
                    btnIndex++;
                } else {
                    break;
                }
            }
        }



       // emoji();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost", 3000);
                    System.out.println("Socket is connected with server!");
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    // this.start();
                    while (socket.isConnected()) {
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

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        if (msg.matches(".*\\.(png|jpe?g|gif)$")) {
            // Handling image messages
            // Code to display images goes here
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
            // Handling text messages
            String name = msg.split("-")[0];
            String msgFromServer = msg.split("-")[1];

            // Create a text node to display the timestamp
            Text timeText = new Text(timestamp);
            timeText.setStyle("-fx-font-size: 10; -fx-fill: gray;");

            HBox timeBox = new HBox(timeText);
            timeBox.setAlignment(Pos.CENTER_LEFT);
            timeBox.setPadding(new Insets(0, 10, 0, 10));

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

            hBox.getChildren().addAll(timeBox, textFlow);

            Platform.runLater(() -> {
                vBox.getChildren().add(hBoxName);
                vBox.getChildren().add(hBox);
            });
        }
    }

    @FXML
    void emojiBtnOnAction(ActionEvent event) {
        emojiAnchorpane.setVisible(!emojiAnchorpane.isVisible());
    }

    private void emojiButtonAction(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        txtMsg.appendText(button.getText());
    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    @FXML
    void imageBtnOnAction(ActionEvent event) {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory() + dialog.getFile();
        dialog.dispose();
        sendImage(file);
        System.out.println(file + " chosen.");

    }

    private void sendImage(String msgToSend) {
      /*  Image image = new Image(msgToSend);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
//        TextFlow textFlow = new TextFlow(imageView);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);

        VBox.getChildren().add(hBox);

        try {
            dataOutputStream.writeUTF(lblClientName + "-" + msgToSend);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
       /* File file = new File(msgToSend);
        String imageUrl = file.toURI().toString(); // Convert file path to a URL string
        Image image = new Image(imageUrl);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);

        VBox.getChildren().add(hBox);

        try {
            dataOutputStream.writeUTF(lblClientName.getText() + "-" + imageUrl); // Sending the URL, not the file path
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        File file = new File(msgToSend);
        String imageUrl = file.toURI().toString(); // Convert file path to a URL string
        Image image = new Image(imageUrl);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);

        VBox.getChildren().add(hBox);

        try {
            dataOutputStream.writeUTF(lblClientName.getText() + "-" + imageUrl); // Sending the URL, not the file path
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}







