package personalprojects.mytunesproject.gui.Model;

// import Java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//project
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

    public ObservableList<Song> getObservableSongs() throws Exception {
        update();
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

    public void createSong(Song selectedSong) throws Exception {
        songManager.createSong(selectedSong);
        lstSongs.add(selectedSong);
    }

    public void updateSong(Song selectedSong) throws Exception {


        // Proceed to update the song
        songManager.updateSong(selectedSong);

        boolean found = false;
        for (int i = 0; i < lstSongs.size(); i++) {
            Song s = lstSongs.get(i);
            if (s.getSongID() == selectedSong.getSongID()) {
                s.setName(selectedSong.getName());
                s.setArtist(selectedSong.getArtist());
                s.setCategory(selectedSong.getCategory());
                s.setDuration(selectedSong.getDuration());
                s.setFilePath(selectedSong.getFilePath());
                found = true;
                break;
            }
        }

        if (!found) {
            throw new Exception("Song with ID " + selectedSong.getSongID() + " not found in the list.");
        }
    }

    public void update() throws Exception {
        filteredSongs.clear();
        filteredSongs.addAll(songManager.getAllSongs());
    }
}


