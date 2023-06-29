package com.example.demo2.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Unit implements Comparable<Unit> {
    private String name;
    private String id;
    private int leaderId;
    private String role;
    private int employeeCount;
    private String leaderName;

    public Unit(String name, String id, int leaderId, String role, int employeeCount) {
        this.name = name;
        this.id = id;
        this.leaderId = leaderId;
        this.role = role;
        this.employeeCount = employeeCount;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Unit(int leaderId) {
        this.leaderId = leaderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    @Override
    public int compareTo(Unit other) {
        return Integer.compare(Integer.parseInt(this.getId()), Integer.parseInt(other.getId()));
    }
}
