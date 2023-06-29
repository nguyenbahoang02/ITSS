package com.example.demo2.configure;

public class RangeTimeView {
    private static int year;
    private static int monthStart;
    private static int monthEnd;

    public static int getYear() {
        return year;
    }

    public static void setYear(int year) {
        RangeTimeView.year = year;
    }

    public static int getMonthStart() {
        return monthStart;
    }

    public static void setMonthStart(int monthStart) {
        RangeTimeView.monthStart = monthStart;
    }

    public static int getMonthEnd() {
        return monthEnd;
    }

    public static void setMonthEnd(int monthEnd) {
        RangeTimeView.monthEnd = monthEnd;
    }
}
