package com.company;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void isaBoolean() {
        assertEquals(true,Main.isaBoolean("мм"));
    }

    @Test
    public void getDivide() {
        BigDecimal a = new BigDecimal("0.00015");
        BigDecimal b = Main.getDivide(BigDecimal.valueOf(150));
        assertEquals(a,b);
    }

    @Test
    public void getMultiply() {
        BigDecimal a = new BigDecimal("1000000");
        BigDecimal b = Main.getMultiply(BigDecimal.valueOf(1));
        assertEquals(a,b);
    }
}