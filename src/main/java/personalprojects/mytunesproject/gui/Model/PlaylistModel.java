package personalprojects.mytunesproject.gui.Model;

import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.BE.Song;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalprojects.mytunesproject.bll.PlaylistManager;

public class PlaylistModel {

    private ObservableList<Playlist> lstPlayList;

    public PlaylistModel() {
        PlaylistManager playlistManager = new PlaylistManager();
        lstPlayList = FXCollections.observableArrayList();

    }

    public ObservableList<Playlist> getObservablePlaylists() {
        return lstPlayList;
    }

    public void createPlaylist(Playlist newPlaylist) {

    }

    public void deletePlaylist(Playlist selectedPlaylist) {
    }

    public void removeSongFromPlaylist(Song selectedSong) {
    }
}
