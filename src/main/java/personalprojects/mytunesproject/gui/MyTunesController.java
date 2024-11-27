package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
    @FXML
    private TableColumn clnTitleSong;
    @FXML
    private TableColumn clnArtistSong;
    @FXML
    private TableColumn clnCategorySong;
    @FXML
    private TableColumn clnTimeSong;

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
        clnTitleSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnArtistSong.setCellValueFactory(new PropertyValueFactory<>("artist"));
        clnCategorySong.setCellValueFactory(new PropertyValueFactory<>("category"));
        clnTimeSong.setCellValueFactory(new PropertyValueFactory<>("duration"));
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
    private void btnMoveUp(ActionEvent actionEvent) {
    }

    @FXML
    private void btnMoveDown(ActionEvent actionEvent) {
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

    @FXML
    private void btnDeletePlaylist(ActionEvent actionEvent) {
        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem();
        deleteItem(selectedPlaylist, "playlist");
    }

    @FXML
    private void btnDeleteFromPlaylist(ActionEvent actionEvent) {
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
        deleteItem(selectedSong, "songFromPlaylist");
    }

    @FXML
    private void btnDeleteSong(ActionEvent actionEvent) {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();
        deleteItem(selectedSong, "song");
    }

    private void deleteItem(Object selectedItem, String itemType) {
        if (selectedItem == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this item?");

        if ("playlist".equals(itemType)) {
            confirmationAlert.setContentText("The playlist will be deleted");
        } else if ("songFromPlaylist".equals(itemType)) {
            confirmationAlert.setContentText("This song will only be removed from the playlist, not deleted from the system.");
        } else if ("song".equals(itemType)) {
            confirmationAlert.setContentText("This song will be deleted from the list.");
        }

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if ("playlist".equals(itemType)) {
                    playlistModel.deletePlaylist((Playlist) selectedItem);
                } else if ("songFromPlaylist".equals(itemType)) {
                    playlistModel.removeSongFromPlaylist((Song) selectedItem);
                } else if ("song".equals(itemType)) {
                    try {
                        songModel.deleteSong((Song) selectedItem);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }


                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Item Deleted");
                successAlert.setContentText("The item has been deleted.");
                successAlert.showAndWait();
            }
        });
    }

}
