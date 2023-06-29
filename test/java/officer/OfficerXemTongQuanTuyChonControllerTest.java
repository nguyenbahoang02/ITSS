package officer;

import com.example.demo2.officer.GDOfficerXemTongQuanTuyChonController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OfficerXemTongQuanTuyChonControllerTest {
    GDOfficerXemTongQuanTuyChonController GD = new GDOfficerXemTongQuanTuyChonController();
    @Test
    void getMonthNumberTest(){
        Assertions.assertEquals(1,GD.getMonthNumber("1"));
        Assertions.assertEquals(2,GD.getMonthNumber("2"));
        Assertions.assertEquals(3,GD.getMonthNumber("3"));
        Assertions.assertEquals(4,GD.getMonthNumber("4"));
        Assertions.assertEquals(5,GD.getMonthNumber("5"));

    }
}
