/** 
 * A class that represent a song player to play a song.
 * 
 * @author Patrick Comden
 * @version 1.0
 */

package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SongPlayer {
	
	private static Media media;
	private static MediaPlayer player;
	
	/**
	 * A method to play a song given a song path.
	 * 
	 * @param song_path		A String for the song's URI.
	 * @return		A Duration object for the duration of the song.
	 */
	public static Duration playSong(String song_path) {
		media = new Media(new File(song_path).toURI().toString());
		player = new MediaPlayer(media);
		player.play();
		
		return media.getDuration();
	}
}