package officer;

import com.example.demo2.Model.Officer.OfficerTimeSheetMonth;
import com.example.demo2.officer.GDOfficerXemTongQuanController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.*;

public class OfficerXemTongQuanControllerTest {
        GDOfficerXemTongQuanController XemTongQuan = new GDOfficerXemTongQuanController();
        @Test
        void TaoTimeSheet(){
                ArrayList<OfficerTimeSheetMonth> checkList = new ArrayList<>();
                for (int i = 1; i <= 12; i++){
                        OfficerTimeSheetMonth data = new OfficerTimeSheetMonth(2022,i,0,0.0);
                        checkList.add(data);
                }
                List<OfficerTimeSheetMonth> officerTimeSheetMonths = XemTongQuan.createTimeSheetOfYear(2022);
                for (int i = 0; i < 12; i++){
                        Assertions.assertEquals(checkList.get(i).getYear(), officerTimeSheetMonths.get(i).getYear());
                        Assertions.assertEquals(checkList.get(i).getMonth(), officerTimeSheetMonths.get(i).getMonth());
                        Assertions.assertEquals(checkList.get(i).getShiftCount(), officerTimeSheetMonths.get(i).getShiftCount());
                        Assertions.assertEquals(checkList.get(i).getLateSoonHours(), officerTimeSheetMonths.get(i).getLateSoonHours());
                }
        }
}
