package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    public YoutubeController() {

    }

    public void setParent(MyTunesController parent) {
        this.parent = parent;

    }
}
