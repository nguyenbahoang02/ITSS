package view.workerDetailView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Employee;
import view.TimeKeepingDetail;

public class WorkerDetailViewController implements Initializable{
	private Employee worker;
	private Employee unitLeader = new Employee("Nguyễn Bá Hoàng", "20204976", "VN03");
	private Stage stage;
	
    @FXML
    private Circle avatar_circle;

    @FXML
    private VBox infoVbox;
    
    @FXML
    private VBox menu;
    
    @FXML
    private Text unitLeaderId;

    @FXML
    private Text unitLeaderName;
    
    @FXML
    private Text workerId;

    @FXML
    private Text workerName;
    
    @FXML
    private TableView<TimeKeepingDetail> table;
    
    @FXML
    private TableColumn<TimeKeepingDetail, Integer> indexCol;
    
    @FXML
    private TableColumn<TimeKeepingDetail, Integer> timeCol;

    @FXML
    private TableColumn<TimeKeepingDetail, Integer> workHourCol;
    
    @FXML
    private TableColumn<TimeKeepingDetail, Integer> overtimeHourCol;

    @FXML
    private TableColumn<TimeKeepingDetail, Integer> dayoffCol;

    @FXML
    private Button monthOption;
    
    @FXML
    private Button quarterOption;
    
    @FXML
    private Button yearOption;
    
    @FXML
    private ChoiceBox<Integer> yearPicker;
    
    public WorkerDetailViewController(Employee officer,Stage stage) {
    	this.worker = officer;
    	this.stage = stage;
    }
        
    public void setOptionForYearPicker() {
    	ObservableList<Integer> items = yearPicker.getItems();
    	items.add(2023);
    	items.add(2022);
    	items.add(2021);
    	
    	yearPicker.setValue(2023);
    }
    
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		avatar_circle.setFill(Color.TRANSPARENT);
		unitLeaderId.setText(unitLeader.getId());
		unitLeaderName.setText(unitLeader.getName());
		
		workerId.setText("Mã số nhân viên: " + worker.getId());
		workerName.setText("Họ và tên: " + worker.getName());
		
		yearPicker.setVisible(false);
		
		quarterOption.setOnMouseClicked(event -> {
			timeCol.setText("Quý");
			yearPicker.setVisible(true);
			
		});
		monthOption.setOnMouseClicked(event -> {
			timeCol.setText("Tháng");
			yearPicker.setVisible(true);
			
		});
		yearOption.setOnMouseClicked(event -> {
			timeCol.setText("Năm");
			yearPicker.setVisible(false);
			
		});
		
		setOptionForYearPicker();
	}
	
    
    
}

