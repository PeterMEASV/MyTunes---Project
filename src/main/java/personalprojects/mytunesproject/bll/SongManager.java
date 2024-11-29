package personalprojects.mytunesproject.bll;


import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.DAL.ISongDataAccess;
import personalprojects.mytunesproject.DAL.db.PlaylistSongsDAO_DB;
import personalprojects.mytunesproject.DAL.db.SongDAO_DB;
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.gui.MyTunesController;

import java.io.IOException;
import java.util.List;

public class SongManager {

    //private SongSearcher songSearcher = new SongSearcher();
    private ISongDataAccess songDAO_db;
     private PlaylistSongsDAO_DB playlistSongsDAO_DB;


    public SongManager() throws IOException {
        songDAO_db = new SongDAO_DB();
        playlistSongsDAO_DB = new PlaylistSongsDAO_DB();

    }

    public List<Song> getAllSongs() throws Exception {
        return songDAO_db.getAllSongs();
    }

    public void createSong(Song song) throws Exception {
        songDAO_db.CreateSong(song);
    }

    public void deleteSong(Song selectedSong) throws Exception {
        songDAO_db.deleteSong(selectedSong);

    }

    public void updateSong(Song selectedSong) throws Exception {
        songDAO_db.updateSong(selectedSong);
    }

    public List<Song> getSongsOnPlaylist(Playlist playlist) throws Exception {
        return playlistSongsDAO_DB.getSongsOnPlaylist(playlist);
    }
    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        playlistSongsDAO_DB.addSongToPlaylist(playlist, song);
    }

}
