"""
Automated unit test suite for Category C · Problem 7: Second-Best Score.
Tests correctness against sample inputs, edge cases, boundaries, zero values,
duplicate values, large constraints (n = 1000), input parsing, and validation.
"""

import unittest
from second_best_score import (
    second_highest,
    parse_scores_line,
    format_scores,
)


class TestSecondBestScore(unittest.TestCase):

    def test_sample_1(self):
        scores = [45, 78, 92, 78, 60]
        self.assertEqual(second_highest(scores), 78)

    def test_sample_2(self):
        scores = [50, 50, 50]
        self.assertEqual(second_highest(scores), -1)

    def test_single_element(self):
        self.assertEqual(second_highest([100]), -1)
        self.assertEqual(second_highest([0]), -1)

    def test_two_elements_increasing(self):
        self.assertEqual(second_highest([45, 90]), 45)

    def test_two_elements_decreasing(self):
        self.assertEqual(second_highest([90, 45]), 45)

    def test_two_elements_equal(self):
        self.assertEqual(second_highest([70, 70]), -1)

    def test_duplicate_highest_at_beginning(self):
        self.assertEqual(second_highest([95, 95, 80, 70]), 80)

    def test_duplicate_highest_at_end(self):
        self.assertEqual(second_highest([70, 80, 95, 95]), 80)

    def test_duplicate_highest_scattered(self):
        self.assertEqual(second_highest([95, 70, 95, 80, 95]), 80)

    def test_duplicate_second_highest(self):
        self.assertEqual(second_highest([95, 80, 70, 80]), 80)

    def test_multiple_duplicate_second_highest(self):
        self.assertEqual(second_highest([90, 85, 85, 85, 40]), 85)

    def test_strictly_increasing(self):
        self.assertEqual(second_highest([10, 20, 30, 40, 50]), 40)

    def test_strictly_decreasing(self):
        self.assertEqual(second_highest([50, 40, 30, 20, 10]), 40)

    def test_zero_scores(self):
        self.assertEqual(second_highest([0, 5, 0]), 0)
        self.assertEqual(second_highest([0, 10, 20]), 10)
        self.assertEqual(second_highest([0, 0, 0]), -1)

    def test_boundary_values(self):
        self.assertEqual(second_highest([0, 100]), 0)
        self.assertEqual(second_highest([100, 100]), -1)

    def test_max_length_1000(self):
        scores = [i % 90 for i in range(998)] + [99, 100]
        self.assertEqual(second_highest(scores), 99)

    def test_max_length_1000_all_identical(self):
        scores = [88] * 1000
        self.assertEqual(second_highest(scores), -1)

    def test_parse_sample_1(self):
        line = "scores = [45, 78, 92, 78, 60]"
        parsed = parse_scores_line(line)
        self.assertEqual(parsed, [45, 78, 92, 78, 60])
        self.assertEqual(second_highest(parsed), 78)

    def test_parse_sample_2_with_comment(self):
        line = "scores = [50, 50, 50] // Expected Output: -1"
        parsed = parse_scores_line(line)
        self.assertEqual(parsed, [50, 50, 50])
        self.assertEqual(second_highest(parsed), -1)

    def test_parse_space_separated(self):
        line = "10 20 30 40 50"
        parsed = parse_scores_line(line)
        self.assertEqual(second_highest(parsed), 40)

    def test_format_scores(self):
        self.assertEqual(format_scores([45, 78, 92]), "[45, 78, 92]")

    def test_validation_exceptions(self):
        with self.assertRaises(ValueError):
            second_highest(None)
        with self.assertRaises(ValueError):
            second_highest([])
        with self.assertRaises(ValueError):
            second_highest([50, -1, 80])
        with self.assertRaises(ValueError):
            second_highest([50, 101, 80])
        with self.assertRaises(ValueError):
            second_highest([10] * 1001)
        with self.assertRaises(TypeError):
            second_highest([10, "90", 80])  # type: ignore


if __name__ == "__main__":
    unittest.main()
