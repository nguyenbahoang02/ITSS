package view.detailTab;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.API;
import view.Officer;
import view.OfficerTimesheet;
import view.editTab.EditTabController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.importTab.ImportTabController;
import view.overviewTab.OverviewTabController;
import view.unitTab.UnitTabController;

public class DetailTabController implements Initializable{
	private String userName = "Nguyễn Bá Hoàng";
	private Stage stage;
	private Officer user;
	private String time;
	
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
    private Button editTab;

    @FXML
    private Button importTab;
    
    @FXML
    private Button exportTab;

    @FXML
    private MenuButton userSettings;

    @FXML
    private Text currentTime = new Text();
    
    @FXML 
    private TableView<OfficerTimesheet> table = new TableView<>();
    
    @FXML
    private TableColumn<OfficerTimesheet, String> dayCol = new TableColumn<OfficerTimesheet, String>();
    
    @FXML
    private TableColumn<OfficerTimesheet, String> morningCol= new TableColumn<OfficerTimesheet, String>();;
    
    @FXML
    private TableColumn<OfficerTimesheet, String> afternoonCol= new TableColumn<OfficerTimesheet, String>();;
    
    @FXML
    private TableColumn<OfficerTimesheet, String> lateCol= new TableColumn<OfficerTimesheet, String>();;
    
    @FXML
    private TableColumn<OfficerTimesheet, String> earlyCol= new TableColumn<OfficerTimesheet, String>();;
    
    @FXML
    void nextClicked(MouseEvent event) {
    	if(currentTime.getText().length()==7) {
    		int month = Integer.parseInt(currentTime.getText().split("/")[0]);
    		int year = Integer.parseInt(currentTime.getText().split("/")[1]);
    		if(month==12) {
    			year++;
    			month=1;
    		}else month+=1;
    		
    		if(month>=10) currentTime.setText(month + "/" + year);
    		else currentTime.setText("0" + month + "/" + year);
    		setDataToTable(user.getOfficerWorksDetail().getTimesheetByMonth(currentTime.getText()));
    	}
    	else if(currentTime.getText().length()==4) {
    		int year = Integer.parseInt(currentTime.getText());
    		year++;
    		currentTime.setText("" + year);
    		setDataToTable(user.getOfficerWorksDetail().getTimesheetByYear(currentTime.getText()));
    	}
    	else if(currentTime.getText().length()==10) {
    		int quy = Integer.parseInt(currentTime.getText().split(" ")[1]);
    		int year = Integer.parseInt(currentTime.getText().split(" ")[2]);
    		if(quy==4) {
    			quy = 1;
    			year++;
    		}else quy++;
    		currentTime.setText("Quý " + quy + " " + year);
    		setDataToTable(user.getOfficerWorksDetail().getTimesheetByQuarter(currentTime.getText()));
    	}
    }
    
    @FXML
    void prevClicked(MouseEvent event) {
    	if(currentTime.getText().length()==7) {
    		int month = Integer.parseInt(currentTime.getText().split("/")[0]);
    		int year = Integer.parseInt(currentTime.getText().split("/")[1]);
    		if(month==1) {
    			year--;
    			month=12;
    		}else {
    			month-=1;
    		}
    		if(month>=10) currentTime.setText(month + "/" + year);
    		else currentTime.setText("0" + month + "/" + year);
    		setDataToTable(user.getOfficerWorksDetail().getTimesheetByMonth(currentTime.getText()));
    	}
    	else if(currentTime.getText().length()==4) {
    		int year = Integer.parseInt(currentTime.getText());
    		year--;
    		currentTime.setText("" + year);
    		setDataToTable(user.getOfficerWorksDetail().getTimesheetByYear(currentTime.getText()));
    	}
    	else if(currentTime.getText().length()==10) {
    		int quy = Integer.parseInt(currentTime.getText().split(" ")[1]);
    		int year = Integer.parseInt(currentTime.getText().split(" ")[2]);
    		if(quy==1) {
    			quy = 4;
    			year--;
    		}else quy--;
    		currentTime.setText("Quý " + quy + " " + year);
    		setDataToTable(user.getOfficerWorksDetail().getTimesheetByQuarter(currentTime.getText()));
    	}
    }
    
    @FXML
    void monthClicked(MouseEvent event) {
    	currentTime.setText("06/2023");
    	setDataToTable(user.getOfficerWorksDetail().getTimesheetByMonth(currentTime.getText()));
    }

    @FXML
    void quarterClicked(MouseEvent event) {
    	currentTime.setText("Quý 2 2023");
    	setDataToTable(user.getOfficerWorksDetail().getTimesheetByQuarter(currentTime.getText()));
    }

    @FXML
    void yearClicked(MouseEvent event) {
    	currentTime.setText("2023");
    	setDataToTable(user.getOfficerWorksDetail().getTimesheetByYear(currentTime.getText()));
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
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/unitTab/UnitTab.fxml"));
    		loader.setController(new UnitTabController(stage));
    		Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
	    		stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	editTab.setOnMouseClicked(event ->{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editTab/EditTab.fxml"));
    		loader.setController(new EditTabController(stage));
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
    
    public void setDataToTable(ArrayList<OfficerTimesheet> data) {
    	ObservableList<OfficerTimesheet> list = FXCollections.observableArrayList();
    	list.addAll(data);
    	
    	dayCol.setCellValueFactory(new PropertyValueFactory<OfficerTimesheet, String>("date"));
    	morningCol.setCellValueFactory(new PropertyValueFactory<OfficerTimesheet, String>("morning"));
    	afternoonCol.setCellValueFactory(new PropertyValueFactory<OfficerTimesheet, String>("afternoon"));
    	lateCol.setCellValueFactory(new PropertyValueFactory<OfficerTimesheet, String>("lateHours"));
        earlyCol.setCellValueFactory(new PropertyValueFactory<OfficerTimesheet, String>("soonHours"));
        
    	table.setItems(list);
    }
    
    public DetailTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.USER;
    	time="06/2023";
    }
    
    public DetailTabController(Stage stage, Officer user, String currentTime) {
    	this.stage=stage;
    	this.user=user;
    	this.time=currentTime;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		currentTime.setText(time);
		officerName.setText("Tên: " + user.getName());
		officerId.setText("Id: " + user.getId());
    	setDataToTable(user.getOfficerWorksDetail().getTimesheetByMonth(this.currentTime.getText()));
	}
    
}
