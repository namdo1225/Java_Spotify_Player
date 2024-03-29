# Java Spotify Player
  - This program uses the Spotify API to play demo tracks from Spotify. The user will need to login with Spotify and grant the app permissions to act on your behalf to download these Spotify tracks.

# NOTE
  - Your favorite track might not be available, depending on how it is configured on Spotify.
  - Please use a JDK or JRE capable of running Java 17 or later (Class File Format Version 61).
  - Start application by running GuiStart.java.
  - Developed and tested on Windows 10 and 11 using the Eclipse IDE.
  - The music player streams songs, so you need **consistent** internet connection to use the music player.
  - The application will be blocked while waiting for the Spotify API to process.
  - There is a 20-tracks limit per playlist due to the fact that there are only 20 button variables set up. Since the "Library" is a playlist, this means that the library might not contain all the songs that another playlist has once the library reaches its limit.

# REDIRECTED HERE
  - Are you redirected to this page? If the authorization happens successfully, your URL should have a "code=" portion. Copy anything after that portion and return to the app!

# RESOURCES USED
  - GSON            - Library to parse JSON objects returned from HTTP requests - https://github.com/google/gson
  - JavaFX          - UI Library - https://openjfx.io/
  - Spotify API     - Songs are taken from Spotify - https://developer.spotify.com/
  - Images used for UI icons - Found through Google Search
  - [Ui icons created by Dwi ridwanto - Flaticon](https://www.flaticon.com/free-icons/ui)
  - PCKE Java Code  - https://www.appsdeveloperblog.com/pkce-code-verifier-and-code-challenge-in-java/

# AUTHORS
  - JhihYang Wu
  - Andrew Hardy
  - Patrick Comden
  - Nam Do

# Instructions to use:
1. Click “Login”. You will be redirected to Spotify’s website.

![Login into the app picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/00_login.png)

2. Login to Spotify and click “Agree”.

![Login into Spotify and grant authorization picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/01_agree.png)

3. Grab the code from the URL.

![Grab the code from URL picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/02_code.png)

4. Copy the code into the app.

![Copy the code into app picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/03_copy.png)

5. You can now search for tracks if the provided code is correct.

![Search for a song picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/04_search.png)

6. You can play songs, change playback speed, adjust volume, etc.

![Play a song picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/05_play.png)

7. You can add songs to playlists.

![Add songs to playlist picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/06_playlist.png)

8. The red icon at the top right has instructions that can help you navigate through the app.

![Help icon picture](https://namdo1225.github.io/images/projects_media/20230526_demospotify/07_instruction.png)
