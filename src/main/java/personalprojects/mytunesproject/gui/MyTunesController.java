package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// import Project
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.gui.Model.PlaylistModel;
import personalprojects.mytunesproject.gui.Model.SongModel;

public class MyTunesController implements Initializable {
    @FXML
    private TextField txtSearch;
    @FXML
    private Label txtCurrentlyPlaying;
    @FXML
    private TableView<Playlist> lstPlayList;
    @FXML
    private ListView<Song> lstPlaylistSongs;
    @FXML
    private TableView<Song> lstSongs;

    private SongModel songModel;
    private PlaylistModel playlistModel;

    public MyTunesController() {
        try {
            songModel = new SongModel();
            playlistModel = new PlaylistModel();  // Ensure playlistModel is initialized here
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstSongs.setItems(songModel.getObservableSongs());
        lstPlaylistSongs.setItems(songModel.getObservableSongs());

        lstPlayList.setItems(playlistModel.getObservablePlaylists());
    }

    @FXML
    private void btnMoveToPlaylist(ActionEvent actionEvent) {
    }

    public void addNewPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName, 0, "00:00");

        playlistModel.getObservablePlaylists().add(newPlaylist);

        playlistModel.createPlaylist(newPlaylist);
    }

    @FXML
    private void btnNewPlaylist(ActionEvent actionEvent) throws IOException {
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
    private void btnEditSong(ActionEvent actionEvent) throws IOException {
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void displayError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.showAndWait();
    }

    public void btnLastSong(ActionEvent actionEvent) {
    }

    public void btnPlay(ActionEvent actionEvent) {
    }

    public void btnNextSong(ActionEvent actionEvent) {
    }

    public void btnSearch(ActionEvent actionEvent) {
    }

    public void sliderVolume(MouseEvent mouseEvent) {
    }
}
