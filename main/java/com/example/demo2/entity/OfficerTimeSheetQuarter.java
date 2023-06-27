package com.example.demo2.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OfficerTimeSheetQuarter implements Comparable<OfficerTimeSheetQuarter> {
    private int year;
    private int quarterCount;
    private int shiftCount;
    private double lateSoonHours;

    public OfficerTimeSheetQuarter(int year, int quarterCount, int shiftCount, double lateSoonHours) {
        this.year = year;
        this.quarterCount = quarterCount;
        this.shiftCount = shiftCount;
        this.lateSoonHours = lateSoonHours;
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
    public int compareTo(OfficerTimeSheetQuarter other) {
        return Integer.compare(this.quarterCount, other.quarterCount);
    }
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
