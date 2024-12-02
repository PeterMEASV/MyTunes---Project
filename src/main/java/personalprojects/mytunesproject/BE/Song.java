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


public Song(int songID, String Name, String Artist, int Duration, String Category, String filePath) {
    this.songID = songID;
    this.Name = Name;
    this.Artist = Artist;
    this.Duration = Duration;
    this.Category = Category;
    this.filePath = filePath;

}

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

    public String getFormattedTime(){
    int minutes = this.Duration / 60;
    int seconds = this.Duration % 60;
    return String.format("%02d:%02d", minutes, seconds);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return songID == song.songID; // Assuming songID is unique
    }

    @Override
    public int hashCode() {
        return Objects.hash(songID);
    }

}
