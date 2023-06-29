package view;

import api.API;
import controller.HomePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
    	API.CREATE_UNIT();
    	API.SET_USER(API.GET_UNIT("1").getOfficers().get(0));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage/HomePage.fxml"));
		loader.setController(new HomePageController(stage));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("PM chấm công");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
