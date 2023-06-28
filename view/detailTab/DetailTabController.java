package view.detailTab;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.API;
import view.Officer;
import view.AttendanceRecord;
import view.Unit;
import view.editScreen.EditScreenController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.importTab.ImportTabController;
import view.listUnitTab.ListUnitTabController;
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
    private Button importTab;
    
    @FXML
    private Button exportTab;

    @FXML
    private MenuButton userSettings;

    @FXML
    private TextField searchField;
    
    @FXML
    private Text currentTime = new Text();
    
    @FXML 
    private TableView<AttendanceRecord> table = new TableView<>();
    
    @FXML
    private TableColumn<AttendanceRecord, String> dayCol = new TableColumn<AttendanceRecord, String>();
    
    @FXML
    private TableColumn<AttendanceRecord, String> morningCol= new TableColumn<AttendanceRecord, String>();;
    
    @FXML
    private TableColumn<AttendanceRecord, String> afternoonCol= new TableColumn<AttendanceRecord, String>();;
    
    @FXML
    private TableColumn<AttendanceRecord, String> lateCol= new TableColumn<AttendanceRecord, String>();;
    
    @FXML
    private TableColumn<AttendanceRecord, String> earlyCol= new TableColumn<AttendanceRecord, String>();;
    
    @FXML
    void exportClicked(MouseEvent event) {
    	ArrayList<AttendanceRecord> exportData = new ArrayList<>();
    	exportData.addAll(table.getItems());
    	ExportTabController.EXPORT(exportData,stage,user.getId());
    }
    
    @FXML
    void backClicked(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/overviewTab/OverviewTab.fxml"));
		loader.setController(new OverviewTabController(stage,user));
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
    
    public void setSearchFunction() {
    	searchField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String text) {
				if(text.length()!=0) {
					if(text.charAt(0)!= ' ') {
						ObservableList<AttendanceRecord> list = FXCollections.observableArrayList();
						for (AttendanceRecord attendanceRecord : table.getItems()) {
							if(attendanceRecord.getDate().contains(searchField.getText())) {
								list.add(attendanceRecord);
							}
						}
						table.setItems(list);
						
					}else searchField.setText("");
				}else if(currentTime.getText().length()==4) {
					setDataToTable(user.getOfficerWorksDetail().getTimesheetByYear(currentTime.getText()));
				}else if(currentTime.getText().length()==7) {
					setDataToTable(user.getOfficerWorksDetail().getTimesheetByMonth(currentTime.getText()));
				}else setDataToTable(user.getOfficerWorksDetail().getTimesheetByQuarter(currentTime.getText()));
				
			}
    		
		});
    }
    
    public void setDataToTable(ArrayList<AttendanceRecord> data) {
    	ObservableList<AttendanceRecord> list = FXCollections.observableArrayList();
    	list.addAll(data);
    	
    	dayCol.setCellValueFactory(new PropertyValueFactory<AttendanceRecord, String>("date"));
    	morningCol.setCellValueFactory(new PropertyValueFactory<AttendanceRecord, String>("morning"));
    	afternoonCol.setCellValueFactory(new PropertyValueFactory<AttendanceRecord, String>("afternoon"));
    	lateCol.setCellValueFactory(new PropertyValueFactory<AttendanceRecord, String>("lateHours"));
        earlyCol.setCellValueFactory(new PropertyValueFactory<AttendanceRecord, String>("soonHours"));
        
    	table.setItems(list);
    	table.setOnMouseClicked(event -> {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount()==2) {
				AttendanceRecord selectedItem = table.getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editScreen/EditScreen.fxml"));
					loader.setController(new EditScreenController(stage,selectedItem,user,currentTime.getText()));
					Parent root;
					try {
						root = loader.load();
						Scene scene = new Scene(root);
						stage.setScene(scene);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
    }
    
    public DetailTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.GET_USER();
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
		if(currentTime.getText().length()==4) {
			setDataToTable(user.getOfficerWorksDetail().getTimesheetByYear(this.currentTime.getText()));
		}else if(currentTime.getText().length()==7) {
			setDataToTable(user.getOfficerWorksDetail().getTimesheetByMonth(this.currentTime.getText()));
		}else setDataToTable(user.getOfficerWorksDetail().getTimesheetByQuarter(this.currentTime.getText()));
		setSearchFunction();	
		
	}
    
}
