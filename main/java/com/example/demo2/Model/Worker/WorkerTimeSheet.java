package com.example.demo2.Model.Worker;

import com.example.demo2.Model.Officer.OfficerTimeSheet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkerTimeSheet implements Comparable<WorkerTimeSheet> {
    private int workerId;
    private String date;
    private double workHoursShift1;
    private double workHoursShift2;
    private double workHoursShift3;

    public WorkerTimeSheet(int workerId, String date, double workHoursShift1, double workHoursShift2, double workHoursShift3) {
        this.workerId = workerId;
        this.date = date;
        this.workHoursShift1 = workHoursShift1;
        this.workHoursShift2 = workHoursShift2;
        this.workHoursShift3 = workHoursShift3;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWorkHoursShift1() {
        return workHoursShift1;
    }

    public void setWorkHoursShift1(double workHoursShift1) {
        this.workHoursShift1 = workHoursShift1;
    }

    public double getWorkHoursShift2() {
        return workHoursShift2;
    }

    public void setWorkHoursShift2(double workHoursShift2) {
        this.workHoursShift2 = workHoursShift2;
    }

    public double getWorkHoursShift3() {
        return workHoursShift3;
    }

    public void setWorkHoursShift3(double workHoursShift3) {
        this.workHoursShift3 = workHoursShift3;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    public int compareTo(WorkerTimeSheet other) {
        return parseDate(this.date).compareTo(parseDate(other.date));
    }

    private static Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
