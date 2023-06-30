package com.example.demo2.Model.Officer;

public class UnitTimeSheetMonthOfficer implements Comparable<UnitTimeSheetMonthOfficer> {
    private int year;
    private int month;
    private int shiftCount;
    private double lateSoonHours;

    public UnitTimeSheetMonthOfficer(int year, int month, int shiftCount, double lateSoonHours) {
        this.year = year;
        this.month = month;
        this.shiftCount = shiftCount;
        this.lateSoonHours = lateSoonHours;
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

    public int getShiftCount() {
        return shiftCount;
    }

    public void setShiftCount(int shiftCount) {
        this.shiftCount = shiftCount;
    }

    public double getLateSoonHours() {
        return lateSoonHours;
    }

    public void setLateSoonHours(double lateSoonHours) {
        this.lateSoonHours = lateSoonHours;
    }
    @Override
    public int compareTo(UnitTimeSheetMonthOfficer other) {
        return Integer.compare(this.month, other.month);
    }
}
