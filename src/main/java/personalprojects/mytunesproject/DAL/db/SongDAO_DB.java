package personalprojects.mytunesproject.DAL.db;
import personalprojects.mytunesproject.be.Song;
import personalprojects.mytunesproject.DAL.ISongDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_DB implements ISongDataAccess {
DBConnector db;

    @Override
    public List<Song> getAllSongs() throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();
        db =new DBConnector();

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Songs";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int SongID = rs.getInt("SongID");
                String Name = rs.getString("Name");
                String Artist = rs.getString("Artist");
                String Duration = rs.getString("Duration");
                String Category = rs.getString("Category");

                Song song = new Song(SongID, Name, Artist, Duration, Category);
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
        String sql = "INSERT INTO dbo.Songs(Name, Artist, Duration, Category) VALUES (?,?,?,?)";
        Song song = newSong;

        try (Connection conn = db.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, song.getName());
            stmt.setString(2,song.getArtist());
            stmt.setString(3, song.getDuration());
            stmt.setString(4, song.getCategory());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int SongID = 0;

            if(rs.next()){
                SongID = rs.getInt(1);
            }

            Song createdSong = new Song(SongID, song.getName(), song.getArtist(), song.getDuration(), song.getCategory());

            return createdSong;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create song",ex);
        }
    }

    @Override
    public void updateSong(Song song) {
    }

    @Override
    public void deleteSong(Song song) {
    }
}
