package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewPlaylistController {
    @FXML
    private TextField txtPlaylistName;

    public void setParent(MyTunesController myTunesController) {
        MyTunesController perent = myTunesController;
    }
    @FXML
    private void btnSavePlaylist(ActionEvent actionEvent) {
    }
    @FXML
    private void btnCancelPlaylist(ActionEvent actionEvent) {
    }
}
