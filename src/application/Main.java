package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.StartScreenViewController;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			StartScreenViewController startScreenViewController = new StartScreenViewController(primaryStage);
			Scene scene = new Scene(startScreenViewController);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Port Royal");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
