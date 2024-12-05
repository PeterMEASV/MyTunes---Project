package personalprojects.mytunesproject.DAL.db;

import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.DAL.IPlaylistDataAccess;
import personalprojects.mytunesproject.BE.Playlist;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO_DB implements IPlaylistDataAccess {
    private DBConnector db;

    public PlaylistDAO_DB() throws IOException {
        db = new DBConnector();
    }

    @Override
    public List<Playlist> getAllPlaylists() throws Exception {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();


        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Playlists";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int playlistID = rs.getInt("PlaylistID");
                String Name = rs.getString("Name");

                Playlist playlist = new Playlist(playlistID, Name);
                allPlaylists.add(playlist);
            }
            return allPlaylists;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }

    /**
     * Creates a new playlist in the database.
     *
     * @param playlist the name of the playlist to be created
     * @return the created playlist with its generated ID
     * @throws Exception if an error occurs while creating the playlist
     */
    @Override
    public Playlist createPlaylist(String playlist) throws Exception {
        String sql = "INSERT INTO dbo.Playlists(Name) VALUES (?)";

        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, playlist);


            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int playlistID = 0;

            if (rs.next()) {
                playlistID = rs.getInt(1);
            }

            return new Playlist(playlistID, playlist);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create song", ex);
        }
    }

    /**
     * Updates an existing playlist in the database.
     *
     * @param playlist the playlist with updated information
     * @return the updated playlist
     * @throws Exception if an error occurs while updating the playlist
     */
    @Override
    public Playlist updatePlaylist(Playlist playlist) throws Exception {
        String sql = "UPDATE dbo.Playlists SET Name = ? WHERE PlaylistID = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, playlist.getPlaylistName());
            stmt.setInt(2, playlist.getPlaylistID());


            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new Exception("No playlist found with ID: " + playlist.getPlaylistID());
            }

            return playlist;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update playlist in the database", ex);
        }
    }

    /**
     * Deletes a playlist from the database.
     *
     * @param playlist the playlist to be deleted
     * @throws Exception if an error occurs while deleting the playlist
     */
    @Override
    public void deletePlaylist(Playlist playlist) throws Exception {
        try(Connection conn = db.getConnection())
        {
            // Clear existing songs in the playlist
            String deleteSQL = "DELETE FROM dbo.Connection WHERE PlaylistID = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, playlist.getPlaylistID());
                deleteStmt.executeUpdate();
            }

            String sql = "DELETE FROM dbo.Playlists WHERE Name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, playlist.getPlaylistName());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw new Exception("Could not get movies from database.", ex);
        }
    }
}
