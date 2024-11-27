package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private TextField txtCategory;
    @FXML
    private TextField txtTimer;
    @FXML
    private TextField txtFileName;

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
    }

    public void DropDownCategory(ActionEvent actionEvent) {
    }
}
