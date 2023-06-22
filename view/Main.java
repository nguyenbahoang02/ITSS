package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.listOfficerView.ListOfficerViewController;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listOfficerView/ListOfficerView.fxml"));
		loader.setController(new ListOfficerViewController(stage));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Giao diện xem list công nhân trong đơn vị");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
