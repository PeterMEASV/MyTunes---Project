package personalprojects.mytunesproject.be;

public class Song {

    private int songID;
    private String Name;
    private String Artist;
    private String Duration;
    private String Category;
    //private String FilePath;


public Song(int songID, String Name, String Artist, String Duration, String Category) {
    this.songID = songID;
    this.Name = Name;
    this.Artist = Artist;
    this.Duration = Duration;
    this.Category = Category;

}

    public int getSongID() {return songID;}

    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}

    public String getArtist() {return Artist;}
    public void setArtist(String Artist) {this.Artist = Artist;}

    public String getDuration() {return Duration;}
    public void setDuration(String Duration) {this.Duration = Duration;}

    public String getCategory() {return Category;}
    public void setCategory(String Category) {this.Category = Category;}

}
