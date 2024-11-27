package personalprojects.mytunesproject.gui.Model;

// import Java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//project
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.bll.SongManager;

public class SongModel {

    private ObservableList<Song> lstSongs;
    private ObservableList<Song> songs;
    private SongManager songManager;


    public SongModel() throws Exception {
        songManager = new SongManager();
        lstSongs = FXCollections.observableArrayList();
        lstSongs.addAll(songManager.getAllSongs());
    }

    public ObservableList<Song> getObservableSongs() {
        return lstSongs;
    }

    public void deleteSong(Song selectedSong) {
    }
}


