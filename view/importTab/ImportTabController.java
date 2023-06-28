package view.importTab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.API;
import view.Officer;
import view.AttendanceRecord;
import view.detailTab.DetailTabController;
import view.exportTab.ExportTabController;
import view.homePage.HomePageController;
import view.listUnitTab.ListUnitTabController;
import view.overviewTab.OverviewTabController;

public class ImportTabController implements Initializable{
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
    private ImageView fortmatImg;

    @FXML
    private MenuButton userSettings;
    
    public static ArrayList<AttendanceRecord> readExcel(String excelFilePath) throws IOException{
    	ArrayList<AttendanceRecord> list = new ArrayList<>();
    	
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
    		
    		AttendanceRecord attendanceRecord = new AttendanceRecord();
    		while(cellIterator.hasNext()) {
    			Cell cell = cellIterator.next();
    			Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                case 0:
                	attendanceRecord.setId(cellValue.toString().split("\\.")[0]);
                	break;
                case 1:
                    attendanceRecord.setDate(cellValue.toString());
                    break;
                case 2:
                    attendanceRecord.setMorning(cellValue.toString());
                    break;
                case 3:
                    attendanceRecord.setAfternoon(cellValue.toString());
                    break;
                case 4:
                    attendanceRecord.setLateHours(cellValue.toString());
                    break;
                case 5:
                    attendanceRecord.setSoonHours(cellValue.toString());
                    break;
                default:
                    break;
                }
    		}
    		list.add(attendanceRecord);
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
    			showMsg("Hãy chọn đúng loại file");
    		}else {
    			try {
    				if(!API.IMPORT_DATA(readExcel(selectedFile.getAbsolutePath()))) {
    					showMsg("Dữ liệu bị trùng");
    				}else showMsg("Import thành công");  				
    				
    				
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
    
    public ImportTabController(Stage stage) {
    	this.stage=stage;
    	this.user=API.GET_USER();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userSettings.setText(userName);
		setTabSwitchinFunction();
		fortmatImg.setImage(new Image("/view/importTab/importFormat.PNG"));
		
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
    
	private void showMsg(String string) {
		Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.initStyle(StageStyle.DECORATED);
        notification.setHeaderText(null);
        notification.setContentText(string);
        
        Stage stage = (Stage) notification.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        
        notification.showAndWait();
	}
}
