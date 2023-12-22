package backend_SpotifyAPI;

/**
 * Class inspired by: https://www.appsdeveloperblog.com/pkce-code-verifier-and-code-challenge-in-java/
 * 
 * A class that contains an algorithm to help create PCKE code verifier and challenge.
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
 
public class PkceUtil {
	
	/**
	 * A method to generate a code verifier.
	 * 
	 * @return a string for the code verifier.
	 * @throws UnsupportedEncodingException thrown if an encoding type is not supported.
	 */
    public String generateCodeVerifier() throws UnsupportedEncodingException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }
    
    /**
     * A method to generate a code challenge.
     * 
     * @param codeVerifier a string for the code verifier to encrypt.
	 * @return a string for the code challenge.
	 * @throws UnsupportedEncodingException thrown if an encoding type is not supported.
     * @throws NoSuchAlgorithmException thrown if no algorithm is found for the encryption.
     */
    public String generateCodeChallange(String codeVerifier) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = codeVerifier.getBytes("US-ASCII");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bytes, 0, bytes.length);
        byte[] digest = messageDigest.digest();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}