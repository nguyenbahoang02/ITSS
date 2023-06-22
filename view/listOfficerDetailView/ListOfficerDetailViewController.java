package view.listOfficerDetailView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import view.Officer;
import view.TimeKeepingDetail;

public class ListOfficerDetailViewController implements Initializable{
	private Officer officer;
	private Officer unitLeader = new Officer("Nguyễn Bá Hoàng", "20204976");

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
    private Text officerId;

    @FXML
    private Text officerName;
    
    @FXML
    private TableView<TimeKeepingDetail> table;
    
    @FXML
    private TableColumn<TimeKeepingDetail, Integer> indexCol;
    
    @FXML
    private TableColumn<TimeKeepingDetail, Integer> yearCol;

    @FXML
    private TableColumn<TimeKeepingDetail, Integer> workHourCol;
    
    @FXML
    private TableColumn<TimeKeepingDetail, Integer> overtimeHourCol;

    @FXML
    private TableColumn<TimeKeepingDetail, Integer> dayoffCol;

    public ListOfficerDetailViewController(Officer officer) {
    	this.officer = officer;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		avatar_circle.setFill(Color.TRANSPARENT);
		unitLeaderId.setText(unitLeader.getId());
		unitLeaderName.setText(unitLeader.getName());
		menu.setStyle("-fx-border-color: #000000;"
				+ "-fx-border-width: 1;");
		infoVbox.setStyle("-fx-border-color: #000000;"
				+ "-fx-border-style: dotted;"
				+ "-fx-border-width: 0 0 1 0;"
				+ "-fx-alignment: center");
		table.setStyle(":focused{    -fx-background-color: transparent;\r\n"
				+ "    -fx-background-insets: 0;\r\n"
				+ "    -fx-background-radius: 0;\r\n"
				+ "    -fx-border-color: transparent;\r\n"
				+ "    -fx-border-width: 0;\r\n"
				+ "    -fx-padding: 0;\r\n"
				+ "    -fx-text-fill: inherit;\r\n"
				+ "    -fx-focus-color: transparent;\r\n"
				+ "    -fx-faint-focus-color: transparent;}");
		officerId.setText("Mã số nhân viên: " + officer.getId());
		officerName.setText("Họ và tên: " + officer.getName());
		
		
	}
	
    
    
}

