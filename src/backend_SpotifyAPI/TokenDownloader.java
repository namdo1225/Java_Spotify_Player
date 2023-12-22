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
package backend_SpotifyAPI;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class TokenDownloader {
	
	/**
	 * A class meant to represent a token JSON object.
	 */
	private class Token {
		private String access_token;
		private String expires_in;
		
		/**
		 * A getter to retrieve the token from the API call.
		 * 
		 * @return		a String of the token retrieved from the API call.
		 */
		public String getToken() {
			return access_token;
		}
	}

	private static String urlString = "https://accounts.spotify.com/api/token";
	private static String authorizeStr = "https://accounts.spotify.com/authorize";
	private static String clientID = "7b4e70c11bce47079593bbfb56b20aaa";
	private static String redirectURI = "https://github.com/namdo1225/Java_Spotify_Player";
	private static Token token;
	private static TokenDownloader downloader;

	private static String authorizeRedirectURL;
	private static String codeVerifier;
	private static String authorizeCode;
	
	// in nanoseconds
	private long startTime = -1;
	private long estimatedTime = -1;

	private double adjustedExpire;

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
	 * Authorize the app to get access to user's information so the music player could work correctly.
	 * 
	 * @throws NoSuchAlgorithmException Thrown if the PKCE algorithm does not work.
	 * @throws IOException Thrown if there's any error in opening up the user's browser.
	 * @throws URISyntaxException Thrown if there's any syntax error in provided URIs.
	 */
	public static void authorizeUser() throws NoSuchAlgorithmException, IOException, URISyntaxException {
		PkceUtil pkce = new PkceUtil();
		codeVerifier = pkce.generateCodeVerifier();
		String codeChallenge = pkce.generateCodeChallange(codeVerifier);

		URL url = new URL(String.format("%s?client_id=%s&response_type=%s&redirect_uri=%s&code_challenge_method=%s&code_challenge=%s",
				authorizeStr,
				clientID,
				"code",
				redirectURI,
				"S256",
				codeChallenge
				));
		HttpURLConnection request = (HttpURLConnection)url.openConnection();
		request.setRequestMethod("GET");
		request.setDoOutput(true);

		int code = request.getResponseCode();
		if (code == HttpURLConnection.HTTP_OK) {
			authorizeRedirectURL = request.getURL().toString();

			if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(authorizeRedirectURL));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			} else {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("xdg-open " + url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * A method to generate a new token from the API call.
	 * 
	 * @throws IOException Thrown if any error is made when connecting to the API.
	 * @throws NoSuchAlgorithmException Thrown when calling PCKE generation methods.
	 */
	private void generateToken() throws IOException, NoSuchAlgorithmException {
		if (authorizeCode == null)
			return;
		else if (token != null && estimatedTime != -1 &&
				TimeUnit.SECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS) < adjustedExpire) {
			estimatedTime = System.nanoTime() - startTime;
			return;
		}
		
		URL url = new URL(String.format("%s?grant_type=%s&code=%s&redirect_uri=%s&client_id=%s&code_verifier=%s",
				urlString,
				"authorization_code",
				authorizeCode,
				redirectURI,
				clientID,
				codeVerifier));
		
		HttpURLConnection request = (HttpURLConnection)url.openConnection();
		request.setRequestMethod("POST");
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
			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
			in.close();

		}

		String json = response.toString();
		Gson gson = new Gson();
		token = gson.fromJson(json, Token.class);

		long longExpire = Long.parseLong(token.expires_in);
		adjustedExpire = longExpire * 0.75;

		startTime = System.nanoTime();
		estimatedTime = System.nanoTime() - startTime;
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
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return (token == null) ? null : token.getToken();
	}

	/**
	 * A setter for the authorization code.
	 * 
	 * @param code	a String for the authorization code.
	 */
	public static void setAuthorizeCode(String code) {
		authorizeCode = code;
	}
}
