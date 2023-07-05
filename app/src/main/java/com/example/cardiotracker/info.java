package com.example.cardiotracker;

import java.util.ArrayList;
import java.util.List;

public class info {
    private String comment;
    private String diaPressure;
    private String heartRate;
    private String sysPressure;
    private String systemDate;
    private String systemTime;
    private List<info> info_list = new ArrayList<>();
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

    public void addUserData(info data)
    {
        if(info_list.contains(data))
        {
            throw new IllegalArgumentException();
        }
        info_list.add(data);
    }
    public int count()
    {
        return info_list.size();
    }
    public List<info> getData()
    {
        List<info>datalist = info_list;
        return datalist;
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
    /**
     * Returns the system date when the data was recorded.
     *
     * @return the system date
     */
    public String getSystemDate() {
        return systemDate;
    }
    /**
     * Returns the system time when the data was recorded.
     *
     * @return the system time
     */
    public String getSystemTime() {
        return systemTime;
    }
}

