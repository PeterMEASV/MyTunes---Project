package personalprojects.mytunesproject.BLL;


import personalprojects.mytunesproject.DAL.ISongDataAccess;
import personalprojects.mytunesproject.DAL.db.SongDAO_DB;
import personalprojects.mytunesproject.BE.Song;

import java.util.List;

public class SongManager {

    //private SongSearcher songSearcher = new SongSearcher();
    private ISongDataAccess songDAO_db;


    public SongManager() {
        songDAO_db = new SongDAO_DB();

    }

    public List<Song> getAllSongs() throws Exception {
        return songDAO_db.getAllSongs();
    }
}
