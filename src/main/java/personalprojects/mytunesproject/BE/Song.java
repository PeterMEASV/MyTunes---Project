package personalprojects.mytunesproject.BE;

import java.io.File;
import java.util.Objects;

public class Song {

    private int songID;
    private String Name;
    private String Artist;
    private int Duration;
    private String Category;
    private String filePath;


    /**
     * Constructor for the Song class.
     *
     * @param songID   the unique identifier for the song
     * @param Name     the name of the song
     * @param Artist   the artist of the song
     * @param Duration the duration of the song in seconds
     * @param Category the category or genre of the song
     * @param filePath the file path to the song file
     */
public Song(int songID, String Name, String Artist, int Duration, String Category, String filePath) {
    this.songID = songID;
    this.Name = Name;
    this.Artist = Artist;
    this.Duration = Duration;
    this.Category = Category;
    this.filePath = filePath;

}

    /**
     * Getters and setters
     */
    public int getSongID() {return songID;}
    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}
    public String getArtist() {return Artist;}
    public void setArtist(String Artist) {this.Artist = Artist;}
    public int getDuration() {return Duration;}
    public void setDuration(int Duration) {this.Duration = Duration;}
    public String getCategory() {return Category;}
    public void setCategory(String Category) {this.Category = Category;}
    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) {this.filePath = filePath;}

    /**
     * Formats the duration of the song into a string in the format mm:ss.
     *
     * @return a formatted string representing the duration of the song
     */
    public String getFormattedTime(){
    int minutes = this.Duration / 60;
    int seconds = this.Duration % 60;
    return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Checks if this song is equal to another song.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return songID == song.songID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(songID);
    }

}
