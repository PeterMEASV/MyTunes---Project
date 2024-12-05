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

    /**
     * Constructor for SongModel.
     * Initializes the song manager and loads all songs into the observable list.
     *
     * @throws Exception if an error occurs while initializing the song manager or loading songs
     */
    public SongModel() throws Exception {
        songManager = new SongManager();
        lstSongs = FXCollections.observableArrayList();
        lstSongs.addAll(songManager.getAllSongs());
        filteredSongs = FXCollections.observableArrayList(lstSongs);
        lstSongsOnPlaylist = FXCollections.observableArrayList();
    }

    /**
     * Returns an observable list of all songs.
     * Updates the list before returning it.
     *
     * @return an observable list of songs
     * @throws Exception if an error occurs while updating the song list
     */
    public ObservableList<Song> getObservableSongs() throws Exception {
        update();
        return filteredSongs;

    }

    /**
     * Returns the songs in the specified playlist.
     *
     * @param playlist the playlist to get songs from
     * @return an observable list of songs in the playlist
     * @throws Exception if an error occurs while retrieving songs from the playlist
     */
    public ObservableList<Song> getSongsOnPlaylist(Playlist playlist) throws Exception {
        lstSongsOnPlaylist.clear();
        lstSongsOnPlaylist.addAll(songManager.getSongsOnPlaylist(playlist));
        return lstSongsOnPlaylist;
    }

    /**
     * Adds a song to the specified playlist.
     *
     * @param playlist the playlist to add the song to
     * @param song     the song to be added
     * @throws Exception if an error occurs while adding the song to the playlist
     */
    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        songManager.addSongToPlaylist(playlist, song);
    }

    /**
     * Removes a song from the specified playlist.
     *
     * @param playlist the playlist to remove the song from
     * @param song     the song to be removed
     * @throws Exception if an error occurs while removing the song from the playlist
     */
    public void removeSongFromPlaylist(Playlist playlist, Song song) throws Exception {
        songManager.deleteSongFromPlaylist(playlist, song);
    }

    /**
     * Searches for songs based on the provided query and updates the filtered song list.
     *
     * @param query the search query
     */
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

    /**
     * Deletes the specified song from the song list and the data source.
     *
     * @param selectedSong the song to be deleted
     * @throws Exception if an error occurs while deleting the song
     */
    public void deleteSong(Song selectedSong) throws Exception {
        songManager.deleteSong(selectedSong);
        lstSongs.remove(selectedSong);
        filteredSongs.remove(selectedSong);
        update();
    }

    /**
     * Creates a new song and adds it to the song list.
     *
     * @param selectedSong the song to be created
     * @throws Exception if an error occurs while creating the song
     */
    public void createSong(Song selectedSong) throws Exception {
        songManager.createSong(selectedSong);
        lstSongs.add(selectedSong);
    }

    /**
     * Updates the details of an existing song.
     *
     * @param selectedSong the song with updated details
     * @throws Exception if an error occurs while updating the song
     */
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

    /**
     * Updates the filtered song list with all songs from the data source.
     *
     * @throws Exception if an error occurs while updating the song list
     */
    public void update() throws Exception {
        filteredSongs.clear();
        filteredSongs.addAll(songManager.getAllSongs());
    }

    /**
     * Moves a song within the specified playlist.
     *
     * @param selectedPlaylist the playlist containing the song
     * @param selectedSong     the song to be moved
     * @param moveSong         true to move up, false to move down
     * @throws Exception if an error occurs while moving the song
     */
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

    /**
     * Updates the playlist in the data source with the new order of songs.
     *
     * @param playlist the playlist to be updated
     * @param song    the new order of songs
     * @throws Exception if an error occurs while updating the playlist
     */
    private void updatePlaylist(Playlist playlist, ObservableList<Song> song) throws Exception {
        songManager.updatePlaylist(playlist, song);
    }

    /**
     * Calculates the total duration of all songs in the specified playlist.
     *
     * @param playlist the playlist to calculate the total duration for
     * @return the total duration in seconds
     * @throws Exception if an error occurs while retrieving songs from the playlist
     */
    public int getTotalDuration(Playlist playlist) throws Exception {
        ObservableList<Song> songsOnPlaylist = getSongsOnPlaylist(playlist);
        int duration = 0;
        for (Song song : songsOnPlaylist) {
            duration += song.getDuration();
        }
        return duration;
    }
}


