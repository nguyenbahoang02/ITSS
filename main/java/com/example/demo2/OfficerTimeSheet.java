package com.example.demo2;

public class OfficerTimeSheet {
    private String date;
    private String morning;
    private String afternoon;
    private double lateHours;
    private double soonHours;

    public OfficerTimeSheet(String date, String morning, String afternoon, double lateHours, double soonHours) {
        this.date = date;
        this.morning = morning;
        this.afternoon = afternoon;
        this.lateHours = lateHours;
        this.soonHours = soonHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon;
    }

    public double getLateHours() {
        return lateHours;
    }

    public void setLateHours(double lateHours) {
        this.lateHours = lateHours;
    }

    public double getSoonHours() {
        return soonHours;
    }

    public void setSoonHours(double soonHours) {
        this.soonHours = soonHours;
    }
}
