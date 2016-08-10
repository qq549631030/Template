package com.hx.template;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        File file = new File("d:\\1\\2.jpg");
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println(file.isDirectory());
        assertEquals(4, 2 + 2);
    }
}