package com.example.demo2.Model.Worker;

public class WorkerTimeSheetYear {
    private int workerId;
    private int year;
    private double workHours;
    private double overtimeHours;

    public WorkerTimeSheetYear(int workerId, int year, double workHours, double overtimeHours) {
        this.workerId = workerId;
        this.year = year;
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
