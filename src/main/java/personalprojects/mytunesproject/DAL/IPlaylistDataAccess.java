package personalprojects.mytunesproject.DAL;

import personalprojects.mytunesproject.be.Playlist;

import java.util.List;

public interface IPlaylistDataAccess {
    List<Playlist> getAllPlaylists() throws Exception; // Retrieve all playlists
    Playlist createPlaylist(Playlist playlist) throws Exception; // Create a new playlist
    Playlist updatePlaylist(Playlist playlist) throws Exception; // Update an existing playlist
    Playlist deletePlaylist(Playlist playlist) throws Exception; // Delete a playlist
}
