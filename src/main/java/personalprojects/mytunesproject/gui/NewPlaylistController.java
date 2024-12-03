package personalprojects.mytunesproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalprojects.mytunesproject.BE.Playlist;


public class NewPlaylistController {

    @FXML
    private TextField txtPlaylistName;

    private MyTunesController parentController;
    private Playlist existingPlaylist;

    /**
     *
     * @param myTunesController
     */
    public void setParent(MyTunesController myTunesController) {
        this.parentController = myTunesController;
    }

    /**
     *
     * @param playlist
     */
    public void setPlaylist(Playlist playlist) {
        this.existingPlaylist = playlist;
        if (playlist != null) {
            txtPlaylistName.setText(playlist.getPlaylistName());
        }
    }

    /**
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void btnSavePlaylist(ActionEvent actionEvent) throws Exception {
        String playlistName = txtPlaylistName.getText();

        if (playlistName.isEmpty()) {
            return;
        }

        if (existingPlaylist != null) {
            existingPlaylist.setPlaylistName(playlistName);
            parentController.updatePlaylist(existingPlaylist);
        } else {
            parentController.addNewPlaylist(playlistName);
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param actionEvent
     */
    @FXML
    private void btnCancelPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


}
