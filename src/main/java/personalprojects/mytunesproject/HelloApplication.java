package personalprojects.mytunesproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    String css;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/personalprojects/mytunesproject/My-Tunes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        css = this.getClass().getResource("/CSS/MyTunes.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("My Tunes");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);

    }

    public static void main(String[] args) {
        launch();
    }

    public String getCSS()
    {
        return css;
    }
}