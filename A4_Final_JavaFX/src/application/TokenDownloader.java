/**
 * 
 * A class to abstract the process of getting a token from the Spotify API.
 * 
 * It also contains an inner class Token to store the actual token received
 * from the API.
 * 
 * Pattern: Singleton
 * 
 * @author Nam Do
 * @version 1.0
 */
package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class TokenDownloader {
	private class Token {
		private String access_token;
		
		/**
		 * A getter to retrieve the token from the API call.
		 * 
		 * @return		a String of the token retrieved from the API call.
		 */
		public String getToken() {
			return access_token;
		}
	}
	
	private static String urlString = "https://accounts.spotify.com/api/token?grant_type=client_credentials";
	private static Token token;
	private static TokenDownloader downloader;
	
	/**
	 * A private constructor for TokenDownloader to restrict new objects
	 * of this class from being created.
	 */
	private TokenDownloader() {}
	
	/**
	 * A method to get the single TokenDownloader object.
	 * 
	 * @return		a TokenDownloader object.
	 */
	public static TokenDownloader getTokenDownloader() {
		if (downloader == null)
			downloader = new TokenDownloader();
		return downloader;
	}
	
	/**
	 * A method to generate a new token from the API call.
	 * 
	 * @throws IOException	Thrown if any error is made when connecting to the API.
	 */
	private void generateToken() throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection request = (HttpURLConnection)url.openConnection();
		
		request.setRequestMethod("POST");
		request.setRequestProperty("Authorization", "Basic NDkwZWIxZjA2YzQ2NDU1YTg2ZDA4OWQ4ODljNGRiMGY6YTllZDdhZWYxNmFmNDFjNGE3Mjg0MDcyZjJkZGMwNjc=");
		request.setRequestProperty("Content-Length", "0");
		request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		request.setDoInput(true);
        request.setDoOutput(true);
        
        DataOutputStream writer = new DataOutputStream(request.getOutputStream());
        writer.writeBytes("");
        writer.flush();
        
		int code = request.getResponseCode();
		
		StringBuffer response = new StringBuffer();
		
		if (code == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		}
	    
		String json = response.toString();
        Gson gson = new Gson();
        token = gson.fromJson(json, Token.class);
	}
	
	/**
	 * A getter to retrieve the token from the API call.
	 * 
	 * @return		a String of the token retrieved from the API call.
	 */
	public String getToken() {
		if (downloader == null)
			return "";
		try {
			generateToken();
		} catch (IOException e) {
			System.out.println("Cannot generate API token. Please check your internet connection.");
		}
		return (token == null) ? null : token.getToken();
	}
}
