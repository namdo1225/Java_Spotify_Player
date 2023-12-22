/**
 * Defines the main module for the entire application.
 */
module A4_Final_JavaFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	requires com.google.gson;
	requires javafx.fxml;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml, com.google.gson;
	opens backend_SpotifyAPI to com.google.gson;
}
