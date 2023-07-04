package com.example.cardiotracker;


public class HealthData {
    private String date;
    private String time;
    private String diastolicPressure;
    private String systolicPressure;
    private String heartRate;
    private String comment;

    public HealthData(String date, String time, String diastolicPressure, String systolicPressure, String heartRate, String comment) {
        this.date = date;
        this.time = time;
        this.diastolicPressure = diastolicPressure;
        this.systolicPressure = systolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public String getSystolicPressure() {
        return systolicPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getComment() {
        return comment;
    }
}
