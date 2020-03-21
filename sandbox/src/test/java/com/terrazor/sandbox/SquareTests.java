package com.terrazor.sandbox;

import org.testng.annotations.Test;

public class SquareTests {
    @Test
    public void testArea() {
        com.terrazor.sandbox.Square s = new Square(4);
        assert s.area() == 16;
    }

}
