package view.importTab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.API;
import view.Officer;
import view.OfficerTimesheet;
import view.Unit;
import view.detailTab.DetailTabController;
import view.editTab.EditTabController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.overviewTab.OverviewTabController;
import view.unitTab.UnitTabController;

public class ImportTabController implements Initializable{
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
    private Button editTab;

    @FXML
    private Button importTab;
    
    @FXML
    private Button exportTab;

    @FXML
    private MenuButton userSettings;
    
    public static ArrayList<OfficerTimesheet> readExcel(String excelFilePath) throws IOException{
    	ArrayList<OfficerTimesheet> list = new ArrayList<>();
    	
    	InputStream inputStream = new FileInputStream(new File(excelFilePath));
    	
    	Workbook workbook = new XSSFWorkbook(inputStream);
    	
    	Sheet sheet = workbook.getSheetAt(0);
    	
    	Iterator<Row> iterator = sheet.iterator();
    	while(iterator.hasNext()) {
    		Row nextRow = iterator.next();
    		if(nextRow.getRowNum() == 0) {
    			continue;
    		}
    		
    		Iterator<Cell> cellIterator = nextRow.cellIterator();
    		
    		OfficerTimesheet officerTimesheet = new OfficerTimesheet();
    		while(cellIterator.hasNext()) {
    			Cell cell = cellIterator.next();
    			Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                case 0:
                	officerTimesheet.setId(cellValue.toString().split("\\.")[0]);
                	break;
                case 1:
                    officerTimesheet.setDate(cellValue.toString());
                    break;
                case 2:
                    officerTimesheet.setMorning(cellValue.toString());
                    break;
                case 3:
                    officerTimesheet.setAfternoon(cellValue.toString());
                    break;
                case 4:
                    officerTimesheet.setLateHours(cellValue.toString());
                    break;
                case 5:
                    officerTimesheet.setSoonHours(cellValue.toString());
                    break;
                default:
                    break;
                }
    		}
    		list.add(officerTimesheet);
    	}
    	
    	return list;
    }
    
    @FXML
    void chooseFileClicked(MouseEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Chọn file");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); 
    	File selectedFile = fileChooser.showOpenDialog(stage);
    	if(selectedFile!=null) {
    		if(!selectedFile.getName().split("\\.")[1].toUpperCase().equals("XLSX")) {
    			Alert notification = new Alert(Alert.AlertType.INFORMATION);
    	        notification.initStyle(StageStyle.DECORATED);
    	        notification.setHeaderText(null);
    	        notification.setContentText("Hãy chọn đúng loại file");
    	        
    	        Stage stage = (Stage) notification.getDialogPane().getScene().getWindow();
    	        stage.setAlwaysOnTop(true);
    	        
    	        notification.showAndWait();
    		}else {
    			try {
    				if(!API.UNIT.importData(readExcel(selectedFile.getAbsolutePath()))) {
    					Alert notification = new Alert(Alert.AlertType.INFORMATION);
    	    	        notification.initStyle(StageStyle.DECORATED);
    	    	        notification.setHeaderText(null);
    	    	        notification.setContentText("Dữ liệu bị trùng");
    	    	        
    	    	        Stage stage = (Stage) notification.getDialogPane().getScene().getWindow();
    	    	        stage.setAlwaysOnTop(true);
    	    	        
    	    	        notification.showAndWait();
    				}   				
    				
    				
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
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
    
    public ImportTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.USER;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		
	}
	
	private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        if(cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	return dateFormat.format(cell.getDateCellValue());
        }
        Object cellValue = null;
        switch (cellType) {
        case BOOLEAN:
            cellValue = cell.getBooleanCellValue();
            break;
        case FORMULA:
            Workbook workbook = cell.getSheet().getWorkbook();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            cellValue = evaluator.evaluate(cell).getNumberValue();
            break;
        case NUMERIC:
            cellValue = cell.getNumericCellValue();
            break;
        case STRING:
            cellValue = cell.getStringCellValue();
            break;
        case _NONE:
        case BLANK:
        case ERROR:
            break;
        default:
            break;
        }
 
        return cellValue;
    }
    
}
