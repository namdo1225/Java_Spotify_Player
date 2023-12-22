/**
 * A class to represent a Song and store informations about that song
 * retrieved from Spotify API.
 * 
 * There is also two inner classes Artist and Album that allow the
 * Spotify API's response to be easily stored in the Java program.
 * 
 * There is also an inner class inside Album that has data about the
 * song's image.
 * 
 * @author Nam Do
 * @version 1.0
 */
package application;

public class Song implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private class Artist implements java.io.Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		 * A getter to get the song artist's name
		 * 
		 * @return		a String for the song artist's name.
		 */
		public String getArtist() {
			return name;
		}
	}

	private class Album implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		
		private String release_date;
		private SongImage[] images;
		
		private class SongImage implements java.io.Serializable {

			private static final long serialVersionUID = 1L;
			
			private String url;
			
			/**
			 * A getter to get the song cover's url.
			 * 
			 * @return		a String for the song cover's url.
			 */
			public String getURL() {
				return url;
			}
		}
		
		/**
		 * A getter to get the song's release date.
		 * 
		 * @return		a String for the song's release date.
		 */
		public String getDate() {
			return release_date;
		}
		
		/**
		 * A getter to get the song cover's url in 640*640 pixels.
		 * 
		 * @return		a String for the song cover's url.
		 */
		public String getURL640() {
			return images[0].getURL();
		}
	}
	
	private String name;
	private String preview_url;
    
	private Artist[] artists;
	private Album album;
	
	/**
	 * A getter to get the song cover's url.
	 * 
	 * @return		a String for the song cover's url.
	 */
    public String getPreview() {
    	return preview_url;
    }
    
	/**
	 * A getter to get the song's name.
	 * 
	 * @return		a String for the song's name.
	 */
    public String getSongTitle() {
    	return name;
    }
    
	/**
	 * A getter to get the song's first artist.
	 * 
	 * @return		a String for the song's first artist.
	 */
    public String getFirstArtist() {
    	return artists[0].getArtist();
    }
    
	/**
	 * A getter to get the song's release date.
	 * 
	 * @return		a String for the song's release date.
	 */
    public String getDate() {
    	return album.getDate();
    }
    
	/**
	 * A getter to get the song cover's url in 640*640 pixels.
	 * 
	 * @return		a String for the song cover's url.
	 */
    public String getURL640() {
    	return album.getURL640();
    }
}