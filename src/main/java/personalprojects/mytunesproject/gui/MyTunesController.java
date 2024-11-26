package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personalprojects.mytunesproject.HelloApplication;

import java.io.IOException;

public class MyTunesController {
    @FXML
    private TextField txtSearch;
    @FXML
    private Label txtCurrentlyPlaying;
    @FXML
    private TableView lstPlayList;
    @FXML
    private ListView lstPlaylistSongs;
    @FXML
    private TableView lstSongs;



    @FXML
    private void btnMoveToPlaylist(ActionEvent actionEvent) {
    }
    @FXML
    private void btnNewPlaylist(ActionEvent actionEvent) throws IOException  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Playlist.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewPlaylistController controller = loader.getController();
        controller.setParent(this);

        stage.setTitle("New/Edit Playlist");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    private void btnEditPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Playlist.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewPlaylistController controller = loader.getController();
        controller.setParent(this);

        stage.setTitle("New/Edit Playlist");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    private void btnDeletePlaylist(ActionEvent actionEvent) {
    }
    @FXML
    private void btnMoveUp(ActionEvent actionEvent) {
    }
    @FXML
    private void btnMoveDown(ActionEvent actionEvent) {
    }
    @FXML
    private void btnDeleteFromPlaylist(ActionEvent actionEvent) {
    }
    @FXML
    private void btnNewSong(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Songs.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewSongController controller = loader.getController();
        controller.setParent(this);

        stage.setTitle("New/Edit Songs");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    @FXML
    private void btnEditSong(ActionEvent actionEvent) throws IOException  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Songs.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewSongController controller = loader.getController();
        controller.setParent(this);

        stage.setTitle("New/Edit Songs");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    private void btnDeleteSong(ActionEvent actionEvent) {
    }
    @FXML
    private void btnCloseProgram(ActionEvent actionEvent) {
        
    }
    @FXML
    private void sliderVolume(MouseEvent mouseEvent) {
    }
    @FXML
    private void btnNextSong(ActionEvent actionEvent) {
    }
    @FXML
    private void btnSearch(ActionEvent actionEvent) {
    }
    @FXML
    private void btnPlay(ActionEvent actionEvent) {
    }
    @FXML
    private void btnLastSong(ActionEvent actionEvent) {
    }
}