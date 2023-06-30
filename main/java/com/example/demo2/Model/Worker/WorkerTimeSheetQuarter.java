package com.example.demo2.Model.Worker;

import com.example.demo2.Model.Officer.OfficerTimeSheetQuarter;

public class WorkerTimeSheetQuarter implements Comparable<WorkerTimeSheetQuarter> {
    private int year;
    private int quarterCount;
    private double workHours;
    private double overtimeHours;

    public WorkerTimeSheetQuarter( int year, int quarterCount, double workHours, double overtimeHours) {
        this.year = year;
        this.quarterCount = quarterCount;
        this.workHours = workHours;
        this.overtimeHours = overtimeHours;
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
    @Override
    public int compareTo(WorkerTimeSheetQuarter other) {
        return Integer.compare(this.quarterCount, other.quarterCount);
    }
}
