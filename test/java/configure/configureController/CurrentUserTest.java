package configure.configureController;

import com.example.demo2.Model.Employee;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.fileJSON.fileController.GetData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CurrentUserTest {

    @Test
    void getCurrentEmployeeTest(){
        List<Employee> employeeList = GetData.getEmployeeToFile();
        for (Employee employee : employeeList) {
            Assertions.assertEquals(employee.getId(),UserIdCurrent.getUserId());
        }
    }
}
