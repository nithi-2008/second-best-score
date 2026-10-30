package tests;

import src.SecondBestScore;

import java.util.Arrays;

/**
 * Automated Unit Test Suite for Problem 7: Second-Best Score.
 * Validates correctness across sample cases, boundary conditions, duplicate scenarios,
 * zero values, large constraint limits, and input parsing / exception handling.
 */
public class SecondBestScoreTest {

    private static int totalPassed = 0;
    private static int totalFailed = 0;

    private static void assertEquals(String testName, int expected, int actual) {
        if (expected == actual) {
            System.out.println("  [PASS] " + testName + " => " + actual);
            totalPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Expected: " + expected + ", Got: " + actual);
            totalFailed++;
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            totalPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Condition was false");
            totalFailed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("  Running Test Suite: Second-Best Score (Problem 7)      ");
        System.out.println("=========================================================");

        testSample1();
        testSample2();
        testSingleElement();
        testSingleElementZero();
        testTwoElementsIncreasing();
        testTwoElementsDecreasing();
        testTwoElementsEqual();
        testDuplicateHighestAtBeginning();
        testDuplicateHighestAtEnd();
        testDuplicateHighestScattered();
        testDuplicateSecondHighest();
        testMultipleDuplicateSecondHighest();
        testStrictlyIncreasing();
        testStrictlyDecreasing();
        testZeroScoresWithPositive();
        testAllZeroScores();
        testBoundaryMinMaxScores();
        testMaxConstraint1000Elements();
        testMaxConstraint1000AllIdentical();
        testInputParsingSample1();
        testInputParsingSample2WithComment();
        testInputParsingSpaceSeparated();
        testValidationExceptions();

        System.out.println("---------------------------------------------------------");
        System.out.println("Test Results: " + totalPassed + " passed, " + totalFailed + " failed.");
        System.out.println("=========================================================");

        if (totalFailed > 0) {
            System.exit(1);
        }
    }

    private static void testSample1() {
        int[] scores = {45, 78, 92, 78, 60};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Sample 1: [45, 78, 92, 78, 60]", 78, result);
    }

    private static void testSample2() {
        int[] scores = {50, 50, 50};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Sample 2: [50, 50, 50]", -1, result);
    }

    private static void testSingleElement() {
        int[] scores = {100};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Single element: [100]", -1, result);
    }

    private static void testSingleElementZero() {
        int[] scores = {0};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Single element zero: [0]", -1, result);
    }

    private static void testTwoElementsIncreasing() {
        int[] scores = {45, 90};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Two elements increasing: [45, 90]", 45, result);
    }

    private static void testTwoElementsDecreasing() {
        int[] scores = {90, 45};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Two elements decreasing: [90, 45]", 45, result);
    }

    private static void testTwoElementsEqual() {
        int[] scores = {70, 70};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Two elements identical: [70, 70]", -1, result);
    }

    private static void testDuplicateHighestAtBeginning() {
        int[] scores = {95, 95, 80, 70};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Duplicate highest at start: [95, 95, 80, 70]", 80, result);
    }

    private static void testDuplicateHighestAtEnd() {
        int[] scores = {70, 80, 95, 95};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Duplicate highest at end: [70, 80, 95, 95]", 80, result);
    }

    private static void testDuplicateHighestScattered() {
        int[] scores = {95, 70, 95, 80, 95};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Duplicate highest scattered: [95, 70, 95, 80, 95]", 80, result);
    }

    private static void testDuplicateSecondHighest() {
        int[] scores = {95, 80, 70, 80};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Duplicate second highest: [95, 80, 70, 80]", 80, result);
    }

    private static void testMultipleDuplicateSecondHighest() {
        int[] scores = {90, 85, 85, 85, 40};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Multiple duplicate second highest: [90, 85, 85, 85, 40]", 85, result);
    }

    private static void testStrictlyIncreasing() {
        int[] scores = {10, 20, 30, 40, 50};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Strictly increasing: [10, 20, 30, 40, 50]", 40, result);
    }

    private static void testStrictlyDecreasing() {
        int[] scores = {50, 40, 30, 20, 10};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Strictly decreasing: [50, 40, 30, 20, 10]", 40, result);
    }

    private static void testZeroScoresWithPositive() {
        int[] scores = {0, 5, 0};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Zero scores with positive: [0, 5, 0]", 0, result);

        int[] scores2 = {0, 10, 20};
        int result2 = SecondBestScore.secondHighest(scores2);
        assertEquals("Zero scores ascending: [0, 10, 20]", 10, result2);
    }

    private static void testAllZeroScores() {
        int[] scores = {0, 0, 0, 0};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("All zero scores: [0, 0, 0, 0]", -1, result);
    }

    private static void testBoundaryMinMaxScores() {
        int[] scores = {0, 100};
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Boundary min and max: [0, 100]", 0, result);

        int[] scores2 = {100, 100};
        int result2 = SecondBestScore.secondHighest(scores2);
        assertEquals("Boundary identical max: [100, 100]", -1, result2);
    }

    private static void testMaxConstraint1000Elements() {
        int[] scores = new int[1000];
        // Populate with values 0 to 98, with one 99 and one 100
        for (int i = 0; i < 998; i++) {
            scores[i] = i % 90;
        }
        scores[998] = 99;
        scores[999] = 100;
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Max length 1000 with distinct top 2 [..., 99, 100]", 99, result);
    }

    private static void testMaxConstraint1000AllIdentical() {
        int[] scores = new int[1000];
        Arrays.fill(scores, 88);
        int result = SecondBestScore.secondHighest(scores);
        assertEquals("Max length 1000 with all identical scores (88)", -1, result);
    }

    private static void testInputParsingSample1() {
        String line = "scores = [45, 78, 92, 78, 60]";
        int[] parsed = SecondBestScore.parseScores(line);
        assertTrue("Parse Sample 1 length", parsed.length == 5);
        assertEquals("Parse Sample 1 result", 78, SecondBestScore.secondHighest(parsed));
    }

    private static void testInputParsingSample2WithComment() {
        String line = "scores = [50, 50, 50] // Expected Output: -1";
        int[] parsed = SecondBestScore.parseScores(line);
        assertTrue("Parse Sample 2 length", parsed.length == 3);
        assertEquals("Parse Sample 2 result", -1, SecondBestScore.secondHighest(parsed));
    }

    private static void testInputParsingSpaceSeparated() {
        String line = "10 20 30 40 50";
        int[] parsed = SecondBestScore.parseScores(line);
        assertEquals("Parse space-separated result", 40, SecondBestScore.secondHighest(parsed));
    }

    private static void testValidationExceptions() {
        // Null array
        try {
            SecondBestScore.secondHighest(null);
            assertTrue("Expected exception for null scores", false);
        } catch (IllegalArgumentException e) {
            assertTrue("Caught exception for null scores", true);
        }

        // Empty array
        try {
            SecondBestScore.secondHighest(new int[0]);
            assertTrue("Expected exception for empty scores", false);
        } catch (IllegalArgumentException e) {
            assertTrue("Caught exception for empty scores", true);
        }

        // Negative score
        try {
            SecondBestScore.secondHighest(new int[]{50, -1, 80});
            assertTrue("Expected exception for negative score", false);
        } catch (IllegalArgumentException e) {
            assertTrue("Caught exception for negative score", true);
        }

        // Score > 100
        try {
            SecondBestScore.secondHighest(new int[]{50, 101, 80});
            assertTrue("Expected exception for score > 100", false);
        } catch (IllegalArgumentException e) {
            assertTrue("Caught exception for score > 100", true);
        }

        // Array length > 1000
        try {
            int[] large = new int[1001];
            SecondBestScore.secondHighest(large);
            assertTrue("Expected exception for array length > 1000", false);
        } catch (IllegalArgumentException e) {
            assertTrue("Caught exception for array length > 1000", true);
        }
    }
}
