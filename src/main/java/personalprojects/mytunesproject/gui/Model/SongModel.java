package personalprojects.mytunesproject.gui.Model;

// import Java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//project
import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.bll.SongManager;

import java.util.List;

public class SongModel {

    private ObservableList<Song> lstSongs;
    private ObservableList<Song> filteredSongs;
    private ObservableList<Song> lstSongsOnPlaylist;
    private SongManager songManager;


    public SongModel() throws Exception {
        songManager = new SongManager();
        lstSongs = FXCollections.observableArrayList();
        lstSongs.addAll(songManager.getAllSongs());
        filteredSongs = FXCollections.observableArrayList(lstSongs);
        lstSongsOnPlaylist = FXCollections.observableArrayList();
    }

    public ObservableList<Song> getObservableSongs() throws Exception {
        update();
        return filteredSongs;

    }

    public ObservableList<Song> getSongsOnPlaylist(Playlist playlist) throws Exception {
        lstSongsOnPlaylist.clear();
        lstSongsOnPlaylist.addAll(songManager.getSongsOnPlaylist(playlist));
        return lstSongsOnPlaylist;
    }
    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        songManager.addSongToPlaylist(playlist, song);
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song) throws Exception {
        songManager.deleteSongFromPlaylist(playlist, song);
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
        update();
    }

    public void createSong(Song selectedSong) throws Exception {
        songManager.createSong(selectedSong);
        lstSongs.add(selectedSong);
    }

    public void updateSong(Song selectedSong) throws Exception {


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

    public void moveSongInPlaylist(Playlist selectedPlaylist, Song selectedSong, boolean moveSong) throws Exception {
        // Get the list of songs in the selected playlist
        ObservableList<Song> songsInPlaylist = getSongsOnPlaylist(selectedPlaylist);

        // Find the index of the selected song
        int index = songsInPlaylist.indexOf(selectedSong);

        // Check if the song is found and if it can be moved
        if (index == -1) {
            throw new Exception("Song not found in the playlist.");
        }

        if (moveSong && index > 0) { // Move up
            // Swap with the song above
            Song songAbove = songsInPlaylist.get(index - 1);
            songsInPlaylist.set(index - 1, selectedSong);
            songsInPlaylist.set(index, songAbove);
        } else if (!moveSong && index < songsInPlaylist.size() - 1) { // Move down
            // Swap with the song below
            Song songBelow = songsInPlaylist.get(index + 1);
            songsInPlaylist.set(index + 1, selectedSong);
            songsInPlaylist.set(index, songBelow);
        } else {
            // If the song is already at the top or bottom, do nothing
            return;
        }

        // Update the playlist with the new order
        updatePlaylist(selectedPlaylist, songsInPlaylist);
    }

    // Method to persist the new order in your data source
    private void updatePlaylist(Playlist playlist, ObservableList<Song> song) throws Exception {
        songManager.updatePlaylist(playlist, song);
    }
}


