/** 
* This program is a music player which uses the Spotify API to download and play
* 30 seconds previews of a song. It supports basic functionality such as song
* rewind, fast forward, and playlist creation.
* 
* GuiStart is a class which holds the main function to start the application.
* 
* @author Andrew Hardy
* @version 1.0
* 
*/

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiStart extends Application {
	
    /**
     * A method partly responsible for creating the overall UI object
     * to start the UI portion of the application.
     * 
     * @param stage			a Stage object for the window that
     * 						displays the UI.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiStart.class.getResource("GuiStart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 799, 528);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
        Library.get().savePlaylists();
    }
}
