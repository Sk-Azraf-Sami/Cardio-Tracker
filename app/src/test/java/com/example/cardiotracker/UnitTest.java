package com.example.cardiotracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testaddUserData()
    {
        info datalist = new info("Sample Comment","80","80","120","05/07/2023","10:30:45");
        datalist.addUserData(datalist);
        assertEquals(1,datalist.count());

        info datalist2 = new info("Sample Comment","90","72","130","05/07/2023","10:30:45");
        datalist.addUserData(datalist2);
        assertEquals(2,datalist.count());

        assertTrue(datalist.getData().contains(datalist));
        assertTrue(datalist.getData().contains(datalist));
    }


}