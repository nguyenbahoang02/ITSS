package com.example.demo2.entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OfficerTimeSheet implements Comparable<OfficerTimeSheet> {
    private String date;
    private String morning;
    private String afternoon;
    private double lateHours;
    private double soonHours;
    private String startTime;
    private String endTime;
    private int officerId;

    public OfficerTimeSheet(String date, String morning, String afternoon, double lateHours, double soonHours, String startTime, String endTime, int officerId) {
        this.date = date;
        this.morning = morning;
        this.afternoon = afternoon;
        this.lateHours = lateHours;
        this.soonHours = soonHours;
        this.startTime = startTime;
        this.endTime = endTime;
        this.officerId = officerId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public void setSoonHours(Double soonHours) {
        this.soonHours = soonHours;
    }
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public int compareTo(OfficerTimeSheet other) {
        return parseDate(this.date).compareTo(parseDate(other.date));
    }

    private static Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
