package personalprojects.mytunesproject.bll;

import personalprojects.mytunesproject.DAL.IPlaylistDataAccess;
import personalprojects.mytunesproject.DAL.db.PlaylistDAO_DB;
import personalprojects.mytunesproject.BE.Playlist;

import java.io.IOException;
import java.util.List;

public class PlaylistManager {

    private IPlaylistDataAccess playlistDAO_db;

    public PlaylistManager() throws IOException {
        playlistDAO_db = new PlaylistDAO_DB(); // Use the database implementation
    }

    public List<Playlist> getAllPlaylists() throws Exception {
        return playlistDAO_db.getAllPlaylists();
    }

    public Playlist createPlaylist(Playlist playlist) throws Exception {
        return playlistDAO_db.createPlaylist(playlist);
    }

    public Playlist updatePlaylist(Playlist playlist) throws Exception {
        return playlistDAO_db.updatePlaylist(playlist);
    }

    public Playlist deletePlaylist(Playlist playlist) throws Exception {
        return playlistDAO_db.deletePlaylist(playlist);
    }
}
