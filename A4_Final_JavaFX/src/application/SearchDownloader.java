/**
 * A class to abstract the process of searching for a song from Spotify API.
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchDownloader {
	private static SearchDownloader downloader;
	private static List<String> search;
	private static String urlString = "https://api.spotify.com/v1/search?";
	private static TokenDownloader tokenDownloader;
	
	/**
	 * A private constructor for SearchDownloader to restrict new objects
	 * of this class from being created.
	 */
	private SearchDownloader() {
		search = new ArrayList<String>();
		tokenDownloader = TokenDownloader.getTokenDownloader();
	}
	
	/**
	 * A method to get the single SearchDownloader object.
	 * 
	 * @return		a SearchDownloader object.
	 */
	public static SearchDownloader getSearchDownloader() {
		if (downloader == null)
			downloader = new SearchDownloader();
		return downloader;
	}
	
	/**
	 * A method to search a Spotify song using the API and store the results into a
	 * list.
	 * 
	 * @param query		a String for the keyword to search for.
	 * @param type		a String for the type of content to search for.
	 * @throws IOException	Thrown if any error is made when connecting to the API.
	 */
	public void search(String query, String type) throws IOException {
		if (!type.equals("track"))
			return;
			
		URL url = new URL(urlString + "q=" + URLEncoder.encode(query, "utf-8") + "&type=" + type + "&include_external=audio");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestMethod("GET");
		request.setRequestProperty("Authorization", "Bearer " + tokenDownloader.getToken());
		int code = request.getResponseCode();
				
		if (code == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null)
				if (inputLine.contains("api.spotify.com/v1/tracks/")) {
					String[] temp = inputLine.strip().split(" ");
					temp = temp[2].split("/");
					search.add(temp[5].substring(0, temp[5].length() - 2));
				}

			in.close();

		}
	}
	
	/**
	 * A method to get the list of the search items returned.
	 * 
	 * @return		a List<String> of the track IDs of the songs.
	 */
	public List<String> getSearchResult() {
		List<String> copy = new ArrayList<>(search);
		search.clear();
		return copy;
	}
}
