package com.example.demo2.configure;

public class UnitIdTable {
    private static String unitId;

    public static String getUnitId() {
        return unitId;
    }

    public static void setUnitId(String unitId) {
        UnitIdTable.unitId = unitId;
    }
}
