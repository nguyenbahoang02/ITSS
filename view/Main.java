package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.homePage.HomePageController;
import view.listWorkerView.ListWorkerViewController;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listWorkerView/ListWorkerView.fxml"));
//		loader.setController(new ListWorkerViewController(stage));
    	Unit unit = new Unit("VN-03");
    	unit.getEmployees();
    	unit.getEmployeesWorksData();
    	Officer user = new Officer("Nguyễn Bá Hoàng", "20204976","VN03");
    	user.getOfficerWorksDetail().getDataFromFile();
    	API.USER = user;
    	API.UNIT=unit;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage/HomePage.fxml"));
		loader.setController(new HomePageController(stage));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
