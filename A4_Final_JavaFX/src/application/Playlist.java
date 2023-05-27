/** 
 * A class that represents a playlist to hold a collection
 * of songs that could be sorted in certain orders.
 *
 * @author Patrick Comden
 * @version 1.0
 */

package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Playlist implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	protected ArrayList<Song> songs;
	protected String name;
	protected int cur_song;
	protected static final String SAVE_DIRECTORY = "Music/";
	protected static final String PLAYLIST_EXTENSION = ".pla";
	
	/**
	 * A constructor for Playlist.
	 * 
	 * @param name		A String for the name of this new playlist.
	 */
	public Playlist(String name) {
		songs = new ArrayList<Song>();
		this.name = name;
		cur_song = 0;
	}
	
	/**
	 * A getter for the playlist's name.
	 * 
	 * @return		A String for the playlist's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * A method to return a list of songs from the playlist.
	 * 
	 * @return		An ArrayList<Song> object for the list of songs.
	 */
	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	/**
	 * A method to add a song to the playlist.
	 * 
	 * @param song		A Song object representing the song to be added.
	 */
	public void addSong(Song song) {
		for (Song other : songs) {
			if (other.getPreview().equals(song.getPreview())) {
				return;
			}
		}
		songs.add(song);
	}
	
	/**
	 * A method to download a song given a url and a path to save the song to.
	 * 
	 * @param url		A String for the url of the song.
	 * @param save_path	A String for the path to save the song to.
	 */
	private void downloadSong(String url, String save_path) {
		try {
			URLConnection conn = new URL(url).openConnection();
			InputStream is = conn.getInputStream();
			
			OutputStream outstream = new FileOutputStream(new File(save_path));
			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				outstream.write(buffer, 0, len);
			}
			outstream.close();
		}
		catch (Exception e) {
			System.out.println("Song downloading error. Your song cannot be downloaded.");
		}
	}
	
	/**
	 * A method to play a song.
	 * 
	 * @param start		An int for where to start the song from.
	 * @return		A Duration object representing the duration of the song.
	 */
	public javafx.util.Duration play(int start) {
		cur_song = start;
		//songs.get(cur_song);
		String save_path = SAVE_DIRECTORY + songs.get(cur_song).getSongTitle().replace('.', ' ') + ".mp3";
		File file = new File(save_path);
		if (!file.exists()) {
			downloadSong(songs.get(cur_song).getPreview(), save_path);
		}
		return SongPlayer.playSong(save_path);
	}
	
	/**
	 * A method to sort a playlist given a sorting pattern.
	 * Sort types are "artist", "date", and "name"
	 * 
	 * @param sort_type		A String for the sort type.
	 */
	public void sort(String sort_type) {
		for (int i = 0; i < songs.size(); i++) {
			String smallest_value = "";
			int smallest_index = -1;
			// find smallest
			for (int j = i; j < songs.size(); j++) {
				String value = "";
				if (sort_type == "artist") {
					value = songs.get(j).getFirstArtist();
				}
				if (sort_type == "date") {
					value = songs.get(j).getDate();
				}
				if (sort_type == "name") {
					value = songs.get(j).getSongTitle();
				}
				if (smallest_index == -1 || value.compareTo(smallest_value) < 0) {
					smallest_index = j;
					smallest_value = value;
				}
			}
			// swap
			Song temp = songs.get(i);
			songs.set(i, songs.get(smallest_index));
			songs.set(smallest_index, temp);
		}
	}
}
