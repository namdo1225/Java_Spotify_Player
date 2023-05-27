/** 
 * A class for the library of the music player.
 * The library acts very much like another playlist.
 *
 * Pattern: Singleton
 *
 * @author JhihYang Wu
 * @version 1.0
 */

package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Library extends Playlist {
	
	private static Library instance = null;
	private ArrayList<Playlist> playlists;
	
	/**
	 * A private constructor for the Library class.
	 */
	private Library() {
		super("Library");
		playlists = new ArrayList<Playlist>();
		playlists.add(new Playlist("Library"));
	}
	
	/**
	 * A method to load a playlist.
	 */
	public void loadPlaylists() {
		FileInputStream in = null;
		ObjectInputStream file = null;
		File dir = new File(SAVE_DIRECTORY);
		for (File load : dir.listFiles()) {
			if (load.getName().contains(PLAYLIST_EXTENSION)) {
				try {
					in = new FileInputStream(load);
					file = new ObjectInputStream(in);			
				} catch (Exception e) {
					System.out.println("File reading error. Your playlist file cannot be read.");
					return;
				}
				
				try {
					Playlist new_playlist = (Playlist)file.readObject();
					if (new_playlist.getName().equals("Library")) {
						for (int i = 0; i < getNumPlaylists(); i++) {
							if (playlists.get(i).getName().equals("Library")) {
								playlists.set(i, new_playlist);
							}
						}
					}
					else
						playlists.add(new_playlist);
					file.close();
					in.close();
				} catch (Exception e) {
					System.out.println("File reading error. Your playlist file cannot be read.");
				}
			}
		}
	}
	
	/**
	 * A getter method to return the singular Library object.
	 * 
	 * @return		the singular Library object.
	 */
	public static Library get() {
		if (instance == null) {
			instance = new Library();
		}
		
		return instance;
	}
	
	/**
	 * A method to save playlists in the computer file system.
	 */
	public void savePlaylists() {
		FileOutputStream out;
		ObjectOutputStream file;
		for (Playlist playlist : playlists) {
			String playlist_directory = SAVE_DIRECTORY + playlist.getName() + PLAYLIST_EXTENSION;
			try {
				out = new FileOutputStream(playlist_directory);
				file = new ObjectOutputStream(out);
				file.writeObject(playlist);
				out.close();
			} catch (Exception e) {
				System.out.println("File writing error. Your playlist file cannot be written to.");
				e.printStackTrace();
				return;
			}

		}
	}
	
	
	/**
	 * A method to create a new playlist.
	 * 
	 * @param name		A String for the name of the new playlist.
	 */
	public void createPlaylist(String name) {
		if (getPlaylist(name) == null && !name.equals("Library")) {
			playlists.add(new Playlist(name));
		}
	}
	
	public void deletePlaylist(String name) {
		for (int i = 0; i < getNumPlaylists(); i++) {
			if (playlists.get(i).getName().equals(name)) {
				playlists.remove(i);
				// delete playlist file
				File file = new File(SAVE_DIRECTORY + name + PLAYLIST_EXTENSION);
				if (file.exists()) {
					file.delete();
				}
				break;
			}
		}
	}
	
	/**
	 * A getter for a specific playlist given an index.
	 * 
	 * @param index		An int for the index of the playlist the player wants.
	 * @return		A Playlist object representing the playlist the player wants.
	 */
	public Playlist getPlaylist(int index) {
		return playlists.get(index);
	}
	
	/**
	 * A getter for a specific playlist given a playlist's name.
	 * 
	 * @param playlistName	A String for the name of the playlist the player wants.
	 * @return		A Playlist object representing the playlist the player wants.
	 */
	public Playlist getPlaylist(String playlistName) {
		for (int i = 0; i < getNumPlaylists(); i++) {
			if (playlists.get(i).getName().equals(playlistName)) {
				return playlists.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Getter for the total number of playlists.
	 * 
	 * @return		An int for the number of playlists.
	 */
	public int getNumPlaylists() {
		return playlists.size();
	}
	
	/**
	 * A method to add a song to a playlist
	 * 
	 * @param playlist		An int for the playlist # to add the song to.
	 * @param song			An int representing the song that will be added.
	 */
	public void addSongToPlaylist(int playlist, int song) {
		if (playlist < playlists.size() && song < songs.size())
			playlists.get(playlist).addSong(songs.get(song));
	}
}
