package com.example.cardiotracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnitTest {


    @Test
    public void testAdd() {
        info  demo = new info ("Sample Comment","80","80","120","05/07/2023","10:30:45");
        assertEquals("05/07/2023" ,demo.getSystemDate());
        assertEquals("10:30:45" ,demo.getSystemTime());
        assertEquals("120" ,demo.getSysPressure());
        assertEquals("80" ,demo.getDiaPressure());
        assertEquals("80" ,demo.getHeartRate());
        assertEquals("Sample Comment" ,demo.getComment());
    }

    @Test
    public void testaddUserData()
    {
        info demo = new info("Sample Comment","80","80","120","05/07/2023","10:30:45");
        demo.addUserData(demo);
        assertEquals(1,demo.count());

        info demo2 = new info("Sample Comment 2","90","72","130","05/07/2023","10:30:45");
        demo.addUserData(demo2);
        assertEquals(2,demo.count());

        assertTrue(demo.getData().contains(demo));

    }


    @Test
    public void testdeleteUserData()
    {
        info demo = new info("Sample Comment","80","80","120","05/07/2023","10:30:45");
        demo.addUserData(demo);
        assertEquals(1,demo.getData().size());

        info demo2 = new info("Sample Comment 2","90","72","130","05/07/2023","10:30:45");
        demo.addUserData(demo2);
        assertEquals(2,demo.count());

        demo.deleteUserData(demo);
        assertEquals(1,demo.getData().size());
        assertFalse(demo.getData().contains(demo));

        demo.deleteUserData(demo2);
        assertEquals(0,demo.getData().size());
        assertFalse(demo.getData().contains(demo2));

    }

    @Test
    public void testdeleteUserDataException()
    {
        info demo = new info("Sample Comment","80","80","120","05/07/2023","10:30:45");
        demo.addUserData(demo);

        demo.deleteUserData(demo);
        assertThrows(IllegalArgumentException.class,() -> demo.deleteUserData(demo));
    }

}