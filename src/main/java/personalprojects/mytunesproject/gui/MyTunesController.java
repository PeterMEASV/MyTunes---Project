package personalprojects.mytunesproject.gui;

import javafx.collections.ObservableList;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


// import Project
import javafx.util.Duration;
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
    @FXML
    private Slider sliderVolume;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private double currentTime = 0;
    private int currentSongIndex = -1;

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
        try {
            lstSongs.setItems(songModel.getObservableSongs());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clnTitleSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnArtistSong.setCellValueFactory(new PropertyValueFactory<>("artist"));
        clnCategorySong.setCellValueFactory(new PropertyValueFactory<>("category"));
        clnTimeSong.setCellValueFactory(new PropertyValueFactory<>("duration"));
        try {
            lstPlaylistSongs.setItems(songModel.getObservableSongs());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        lstPlayList.setItems(playlistModel.getObservablePlaylists());

        // Volume slider setup
        sliderVolume.setValue(50);
        sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
            }
        });
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

        // No song passed for new song
        controller.setSongForEdit(null);

        stage.setTitle("New Song");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void btnEditSong(ActionEvent actionEvent) throws IOException {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem(); // Get selected song from the list
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Song Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song to edit.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Songs.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewSongController controller = loader.getController();
        controller.setParent(this);

        // Pass the selected song to the controller for editing
        controller.setSongForEdit(selectedSong);

        stage.setTitle("Edit Song");
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



    public void btnPlay(ActionEvent actionEvent) {
        try {
            if (mediaPlayer != null) {
                if (isPlaying) {
                    currentTime = mediaPlayer.getCurrentTime().toSeconds();
                    mediaPlayer.pause();
                    isPlaying = false;
                    txtCurrentlyPlaying.setText("Paused");
                } else {
                    mediaPlayer.seek(Duration.seconds(currentTime));
                    mediaPlayer.play();
                    isPlaying = true;
                    txtCurrentlyPlaying.setText("Now Playing: " + lstSongs.getItems().get(currentSongIndex).getName());
                }
            } else {
                Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();
                if (selectedSong == null) {
                    if (currentSongIndex >= 0 && currentSongIndex < lstSongs.getItems().size()) {
                        selectedSong = lstSongs.getItems().get(currentSongIndex);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a song to play.");
                        alert.showAndWait();
                        return;
                    }
                }
                playNewSong(selectedSong);
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the song: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    private void playNewSong(Song song) throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String filePath = song.getFilePath();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        isPlaying = true;
        currentTime = 0;

        currentSongIndex = lstSongs.getItems().indexOf(song);

        txtCurrentlyPlaying.setText("Now Playing: " + song.getName() + " by " + song.getArtist());

        mediaPlayer.setOnEndOfMedia(() -> {
            isPlaying = false;
            currentTime = 0;

            btnNextSong(null);
        });
    }

    public void btnNextSong(ActionEvent actionEvent) {
        ObservableList<Song> songs = lstSongs.getItems();

        if (songs.isEmpty()) {
            txtCurrentlyPlaying.setText("No songs in the playlist.");
            return;
        }

        // Move to the next song
        currentSongIndex = (currentSongIndex + 1) % songs.size();
        Song nextSong = songs.get(currentSongIndex);

        try {
            playNewSong(nextSong);
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the next song: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    public void btnLastSong(ActionEvent actionEvent) {
        ObservableList<Song> songs = lstSongs.getItems();

        if (songs.isEmpty()) {
            txtCurrentlyPlaying.setText("No songs in the playlist.");
            return;
        }

        // Move to the next song
        currentSongIndex = (currentSongIndex - 1) % songs.size();
        Song nextSong = songs.get(currentSongIndex);

        try {
            playNewSong(nextSong);
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the next song: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void btnSearch(ActionEvent actionEvent) {
        String query = txtSearch.getText().trim().toLowerCase();
        Button searchButton = (Button) actionEvent.getSource();

        if (searchButton.getText().equals("Clear")) {

            txtSearch.clear();
            songModel.searchSongs("");
            searchButton.setText("Search");
        } else {
            if (!query.isEmpty()) {
                songModel.searchSongs(query);
                searchButton.setText("Clear");
            }
        }
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
    public void UpdateSongs() throws Exception {
        lstSongs.setItems(songModel.getObservableSongs());
        System.out.println(songModel.getObservableSongs().size());
    }

    public void btnMute(ActionEvent actionEvent) {
    }

    public void sliderDuration(MouseEvent mouseEvent) {
    }
}
