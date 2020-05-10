package com.terrazor.tests;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIPTests {

    @Test
    public void testMyIp() {
        String geoIPService = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("188.93.20.83");
        assertEquals(geoIPService, "<GeoIP><Country>RU</Country><State>CA</State></GeoIP>");    }
}
