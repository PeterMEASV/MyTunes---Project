package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MyTunesController {
    public TextField txtSearch;
    public Label txtCurrentlyPlaying;
    public TableView lstPlayList;
    public ListView lstPlaylistSongs;
    public TableView lstSongs;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

    }

    public void btnMoveToPlaylist(ActionEvent actionEvent) {
    }

    public void btnNewPlaylist(ActionEvent actionEvent) {
    }

    public void btnEditPlaylist(ActionEvent actionEvent) {
    }

    public void btnDeletePlaylist(ActionEvent actionEvent) {
    }

    public void btnMoveUp(ActionEvent actionEvent) {
    }

    public void btnMoveDown(ActionEvent actionEvent) {
    }

    public void btnDeleteFromPlaylist(ActionEvent actionEvent) {
    }

    public void btnNewSong(ActionEvent actionEvent) {
    }

    public void btnEditSong(ActionEvent actionEvent) {
    }

    public void btnDeleteSong(ActionEvent actionEvent) {
    }

    public void btnCloseProgram(ActionEvent actionEvent) {
    }

    public void sliderVolume(MouseEvent mouseEvent) {
    }

    public void btnNextSong(ActionEvent actionEvent) {
    }

    public void btnSearch(ActionEvent actionEvent) {
    }

    public void btnPlay(ActionEvent actionEvent) {
    }

    public void btnLastSong(ActionEvent actionEvent) {
    }
}