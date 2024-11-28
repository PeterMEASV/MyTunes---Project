package personalprojects.mytunesproject.DAL.db;
import personalprojects.mytunesproject.BE.Song;
import personalprojects.mytunesproject.DAL.ISongDataAccess;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_DB implements ISongDataAccess {
    DBConnector db;

    public SongDAO_DB() throws IOException {
        db =new DBConnector();
    }

    @Override
    public List<Song> getAllSongs() throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();


        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Songs";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int SongID = rs.getInt("SongID");
                String Name = rs.getString("Name");
                String Artist = rs.getString("Artist");
                int Duration = rs.getInt("Duration");
                String Category = rs.getString("Category");
                String filePath = rs.getString("FilePath");

                Song song = new Song(SongID, Name, Artist, Duration, Category, filePath);
                allSongs.add(song);
            }
            return allSongs;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }

    @Override
    public Song CreateSong(Song newSong) throws Exception {
        String sql = "INSERT INTO dbo.Songs(Name, Artist, Duration, Category, FilePath) VALUES (?,?,?,?,?)";

        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, newSong.getName());
            stmt.setString(2, newSong.getArtist());
            stmt.setInt(3, newSong.getDuration());
            stmt.setString(4, newSong.getCategory());
            stmt.setString(5, newSong.getFilePath());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int SongID = 0;

            if (rs.next()) {
                SongID = rs.getInt(1);
            }

            return new Song(SongID, newSong.getName(), newSong.getArtist(), newSong.getDuration(), newSong.getCategory(), newSong.getFilePath());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create song", ex);
        }
    }

    @Override
    public void updateSong(Song song) {

        String sql = "UPDATE dbo.Songs SET Name = ?, Artist = ?, Duration = ?, Category = ? WHERE SongID = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, song.getName());
            stmt.setString(2, song.getArtist());
            stmt.setInt(3, song.getDuration());
            stmt.setString(4, song.getCategory());
            stmt.setInt(5, song.getSongID());


            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void deleteSong(Song song) throws Exception {
        String sql = "DELETE FROM dbo.Songs WHERE SongID = ?";

        try(Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, song.getSongID());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw new Exception("Could not get movies from database.", ex);
        }
    }
}





