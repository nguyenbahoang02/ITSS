package com.example.demo2.entity;

public class OfficerTimeSheetYear {
    private int year;
    private int shiftCount;
    private double lateSoonHours;

    public OfficerTimeSheetYear(int year, int shiftCount, double lateSoonHours) {
        this.year = year;
        this.shiftCount = shiftCount;
        this.lateSoonHours = lateSoonHours;
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
