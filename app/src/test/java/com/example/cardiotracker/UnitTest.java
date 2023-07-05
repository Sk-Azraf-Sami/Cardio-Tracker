package com.example.cardiotracker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitTest {


    @Test
    public void testAdd() {
        info  datalist = new info ("Sample Comment","80","80","120","05/07/2023","10:30:45");
        assertEquals("05/07/2023" ,datalist.getSystemDate());
        assertEquals("10:30:45" ,datalist.getSystemTime());
        assertEquals("120" ,datalist.getSysPressure());
        assertEquals("80" ,datalist.getDiaPressure());
        assertEquals("80" ,datalist.getHeartRate());
        assertEquals("Sample Comment" ,datalist.getComment());


    }

}