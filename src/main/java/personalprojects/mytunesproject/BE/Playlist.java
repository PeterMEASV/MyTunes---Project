package personalprojects.mytunesproject.BE;

public class Playlist {

    private String playlistName;
    private int Songs;
    private String Duration;
    private int playlistID;

    public Playlist(int playlistID, String playlistName) {
        this.playlistName = playlistName;
        this.playlistID = playlistID;

    }


    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }
}
