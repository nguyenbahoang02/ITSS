package com.example.demo2.entity;

public class OfficerTimeSheetCustom {
    private int monthStart;
    private int monthEnd;
    private int year;

    private int shiftCount;
    private double lateSoonHours;

    public OfficerTimeSheetCustom(int monthStart, int monthEnd, int year, int shiftCount, double lateSoonHours) {
        this.monthStart = monthStart;
        this.monthEnd = monthEnd;
        this.year = year;
        this.shiftCount = shiftCount;
        this.lateSoonHours = lateSoonHours;
    }

    public int getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(int monthStart) {
        this.monthStart = monthStart;
    }

    public int getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(int monthEnd) {
        this.monthEnd = monthEnd;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
}
