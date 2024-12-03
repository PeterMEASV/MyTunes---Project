package personalprojects.mytunesproject.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// Import project-specific classes
import javafx.util.Duration;
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.gui.Model.PlaylistModel;
import personalprojects.mytunesproject.gui.Model.SongModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MyTunesController implements Initializable {
    // FXML components
    @FXML
    private TextField txtSearch; // Text field for searching songs
    @FXML
    private Label txtCurrentlyPlaying; // Label to display currently playing song
    @FXML
    private TableView<Playlist> lstPlayList; // TableView for displaying playlists
    @FXML
    private TableView<Song> lstPlaylistSongs; // ListView for displaying songs in the selected playlist
    @FXML
    private TableView<Song> lstSongs; // TableView for displaying all songs
    @FXML
    private TableColumn<Song, String> clnTitleSong; // Column for song titles
    @FXML
    private TableColumn<Song, String> clnArtistSong; // Column for song artists
    @FXML
    private TableColumn<Song, String> clnCategorySong; // Column for song categories
    @FXML
    private TableColumn<Song, String> clnTimeSong; // Column for song duration
    @FXML
    private TableColumn<Song, String> clnTitlePlaylist; // Column for song titles in the playlist
    @FXML
    private TableColumn<Song, String> clnArtistPlaylist; // Column for song artists in the playlist
    @FXML
    private TableColumn<Song, String> clnCategoryPlaylist; // Column for song categories in the playlist
    @FXML
    private TableColumn<Song, String> clnTimePlaylist; // Column for song duration in the playlist
    @FXML
    private Slider sliderVolume; // Slider for adjusting volume
    @FXML
    private Slider sliderDuration; // Slider for song duration
    @FXML
    private Label volumeProcent; // Label to display current volume percentage
    @FXML
    private Button btnMute; // Button to mute/unmute audio
    @FXML
    private TableColumn<Playlist, String> clnPlaylistName; // Column for playlist names
    @FXML
    private TableColumn<Playlist, String> clnPlaylistSongs; // Column for number of songs in the playlist
    @FXML
    private TableColumn<Playlist, String> clnPlaylistTime; // Column for total duration of the playlist
    @FXML
    private Label lblTotalDuration; // Label to display total duration of the currently playing song
    @FXML
    private Label lblCurrentDuration; // Label to display current duration of the song


    // Model instances
    private SongModel songModel; // Model for managing songs
    private PlaylistModel playlistModel; // Model for managing playlists
    private MediaPlayer mediaPlayer; // Media player for playing songs
    private boolean isPlaying = false; // Flag to check if a song is currently playing
    private double currentTime = 0; // Current playback time of the song
    private int currentSongIndex = -1; // Index of the currently playing song
    private ScheduledExecutorService executorService; // Executor service for updating UI components
    private double volumeNumber = 50; // Current volume level
    private boolean muteCheck = false; // Flag to check if the audio is muted

    /**
     * Constructor for MyTunesController.
     * Initializes the songModel and playlistModel, handling exceptions if they can't be initialized.
     */
    public MyTunesController() {
        try {
            songModel = new SongModel(); // Initialize the song model
            playlistModel = new PlaylistModel(); // Initialize the playlist model
        } catch (Exception e) {
            displayError(e); // Display an error message if initialization fails
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the controller is initialized.
     * It sets up the initial state of the UI components, such as binding data models
     * to UI elements and initializing the volume slider.
     *
     * @param location  the location of the F XML file
     * @param resources the resources to be used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Set the items for the song list
            lstSongs.setItems(songModel.getObservableSongs());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Set up cell value factories for song columns
        clnTitleSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnArtistSong.setCellValueFactory(new PropertyValueFactory<>("artist"));
        clnCategorySong.setCellValueFactory(new PropertyValueFactory<>("category"));
        clnTimeSong.setCellValueFactory(cellData -> {
            Song song = cellData.getValue();
            return new SimpleStringProperty(song.getFormattedTime());
        });
        try {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        lstPlayList.setItems(playlistModel.getObservablePlaylists());
        clnPlaylistName.setCellValueFactory(new PropertyValueFactory<>("playlistName"));

        clnPlaylistSongs.setCellValueFactory(cellData -> {
            Playlist playlist = cellData.getValue();
            try {
                ObservableList<Song> songsOnPlaylist = songModel.getSongsOnPlaylist(playlist);
                return new SimpleStringProperty(Integer.toString(songsOnPlaylist.size()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        clnPlaylistTime.setCellValueFactory(cellData -> {
            Playlist playlist = cellData.getValue();
            try {
                return new SimpleStringProperty(getFormattedTime((songModel.getTotalDuration(playlist))));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Update the properties for each playlist after loading them
        for (Playlist playlist : playlistModel.getObservablePlaylists()) {
            ObservableList<Song> songsOnPlaylist = null;
            try {
                songsOnPlaylist = songModel.getSongsOnPlaylist(playlist);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            playlist.setNumberOfSongs(songsOnPlaylist.size()); // Update the number of songs
            try {
                playlist.setTotalDuration(getFormattedTime(songModel.getTotalDuration(playlist))); // Update the total duration
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // Refresh the TableView to display updated values
        lstPlayList.refresh();

        // Set up cell value factories for song columns in lstPlaylistSongs
        clnTitlePlaylist.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Volume slider setup
        sliderVolume.setMin(0);
        sliderVolume.setMax(100);
        sliderVolume.setValue(20);

        // Listener for volume slider changes
        sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                volumeNumber = newValue.doubleValue() / 100.0;
                double adjustedVolume = Math.max(0.005, volumeNumber);
                DecimalFormat format = new DecimalFormat("#");
                volumeProcent.setText(format.format((volumeNumber * 100)) + "%");
                mediaPlayer.setVolume(adjustedVolume);
                updateVolumeIcon((Button) sliderVolume.getScene().lookup("#btnMute"), volumeNumber * 100);
            }
        });

        // Duration Slider setup
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            if (mediaPlayer != null && !sliderDuration.isValueChanging()) {
                Platform.runLater(() -> {
                    sliderDuration.setValue(mediaPlayer.getCurrentTime().toSeconds());
                });
            }
        }, 0, 1200, TimeUnit.MILLISECONDS);
        executorService.shutdown();

        // Listener for song selection
        lstSongs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                playSelectedSong(newValue);
            }
        });

        // listener for Playlist song selection
        lstPlaylistSongs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                playSelectedSong(newValue);
            }
        });


        // Listener for playlist selection
        lstPlayList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    playlistUpdate(); // Call the method to update the songs in the selected playlist
                } catch (Exception e) {
                    displayError(e); // Handle any exceptions that occur during the update
                }
            }
        });

        clnPlaylistName.setCellValueFactory(new PropertyValueFactory<>("playlistName"));

        clnPlaylistSongs.setCellValueFactory(cellData -> {
            Playlist playlist = cellData.getValue();
            return playlist.numberOfSongsProperty().asObject().asString(); // Bind to the observable property
        });
        clnPlaylistTime.setCellValueFactory(cellData -> {
            Playlist playlist = cellData.getValue();
            return playlist.totalDurationProperty(); // Bind to the observable property
        });


        // Set initial icon for mute button
        String iconPath = "/personalprojects/mytunesproject/UI Icons/volumeMediumIcon.png";
        setButtonIcon(btnMute, iconPath);
    }

    /**
     * Plays the selected song from the song list.
     *
     * @param selectedSong the song to be played
     */
    private void playSelectedSong(Song selectedSong) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop(); // Stop any currently playing song
            }

            // Determine if the selected song is from the playlist or the main song list
            if (lstPlaylistSongs.getItems().contains(selectedSong)) {
                currentSongIndex = lstPlaylistSongs.getItems().indexOf(selectedSong);
            } else if (lstSongs.getItems().contains(selectedSong)) {
                currentSongIndex = lstSongs.getItems().indexOf(selectedSong);
            } else {
                // Handle the case where the song is not found
                Alert alert = new Alert(Alert.AlertType.WARNING, "Selected song not found.");
                alert.showAndWait();
                return;
            }

            playNewSong(selectedSong); // Play the newly selected song
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the selected song: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    /**
     * Moves the selected song to the chosen playlist.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnMoveToPlaylist(ActionEvent actionEvent) throws Exception {
        songModel.addSongToPlaylist(lstPlayList.getSelectionModel().getSelectedItem(), lstSongs.getSelectionModel().getSelectedItem());
        playlistUpdate(); // Update the playlist view
    }

    /**
     * Adds a new playlist with the specified name.
     *
     * @param playlistName the name of the new playlist
     */
    public void addNewPlaylist(String playlistName) throws Exception {
        playlistModel.createPlaylist(playlistName); // Create a new playlist
    }

    /**
     * Opens the dialog to create a new playlist.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnNewPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Playlist.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewPlaylistController controller = loader.getController();
        controller.setParent(this); // Set the parent controller

        stage.setTitle("New/Edit Playlist");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show(); // Show the new playlist dialog
    }

    /**
     * Opens the dialog to edit the selected playlist.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnEditPlaylist(ActionEvent actionEvent) throws IOException {
        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem(); // Get the selected playlist

        if (selectedPlaylist != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Playlist.fxml"));
            Parent scene = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(scene));

            NewPlaylistController controller = loader.getController();
            controller.setParent(this); // Set parent controller
            controller.setPlaylist(selectedPlaylist); // Pass the playlist to edit

            stage.setTitle("Edit Playlist");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    /**
     *
     * @param playlist
     * @throws Exception
     */
    public void updatePlaylist(Playlist playlist) throws Exception {
        playlistModel.updatePlaylist(playlist);
    }

    /**
     * Moves the selected song up in the playlist (not yet implemented).
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnMoveUp(ActionEvent actionEvent) {
        // Implementation for moving a song up in the playlist
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem(); // Get the selected song directly
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Song Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song to move up.");
            alert.showAndWait();
            return; // Exit if no song is selected
        }

        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem(); // Get the selected playlist
        if (selectedPlaylist == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Playlist Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a playlist.");
            alert.showAndWait();
            return; // Exit if no playlist is selected
        }

        try {
            songModel.moveSongInPlaylist(selectedPlaylist, selectedSong, true); // Move the song up
            playlistUpdate(); // Update the playlist view to reflect changes
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error moving the song: " + e.getMessage());
            errorAlert.showAndWait(); // Show error alert if moving fails
        }
    }

    /**
     * Moves the selected song down in the playlist (not yet implemented).
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnMoveDown(ActionEvent actionEvent) {
        // Implementation for moving a song down in the playlist
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem(); // Get the selected song directly
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Song Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song to move down.");
            alert.showAndWait();
            return; // Exit if no song is selected
        }

        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem(); // Get the selected playlist
        if (selectedPlaylist == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Playlist Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a playlist.");
            alert.showAndWait();
            return; // Exit if no playlist is selected
        }

        try {
            songModel.moveSongInPlaylist(selectedPlaylist, selectedSong, false); // Move the song down
            playlistUpdate(); // Update the playlist view to reflect changes
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error moving the song: " + e.getMessage());
            errorAlert.showAndWait(); // Show error alert if moving fails
        }
    }

    /**
     * Opens the dialog to create a new song.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnNewSong(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Songs.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewSongController controller = loader.getController();
        controller.setParent(this); // Set the parent controller
        controller.setSongForEdit(null); // No song is being edited

        stage.setTitle("New Song");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show(); // Show the new song dialog
    }

    /**
     * Opens the dialog to edit the selected song.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnEditSong(ActionEvent actionEvent) throws IOException {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem(); // Get the selected song
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Song Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song to edit.");
            alert.showAndWait();
            return; // Exit if no song is selected
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/personalprojects/mytunesproject/New-Edit-Songs.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        NewSongController controller = loader.getController();
        controller.setParent(this); // Set the parent controller
        controller.setSongForEdit(selectedSong); // Set the song to be edited

        stage.setTitle("Edit Song");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show(); // Show the edit song dialog
    }

    /**
     * Closes the application when the close button is pressed.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnCloseProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close(); // Close the current stage
        System.exit(0); // Exit the application
    }

    /**
     * Displays an error message in case of an exception.
     *
     * @param e the exception to be displayed
     */
    private void displayError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.showAndWait(); // Show the error alert
    }

    /**
     * Plays the selected song or pauses it if it's already playing.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    public void btnPlay(ActionEvent actionEvent) {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem(); // Get the selected song
        if (selectedSong == null) {
            selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem(); // Try to get the selected song from lstPlaylistSongs
        }

        try {
            if (mediaPlayer != null) {
                if (isPlaying) {
                    currentTime = mediaPlayer.getCurrentTime().toSeconds(); // Save the current playback time
                    mediaPlayer.pause(); // Pause the media player
                    isPlaying = false; // Update the playing state
                    txtCurrentlyPlaying.setText("Paused"); // Update the UI label
                } else {
                    if (selectedSong != null) {
                        mediaPlayer.seek(Duration.seconds(currentTime)); // Seek to the saved time
                        mediaPlayer.play(); // Play the media
                        isPlaying = true; // Update the playing state
                        txtCurrentlyPlaying.setText("Now Playing: " + selectedSong.getName()); // Update the UI label
                    } else{
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a song to play.");
                        alert.showAndWait(); // Show warning if no song is selected
                        return; // Exit if no song is selected
                    }
                }
            } else {
                if (selectedSong == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a song to play.");
                    alert.showAndWait(); // Show warning if no song is selected
                    return; // Exit if no song is selected
                }
                playNewSong(selectedSong); // Play the new song
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the song: " + e.getMessage());
            errorAlert.showAndWait(); // Show error alert if playback fails
        }
    }

    /**
     * Plays a new song by creating a MediaPlayer instance.
     *
     * @param song the song to be played
     * @throws IOException if the song file cannot be found
     */
    private void playNewSong(Song song) throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop any currently playing song
        }

        String filePath = song.getFilePath(); // Get the file path of the song
        File file = new File(filePath); // Create a File object

        if (!file.exists()) {
            throw new IOException("File not found: " + filePath); // Throw an exception if the file does not exist
        }

        Media media = new Media(file.toURI().toString()); // Create a Media object
        mediaPlayer = new MediaPlayer(media); // Create a MediaPlayer instance

        // Update currentSongIndex based on the song source
        if (lstPlaylistSongs.getItems().contains(song)) {
            currentSongIndex = lstPlaylistSongs.getItems().indexOf(song);
        } else if (lstSongs.getItems().contains(song)) {
            currentSongIndex = lstSongs.getItems().indexOf(song);
        }

        txtCurrentlyPlaying.setText("Now Playing: " + song.getName() + " by " + song.getArtist()); // Update the UI label

        // Set up event handlers for the media player
        mediaPlayer.setOnEndOfMedia(() -> {
            isPlaying = false; // Update the playing state
            currentTime = 0; // Reset current time
            btnNextSong(null); // Play the next song
        });

        mediaPlayer.setOnReady(() -> {
            double duration = mediaPlayer.getTotalDuration().toSeconds(); // Get the total duration of the song
            sliderDuration.setMax(duration); // Set the maximum value of the duration slider
            sliderDuration.setValue(0); // Reset the duration slider

            // Update the total duration label
            lblTotalDuration.setText(formatDuration(duration)); // Use existing formatDuration method

            mediaPlayer.play(); // Start playing the media
            mediaPlayer.setVolume(volumeNumber); // Set the volume
            isPlaying = true; // Update the playing state
            currentTime = 0; // Reset current time when a new song starts
        });

        // Listener to update currentTime and currentDuration label
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!sliderDuration.isValueChanging()) {
                sliderDuration.setValue(newValue.toSeconds()); // Update the duration slider
                currentTime = newValue.toSeconds(); // Update currentTime here

                // Update the current duration label
                lblCurrentDuration.setText(formatDuration(currentTime)); // Use existing formatDuration method
            }
        });
    }

    /**
     * Plays the next song in the list.
     *
     * @param actionEvent the action event triggered by the button
     */
    public void btnNextSong(ActionEvent actionEvent) {
        ObservableList<Song> songs;

        if (currentSongIndex >= 0 && currentSongIndex < lstPlaylistSongs.getItems().size()) {
            songs = lstPlaylistSongs.getItems(); // Get the songs from the playlist
        } else {
            songs = lstSongs.getItems(); // Fallback to the main song list
        }

        if (songs.isEmpty()) {
            txtCurrentlyPlaying.setText("No songs in the playlist.");
            return;
        }

        // Increment the currentSongIndex and check for bounds
        currentSongIndex++;
        if (currentSongIndex >= songs.size()) {
            currentSongIndex = 0; // Wrap around to the first song if at the end
        }

        Song nextSong = songs.get(currentSongIndex);

        try {
            playNewSong(nextSong);
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the next song: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    /**
     * Plays the previous song in the list.
     *
     * @param actionEvent the action event triggered by the button
     */
    public void btnLastSong(ActionEvent actionEvent) {
        ObservableList<Song> songs;

        if (currentSongIndex >= 0 && currentSongIndex < lstPlaylistSongs.getItems().size()) {
            songs = lstPlaylistSongs.getItems(); // Get the songs from the playlist
        } else {
            songs = lstSongs.getItems(); // Fallback to the main song list
        }

        if (songs.isEmpty()) {
            txtCurrentlyPlaying.setText("No songs in the playlist.");
            return;
        }

        // Decrement the currentSongIndex and check for bounds
        currentSongIndex--;
        if (currentSongIndex < 0) {
            currentSongIndex = songs.size() - 1; // Wrap around to the last song if at the beginning
        }
        Song previousSong = songs.get(currentSongIndex);

        try {
            playNewSong(previousSong);
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error playing the previous song: " + e.getMessage());
            errorAlert.showAndWait();
        }

    }

    /**
     * Searches for songs based on the input in the search field.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    public void btnSearch(ActionEvent actionEvent) {
        String query = txtSearch.getText().trim().toLowerCase(); // Get the search query
        Button searchButton = (Button) actionEvent.getSource(); // Get the button that triggered the event

        if (searchButton.getText().equals("Clear")) {
            txtSearch.clear(); // Clear the search field
            songModel.searchSongs(""); // Reset the song search
            searchButton.setText("Search"); // Change button text to "Search"
        } else {
            if (!query.isEmpty()) {
                songModel.searchSongs(query); // Search for songs matching the query
                searchButton.setText("Clear"); // Change button text to "Clear"
            }
        }
    }

    /**
     * Deletes the selected playlist.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnDeletePlaylist(ActionEvent actionEvent) {
        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem(); // Get the selected playlist
        deleteItem(selectedPlaylist, "playlist", false); // Call deleteItem method to delete the playlist
    }

    /**
     * Deletes a song from the selected playlist.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnDeleteFromPlaylist(ActionEvent actionEvent) throws Exception {
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem(); // Get the selected song directly
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Song Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song to delete.");
            alert.showAndWait();
            return; // Exit if no song is selected
        }

        deleteItem(selectedSong, "songFromPlaylist", false); // Call deleteItem method to delete the song from the playlist
        playlistUpdate(); // Update the playlist view
    }

    /**
     * Deletes the selected song from the song list.
     *
     * @param actionEvent the action event triggered by the button
     */
    @FXML
    private void btnDeleteSong(ActionEvent actionEvent) {
        Song selectedSong = lstSongs.getSelectionModel().getSelectedItem(); // Get the selected song
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Song Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song to delete.");
            alert.showAndWait();
            return; // Exit if no song is selected
        }

        System.out.println("Attempting to delete song: " + selectedSong.getName()); // Debug output

        try {
            deleteItem(selectedSong, "song", false);// Call delete method
            UpdateSongs(); // Refresh the song list
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error deleting the song: " + e.getMessage());
            errorAlert.showAndWait(); // Show error alert if deletion fails
        }
    }

    /**
     * Deletes the specified item (playlist or song) based on the item type.
     *
     * @param selectedItem the item to be deleted
     * @param itemType    the type of item (playlist, song, or songFromPlaylist)
     */
    private void deleteItem(Object selectedItem, String itemType, boolean bypass) {
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait(); // Show warning if no item is selected
            return; // Exit if no item is selected
        }

        // Only show confirmation alert if bypass is false
        if (!bypass) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete this item?");

            // Set content text based on item type
            if ("playlist".equals(itemType)) {
                confirmationAlert.setContentText("The playlist will be deleted");
            } else if ("songFromPlaylist".equals(itemType)) {
                confirmationAlert.setContentText("This song will only be removed from the playlist, not deleted from the system.");
            } else if ("song".equals(itemType)) {
                confirmationAlert.setContentText("This song will be deleted from the list.");
            }

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    performDeletion(selectedItem, itemType);
                }
            });
        } else {
            // If bypass is true, directly perform deletion without confirmation
            performDeletion(selectedItem, itemType);
        }
    }

    // Method to encapsulate the deletion logic
    private void performDeletion(Object selectedItem, String itemType) {
        boolean itemDeleted = false; // Flag to check if an item was deleted

        try {
            // Perform deletion based on item type
            if ("playlist".equals(itemType)) {
                playlistModel.deletePlaylist((Playlist) selectedItem); // Delete the selected playlist
                itemDeleted = true; // Set flag to true if deletion was successful
            } else if ("songFromPlaylist".equals(itemType)) {
                songModel.removeSongFromPlaylist(lstPlayList.getSelectionModel().getSelectedItem(), (Song) selectedItem); // Remove song from playlist
                playlistUpdate();

            } else if ("song".equals(itemType)) {
                removeFromAllPlaylists((Song) selectedItem);
                songModel.deleteSong((Song) selectedItem); // Delete the selected song
                itemDeleted = true; // Set flag to true if deletion was successful
            }

            // Show success alert only if an item was deleted
            if (itemDeleted) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Item Deleted");
                successAlert.setContentText("The item has been deleted.");
                successAlert.showAndWait(); // Show success alert after deletion
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the song list in the UI.
     *
     * @throws Exception if an error occurs while updating the song list
     */
    public void UpdateSongs() throws Exception {
        lstSongs.setItems(songModel.getObservableSongs()); // Update the song list
        System.out.println(songModel.getObservableSongs().size()); // Print the size of the song list
    }

    /**
     * Toggles mute on the media player.
     *
     * @param actionEvent the action event triggered by the button
     */
    public void btnMute(ActionEvent actionEvent) {
        Button muteButton = (Button) actionEvent.getSource(); // Get the mute button
        if (mediaPlayer != null) {
            if (!muteCheck) {
                muteCheck = true; // Set mute flag
                mediaPlayer.setVolume(0); // Mute the media player
                setButtonIcon(muteButton, "/personalprojects/mytunesproject/UI Icons/volumeMuteIcon.png"); // Update button icon
            } else {
                muteCheck = false; // Unmute flag
                mediaPlayer.setVolume(volumeNumber); // Restore volume
                updateVolumeIcon(muteButton, volumeNumber * 100); // Update button icon
            }
        }
    }

    /**
     * Updates the volume icon based on the current volume percentage.
     *
     * @param button          the button to update
     * @param volumePercentage the current volume percentage
     */
    private void updateVolumeIcon(Button button, double volumePercentage) {
        String iconPath = "/personalprojects/mytunesproject/UI Icons/volumeMuteIcon.png"; // Default to mute icon
        if (volumePercentage > 0 && volumePercentage <= 30) {
            iconPath = "/personalprojects/mytunesproject/UI Icons/volumeLowIcon.png"; // Low volume icon
        } else if (volumePercentage > 30 && volumePercentage <= 70) {
            iconPath = "/personalprojects/mytunesproject/UI Icons/volumeMediumIcon.png"; // Medium volume icon
        } else if (volumePercentage > 70) {
            iconPath = "/personalprojects/mytunesproject/UI Icons/volumeHighIcon.png"; // High volume icon
        }
        setButtonIcon(btnMute, iconPath); // Update the mute button icon
    }

    /**
     * Sets the icon for a button.
     *
     * @param button   the button to set the icon for
     * @param iconPath the path to the icon image
     */
    private void setButtonIcon(Button button, String iconPath) {
        button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(iconPath)))); // Set the button graphic
    }

    /**
     * Seeks to a specific duration in the song when the duration slider is adjusted.
     *
     * @param mouseEvent the mouse event triggered by the slider
     */
    public void sliderDuration(MouseEvent mouseEvent) {
        if (mediaPlayer != null && !sliderDuration.isValueChanging()) {
            mediaPlayer.seek(Duration.seconds(sliderDuration.getValue())); // Seek to the selected duration
        }
    }

    /**
     * Updates the playlist view when a playlist is selected.
     *
     * @param mouseEvent the mouse event triggered by the selection
     */
    @FXML
    private void playlistSelection(MouseEvent mouseEvent) throws Exception {
        playlistUpdate(); // Update the playlist view
        currentSongIndex = -1; // Reset the current song index when changing playlists
    }

    /**
     * Updates the songs displayed in the playlist based on the selected playlist.
     *
     * @throws Exception if an error occurs while updating the playlist
     */
    private void playlistUpdate() throws Exception {
        Playlist selectedPlaylist = lstPlayList.getSelectionModel().getSelectedItem(); // Get the selected playlist
        if (selectedPlaylist != null) {
            ObservableList<Song> songsOnPlaylist = songModel.getSongsOnPlaylist(selectedPlaylist); // Get songs in the selected playlist
            lstPlaylistSongs.getItems().clear(); // Clear the current list of songs displayed
            lstPlaylistSongs.getItems().addAll(songsOnPlaylist); // Add each song to the ListView

            // Update the observable properties for the selected playlist
            selectedPlaylist.setNumberOfSongs(songsOnPlaylist.size()); // Update the number of songs
            selectedPlaylist.setTotalDuration(getFormattedTime(songModel.getTotalDuration(selectedPlaylist))); // Update the total duration
        }
    }


    /**
     * Formats the duration from seconds to a string in the format mm:ss.
     *
     * @param seconds the duration in seconds
     * @return a formatted string representing the duration
     */
    private String formatDuration(double seconds) {
        int minutes = (int) seconds / 60;
        int secs = (int) seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    private void removeFromAllPlaylists(Song selectedSong){
            for(int i = 0; i < lstPlayList.getItems().size(); i++){
                lstPlayList.getSelectionModel().select(i);
                System.out.println("test1");

                for(int j = 0; j < lstPlaylistSongs.getItems().size(); j++){
                    System.out.println("test2");
                    lstPlaylistSongs.getSelectionModel().select(j);
                    if(lstPlaylistSongs.getSelectionModel().getSelectedItem().equals(selectedSong)){
                        System.out.println("test3");
                        deleteItem(lstPlaylistSongs.getSelectionModel().getSelectedItem(), "songFromPlaylist", true);
                    }

                }
            }
    }
    public String getFormattedTime(int duration){
        int seconds = duration % 60;
        int minutes = duration / 60;
        int hours = minutes/60;
        for(int i = 0; i < hours; i++)
        {
            minutes -=60;
        }

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}