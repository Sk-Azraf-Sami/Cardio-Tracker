package com.example.cardiotracker;

public class info {
    private String comment;
    private String diaPressure;
    private String heartRate;
    private String sysPressure;
    private String systemDate;
    private String systemTime;

    public info() {
        // Default constructor required for Firebase

    }

    public info(String comment, String diaPressure, String heartRate, String sysPressure, String systemDate, String systemTime) {
        this.comment = comment;
        this.diaPressure = diaPressure;
        this.heartRate = heartRate;
        this.sysPressure = sysPressure;
        this.systemDate = systemDate;
        this.systemTime = systemTime;
    }

    public String getComment() {
        return comment;
    }

    public String getDiaPressure() {
        return diaPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getSysPressure() {
        return sysPressure;
    }

    public String getSystemDate() {
        return systemDate;
    }

    public String getSystemTime() {
        return systemTime;
    }
}

