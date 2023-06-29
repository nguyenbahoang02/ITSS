package com.example.demo2.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String unitId;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(int id, String name, String unitId) {
        this.id = id;
        this.name = name;
        this.unitId = unitId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }
}
