package view.unitTab;

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
import view.Unit;
import view.detailTab.DetailTabController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.importTab.ImportTabController;
import view.listUnitTab.ListUnitTabController;
import view.overviewTab.OverviewTabController;

public class UnitTabController implements Initializable{
	private String userName = "Nguyễn Bá Hoàng";
	private Stage stage;
	private Officer user;
	private Unit unit;
	
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
    private Text unitId;
    
    @FXML
    private TableView<Officer> table;
    
    @FXML 
    private TableColumn<Officer, String> idCol;
    
    @FXML
    private TableColumn<Officer, String> nameCol;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Text shiftCount;
    
    @FXML
    private Text numberOfEmployee;
    
    @FXML
    private Text lateHours;
    
    @FXML
    private Text earlyHours;
    
    @FXML
    private Text currentTime;
    
    @FXML
    void backClicked(MouseEvent event) {
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
    
    private void setInfoMonth() {
    	int shift=0;
    	float late=0f;
    	float early=0f;
    	for (Officer officer : unit.getOfficers()) {
			shift+=Integer.parseInt(officer.getOfficerWorksDetail().getTotalShiftByMonth(currentTime.getText()));
			late+=Float.parseFloat(officer.getOfficerWorksDetail().getLateHoursByMonth(currentTime.getText()));
			early+=Float.parseFloat(officer.getOfficerWorksDetail().getEarlyHoursByMonth(currentTime.getText()));
		}
    	shiftCount.setText("Tổng số buổi đi làm: " + shift);
    	lateHours.setText("Tổng số giờ đi muộn: " + late);
    	earlyHours.setText("Tổng số giờ về sớm: " + early);
    }
    
    private void setInfoYear() {
    	int shift=0;
    	float late=0f;
    	float early=0f;
    	for (Officer officer : unit.getOfficers()) {
			shift+=Integer.parseInt(officer.getOfficerWorksDetail().getTotalShiftByYear(currentTime.getText()));
			late+=Float.parseFloat(officer.getOfficerWorksDetail().getLateHoursByYear(currentTime.getText()));
			early+=Float.parseFloat(officer.getOfficerWorksDetail().getLateHoursByYear(currentTime.getText()));
		}
    	shiftCount.setText("Tổng số buổi đi làm: " + shift);
    	lateHours.setText("Tổng số giờ đi muộn: " + late);
    	earlyHours.setText("Tổng số giờ về sớm: " + early);
    }
    
    private void setInfoQuarter() {
    	int shift=0;
    	float late=0f;
    	float early=0f;
    	for (Officer officer : unit.getOfficers()) {
			shift+=Integer.parseInt(officer.getOfficerWorksDetail().getTotalShiftByQuarter(currentTime.getText()));
			late+=Float.parseFloat(officer.getOfficerWorksDetail().getLateHoursByQuarter(currentTime.getText()));
			early+=Float.parseFloat(officer.getOfficerWorksDetail().getEarlyHoursByQuarter(currentTime.getText()));
		}
    	shiftCount.setText("Tổng số buổi đi làm: " + shift);
    	lateHours.setText("Tổng số giờ đi muộn: " + late);
    	earlyHours.setText("Tổng số giờ về sớm: " + early);
    }
    
    public void setDataToTable(ArrayList<Officer> officers) {
    	ObservableList<Officer> data = FXCollections.observableArrayList();
    	idCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("id"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("name"));
    	data.addAll(officers);
    	table.setItems(data);
    	table.setOnMouseClicked(event -> {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount()==2) {
				Officer selectedItem = table.getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/overviewTab/OverviewTab.fxml"));
					loader.setController(new OverviewTabController(stage,selectedItem));
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
    
    public UnitTabController(Stage stage, Unit unit) {
    	this.stage=stage;
    	this.user=API.GET_USER();
    	this.unit=unit;
    }
    
    public void setSearchFunction() {
    	searchField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String text) {
				if(text.length()!=0) {
					if(text.charAt(0)!= ' ') {
						ObservableList<Officer> list = FXCollections.observableArrayList();
						for (Officer officer : unit.getOfficers()) {
							if(officer.getName().toUpperCase().contains(text.toUpperCase())||officer.getId().toUpperCase().contains(text.toUpperCase())) {
								list.add(officer);
							}
						}
						table.setItems(list);
						
					}else searchField.setText("");
				}else setDataToTable(unit.getOfficers());
				
			}
    		
		});
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		unitId.setText(unit.getId());
		setDataToTable(unit.getOfficers());
		setSearchFunction();
		numberOfEmployee.setText("Số nhân viên: " + unit.getOfficers().size());
		monthClicked(null);
	}
    
}
