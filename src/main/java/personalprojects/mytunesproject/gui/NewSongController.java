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

import personalprojects.mytunesproject.gui.Model.SongModel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

public class NewSongController {
    @FXML
    private TextField txtSongTitle;
    @FXML
    private TextField txtSongArtist;
    @FXML
    private Label lblTimer;
    @FXML
    private TextField txtFileName;
    @FXML
    private ChoiceBox<String> DropDownCategory;

    private SongModel songModel = new SongModel();

    private MyTunesController parent;

    private double songDuration;

    private Song songToEdit = null;

    private String[] testObjects = {"Pop", "Rock", "Rap", "Disco", "Jazz", "House"};

    public NewSongController() throws Exception {
    }

    public void initialize(){
        DropDownCategory.getItems().addAll(testObjects);
    }

    public void setParent(MyTunesController parent) {
        this.parent = parent;
    }


    public void setSongForEdit(Song song) {
        this.songToEdit = song;

        if (songToEdit != null) {
            txtSongTitle.setText(songToEdit.getName());
            txtSongArtist.setText(songToEdit.getArtist());
            lblTimer.setText(formatDuration(songToEdit.getDuration()));
            DropDownCategory.setValue(songToEdit.getCategory());
            txtFileName.setText(songToEdit.getFilePath());
        }
    }

    @FXML
    private void btnChooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Music file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            txtFileName.setText(file.getAbsolutePath());

            // Calculate and display duration using MediaPlayer
            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                // Get the duration in seconds
                songDuration = mediaPlayer.getTotalDuration().toSeconds();
                lblTimer.setText(formatDuration((int) songDuration));
                mediaPlayer.dispose();
            });

            mediaPlayer.setOnError(() -> {
                showErrorAlert("Error", "Could not retrieve duration for the selected file.");
            });
        }
    }

    @FXML
    private void btnCancelNewSong(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnSaveSong(ActionEvent actionEvent) throws Exception {
        if (!txtSongTitle.getText().isEmpty() && !txtSongArtist.getText().isEmpty() && !lblTimer.getText().isEmpty() && DropDownCategory.getValue() != null && !txtFileName.getText().isEmpty()) {
            if (songDuration < 0) {
                showErrorAlert("Invalid Duration", "Please select a valid music file(.mp3 or .wav) to get duration.");
                return; // Exit if duration is invalid
            }

            String songGenre = DropDownCategory.getValue();

            // Define the destination directory
            String destinationDir = "src/main/resources/personalprojects/mytunesproject/Songs";
            Path destinationPath = Paths.get(destinationDir, new File(txtFileName.getText()).getName());

            // Copy the file to the destination directory, overwriting if it exists
            Files.copy(Paths.get(txtFileName.getText()), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Get the new file path
            String newFilePath = destinationPath.toString();

            if (songToEdit == null) {
                Song newSong = new Song(1, txtSongTitle.getText(), txtSongArtist.getText(), (int) songDuration, songGenre, newFilePath);
                songModel.createSong(newSong);
            } else {
                songToEdit.setName(txtSongTitle.getText());
                songToEdit.setArtist(txtSongArtist.getText());
                songToEdit.setDuration((int) songDuration);
                songToEdit.setCategory(songGenre);
                songToEdit.setFilePath(newFilePath); // Update with the new file path
                songModel.updateSong(songToEdit);
            }

            if (parent != null) {
                parent.UpdateSongs();
            }

            btnCancelNewSong(actionEvent);
        } else {
            showErrorAlert("Invalid Input", "Please fill in all the fields.");
        }
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String formatDuration(int durationInSeconds) {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
