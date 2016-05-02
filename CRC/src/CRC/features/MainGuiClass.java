package CRC.features;

import CRC.view.MainWindowController;
import CRC.view.RootWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGuiClass extends Application {

	public static final String TITLE = "Kontrola b³êdów";
	
	private Stage mStage;
	private BorderPane rootLayout;
    private AnchorPane targetView;    
	public Scene mScene;	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		this.mStage = primaryStage;
		this.mStage.setTitle(TITLE);
		
		initRootLayout();
		initMainWindow();
	}
	
	public Scene getScene() {
		return this.mScene;
	}
	
	public Stage getStage() {		
		return this.mStage;
	}
	
	private void initRootLayout() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader();	
			
			loader.setLocation(getClass().getResource("../view/RootWindow.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			mScene = new Scene(rootLayout);	        
			mStage.setScene(mScene);
			
			RootWindowController controller = loader.getController();
	        controller.setMainClass(this);
	        
	        mStage.show();
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void initMainWindow() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("../view/MainWindow.fxml"));
			targetView = (AnchorPane) loader.load();
			
			rootLayout.setCenter(targetView);
			
			MainWindowController controller = loader.getController();
			controller.setMainClass(this);
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}
}