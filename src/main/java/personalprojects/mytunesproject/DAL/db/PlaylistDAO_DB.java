package personalprojects.mytunesproject.DAL.db;

import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.DAL.IPlaylistDataAccess;
import personalprojects.mytunesproject.BE.Playlist;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO_DB implements IPlaylistDataAccess {
    DBConnector db;
    public PlaylistDAO_DB() throws IOException {
        db = new DBConnector();
    }

    @Override
    public List<Playlist> getAllPlaylists() throws Exception {
        // Implement logic to retrieve playlists from a database
        List<Playlist> playlists = new ArrayList<>();
        // Example: fetch from database
        // playlists = database.getPlaylists();
        return playlists;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) throws Exception {
        String sql = "INSERT INTO dbo.Playlists(Name) VALUES (?)";

        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, playlist.getPlaylistName());


            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int playlistID = 0;

            if (rs.next()) {
                playlistID = rs.getInt(1);
            }

            return new Playlist(playlist.getPlaylistName(), 0, "0");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create song", ex);
        }
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) throws Exception {
        // Implement logic to update an existing playlist in the database
        // Example: update the playlist in the database
        // database.updatePlaylist(playlist);
        return playlist; // Return the updated playlist
    }

    @Override
    public Playlist deletePlaylist(Playlist playlist) throws Exception {
        // Implement logic to delete a playlist from the database
        // Example: delete the playlist from the database
        // database.deletePlaylist(playlist);
        return playlist; // Return the deleted playlist
    }
}
