package view.overviewTab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.API;
import view.Officer;
import view.detailTab.DetailTabController;
import view.editTab.EditTabController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.importTab.ImportTabController;
import view.listUnitTab.ListUnitTabController;
import view.unitTab.UnitTabController;

public class OverviewTabController implements Initializable{
	private String userName = "Nguyễn Bá Hoàng";
	private Stage stage;
	private Officer user;
	
	@FXML
	private Text officerName;
	
	@FXML
	private Text officerId;
	
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
    private Text currentTime;
    
    @FXML
    private Text totalShift;
    
    @FXML
    private Text lateHours;
    
    @FXML
    private Text earlyHours;
    
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
    		setInfoMonth();
    	}
    	else if(currentTime.getText().length()==4) {
    		int year = Integer.parseInt(currentTime.getText());
    		year++;
    		currentTime.setText("" + year);
    		setInfoYear();
    	}
    	else if(currentTime.getText().length()==10) {
    		int quy = Integer.parseInt(currentTime.getText().split(" ")[1]);
    		int year = Integer.parseInt(currentTime.getText().split(" ")[2]);
    		if(quy==4) {
    			quy = 1;
    			year++;
    		}else quy++;
    		currentTime.setText("Quý " + quy + " " + year);
    		setInfoQuarter();
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
    		setInfoMonth();
    	}
    	else if(currentTime.getText().length()==4) {
    		int year = Integer.parseInt(currentTime.getText());
    		year--;
    		currentTime.setText("" + year);
    		setInfoYear();
    	}
    	else if(currentTime.getText().length()==10) {
    		int quy = Integer.parseInt(currentTime.getText().split(" ")[1]);
    		int year = Integer.parseInt(currentTime.getText().split(" ")[2]);
    		if(quy==1) {
    			quy = 4;
    			year--;
    		}else quy--;
    		currentTime.setText("Quý " + quy + " " + year);
    		setInfoQuarter();
    	}
    }
    
    public void setInfoMonth() {
    	totalShift.setText("Tổng số buổi đi làm: " + user.getOfficerWorksDetail().getTotalShiftByMonth(currentTime.getText()));
    	lateHours.setText("Tổng số giờ đi muộn: " + user.getOfficerWorksDetail().getLateHoursByMonth(currentTime.getText()));
    	earlyHours.setText("Tổng số giờ về sớm: " + user.getOfficerWorksDetail().getEarlyHoursByMonth(currentTime.getText()));
    }
    
    public void setInfoYear() {
    	totalShift.setText("Tổng số buổi đi làm: " + user.getOfficerWorksDetail().getTotalShiftByYear(currentTime.getText()));
    	lateHours.setText("Tổng số giờ đi muộn: " + user.getOfficerWorksDetail().getLateHoursByYear(currentTime.getText()));
    	earlyHours.setText("Tổng số giờ về sớm: " + user.getOfficerWorksDetail().getEarlyHoursByYear(currentTime.getText()));
    }
    
    public void setInfoQuarter() {
    	totalShift.setText("Tổng số buổi đi làm: " + user.getOfficerWorksDetail().getTotalShiftByQuarter(currentTime.getText()));
    	lateHours.setText("Tổng số giờ đi muộn: " + user.getOfficerWorksDetail().getLateHoursByQuarter(currentTime.getText()));
    	earlyHours.setText("Tổng số giờ về sớm: " + user.getOfficerWorksDetail().getEarlyHoursByQuarter(currentTime.getText()));
    }
    
    @FXML
    void monthClicked(MouseEvent event) {
    	currentTime.setText("06/2023");
    	setInfoMonth();
    }

    @FXML
    void quarterClicked(MouseEvent event) {
    	currentTime.setText("Quý 2 2023");
    	setInfoQuarter();
    	
    }

    @FXML
    void yearClicked(MouseEvent event) {  	
    	currentTime.setText("2023");
    	setInfoYear();
    }
    
    @FXML
    void detailClicked(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/detailTab/DetailTab.fxml"));
		loader.setController(new DetailTabController(stage,user,currentTime.getText()));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
    		stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    
    public OverviewTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.GET_USER();
    }
    
    public OverviewTabController(Stage stage, Officer user) {
    	this.stage=stage;
    	this.user=user;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		monthClicked(null);
		officerId.setText("Id: " + user.getId());
		officerName.setText("Tên: " + user.getName());
	}
    
}
