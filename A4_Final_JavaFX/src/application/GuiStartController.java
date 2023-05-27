/** 
 * A class representing the controller of the program,
 * containing methods for the functionality of the music player.
 * 
 * Pattern: Controller of the MVC
 *
 * @author JhihYang Wu
 * @version 1.0
 */

package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GuiStartController {
    @FXML
    public Button hitButton;
    public AnchorPane pane;
    public Button firstButton;
    public Button secondButton;
    public Button thirdButton;
    public Button fourthButton;
    public Button fifthButton;
    public Button sixthButton;
    public Button seventhButton;
    public Button eightButton;
    public Button ninthButton;
    public Button tenthButton;
    public TextField searchText;
    public Button iNeedName;
    public Button iNeedAnother;

    private Button[] buttons;

    private Song[] searchedSongs;
    
    public ImageView add1;
    public ImageView add2;
    public ImageView add3;
    public ImageView add4;
    public ImageView add5;
    public ImageView add6;
    public ImageView add7;
    public ImageView add8;
    public ImageView add9;
    public ImageView add10;
    public ImageView cover1;
    public ImageView cover2;
    public ImageView cover3;
    public ImageView cover4;
    public ImageView cover5;
    public ImageView cover6;
    public ImageView cover7;
    public ImageView cover8;
    public ImageView cover9;
    public ImageView cover10;
    
    public ImageView[] addButtons;
    public ImageView[] covers;
    
    public MediaPlayer mediaPlayer;
    
    private boolean playing;
    
    public ImageView playPause;
    
    public ImageView mainSongCover;
    public Label mainSongTitle;
    public Label mainSongArtist;
    
    public ProgressBar songProgress;
    public ProgressBar songVolume;
    
    public Label songTimeLabel;
    public ComboBox<String> playbackSpeed;
    
    private double volumeVal;
    private double songSpeed;
    
    public ImageView listCover1, listCover2, listCover3, listCover4, listCover5, listCover6, listCover7, listCover8, listCover9, listCover10, listCover11, listCover12, listCover13, listCover14, listCover15, listCover16, listCover17, listCover18, listCover19, listCover20;
    public Button listButton1, listButton2, listButton3, listButton4, listButton5, listButton6, listButton7, listButton8, listButton9, listButton10, listButton11, listButton12, listButton13, listButton14, listButton15, listButton16, listButton17, listButton18, listButton19, listButton20;
    public ImageView listDel1, listDel2, listDel3, listDel4, listDel5, listDel6, listDel7, listDel8, listDel9, listDel10, listDel11, listDel12, listDel13, listDel14, listDel15, listDel16, listDel17, listDel18, listDel19, listDel20;
    
    private ImageView listCovers[];
    private Button listButtons[];
    private ImageView listDels[];
    
    public TextField playlistName;
    
    public ComboBox<String> playlistDropdown;
    public ComboBox<String> sortDropdown;
    
    public AnchorPane pane2;
    
    private boolean playingFromPlaylist;
    private int songIndex;
    
    /**
     * Create and initialize the JavaFX UI elements.
     */
    public void initialize() {
        hitButton.setStyle("-fx-background-color: #ffc0cb; ");
    	pane.setStyle("-fx-background-color: #ffffff");
    	pane2.setStyle("-fx-background-color: #ffffff");
        playlistDropdown.setStyle("-fx-background-color: #ffc0cb");
        playbackSpeed.setStyle("-fx-background-color: #ffc0cb; ");
        sortDropdown.setStyle("-fx-background-color: #ffc0cb; ");
        iNeedName.setStyle("-fx-background-color: #ffc0cb; ");
        iNeedAnother.setStyle("-fx-background-color: #ffc0cb; ");
        allocateButtons();
        allocateAddButtons();
        allocateCovers();
        allocateListButtons();
        searchedSongs = new Song[10];
        playing = false;
        volumeVal = 0.5;
        songSpeed = 1;
        sortDropdown.getItems().addAll("Sort by Name", "Sort by Date", "Sort by Artist");
        sortDropdown.setValue("Sort by Name");
        sortDropdown.valueProperty().addListener(new ChangeListener<String>() {
        	@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        		if (playlistDropdown.getValue() != null) {
        			refreshPlaylistItems();
            	}
			}
        });
        playbackSpeed.getItems().addAll("0.25x", "0.5x", "0.75x", "1x", "1.25x", "1.5x", "1.75x", "2x");
        playbackSpeed.setValue("1x");
        playbackSpeed.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				double speed = Double.valueOf(arg2.substring(0, arg2.indexOf('x')));
				songSpeed = speed;
			}
        });
        
        // Create a timeline object to update song progress bar every 0.1 seconds.
        Timeline songProgressUpdater = new Timeline(
        		new KeyFrame(Duration.seconds(0.1), e -> {
        			if (mediaPlayer != null) {
        				Duration totalTime = mediaPlayer.getTotalDuration();
        				Duration timeNow = mediaPlayer.getCurrentTime();
        				if (totalTime.isUnknown()) {
        					return;
        				}
        				double percent = timeNow.toMillis() / totalTime.toMillis();
        				songProgress.setProgress(percent);
        				
        				if (percent >= 0.99 && playingFromPlaylist) {
        					playNextSongHelper();
        				}
        				
        				// Set volume.
        				mediaPlayer.setVolume(volumeVal);
        				
        				// Set playback speed.
        				mediaPlayer.setRate(songSpeed);
        				
        				// Set song time label.
        				String timeLabelLeft = (int)timeNow.toMinutes() + ":" + String.format("%02d", (int)(timeNow.toSeconds() % 60));
        				String timeLabelRight = (int)totalTime.toMinutes() + ":" + String.format("%02d", (int)(totalTime.toSeconds() % 60));
        				songTimeLabel.setText(timeLabelLeft + "/" + timeLabelRight);
        			} else {
        				songProgress.setProgress(0);
        				songTimeLabel.setText("0:00/0:00");
        			}
        		})
        );
        songProgressUpdater.setCycleCount(Animation.INDEFINITE);
        songProgressUpdater.play();
        
        playlistDropdown.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2 != null) {
					refreshPlaylistItems();
				}
			}
        });
        
        Library.get().loadPlaylists();
        
        // loads loaded playlists into the dropdown menu
        refreshPlaylistDropdown("");
    }

    /**
     * Put buttons into an array and set them hidden.
     */
    private void allocateButtons() {
        buttons = new Button[10];
        buttons[0] = firstButton; buttons[1] = secondButton; buttons[2] = thirdButton; buttons[3] = fourthButton;
        buttons[4] = fifthButton; buttons[5] = sixthButton; buttons[6] = seventhButton; buttons[7] = eightButton;
        buttons[8] = ninthButton; buttons[9] = tenthButton;
        for (Button a: buttons){
            a.setVisible(false);
        }
    }
    
    /**
     * Put image buttons into an array and set them hidden.
     */
    private void allocateAddButtons() {
    	addButtons = new ImageView[] {add1, add2, add3, add4, add5, add6, add7, add8, add9, add10};
    	for (ImageView x : addButtons) {
    		x.setVisible(false);
    	}
    }
    
    /**
     * Allocate buttons for the playlist so the player could manipulate it.
     */
    private void allocateListButtons() {
    	listCovers = new ImageView[] {listCover1, listCover2, listCover3, listCover4, listCover5, listCover6, listCover7, listCover8, listCover9, listCover10, listCover11, listCover12, listCover13, listCover14, listCover15, listCover16, listCover17, listCover18, listCover19, listCover20};
    	listButtons = new Button[] {listButton1, listButton2, listButton3, listButton4, listButton5, listButton6, listButton7, listButton8, listButton9, listButton10, listButton11, listButton12, listButton13, listButton14, listButton15, listButton16, listButton17, listButton18, listButton19, listButton20};
    	listDels = new ImageView[] {listDel1, listDel2, listDel3, listDel4, listDel5, listDel6, listDel7, listDel8, listDel9, listDel10, listDel11, listDel12, listDel13, listDel14, listDel15, listDel16, listDel17, listDel18, listDel19, listDel20};
    	for (int i = 0; i < listCovers.length; i++) {
    		listCovers[i].setVisible(false);
    		listButtons[i].setVisible(false);
            listButtons[i].setStyle("-fx-background-color: #ffc0cb; ");
    		listDels[i].setVisible(false);
    	}
    }
    
    /**
     * Allocate UI resources for the covers so that the covers could be displayed correctly.
     */
    private void allocateCovers() {
    	covers = new ImageView[] {cover1, cover2, cover3, cover4, cover5, cover6, cover7, cover8, cover9, cover10};
    	for (ImageView x : covers) {
    		x.setVisible(false);
    	}
    }

    /**
     * Controller to handle the event of a button being clicked.
     * 
     * @param mouseEvent		a MouseEvent object with the information
     * 							about the mouse event itself.
     */
    public void buttonClicked(MouseEvent mouseEvent) {
    	// SEARCH
    	// First set all add buttons, covers, and buttons to invisible.
    	for (int i = 0; i < 10; i++) {
    		addButtons[i].setVisible(false);
    		covers[i].setVisible(false);
        	buttons[i].setVisible(false);
        }
        // Then use the API to get a list of songs based on search text.
        SearchDownloader search = SearchDownloader.getSearchDownloader();
        try {
			search.search(searchText.getText(), "track");
		} catch (IOException e) {
			System.out.println("The search has returned an error. Check your internet connection.");
		}
        
        List<String> songsID =	search.getSearchResult();
        // Fill UI with search result.
        int i = 0;
        for (String str : songsID) {
			Song song = SongDownloader.createSong(str);
			// Check if song has preview mp3. If not then just skip this song.
			if (song.getPreview() == null) {
				continue;
			}
			// Save reference of song in array.
			searchedSongs[i] = song;
			// 1. Make add to playlist button visible.
			addButtons[i].setVisible(true);
			// 2. Set cover image.
			covers[i].setImage(new Image(song.getURL640()));
			covers[i].setVisible(true);
			// 3. Create and set button text.
			String label = (i + 1) + ". " + song.getSongTitle() + " | " + song.getFirstArtist() + " | " + song.getDate();
			buttons[i].setText(label);
            buttons[i].setStyle("-fx-background-color: #FFC0CB; ");
			buttons[i].setVisible(true);
			
			i++;
			// If no more space in UI to fill search result, just disregard and break.
			if (i == 10)
				break;
		}
    }

    /**
     * A method to play/stop a song after play has clicked play.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							player's mouse event.
     */
    public void playSong(MouseEvent mouseEvent) {

        Button clicked = (Button) mouseEvent.getSource();

        // Switch to player screen.
        String num = clicked.getText().substring(0, clicked.getText().indexOf(". "));
        Song song = searchedSongs[Integer.parseInt(num) - 1];

        // Stop current song.
        if (mediaPlayer != null)
        	mediaPlayer.stop();
        
        Media media = new Media(song.getPreview());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(new Duration(0));
        mainSongCover.setImage(new Image(song.getURL640()));
        mainSongTitle.setText(song.getSongTitle());
        mainSongArtist.setText(song.getFirstArtist());
        playHelper();
        
        playingFromPlaylist = false;
    }
    
    /**
     * A helper method for the play/pause song method to help with
     * playing a song.
     */
    private void playHelper() {
    	playing = true;
    	mediaPlayer.play();
    	playPause.setImage(new Image(new File("ui_images/Pause.png").toURI().toString()));
    }
    
    /**
     * A helper method for the play/pause song method to help with
     * pausing a song.
     */
    private void pauseHelper() {
    	playing = false;
    	mediaPlayer.pause();
    	playPause.setImage(new Image(new File("ui_images/Play1.png").toURI().toString()));
    }
    
    /**
     * A method to play or pause a song.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void playPause(MouseEvent mouseEvent) {
    	if (mediaPlayer == null) {
    		return;
    	}
    	if (playing) {
    		pauseHelper();
    	} else {
    		playHelper();
    	}
    }

    /**
     * A method to fast forward the song.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void forward(MouseEvent mouseEvent) {
	// currently it is set to fast forward by 5 seconds.
    	if (mediaPlayer != null) {
    		mediaPlayer.seek(new Duration(mediaPlayer.getCurrentTime().toMillis() + 5000));
    	}
    }
    
    /**
     * A method to rewind the song.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void backward(MouseEvent mouseEvent) {
	// currently it is set to rewind by 5 seconds.
    	if (mediaPlayer != null) {
    		mediaPlayer.seek(new Duration(mediaPlayer.getCurrentTime().toMillis() - 5000));
    	}
    }
    
    /**
     * A method to change the music player's volume.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void changeVolume(MouseEvent mouseEvent) {
    	ProgressBar volumeBar = (ProgressBar)mouseEvent.getSource();
    	double percent = mouseEvent.getX() / volumeBar.getWidth();
    	percent = Math.max(Math.min(percent, 1), 0);
    	volumeBar.setProgress(percent);
    	volumeVal = percent;
    }
    
    /**
     * A method to change the song's play time.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void changeSongTime(MouseEvent mouseEvent) {
    	if (mediaPlayer == null) {
    		return;
    	}
    	ProgressBar songProgress = (ProgressBar)mouseEvent.getSource();
    	double percent = mouseEvent.getX() / songProgress.getWidth();
    	percent = Math.max(Math.min(percent, 1), 0);
    	Duration totalTime = mediaPlayer.getTotalDuration();
    	if (totalTime.isUnknown()) {
    		return;
    	}
    	mediaPlayer.seek(new Duration(totalTime.toMillis() * percent));
    }
    
    /**
     * A method to create a new playlist.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void newPlaylist(MouseEvent mouseEvent) {
    	// Make sure playlistName is not just empty.
    	if (!playlistName.getText().equals("")) {
    		// Make sure playlistName is not the same as anything before.
    		String newName = playlistName.getText();
    		Library lib = Library.get();
    		if (lib.getPlaylist(newName) != null) {
    			playlistName.setText("");
    			return;
        	}
    		// Create playlist with this name.
    		lib.createPlaylist(newName);
    		refreshPlaylistDropdown(newName);
    		playlistName.setText("");
    	}
    }
    
    /**
     * A method to refresh a playlist dropdown menu.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    private void refreshPlaylistDropdown(String newOne) {
        playlistDropdown.getItems().clear();
        Library lib = Library.get();
        for (int i = 0; i < lib.getNumPlaylists(); i++) {
            String name = lib.getPlaylist(i).getName();
            playlistDropdown.getItems().addAll(name);
        }
        if (!newOne.equals("")) {
            playlistDropdown.setValue(newOne);
            refreshPlaylistItems();
        } else {
        	playlistDropdown.setValue(null);
        	// Turn off all the buttons/imageviews.
    		for (int k = 0; k < listCovers.length; k++) {
    			listCovers[k].setVisible(false);
        		listButtons[k].setVisible(false);
        		listDels[k].setVisible(false);
    		}
        }
        
    }
    
    /**
     * A method to refresh a playlist's items.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    private void refreshPlaylistItems() {
    	String playlistName = (String)playlistDropdown.getValue();
    	Library lib = Library.get();
		Playlist playlist = lib.getPlaylist(playlistName);
		// Sort playlist internally.
		if (sortDropdown.getValue().equals("Sort by Name")) {
			playlist.sort("name");
		} else if (sortDropdown.getValue().equals("Sort by Date")) {
			playlist.sort("date");
		} else if (sortDropdown.getValue().equals("Sort by Artist")) {
			playlist.sort("artist");
		}
		// Turn off all the buttons/imageviews first.
		for (int k = 0; k < listCovers.length; k++) {
			listCovers[k].setVisible(false);
    		listButtons[k].setVisible(false);
    		listDels[k].setVisible(false);
		}
		// Fill buttons/imageviews using songs in playlist.
		int j = 0;
		for (Song s : playlist.getSongs()) {
			String label = (j + 1) + ". " + s.getSongTitle() + " | " + s.getFirstArtist() + " | " + s.getDate();
			listCovers[j].setVisible(true);
    		listButtons[j].setVisible(true);
    		listDels[j].setVisible(true);
    		listCovers[j].setImage(new Image(s.getURL640()));
			listButtons[j].setText(label);
    		
    		j++;
    		if (j == listCovers.length) {
    			break;
    		}
		}
    }
    
    /**
     * A method to create to add a new song to a playlist.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void addSong(MouseEvent mouseEvent) {
    	ImageView iv = (ImageView) mouseEvent.getSource();
    	int i = Integer.valueOf(iv.getId().substring(3)) - 1;
    	Song song = searchedSongs[i];
    	if (playlistDropdown.getValue() != null) {
		String playlistName = (String)playlistDropdown.getValue();
		Library lib = Library.get();
    		
		int size1 = lib.getPlaylist(playlistName).getSongs().size();
		if (size1 < 20)
			lib.getPlaylist(playlistName).addSong(song);
            
		int size2 = lib.getPlaylist("Library").getSongs().size();
		if (size2 < 20)
			lib.getPlaylist("Library").addSong(song);
            
		refreshPlaylistItems();
    	}
    }
    
    /**
     * A method to create a song in the playlist.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void playPlaylistSong(MouseEvent mouseEvent) {
    	Button btn = (Button) mouseEvent.getSource();
    	int i = Integer.valueOf(btn.getText().substring(0, btn.getText().indexOf(". "))) - 1;
    	String playlistName = (String)playlistDropdown.getValue();
    	Library lib = Library.get();
    	Playlist playlist = lib.getPlaylist(playlistName);
    	Song song = playlist.getSongs().get(i);
    	
    	// Stop current song.
        if (mediaPlayer != null) {
        	mediaPlayer.stop();
        }
        
        Media media = new Media(song.getPreview());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(new Duration(0));
        mainSongCover.setImage(new Image(song.getURL640()));
        mainSongTitle.setText(song.getSongTitle());
        mainSongArtist.setText(song.getFirstArtist());
        playHelper();
        
        playingFromPlaylist = true;
        songIndex = i;
    }
    
    /**
     * A method to delete a playlist.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void deletePlaylist(MouseEvent mouseEvent) {
    	if (playlistDropdown.getValue() != null) {
    		String playlistName = (String)playlistDropdown.getValue();
    		Library lib = Library.get();
    		lib.deletePlaylist(playlistName);
    		refreshPlaylistDropdown("");
    		playingFromPlaylist = false;
    	}
    }
    
    /**
     * A method to remove a song from the playlist.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void removeFromPlaylist(MouseEvent mouseEvent) {
    	ImageView im = (ImageView) mouseEvent.getSource();
    	int i = Integer.valueOf(im.getId().substring(7)) - 1;
    	String playlistName = (String)playlistDropdown.getValue();
    	Library lib = Library.get();
    	Playlist playlist = lib.getPlaylist(playlistName);
    	playlist.getSongs().remove(i);
    	refreshPlaylistItems();
    }
    
    /**
     * A method to play the next song from the playlist.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void playNextSong(MouseEvent mouseEvent) {
    	playNextSongHelper();
    }
    
    /**
     * A helper method for playNextSong().
     * 
     */
    private void playNextSongHelper() {
    	if (playingFromPlaylist) {
    		String playlistName = (String)playlistDropdown.getValue();
        	Library lib = Library.get();
        	Playlist playlist = lib.getPlaylist(playlistName);
        	if (songIndex + 1 < playlist.getSongs().size()) {
        		songIndex++;
        		Song song = playlist.getSongs().get(songIndex);
        		
        		// Stop current song.
                if (mediaPlayer != null) {
                	mediaPlayer.stop();
                }
                
                Media media = new Media(song.getPreview());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setStartTime(new Duration(0));
                mainSongCover.setImage(new Image(song.getURL640()));
                mainSongTitle.setText(song.getSongTitle());
                mainSongArtist.setText(song.getFirstArtist());
                playHelper();
        	}
    	}
    }
    
    /**
     * A method to play the previous song.
     * 
     * @param mouseEvent		a MouseEvent with more info about the
     * 							the player's mouse.
     */
    public void playPrevSong(MouseEvent mouseEvent) {
    	playPrevSongHelper();
    }
    
    /**
     * A helper method for playPrevSong().
     * 
     */
    private void playPrevSongHelper() {
    	if (playingFromPlaylist) {
    		String playlistName = (String)playlistDropdown.getValue();
        	Library lib = Library.get();
        	Playlist playlist = lib.getPlaylist(playlistName);
        	if (songIndex - 1 >= 0) {
        		songIndex--;
        		Song song = playlist.getSongs().get(songIndex);
        		
        		// Stop current song.
                if (mediaPlayer != null) {
                	mediaPlayer.stop();
                }
                
                Media media = new Media(song.getPreview());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setStartTime(new Duration(0));
                mainSongCover.setImage(new Image(song.getURL640()));
                mainSongTitle.setText(song.getSongTitle());
                mainSongArtist.setText(song.getFirstArtist());
                playHelper();
        	}
    	}
    }
}
