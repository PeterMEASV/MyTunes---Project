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

    public PlaylistModel() throws IOException {

        lstPlayList = FXCollections.observableArrayList();

    }

    public ObservableList<Playlist> getObservablePlaylists() {
        return lstPlayList;
    }

    public void createPlaylist(Playlist newPlaylist) throws Exception {
        playlistManager.createPlaylist(newPlaylist);
        lstPlayList.add(newPlaylist);
    }

    public void deletePlaylist(Playlist selectedPlaylist) {
    }

    public void removeSongFromPlaylist(Song selectedSong) {
    }
}
