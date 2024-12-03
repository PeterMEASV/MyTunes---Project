package personalprojects.mytunesproject.gui.Model;

import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.BE.Song;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalprojects.mytunesproject.bll.PlaylistManager;

import java.io.IOException;

public class PlaylistModel {

    private ObservableList<Playlist> lstPlayList;
    PlaylistManager playlistManager = new PlaylistManager();

    public PlaylistModel() throws Exception {

        lstPlayList = FXCollections.observableArrayList();
        lstPlayList.addAll(playlistManager.getAllPlaylists());
    }

    public ObservableList<Playlist> getObservablePlaylists() {
        return lstPlayList;
    }

    public void createPlaylist(String newPlaylist) throws Exception {
        Playlist tempPlaylist = playlistManager.createPlaylist(newPlaylist);
        lstPlayList.add(tempPlaylist);

    }

    public void deletePlaylist(Playlist selectedPlaylist) throws Exception {
        playlistManager.deletePlaylist(selectedPlaylist);
        lstPlayList.remove(selectedPlaylist);
    }

    public void updatePlaylist(Playlist playlist) throws Exception {
        playlistManager.updatePlaylist(playlist);
    }
}
