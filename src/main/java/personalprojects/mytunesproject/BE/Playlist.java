package personalprojects.mytunesproject.BE;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Playlist {

    private StringProperty playlistName; // Use StringProperty for observable properties
    private IntegerProperty numberOfSongs; // Use IntegerProperty for observable properties
    private StringProperty totalDuration; // Use StringProperty for observable properties
    private int playlistID;

    public Playlist(int playlistID, String playlistName) {
        this.playlistName = new SimpleStringProperty(playlistName);
        this.playlistID = playlistID;
        this.numberOfSongs = new SimpleIntegerProperty(0); // Initialize with 0 songs
        this.totalDuration = new SimpleStringProperty("00:00"); // Initialize with default duration
    }

    // Getters and Setters for playlistName
    public String getPlaylistName() {
        return playlistName.get();
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.set(playlistName);
    }

    // Getters and Setters for numberOfSongs
    public int getNumberOfSongs() {
        return numberOfSongs.get();
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs.set(numberOfSongs);
    }

    // Getters and Setters for totalDuration
    public String getTotalDuration() {
        return totalDuration.get();
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration.set(totalDuration);
    }

    // Getter for playlistID
    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    // Property accessors for binding
    public StringProperty playlistNameProperty() {
        return playlistName;
    }

    public IntegerProperty numberOfSongsProperty() {
        return numberOfSongs;
    }

    public StringProperty totalDurationProperty() {
        return totalDuration;
    }
}
