package view.exportTab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.API;
import view.Officer;
import view.OfficerTimesheet;
import view.Unit;
import view.detailTab.DetailTabController;
import view.editTab.EditTabController;
import view.homePage.HomePageController;
import view.importTab.ImportTabController;
import view.listUnitTab.ListUnitTabController;
import view.overviewTab.OverviewTabController;
import view.unitTab.UnitTabController;

public class ExportTabController implements Initializable{
	private String userName = "Nguyễn Bá Hoàng";
	private Stage stage;
	private Officer user;
	private ArrayList<Officer> officers = new ArrayList<>();
	
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
    private TableView<Officer> table;
    
    @FXML
    private TableColumn<Officer, String> nameCol;
    
    @FXML
    private TableColumn<Officer, String> idCol;
    
    @FXML
    private TableColumn<Officer, String> unitIdCol;
    
    @FXML
    private TextField searchField;
    
    public void setSearchFunction() {
    	searchField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String text) {
				if(text.length()!=0) {
					if(text.charAt(0)!= ' ') {
						ObservableList<Officer> list = FXCollections.observableArrayList();
						for (Officer officer : officers) {
							if(officer.getName().toUpperCase().contains(text.toUpperCase())||
									officer.getId().toUpperCase().contains(text.toUpperCase())||
									officer.getUnitId().toUpperCase().contains(text.toUpperCase())) {
								list.add(officer);
							}
						}
						table.setItems(list);
						
					}else searchField.setText("");
				}else setDataToTable(officers);
				
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
    
    public ExportTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.GET_USER();
    	for (Unit unit : API.GET_ALL_UNITS()) {
			officers.addAll(unit.getOfficers());
		}
    }
    
    public void setDataToTable(ArrayList<Officer> data) {
    	ObservableList<Officer> list = FXCollections.observableArrayList();
    	list.addAll(data);
    	
    	idCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("id"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("name"));
    	unitIdCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("unitId"));
    	table.setItems(list);
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
    
    public static void EXPORT(ArrayList<OfficerTimesheet> officerTimesheets, Stage stage, String id) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setTitle("Chọn thư mục để lưu file");
    	
    	File selectedDirectory = directoryChooser.showDialog(stage);
    	
    	if (selectedDirectory != null) {
    		TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Đặt tên cho file");
            dialog.setHeaderText("Nhập tên file");
            dialog.setContentText("Tên file: ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                String fileName = result.get();
                WRITE_EXCEL(officerTimesheets, selectedDirectory.getAbsolutePath() +"\\" + fileName + ".xlsx", id);

            } else {
            	Alert notification = new Alert(Alert.AlertType.INFORMATION);
    	        notification.initStyle(StageStyle.DECORATED);
    	        notification.setHeaderText(null);
    	        notification.setContentText("Không được bỏ trống");
    	        
    	        Stage stage1 = (Stage) notification.getDialogPane().getScene().getWindow();
    	        stage1.setAlwaysOnTop(true);
            }
    	}
    	
    }
    
    private static void WRITE_EXCEL(ArrayList<OfficerTimesheet> officerTimesheets, String excelFilePath, String id) {
    	Workbook workbook = new XSSFWorkbook();
    	
    	Sheet sheet = workbook.createSheet("Sheet");
    	
    	int rowIndex = 0;
    	
    	WRITE_HEADER(sheet, rowIndex);
    	
    	rowIndex++;
    	for(OfficerTimesheet officerTimesheet : officerTimesheets) {
    		
    		Row row = sheet.createRow(rowIndex);
    		
    		WRITE_DATA(officerTimesheet, row, id);
    		rowIndex++;
    	}
    	
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        AUTOSIZE_COLUMN(sheet, numberOfColumn);
 
        try {
			CREATE_OUTPUT_FILE(workbook, excelFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman"); 
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color
 
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }
    
    private static void WRITE_HEADER(Sheet sheet, int rowIndex) {
    	// create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
         
        // Create row
        Row row = sheet.createRow(rowIndex);
         
        // Create cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("OfficerId");
 
        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ngày");
 
        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Sáng");
 
        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Chiều");
 
        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Đi muộn");
        
        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Về sớm");
    }
    
    private static void WRITE_DATA(OfficerTimesheet officerTimesheet, Row row, String id){    
         
        Cell cell = row.createCell(0);
        cell.setCellValue(id);
 
        cell = row.createCell(1);
        cell.setCellValue(officerTimesheet.getDate());
 
        cell = row.createCell(2);
        cell.setCellValue(officerTimesheet.getMorning());
 
        cell = row.createCell(3);
        cell.setCellValue(officerTimesheet.getAfternoon());
        
        cell = row.createCell(4);
        cell.setCellValue(officerTimesheet.getLateHours());
        
        cell = row.createCell(5);
        cell.setCellValue(officerTimesheet.getSoonHours());
        
    }
    
    private static void AUTOSIZE_COLUMN(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
    
    private static void CREATE_OUTPUT_FILE(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		setDataToTable(officers);
		setSearchFunction();
	}
    
	
}
