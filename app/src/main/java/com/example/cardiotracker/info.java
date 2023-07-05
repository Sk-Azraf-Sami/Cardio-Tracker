package com.example.cardiotracker;

import java.util.ArrayList;
import java.util.List;

/**
 * The `info` class represents information related to health data.
 * It encapsulates the comment, diastolic pressure, heart rate, systolic pressure,
 * system date, and system time of the health data.
 */
public class info {
    private String comment;
    private String diaPressure;
    private String heartRate;
    private String sysPressure;
    private String systemDate;
    private String systemTime;
    private List<info> info_list = new ArrayList<>();

    /**
     * Default constructor required for Firebase.
     */
    public info() {

    }

    /**
     * Constructs an `info` object with the specified information.
     *
     * @param comment     the comment related to the health data
     * @param diaPressure the diastolic pressure value
     * @param heartRate   the heart rate value
     * @param sysPressure the systolic pressure value
     * @param systemDate  the system date when the data was recorded
     * @param systemTime  the system time when the data was recorded
     */
    public info(String comment, String diaPressure, String heartRate, String sysPressure, String systemDate, String systemTime) {
        this.comment = comment;
        this.diaPressure = diaPressure;
        this.heartRate = heartRate;
        this.sysPressure = sysPressure;
        this.systemDate = systemDate;
        this.systemTime = systemTime;
    }

    /**
     * Returns the comment related to the health data.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * Returns the diastolic pressure value.
     *
     * @return the diastolic pressure
     */
    public String getDiaPressure() {
        return diaPressure;
    }
    /**
     * Returns the heart rate value.
     *
     * @return the heart rate
     */
    public String getHeartRate() {
        return heartRate;
    }
    /**
     * Returns the systolic pressure value.
     *
     * @return the systolic pressure
     */
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

    /**
     * Adds user data to the `info` object.
     *
     * @param data the user data to add
     * @throws IllegalArgumentException if the data is already present in the `info` object
     */
    public void addUserData(info data)
    {
        if(info_list.contains(data))
        {
            throw new IllegalArgumentException();
        }
        info_list.add(data);
    }
    /**
     * Returns the number of user data entries in the `info` object.
     *
     * @return the count of user data entries
     */
    public int count()
    {
        return info_list.size();
    }
    /**
     * Returns the list of user data stored in the `info` object.
     *
     * @return the list of user data
     */
    public List<info> getData()
    {
        List<info>datalist = info_list;
        return datalist;
    }
    /**
     Deletes the specified user data from the `info` object.
     *
     * @param data the user data to delete
     * @throws IllegalArgumentException if the data does not exist in the `info` object
     */
    public void deleteUserData(info data)
    {
        List<info> datalist = info_list;
        if(datalist.contains(data))
        {
            info_list.remove(data);
        }
        else {
            throw new IllegalArgumentException();
        }
    }


}

