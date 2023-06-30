package com.example.demo2.fileJSON.fileController;

import com.example.demo2.Model.Worker.Worker;
import com.example.demo2.Model.Worker.WorkerTimeSheet;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.Model.*;
import com.example.demo2.Model.Officer.Officer;
import com.example.demo2.Model.Officer.OfficerTimeSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GetData {
    public static List<Unit> getUnitToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\unit.json"));
            List<Unit> unitList = new Gson().fromJson(reader, new TypeToken<List<Unit>>() {
            }.getType());
            List<Employee> employeeList = getEmployeeToFile();
            for (Unit u : unitList){
                for (Employee e : employeeList){
                    if (e.getUnitId() != null && e.getUnitId().equals(u.getId()))
                        u.setEmployeeCount(u.getEmployeeCount()+1);
                    if (e.getId() == u.getLeaderId()) u.setLeaderName(e.getName());
                }
            }
            reader.close();
            return unitList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<Employee> getEmployeeToFile() {
        try {
            List<Employee> employeeList = new ArrayList<>();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officer.json"));
            List<Officer> oficerList = new Gson().fromJson(reader, new TypeToken<List<Officer>>() {
            }.getType());
            Reader reader2 = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\unitLeaderOfficer.json"));
            List<UnitLeaderOfficer> unitLeaderOfficerList = new Gson().fromJson(reader2, new TypeToken<List<UnitLeaderOfficer>>() {
            }.getType());
            Reader reader3 = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\manager.json"));
            List<Manager> managerList = new Gson().fromJson(reader3, new TypeToken<List<Manager>>() {
            }.getType());
            Reader reader4 = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\worker.json"));
            List<Worker> workerList = new Gson().fromJson(reader4, new TypeToken<List<Worker>>() {
            }.getType());
            Reader readerUnitLeaderWorker = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\unitLeaderWorker.json"));
            List<UnitLeaderWorker> unitLeaderWorkerList = new Gson().fromJson(readerUnitLeaderWorker, new TypeToken<List<UnitLeaderWorker>>() {
            }.getType());
            employeeList.addAll(oficerList);
            employeeList.addAll(unitLeaderOfficerList);
            employeeList.addAll(managerList);
            employeeList.addAll(workerList);
            employeeList.addAll(unitLeaderWorkerList);
            reader.close();
            return employeeList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<OfficerTimeSheet> getOfficerTimeSheetToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officerTimeSheet.json"));
            List<OfficerTimeSheet> officerTimeSheetList = new Gson().fromJson(reader, new TypeToken<List<OfficerTimeSheet>>() {
            }.getType());
            List<OfficerTimeSheet> officerTimeSheetListFilter = new ArrayList<>();
            for (OfficerTimeSheet oneDay : officerTimeSheetList){
                if (oneDay.getOfficerId() == UserIdTable.getUserId()){
                    officerTimeSheetListFilter.add(oneDay);
                }
            }
            reader.close();
            return officerTimeSheetListFilter;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<OfficerTimeSheet> getAllOfficerTimeSheetToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officerTimeSheet.json"));
            List<OfficerTimeSheet> officerTimeSheetList = new Gson().fromJson(reader, new TypeToken<List<OfficerTimeSheet>>() {
            }.getType());
            reader.close();
            return officerTimeSheetList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<Request> getRequestToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\request.json"));
            List<Request> requestList = new Gson().fromJson(reader, new TypeToken<List<Request>>() {
            }.getType());
            reader.close();
            return requestList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<WorkerTimeSheet> getWorkerTimeShetToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\workerTimeSheet.json"));
            List<WorkerTimeSheet> workerTimeSheetList = new Gson().fromJson(reader, new TypeToken<List<WorkerTimeSheet>>() {
            }.getType());
            List<WorkerTimeSheet> workerTimeSheetListFilter = new ArrayList<>();
            for (WorkerTimeSheet oneDay : workerTimeSheetList){
                if (oneDay.getWorkerId() == UserIdTable.getUserId()){
                    workerTimeSheetListFilter.add(oneDay);
                }
            }
            reader.close();
            return workerTimeSheetListFilter;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<WorkerTimeSheet> getAllWorkerTimeSheetToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\workerTimeSheet.json"));
            List<WorkerTimeSheet> workerTimeSheetList = new Gson().fromJson(reader, new TypeToken<List<WorkerTimeSheet>>() {
            }.getType());
            reader.close();
            return workerTimeSheetList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
