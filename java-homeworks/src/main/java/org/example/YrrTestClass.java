package org.example;

public class YrrTestClass {

    public void testThisString(String testString) {
        try {
            testMethod(testString);
        } catch (YrrTestException e) {
            System.out.println(e);

        }
    }

    private void testMethod(String testString) throws YrrTestException {
        if (testString.equals("Yrr"))
            throw new YrrTestException("Yrr is that string");

        System.out.println(testString);
    }
}
