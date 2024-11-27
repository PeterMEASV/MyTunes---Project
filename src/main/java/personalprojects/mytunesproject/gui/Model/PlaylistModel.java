package personalprojects.mytunesproject.gui.Model;

import personalprojects.mytunesproject.BE.Playlist;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalprojects.mytunesproject.bll.PlaylistManager;

public class PlaylistModel {

    private ObservableList<Playlist> lstPlayList;  // Observable list to hold the playlists

    public PlaylistModel() {
        PlaylistManager playlistManager = new PlaylistManager();
        lstPlayList = FXCollections.observableArrayList();  // Initialize the ObservableList

        // Example of adding playlists to the ObservableList
        // This could be replaced by a call to fetch data from the database
        // lstPlayList.addAll(playlistManager.getAllPlaylists());
    }

    // Return the observable list of playlists
    public ObservableList<Playlist> getObservablePlaylists() {
        return lstPlayList;
    }

    // Create a new playlist (you can add implementation to save this to a database or another storage)
    public void createPlaylist(Playlist newPlaylist) {
        // Optionally add the playlist to the data model (e.g., save to a database)
        // playlistManager.createPlaylist(newPlaylist);
    }
}
