package com.terrazor.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {
    @Test
    public void testPassed(){
        sandbox.Point point1 = new sandbox.Point(7,0);
        sandbox.Point point2 = new sandbox.Point(70,0);
        Assert.assertEquals(point1.distance(point2), 63);
    }

    @Test
    public void testFailed(){
        sandbox.Point point1 = new sandbox.Point(0,2);
        sandbox.Point point2 = new sandbox.Point(0,5);
        Assert.assertEquals(point1.distance(point2), 1);
    }
    }
