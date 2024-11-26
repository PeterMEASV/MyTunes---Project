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
    public void btnMoveToPlaylist(ActionEvent actionEvent) {
    }
    @FXML
    public void btnNewPlaylist(ActionEvent actionEvent) throws IOException  {
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
    public void btnEditPlaylist(ActionEvent actionEvent) throws IOException {
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
    public void btnDeletePlaylist(ActionEvent actionEvent) {
    }
    @FXML
    public void btnMoveUp(ActionEvent actionEvent) {
    }
    @FXML
    public void btnMoveDown(ActionEvent actionEvent) {
    }
    @FXML
    public void btnDeleteFromPlaylist(ActionEvent actionEvent) {
    }
    @FXML
    public void btnNewSong(ActionEvent actionEvent) throws IOException {

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
    public void btnEditSong(ActionEvent actionEvent) throws IOException  {
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
    public void btnDeleteSong(ActionEvent actionEvent) {
    }
    @FXML
    public void btnCloseProgram(ActionEvent actionEvent) {
    }
    @FXML
    public void sliderVolume(MouseEvent mouseEvent) {
    }
    @FXML
    public void btnNextSong(ActionEvent actionEvent) {
    }
    @FXML
    public void btnSearch(ActionEvent actionEvent) {
    }
    @FXML
    public void btnPlay(ActionEvent actionEvent) {
    }
    @FXML
    public void btnLastSong(ActionEvent actionEvent) {
    }
}