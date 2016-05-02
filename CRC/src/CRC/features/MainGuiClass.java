package CRC.features;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGuiClass extends Application {

	private Stage mStage;
	private BorderPane rootLayout;
    private AnchorPane targetView;    
	public Scene mScene;	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		this.mStage = primaryStage;
	}
	
	public Scene getScene() {
		return this.mScene;
	}
	
	public Stage getStage() {		
		return this.mStage;
	}
	
	private void initRootLayout() {
		
	}
	
	private void initMainWindow() {
		
	}
}