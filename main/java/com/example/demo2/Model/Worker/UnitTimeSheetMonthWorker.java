package com.example.demo2.Model.Worker;

import com.example.demo2.Model.Officer.UnitTimeSheetMonthOfficer;

public class UnitTimeSheetMonthWorker implements Comparable<UnitTimeSheetMonthWorker> {
    private int year;
    private int month;
    private double workHours;
    private double overtimeHours;

    public UnitTimeSheetMonthWorker(int year, int month, double workHours, double overtimeHours) {
        this.year = year;
        this.month = month;
        this.workHours = workHours;
        this.overtimeHours = overtimeHours;
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
    @Override
    public int compareTo(UnitTimeSheetMonthWorker other) {
        return Integer.compare(this.month, other.month);
    }
}
