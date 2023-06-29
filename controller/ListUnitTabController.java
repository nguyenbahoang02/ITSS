package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import api.API;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Officer;
import model.Unit;

public class ListUnitTabController implements Initializable{
	private String userName = "Nguyễn Bá Hoàng";
	private Stage stage;
	private Officer user;
	
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
    private TableView<Unit> table;
    
    @FXML 
    private TableColumn<Unit, String> idCol;
    
    @FXML
    private TableColumn<Unit, String> nameCol;
    
    @FXML
    private TextField searchField;
    
    public void setDataToTable() {
    	ObservableList<Unit> data = FXCollections.observableArrayList();
    	idCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("id"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
    	data.addAll(API.GET_ALL_UNITS());
    	table.setItems(data);
    	table.setOnMouseClicked(event -> {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount()==2) {
				Unit selectedItem = table.getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/unitTab/UnitTab.fxml"));
					loader.setController(new UnitTabController(stage,selectedItem));
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
    
    public ListUnitTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.GET_USER();
    }
    
    
    public void setSearchFunction() {
    	searchField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String text) {
				if(text.length()!=0) {
					if(text.charAt(0)!= ' ') {
						ObservableList<Unit> list = FXCollections.observableArrayList();
						for (Unit unit : API.GET_ALL_UNITS()) {
							if(unit.getName().toUpperCase().contains(text.toUpperCase())||unit.getId().toUpperCase().contains(text.toUpperCase())) {
								list.add(unit);
							}
						}
						table.setItems(list);
						
					}else searchField.setText("");
				}else setDataToTable();
				
			}
    		
		});
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		unitId.setText("Danh sách đơn vị");
		setDataToTable();
		setSearchFunction();
		
	}
    
}
