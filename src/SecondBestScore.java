package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Category C · Coding Assignment
 * Problem 7: Second-Best Score
 *
 * TASK:
 * A quiz app stores the scores of all players in an array. Prizes go to the top
 * two different scores, so the app needs the second-highest distinct score.
 * If every player has the same score, there is no second-highest score.
 *
 * Implement a function secondHighest(scores) that returns the second-highest
 * distinct score, or -1 if it does not exist.
 *
 * CONSTRAINTS:
 * • 1 <= length of scores <= 1000
 * • 0 <= scores[i] <= 100
 * • Do not sort the array
 *
 * EXAMPLES:
 * Sample 1:
 *   scores = [45, 78, 92, 78, 60]
 *   Expected Output: 78
 *
 * Sample 2:
 *   scores = [50, 50, 50]
 *   Expected Output: -1
 *
 * COMPLEXITY REQUIREMENTS:
 * • State the time and space complexity, and compare it with sorting the array first.
 *
 * ALGORITHM & ANALYSIS:
 * 1. Single-Pass Two-Variable Linear Scan:
 *    - Initialize highest = -1 and second = -1.
 *    - Traverse each score s in scores:
 *      * If s > highest:
 *          second = highest
 *          highest = s
 *      * Else if s < highest && s > second:
 *          second = s
 *      * Else (s == highest or s <= second):
 *          Skip s (handles duplicates and smaller elements).
 *    - Return second. If no distinct second element was ever assigned, it remains -1.
 *
 * 2. Time and Space Complexity:
 *    - Time Complexity: O(n) — exactly one pass through the n elements, with at most
 *      two constant-time O(1) comparisons per element.
 *    - Auxiliary Space: O(1) — strictly two integer tracking variables; no array copies
 *      or auxiliary collections created.
 *
 * 3. Comparison with Sorting:
 *    - Sorting First:
 *      * Time: O(n log n) using comparison sorts (Dual-Pivot QuickSort, MergeSort).
 *      * Space: O(n) or O(log n) extra memory; mutates the caller's array unless copied.
 *      * Constraint: Directly VIOLATES the explicit constraint "Do not sort the array".
 *      * Streaming: Sorting requires all elements to be buffered upfront in memory.
 *    - Single-Pass Linear Scan:
 *      * Time: O(n) — 10x faster for n = 1000 (1,000 ops vs ~10,000 ops).
 *      * Space: O(1) without touching/modifying the input.
 *      * Streaming: Can process elements on-the-fly in an online stream.
 */
public class SecondBestScore {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 1000;
    public static final int MIN_SCORE = 0;
    public static final int MAX_SCORE = 100;

    /**
     * Finds and returns the second-highest distinct score in the array.
     * Adheres strictly to the constraint: DO NOT SORT THE ARRAY.
     *
     * @param scores array of player scores (1 <= length <= 1000, 0 <= score <= 100)
     * @return the second-highest distinct score, or -1 if no distinct second score exists
     * @throws IllegalArgumentException if scores is null, empty, exceeds 1000 elements,
     *                                  or contains values outside [0, 100]
     */
    public static int secondHighest(int[] scores) {
        validateScores(scores);

        int highest = -1;
        int second = -1;

        for (int s : scores) {
            if (s > highest) {
                // Found a new strictly greater score: previous highest becomes second highest
                second = highest;
                highest = s;
            } else if (s < highest && s > second) {
                // Score is strictly lower than highest, but strictly higher than second highest
                second = s;
            }
            // If s == highest or s <= second, skip it as per the hint
        }

        return second;
    }

    /**
     * Validates that the input array satisfies all problem constraints.
     *
     * @param scores array to validate
     * @throws IllegalArgumentException on constraint violation
     */
    public static void validateScores(int[] scores) {
        if (scores == null || scores.length == 0) {
            throw new IllegalArgumentException("Scores array cannot be null or empty (length must be >= 1).");
        }
        if (scores.length > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Scores array length (%d) exceeds maximum allowed length (%d).",
                    scores.length, MAX_LENGTH)
            );
        }
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] < MIN_SCORE || scores[i] > MAX_SCORE) {
                throw new IllegalArgumentException(
                    String.format("Score at index %d has value %d, which is outside valid range [%d, %d].",
                        i, scores[i], MIN_SCORE, MAX_SCORE)
                );
            }
        }
    }

    /**
     * Robust parser for various input formats:
     * - "scores = [45, 78, 92, 78, 60]"
     * - "[45, 78, 92, 78, 60]"
     * - "45, 78, 92, 78, 60"
     * - "45 78 92 78 60"
     * Also strips comments like "// Expected Output: 78".
     *
     * @param rawLine user input string
     * @return parsed int[] array of scores
     * @throws IllegalArgumentException if parsing fails or no scores found
     */
    public static int[] parseScores(String rawLine) {
        if (rawLine == null || rawLine.trim().isEmpty()) {
            throw new IllegalArgumentException("Input line cannot be empty.");
        }

        // Strip inline comments starting with // or #
        String line = rawLine;
        int commentIdx = line.indexOf("//");
        if (commentIdx != -1) {
            line = line.substring(0, commentIdx);
        }
        commentIdx = line.indexOf("#");
        if (commentIdx != -1) {
            line = line.substring(0, commentIdx);
        }

        // Extract bracketed content if present, e.g. scores = [...]
        Pattern bracketPattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = bracketPattern.matcher(line);
        String scoreContent;
        if (matcher.find()) {
            scoreContent = matcher.group(1);
        } else {
            // Check if line has "scores ="
            if (line.contains("=")) {
                line = line.substring(line.indexOf("=") + 1);
            }
            scoreContent = line;
        }

        // Split by commas and/or whitespace
        String[] tokens = scoreContent.trim().split("[,\\s]+");
        List<Integer> list = new ArrayList<>();
        for (String token : tokens) {
            String trimmed = token.trim();
            if (!trimmed.isEmpty()) {
                try {
                    list.add(Integer.parseInt(trimmed));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid integer token in scores: '" + trimmed + "'");
                }
            }
        }

        if (list.isEmpty()) {
            throw new IllegalArgumentException("No scores found in input: " + rawLine);
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Formats an int[] array as a string [s1, s2, ..., sn].
     */
    public static String formatScores(int[] scores) {
        if (scores == null) return "null";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < scores.length; i++) {
            sb.append(scores[i]);
            if (i < scores.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Main method providing an interactive command-line interface.
     */
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("  Category C · Problem 7: Second-Best Score Calculator   ");
        System.out.println("=========================================================");
        System.out.println("Constraints: 1 <= length <= 1000, 0 <= scores[i] <= 100");
        System.out.println("Algorithm: O(n) Single-Pass Linear Scan (No Sorting)");
        System.out.println("---------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter score array (e.g. 'scores = [45, 78, 92, 78, 60]' or type 'exit'):");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            try {
                int[] scores = parseScores(line);
                int result = secondHighest(scores);
                System.out.println("Input Scores:    " + formatScores(scores));
                System.out.println("Expected Output: " + result);
                if (result == -1) {
                    System.out.println("Explanation:     No second-highest distinct score exists (all identical or single score).");
                } else {
                    System.out.println("Explanation:     " + result + " is the second-highest distinct score.");
                }
                System.out.println("---------------------------------------------------------");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println("---------------------------------------------------------");
            }
            System.out.println("Enter score array (or type 'exit'):");
        }
    }
}
