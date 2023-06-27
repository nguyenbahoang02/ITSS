package view.editScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.Officer;
import view.OfficerTimesheet;
import view.detailTab.DetailTabController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.importTab.ImportTabController;
import view.listUnitTab.ListUnitTabController;
import view.overviewTab.OverviewTabController;

public class EditScreenController implements Initializable{
	private String userName = "Nguyễn Bá Hoàng";
	private Stage stage;
	private OfficerTimesheet officerTimesheet;
	private Officer officer;
	private String currentTime;
	
	@FXML
	private Text officerId;
	
	@FXML
	private Text officerName;
	
    @FXML
    private Text homePage;
	
    @FXML
    private Button detailTab;
    
    @FXML
    private Button overviewTab;

    @FXML
    private Button unitTab;

    @FXML
    private Button importTab;
    
    @FXML
    private Button exportTab;

    @FXML
    private MenuButton userSettings;
    
    @FXML
    private TextField dateText;
    
    @FXML
    private TextField morningText;

    @FXML
    private TextField afternoonText;
    
    @FXML
    private TextField lateText;
    
    @FXML
    private TextField earlyText;
    
    @FXML
    void backClicked(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/detailTab/DetailTab.fxml"));
		loader.setController(new DetailTabController(stage,officer,currentTime));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
    		stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void saveClicked(MouseEvent event) {
    	if(dateText.getText().length()!=10||countNumberOfChar(dateText.getText())!=2) {
    		showErrorMsg("Hãy nhập đúng định dạng ngày");
    		return;
    	}
    	if(dateText.getText().split("/")[0].length()!=2||dateText.getText().split("/")[1].length()!=2||dateText.getText().split("/")[2].length()!=4){
    		showErrorMsg("Hãy nhập đúng định dạng ngày");
    		return;
    	}
    	if(!morningText.getText().equals("có")&&!morningText.getText().equals("không")) {
    		showErrorMsg("Hãy nhập đúng định dạng ca chấm công sáng");
    		System.out.println(morningText.getText());
    		return;
    	}
    	if(!afternoonText.getText().equals("có")&&!afternoonText.getText().equals("không")) {
    		showErrorMsg("Hãy nhập đúng định dạng ca chấm công chiều");
    		return;
    	}
    	try {
			Float.parseFloat(earlyText.getText());
		} catch (NumberFormatException e) {
			showErrorMsg("Hãy nhập đúng định dạng giờ về sớm");
			return;
		}
    	try {
    		Float.parseFloat(lateText.getText());
		} catch (NumberFormatException e) {
			showErrorMsg("Hãy nhập đúng định dạng giờ đi muộn");
			return;
		}
    	officerTimesheet.setDate(dateText.getText());
    	officerTimesheet.setLateHours(lateText.getText());
    	officerTimesheet.setSoonHours(earlyText.getText());
    	officerTimesheet.setMorning(morningText.getText());
    	officerTimesheet.setAfternoon(afternoonText.getText());
    }
    
    public void setTabSwitchinFunction() {
    	homePage.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage/HomePage.fxml"));
    		loader.setController(new HomePageController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	detailTab.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/detailTab/DetailTab.fxml"));
    		loader.setController(new DetailTabController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	overviewTab.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/overviewTab/OverviewTab.fxml"));
    		loader.setController(new OverviewTabController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	unitTab.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listUnitTab/ListUnitTab.fxml"));
    		loader.setController(new ListUnitTabController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	importTab.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/importTab/ImportTab.fxml"));
    		loader.setController(new ImportTabController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	exportTab.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/exportTab/ExportTab.fxml"));
    		loader.setController(new ExportTabController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    }

    public EditScreenController(Stage stage, OfficerTimesheet officerTimesheet, Officer officer, String currentTime) {
    	this.stage=stage;
    	this.officerTimesheet=officerTimesheet;
    	this.officer=officer;
    	this.currentTime=currentTime;
    }
    
    private void setInitialText() {
    	dateText.setText(officerTimesheet.getDate());
		morningText.setText(officerTimesheet.getMorning());
		afternoonText.setText(officerTimesheet.getAfternoon());
		lateText.setText(officerTimesheet.getLateHours());
		earlyText.setText(officerTimesheet.getSoonHours());
    }
    
    private int countNumberOfChar(String string) {
    	int count = 0;
    	 for (char c : string.toCharArray()) {
             // Check if the character matches the target character
             if (c == '/') {
                 count++;
             }
         }
    	return count;
    }
    
    private void showErrorMsg(String string) {
    	Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.initStyle(StageStyle.DECORATED);
        notification.setHeaderText(null);
        notification.setContentText(string);
        
        Stage stage = (Stage) notification.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        
        notification.showAndWait();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		officerId.setText("Id: " + officer.getId());
		officerName.setText("Tên: " + officer.getName());
		setTabSwitchinFunction();
		setInitialText();
	}
}
