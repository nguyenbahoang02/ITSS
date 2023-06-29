package com.example.demo2.Model.Worker;

public class WorkerTimeSheetMonth {
    private int workerId;
    private int year;
    private int month;
    private double workHours;
    private double overtimeHours;

    public WorkerTimeSheetMonth(int workerId, int year, int month, double workHours, double overtimeHours) {
        this.workerId = workerId;
        this.year = year;
        this.month = month;
        this.workHours = workHours;
        this.overtimeHours = overtimeHours;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
}
