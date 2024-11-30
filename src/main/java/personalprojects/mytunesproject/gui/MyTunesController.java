package personalprojects.mytunesproject.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private ListView<String> lstPlaylistSongs;
    @FXML
    private TableView<Song> lstSongs;
    @FXML
    private TableColumn<Song, String> clnTitleSong;
    @FXML
    private TableColumn<Song, String> clnArtistSong;
    @FXML
    private TableColumn<Song, String> clnCategorySong;
    @FXML
    private TableColumn<Song, String> clnTimeSong;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Slider sliderDuration;
    @FXML
    private Label volumeProcent;
    @FXML
    private Button btnMute;
    @FXML
    private TableColumn<Playlist, String> clnPlaylistName;
    @FXML
    private TableColumn<Playlist, String> clnPlaylistSongs;
    @FXML
    private TableColumn<Playlist, String> clnPlaylistTime;
    @FXML
    private Label lblTotalDuration;
    @FXML
    private Label lblCurrentDuration;

    private SongModel songModel;
    private PlaylistModel playlistModel;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private double currentTime = 0;
    private int currentSongIndex = -1;
    private ScheduledExecutorService executorService;
    private double volumeNumber = 50;
    private boolean muteCheck = false;




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
        clnTimeSong.setCellValueFactory(cellData -> {Song song = cellData.getValue();
            return new SimpleStringProperty(song.getFormattedTime());
        });

        lstPlayList.setItems(playlistModel.getObservablePlaylists());

        clnPlaylistName.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        clnPlaylistSongs.setCellValueFactory(new PropertyValueFactory<>("playlistID"));
            clnPlaylistTime.setCellValueFactory(cellData -> {Playlist playlist = cellData.getValue();
                return new SimpleStringProperty("0");});

        // Volume slider setup
        sliderVolume.setMin(0);
        sliderVolume.setMax(100);
        sliderVolume.setValue(50);

        sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                volumeNumber = newValue.doubleValue() / 100.0;
                //This makes it possible for the song to be played at 1% TEMP FIX
                double adjustedVolume = Math.max(0.005, volumeNumber);

                DecimalFormat format = new DecimalFormat("#");
                volumeProcent.setText(format.format((volumeNumber *100))+ "%");
                //mediaPlayer.setVolume(volumeNumber);
                mediaPlayer.setVolume(adjustedVolume);
                updateVolumeIcon((Button) sliderVolume.getScene().lookup("#btnMute"), volumeNumber * 100);
            }
        });

        // Duration Slider
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            if (mediaPlayer != null && !sliderDuration.isValueChanging()) {
                Platform.runLater(() -> {
                    sliderDuration.setValue(mediaPlayer.getCurrentTime().toSeconds());
                });
            }
        }, 0, 1200, TimeUnit.MILLISECONDS);
        executorService.close();

        // Volume icon
        lstSongs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                playSelectedSong(newValue);
            }
        });
           String  iconPath = "/personalprojects/mytunesproject/UI Icons/volumeMediumIcon.png";
           setButtonIcon(btnMute, iconPath);
    }

    private void playSelectedSong(Song selectedSong) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            playNewSong(selectedSong); // Play the newly selected song
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the selected song: " + e.getMessage());
            errorAlert.showAndWait();
        }

    }


    @FXML
    private void btnMoveToPlaylist(ActionEvent actionEvent) throws Exception {
        songModel.addSongToPlaylist(lstPlayList.getSelectionModel().getSelectedItem(), lstSongs.getSelectionModel().getSelectedItem());
        playlistUpdate();
    }

    public void addNewPlaylist(String playlistName) throws Exception {
        String newPlaylist = playlistName;

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

        controller.setSongForEdit(null);

        stage.setTitle("New Song");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void btnEditSong(ActionEvent actionEvent) throws IOException {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem(); 
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

        controller.setSongForEdit(selectedSong);

        stage.setTitle("Edit Song");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    @FXML
    private void btnCloseProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    private void displayError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.showAndWait();
    }



    public void btnPlay(ActionEvent actionEvent) {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem();
        try {
            if (mediaPlayer != null) {
                if (isPlaying) {
                    currentTime = mediaPlayer.getCurrentTime().toSeconds();
                    mediaPlayer.pause();
                    isPlaying = false;
                    txtCurrentlyPlaying.setText("Paused");
                } else {
                    mediaPlayer.seek(Duration.seconds(currentTime)); // Seek to the current time
                    mediaPlayer.play();
                    isPlaying = true;
                    txtCurrentlyPlaying.setText("Now Playing: " + selectedSong.getName());
                }
            } else {
                if (selectedSong == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a song to play.");
                    alert.showAndWait();
                    return;
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

        currentSongIndex = lstSongs.getItems().indexOf(song);
        txtCurrentlyPlaying.setText("Now Playing: " + song.getName() + " by " + song.getArtist());

        mediaPlayer.setOnEndOfMedia(() -> {
            isPlaying = false;
            currentTime = 0;
            btnNextSong(null);
        });

        mediaPlayer.setOnReady(() -> {
            double duration = mediaPlayer.getTotalDuration().toSeconds();
            sliderDuration.setMax(duration);
            sliderDuration.setValue(0);
            mediaPlayer.play();
            mediaPlayer.setVolume(volumeNumber);
            isPlaying = true;
            currentTime = 0;
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!sliderDuration.isValueChanging()) {
                sliderDuration.setValue(newValue.toSeconds());
            }
        });
    }

    public void btnNextSong(ActionEvent actionEvent) {
        ObservableList<Song> songs = lstSongs.getItems();

        if (songs.isEmpty()) {
            txtCurrentlyPlaying.setText("No songs in the playlist.");
            return;
        }

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

    @FXML
    private void btnDeletePlaylist(ActionEvent actionEvent) {
        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem();
        deleteItem(selectedPlaylist, "playlist");
    }

    @FXML
    private void btnDeleteFromPlaylist(ActionEvent actionEvent) throws Exception {
    String tempSongName = lstPlaylistSongs.getSelectionModel().getSelectedItem();
    ObservableList<Song> songs = songModel.getObservableSongs();
    for (Song song : songs) {
        if (song.getName().equals(tempSongName)) {
            deleteItem(song, "songFromPlaylist");
        }
        playlistUpdate();
    }
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
                    try {
                        playlistModel.deletePlaylist((Playlist) selectedItem);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if ("songFromPlaylist".equals(itemType)) {
                    try {
                        songModel.removeSongFromPlaylist(lstPlayList.getSelectionModel().getSelectedItem(), (Song) selectedItem);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
        Button muteButton = (Button) actionEvent.getSource();
        if (mediaPlayer != null) {
            if (!muteCheck) {
                muteCheck = true;
                mediaPlayer.setVolume(0);
                setButtonIcon(muteButton, "/personalprojects/mytunesproject/UI Icons/volumeMuteIcon.png");
            } else {
                muteCheck = false;
                mediaPlayer.setVolume(volumeNumber);
                updateVolumeIcon(muteButton, volumeNumber * 100);
            }
        }
    }

    private void updateVolumeIcon(Button button, double volumePercentage) {
        String iconPath = "/personalprojects/mytunesproject/UI Icons/volumeMuteIcon.png"; // Default to mute icon
        if (volumePercentage > 0 && volumePercentage <= 30) {
            iconPath = "/personalprojects/mytunesproject/UI Icons/volumeLowIcon.png";
        } else if (volumePercentage > 30 && volumePercentage <= 70) {
            iconPath = "/personalprojects/mytunesproject/UI Icons/volumeMediumIcon.png";
        } else if (volumePercentage > 70) {
            iconPath = "/personalprojects/mytunesproject/UI Icons/volumeHighIcon.png";
        }
        setButtonIcon(btnMute, iconPath);
    }

    private void setButtonIcon(Button button, String iconPath) {
        button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(iconPath))));
    }

    public void sliderDuration(MouseEvent mouseEvent) {
        if (mediaPlayer != null && !sliderDuration.isValueChanging()) {
            mediaPlayer.seek(Duration.seconds(sliderDuration.getValue()));
        }
    }
    @FXML
    private void playlistSelection(MouseEvent mouseEvent) throws Exception {
        playlistUpdate();
        }
    private void playlistUpdate() throws Exception {
        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem();
        ObservableList<Song> songsOnPlaylist = songModel.getSongsOnPlaylist(selectedPlaylist);
        lstPlaylistSongs.getItems().clear();
        for (Song song : songsOnPlaylist) {
            lstPlaylistSongs.getItems().add(song.getName());
        }
    }
}
