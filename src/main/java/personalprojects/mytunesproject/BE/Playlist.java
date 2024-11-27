package personalprojects.mytunesproject.BE;

public class Playlist {

    private String playlistName;
    private int Songs;
    private String Duration;

    public Playlist(String playlistName, int songs, String duration) {
        this.playlistName = playlistName;
        Songs = songs;
        Duration = duration;
    }


    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getSongs() {
        return Songs;
    }

    public void setSongs(int songs) {
        Songs = songs;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
