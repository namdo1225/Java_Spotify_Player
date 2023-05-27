/**
 * 
 * A class to abstract the process of getting a track from Spotify API.
 * 
 * Pattern: Singleton
 * 
 * @author Nam Do
 * @version 1.0
 */
package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class SongDownloader {
	private static String urlString = "https://api.spotify.com/v1/tracks/";
	private static SongDownloader downloader;
	private static Song song;
	private static TokenDownloader tokenDownloader;
	
	/**
	 * A private constructor for SongDownloader to restrict new objects
	 * of this class from being created.
	 */
	private SongDownloader() {}
	
	/**
	 * A method to get a JSON response of a song's information from Spotify API
	 * call.
	 * 
	 * @param trackID		a String of the track's ID on Spotify.
	 * @throws IOException	Thrown if any error is made when connecting to the API.
	 */
	private static void downloadSong(String trackID) throws IOException {
		tokenDownloader = TokenDownloader.getTokenDownloader();
		
		URL url = new URL(urlString + trackID);
		
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestMethod("GET");
		request.setRequestProperty("Authorization", "Bearer " + tokenDownloader.getToken());
		int code = request.getResponseCode();
		
		StringBuffer response = new StringBuffer();
		
		if (code == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
			in.close();
		}
		
		String json = response.toString();
        Gson gson = new Gson();
		song = new Song();
		song = gson.fromJson(json, Song.class);
	}
	
	
	/**
	 * Method to create a Song object to hold a song's information after
	 * being called.
	 * 
	 * @param trackID		a String of the track's ID on Spotify.
	 * @return		a Song object containing a song's information.
	 */
	public static Song createSong(String trackID) {
		if (downloader == null)
			downloader = new SongDownloader();

		try {
			downloadSong(trackID);
		} catch (IOException e) {
			System.out.println("Cannot download song based on track ID. Check your internet connection.");
		}
		
		return song;
	}
}
