package com.example.demo2.configure.configureController;

import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.Model.Employee;
import com.example.demo2.fileJSON.fileController.GetData;

import java.util.List;

public class CurrentUser {
    public static Employee getCurrentEmployee () {
        try {
            List<Employee> employeeList = GetData.getEmployeeToFile();
            for (Employee employee : employeeList) {
                if (employee.getId() == UserIdCurrent.getUserId()) {
                    return  employee;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
