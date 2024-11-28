package personalprojects.mytunesproject.gui.Model;

// import Java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// project
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.bll.SongManager;

import java.util.List;

public class SongModel {

    private ObservableList<Song> lstSongs;
    private ObservableList<Song> filteredSongs;
    private SongManager songManager;

    public SongModel() throws Exception {
        songManager = new SongManager();
        lstSongs = FXCollections.observableArrayList();
        lstSongs.addAll(songManager.getAllSongs());
        filteredSongs = FXCollections.observableArrayList(lstSongs);
    }


    public ObservableList<Song> getObservableSongs() {
        return filteredSongs;
    }


    public void searchSongs(String query) {
        filteredSongs.clear();

        if (query == null || query.trim().isEmpty()) {
            filteredSongs.addAll(lstSongs);
            return;
        }

        for (Song song : lstSongs) {
            if (song.getName().toLowerCase().contains(query.toLowerCase()) ||
                song.getArtist().toLowerCase().contains(query.toLowerCase())) {
                filteredSongs.add(song);
            }
        }
    }

    // Delete a song
    public void deleteSong(Song selectedSong) throws Exception {
        songManager.deleteSong(selectedSong);
        lstSongs.remove(selectedSong);
        filteredSongs.remove(selectedSong);
    }
}
