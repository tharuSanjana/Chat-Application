import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.server.Server;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/login.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Login");

        stage.show();
        new Thread(()->{
            Server server = new Server();
            try {
                server.server();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
