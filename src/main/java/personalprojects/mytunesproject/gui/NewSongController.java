package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.bll.SongManager;
import personalprojects.mytunesproject.gui.Model.SongModel;

import java.io.File;
import java.io.IOException;

public class NewSongController {
    @FXML
    private TextField txtSongTitle;
    @FXML
    private TextField txtSongArtist;
    @FXML
    private TextField txtTimer;
    @FXML
    private TextField txtFileName;
    @FXML
    private ChoiceBox<String> DropDownCategory;

    private SongModel songModel = new SongModel();
    private MyTunesController parent;

    private Song songToEdit = null; // Holds the song to edit (null for new song)

    private String[] testObjects = {"Pop", "Rock", "Rap", "Disco", "Jazz", "House"};

    public NewSongController() throws Exception {
    }
    
    public void initialize(){
        DropDownCategory.getItems().addAll(testObjects);
    }

    public void setParent(MyTunesController parent) {
        this.parent = parent;
    }

    // This method is called to set the song for editing
    public void setSongForEdit(Song song) {
        this.songToEdit = song;

        if (songToEdit != null) {
            // Pre-fill fields with song data for editing
            txtSongTitle.setText(songToEdit.getName());
            txtSongArtist.setText(songToEdit.getArtist());
            txtTimer.setText(formatDuration(songToEdit.getDuration()));
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
        }
    }

    @FXML
    private void btnCancelNewSong(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnSaveSong(ActionEvent actionEvent) throws Exception {
        if (!txtSongTitle.getText().isEmpty() && !txtSongArtist.getText().isEmpty() && !txtTimer.getText().isEmpty() && DropDownCategory.getValue() != null && !txtFileName.getText().isEmpty()) {
            int songDuration = calculateSeconds();
            String songGenre = DropDownCategory.getValue();

            if (songToEdit == null) {
                // Creating a new song
                Song newSong = new Song(1, txtSongTitle.getText(), txtSongArtist.getText(), songDuration, songGenre, txtFileName.getText());
                songModel.createSong(newSong);
            } else {
                // Editing an existing song
                songToEdit.setName(txtSongTitle.getText());
                songToEdit.setArtist(txtSongArtist.getText());
                songToEdit.setDuration(songDuration);
                songToEdit.setCategory(songGenre);
                songToEdit.setFilePath(txtFileName.getText());
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

    private int calculateSeconds() {
        try {
            String[] parts = txtTimer.getText().split(":");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            return (minutes * 60) + seconds;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            showErrorAlert("Invalid Song Length", "Please input a valid duration.");
            return -1;
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
