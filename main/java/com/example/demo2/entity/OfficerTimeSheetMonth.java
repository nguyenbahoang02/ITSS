package com.example.demo2.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class OfficerTimeSheetMonth implements Comparable<OfficerTimeSheetMonth> {
    private int year;
    private int month;
    private int shiftCount;
    private double lateSoonHours;


    public OfficerTimeSheetMonth(int year, int month, int shiftCount, double lateSoonHours) {
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

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    @Override
    public int compareTo(OfficerTimeSheetMonth other) {
        return Integer.compare(this.month, other.month);
    }
}
