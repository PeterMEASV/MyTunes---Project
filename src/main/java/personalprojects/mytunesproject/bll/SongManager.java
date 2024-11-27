package personalprojects.mytunesproject.bll;


import personalprojects.mytunesproject.DAL.ISongDataAccess;
import personalprojects.mytunesproject.DAL.db.SongDAO_DB;
import personalprojects.mytunesproject.BE.Song;

import java.io.IOException;
import java.util.List;

public class SongManager {

    //private SongSearcher songSearcher = new SongSearcher();
    private ISongDataAccess songDAO_db;


    public SongManager() throws IOException {
        songDAO_db = new SongDAO_DB();

    }

    public List<Song> getAllSongs() throws Exception {
        return songDAO_db.getAllSongs();
    }

    public void createSong(Song song) throws Exception {
        songDAO_db.CreateSong(song);
    }

    public void deleteSong(Song selectedSong) throws Exception {
        songDAO_db.deleteSong(selectedSong);

        // TODO: add a way to update the list on GUI. possibly with a method called UpdateSongs().
    }

    public void updateSong(Song selectedSong) throws Exception {
        songDAO_db.updateSong(selectedSong);
    }

}
