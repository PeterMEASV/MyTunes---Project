package personalprojects.mytunesproject.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.bll.SongManager;
import personalprojects.mytunesproject.gui.Model.SongModel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class YoutubeController {

    private MyTunesController parent;
    WebView browser;
    WebEngine webEngine;

    public YoutubeController() {

    }

    public void setParent(MyTunesController parent) {
        this.parent = parent;
    }

    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setWidth(800);
        stage.setHeight(600);
        browser = new WebView();
        webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);

        scene.setRoot(scrollPane);

        // Set the close request handler
        stage.setOnCloseRequest(event -> {
            // Stop the WebEngine from loading any more content
            webEngine.load("about:blank"); // Load a blank page to stop any media

            // Optionally, you can show a confirmation dialog here
            // If you want to prevent closing based on user input, you can consume the event
            // event.consume(); // Uncomment this line to prevent closing

            // Perform any other cleanup if necessary
        });

        webEngine.getLoadWorker().stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            stage.setTitle(webEngine.getLocation());
                        }
                    }
                });
        webEngine.load("https://www.youtube.com/");

        stage.setScene(scene);
        stage.show();
    }
}
