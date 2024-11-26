package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPlaylistController {
    @FXML
    private TextField txtPlaylistName;

    public void setParent(MyTunesController myTunesController) {

    }
    @FXML
    private void btnSavePlaylist(ActionEvent actionEvent) {
    }
    @FXML
    private void btnCancelPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
