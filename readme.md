NOTE
  - This program uses the Spotify API to play tracks. Unfortunately, since it requires an account to use such API, we have deleted the account to avoid being charged or having our API tokens hacked.
  - Start application by running GuiStart.java
  - Developed and tested on Windows 10 and 11 using Eclipse IDE.
  - The music player streams songs, so you need **consistent** internet connection to use the music player.
  - The program uses libraries to make API call. There will be some waiting and loading in the background as the API data is being retrieved. Note that the program might freeze and resume its normal operation afterward if you try to search/load songs without internet.
  - There is a 20 tracks limit per playlist due to the fact that there are only 20 buttons variables set up. Since the Library is a playlist, this means that the library might not contain all the songs that another playlist has once the library reaches its limit.

RESOURCES USED
  - GSON        - Library to parse JSON objects returned from HTTP requests - https://github.com/google/gson
  - JavaFX      - UI Library - https://openjfx.io/
  - Spotify API - Songs are taken from Spotify - https://developer.spotify.com/
  - Images used for UI icons - Found through Google Search

AUTHORS
  - JhihYang Wu
  - Andrew Hardy
  - Patrick Comden
  - Nam Do
