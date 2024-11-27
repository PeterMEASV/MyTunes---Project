package personalprojects.mytunesproject.DAL;
import personalprojects.mytunesproject.BE.Song;

import java.io.IOException;
import java.util.List;

public interface ISongDataAccess {
    List<Song> getAllSongs() throws Exception;

    Song CreateSong(Song newSong) throws Exception;

    void updateSong(Song song);

    void deleteSong(Song song) throws Exception;
}
