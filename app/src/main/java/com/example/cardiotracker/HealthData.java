package com.example.cardiotracker;

/**
 * The HealthData class represents a data model for health-related information, including the date, time, diastolic pressure,
 * systolic pressure, heart rate, and comment. It provides getter methods to access the individual data attributes.
 */
public class HealthData {
    private String date;
    private String time;
    private String diastolicPressure;
    private String systolicPressure;
    private String heartRate;
    private String comment;

    /**
     * Constructs a HealthData object with the provided data.
     *
     * @param date               the date of the health data
     * @param time               the time of the health data
     * @param diastolicPressure  the diastolic pressure value
     * @param systolicPressure   the systolic pressure value
     * @param heartRate          the heart rate value
     * @param comment            the comment associated with the health data
     */
    public HealthData(String date, String time, String diastolicPressure, String systolicPressure, String heartRate, String comment) {
        this.date = date;
        this.time = time;
        this.diastolicPressure = diastolicPressure;
        this.systolicPressure = systolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;
    }
    /**
     * Returns the date of the health data.
     *
     * @return the date of the health data
     */
    public String getDate() {
        return date;
    }
    /**
     * Returns the time of the health data.
     *
     * @return the time of the health data
     */
    public String getTime() {
        return time;
    }
    /**
     * Returns the diastolic pressure value.
     *
     * @return the diastolic pressure value
     */
    public String getDiastolicPressure() {
        return diastolicPressure;
    }
    /**
     * Returns the systolic pressure value.
     *
     * @return the systolic pressure value
     */
    public String getSystolicPressure() {
        return systolicPressure;
    }
    /**
     * Returns the heart rate value.
     *
     * @return the heart rate value
     */
    public String getHeartRate() {
        return heartRate;
    }
    /**
     * Returns the comment associated with the health data.
     *
     * @return the comment associated with the health data
     */
    public String getComment() {
        return comment;
    }
}
