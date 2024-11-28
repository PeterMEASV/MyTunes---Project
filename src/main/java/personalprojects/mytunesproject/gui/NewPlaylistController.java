package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewPlaylistController {

    @FXML
    private TextField txtPlaylistName;

    private MyTunesController parentController;

    public void setParent(MyTunesController myTunesController) {
        this.parentController = myTunesController;
    }

    @FXML
    private void btnSavePlaylist(ActionEvent actionEvent) {
        String playlistName = txtPlaylistName.getText();
        if (parentController != null && !playlistName.isEmpty()) {
            parentController.addNewPlaylist(playlistName);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnCancelPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
