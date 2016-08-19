package com.hx.template;

import org.junit.Test;

import java.io.File;
import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        double d = 0.15445634232323233234;
        String result = String .format("%.2f",d);
        System.out.println(result);
        assertEquals(4, 2 + 2);
    }
}