module A4_Final_JavaFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	requires com.google.gson;
	requires javafx.fxml;

	opens application to javafx.graphics, javafx.fxml, com.google.gson;

}
