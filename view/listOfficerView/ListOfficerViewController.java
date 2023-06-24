package view.listOfficerView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Officer;
import view.listOfficerDetailView.ListOfficerDetailViewController;

public class ListOfficerViewController implements Initializable{
	private Stage stage;
	private ObservableList<Officer> data = FXCollections.observableArrayList(); 
	private Officer unitLeader = new Officer("Nguyễn Bá Hoàng", "20204976");

    @FXML
    private Circle avatar_circle;

    @FXML
    private Text unitLeaderId;

    @FXML
    private Text unitLeaderName;
    
    @FXML
    private TableView<Officer> table;
    
    @FXML
    private TableColumn<Officer, String> idCol;

    @FXML
    private TableColumn<Officer, Integer> indexCol;

    @FXML
    private TableColumn<Officer, String> nameCol;

    @FXML
    private VBox menu;
    
    @FXML
    private VBox infoVbox;
    
	public ListOfficerViewController(Stage stage) {
		super();
		this.stage = stage;
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
		data.add(new Officer("Nguyễn Bá Hoàng", "20204976"));
		data.add(new Officer("Nguyễn Bá Hoàng", "20204975"));
		data.add(new Officer("Nguyễn Bá Hoàng", "20204974"));
		data.add(new Officer("Nguyễn Bá Hoàng", "20204973"));
		data.add(new Officer("Nguyễn Bá Hoàng", "20204972"));
		data.add(new Officer("Nguyễn Bá Hoàng", "20204971"));
		indexCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(cellData.getValue()) + 1));
		idCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("name"));
		table.setItems(data);
		table.setStyle(":focused{    -fx-background-color: transparent;\r\n"
				+ "    -fx-background-insets: 0;\r\n"
				+ "    -fx-background-radius: 0;\r\n"
				+ "    -fx-border-color: transparent;\r\n"
				+ "    -fx-border-width: 0;\r\n"
				+ "    -fx-padding: 0;\r\n"
				+ "    -fx-text-fill: inherit;\r\n"
				+ "    -fx-focus-color: transparent;\r\n"
				+ "    -fx-faint-focus-color: transparent;}");
		table.setOnMouseClicked(event -> {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount()==2) {
				Officer selectedItem = table.getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listOfficerDetailView/ListOfficerDetailView.fxml"));
					loader.setController(new ListOfficerDetailViewController(selectedItem));
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
	
    
    
}
