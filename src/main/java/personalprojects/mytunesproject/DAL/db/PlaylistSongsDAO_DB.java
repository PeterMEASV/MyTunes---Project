package personalprojects.mytunesproject.DAL.db;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import personalprojects.mytunesproject.BE.Playlist;
import personalprojects.mytunesproject.BE.Song;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistSongsDAO_DB {
    DBConnector db;

    public PlaylistSongsDAO_DB() throws IOException {
        db = new DBConnector();
    }

    public List<Song> getSongsOnPlaylist(Playlist playlist) throws SQLServerException {
        ArrayList<Song> songsOnPlaylist = new ArrayList<>();

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT SongID FROM dbo.Connection WHERE PlaylistID = " + playlist.getPlaylistID();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int songID = rs.getInt("SongID");

                String songSql = "SELECT * from dbo.Songs Where SongID = " + songID;
                Statement songStmt = conn.createStatement();
                ResultSet songRs = songStmt.executeQuery(songSql);

                while (songRs.next()) {
                    int SongID = songRs.getInt("SongID");
                    String Name = songRs.getString("Name");
                    String Artist = songRs.getString("Artist");
                    int Duration = songRs.getInt("Duration");
                    String Category = songRs.getString("Category");
                    String filePath = songRs.getString("FilePath");

                    Song song = new Song(SongID, Name, Artist, Duration, Category, filePath);
                    songsOnPlaylist.add(song);

                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return songsOnPlaylist;
    }

    public void addSongToPlaylist(Playlist playlist, Song song) throws SQLException {
        String sql = "INSERT INTO dbo.Connection (PlaylistID, SongID) VALUES (?,?)";

        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, playlist.getPlaylistID());
            stmt.setInt(2, song.getSongID());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteSongFromPlaylist(Playlist playlist, Song song) throws SQLException {
        String sql = "DELETE FROM dbo.Connection WHERE PlaylistID = ? AND SongID = ?";
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, playlist.getPlaylistID());
                stmt.setInt(2, song.getSongID());
                stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
