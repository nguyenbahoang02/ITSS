package com.example.demo2.Model.Worker;

public class WorkerTimeSheetYear {
    private int year;
    private double workHours;
    private double overtimeHours;

    public WorkerTimeSheetYear(int year, double workHours, double overtimeHours) {
        this.year = year;
        this.workHours = workHours;
        this.overtimeHours = overtimeHours;
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
