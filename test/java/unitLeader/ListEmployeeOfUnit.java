package unitLeader;

import com.example.demo2.unitLeader.GDListEmployeeOfUnitController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ListEmployeeOfUnit {
    static GDListEmployeeOfUnitController GD = new GDListEmployeeOfUnitController();

    @BeforeAll
    static void runModule(){
        GD.setSidebarInformation();
    }
    @Test
    void setSidebarInformation(){
        Assertions.assertEquals("Người quản lý",GD.roleLabel.getText());
        Assertions.assertEquals("Trưởng đơn vị",GD.roleLabel.getText());
        Assertions.assertEquals("Công nhân",GD.roleLabel.getText());
        Assertions.assertEquals("Nhân viên văn phòng",GD.roleLabel.getText());
    }

}
