package com.example.demo2.Model;

import com.example.demo2.Model.Officer.OfficerTimeSheet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request implements Comparable<Request>{
    private static int idClass = -1;
    private int id;
    private int senderId;
    private String dateSend;
    private String date;
    private String message;
    private String status; // "Đồng ý" "Từ chối", "Chưa duyệt"

    public Request(int senderId, String date, String message, String status, String dateSend) {
        this.senderId = senderId;
        this.date = date;
        this.message = message;
        this.status = status;
        this.dateSend = dateSend;
        this.id = Request.idClass + 1;
        Request.idClass++;
    }

    public int getId() {
        return id;
    }

    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public int compareTo(Request other) {
        return parseDate(this.dateSend).compareTo(parseDate(other.dateSend));
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
