package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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


    // TODO: Husk at implementere flere typer, eller anden måde at modtage kategorier.
    private String[] testObjects = {"Pop", "Rock", "Rap", "Disco", "Jazz", "House"};

    public void initialize() {
        DropDownCategory.getItems().addAll(testObjects);
    }

    public void setParent(MyTunesController myTunesController) {

    }
    @FXML
    private void btnMore(ActionEvent actionEvent) {
    }
    @FXML
    private void btnChooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Music file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music Files", "*.mp3","*.wav"));
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
    private void btnSaveSong(ActionEvent actionEvent) {
        if (!txtSongTitle.getText().isEmpty() && !txtSongArtist.getText().isEmpty() && !txtTimer.getText().isEmpty()) {
            int songDuration = calculateSeconds();
            System.out.println(songDuration);

        }
    }
    private int calculateSeconds()
    {
        try{
            String[] parts = txtTimer.getText().split(":");

            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            return (minutes*60) + seconds;
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException e)
        {
            showErrorAlert("Invalid Song length", "Please input the duration of the song.");
            return -1;
        }
    }
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: no header
        alert.setContentText(message);
        alert.showAndWait(); // Waits for the user to close the alert
    }

}
