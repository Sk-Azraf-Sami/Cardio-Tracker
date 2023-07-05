package com.example.cardiotracker;
/**
 * The info class represents information related to health data.
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

    /**
     * Default constructor required for Firebase.
     */
    public info() {
        // Default constructor required for Firebase

    }

    /**
     * Constructs an info object with the specified information.
     *
     * @param comment       the comment related to the health data
     * @param diaPressure   the diastolic pressure value
     * @param heartRate     the heart rate value
     * @param sysPressure   the systolic pressure value
     * @param systemDate    the system date when the data was recorded
     * @param systemTime    the system time when the data was recorded
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
}

