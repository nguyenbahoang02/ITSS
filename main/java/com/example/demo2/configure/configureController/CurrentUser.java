package com.example.demo2.configure.configureController;

import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.entity.Employee;
import com.example.demo2.entity.Officer;
import com.example.demo2.entity.UnitLeaderOfficer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CurrentUser {
    public static Employee getCurrentEmployee () {
        try {
            List<Employee> employeeList = new ArrayList<>();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officer.json"));
            List<Officer> oficerList = new Gson().fromJson(reader, new TypeToken<List<Officer>>() {
            }.getType());
            Reader reader2 = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\unitLeaderOfficer.json"));
            List<UnitLeaderOfficer> unitLeaderOfficerList = new Gson().fromJson(reader2, new TypeToken<List<UnitLeaderOfficer>>() {
            }.getType());
            employeeList.addAll(oficerList);
            employeeList.addAll(unitLeaderOfficerList);
            for (Employee employee : employeeList) {
                if (employee.getId() == UserIdCurrent.getUserId()) {
                    return  employee;
                }
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
