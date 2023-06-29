package com.example.demo2.Model.Worker;

public class WorkerTimeSheetQuarter {
    private int workerId;
    private int year;
    private int quarterCount;
    private double workHours;
    private double overtimeHours;

    public WorkerTimeSheetQuarter(int workerId, int year, int quarterCount, double workHours, double overtimeHours) {
        this.workerId = workerId;
        this.year = year;
        this.quarterCount = quarterCount;
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

    public int getQuarterCount() {
        return quarterCount;
    }

    public void setQuarterCount(int quarterCount) {
        this.quarterCount = quarterCount;
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
