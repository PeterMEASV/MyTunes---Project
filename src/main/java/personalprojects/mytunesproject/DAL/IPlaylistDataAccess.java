package personalprojects.mytunesproject.DAL;

import personalprojects.mytunesproject.BE.Playlist;

import java.util.List;

public interface IPlaylistDataAccess {
    List<Playlist> getAllPlaylists() throws Exception; // Retrieve all playlists
    Playlist createPlaylist(String playlist) throws Exception; // Create a new playlist
    Playlist updatePlaylist(Playlist playlist) throws Exception; // Update an existing playlist
    void deletePlaylist(Playlist playlist) throws Exception; // Delete a playlist
}
