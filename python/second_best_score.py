"""
Category C · Coding Assignment
Problem 7: Second-Best Score

TASK:
A quiz app stores the scores of all players in an array. Prizes go to the top two
different scores, so the app needs the second-highest distinct score.
If every player has the same score, there is no second-highest score.

Implement a function second_highest(scores) that returns the second-highest distinct score,
or -1 if it does not exist.

CONSTRAINTS:
• 1 <= length of scores <= 1000
• 0 <= scores[i] <= 100
• Do not sort the array

EXAMPLES:
Sample 1:
  scores = [45, 78, 92, 78, 60]
  Expected Output: 78

Sample 2:
  scores = [50, 50, 50]
  Expected Output: -1

COMPLEXITY REQUIREMENTS:
• State the time and space complexity, and compare it with sorting the array first.
  - Linear scan: O(n) time, O(1) auxiliary space.
  - Sorting: O(n log n) time, O(n) or O(log n) space, violates "Do not sort" constraint.
"""

from typing import List
import re
import sys


def second_highest(scores: List[int]) -> int:
    """
    Finds and returns the second-highest distinct score in the scores list.
    Strictly follows the constraint: DO NOT SORT THE ARRAY.

    :param scores: List of integer scores (1 <= len(scores) <= 1000, 0 <= scores[i] <= 100).
    :return: The second-highest distinct score, or -1 if none exists.
    :raises ValueError: If constraints are violated (None, empty, >1000 elements, scores < 0 or > 100).
    """
    if scores is None or len(scores) == 0:
        raise ValueError("Scores list cannot be None or empty.")
    if len(scores) > 1000:
        raise ValueError(f"Scores list length ({len(scores)}) exceeds maximum allowed (1000).")

    for idx, s in enumerate(scores):
        if not isinstance(s, int):
            raise TypeError(f"Score at index {idx} must be an integer, got {type(s).__name__}.")
        if s < 0 or s > 100:
            raise ValueError(f"Score at index {idx} has invalid value {s}; must be between 0 and 100.")

    highest = -1
    second = -1

    for s in scores:
        if s > highest:
            second = highest
            highest = s
        elif s < highest and s > second:
            second = s
        # If s == highest or s <= second, skip as per hint

    return second


def parse_scores_line(raw_line: str) -> List[int]:
    """
    Parses input lines formatted as:
    - scores = [45, 78, 92, 78, 60]
    - [45, 78, 92, 78, 60]
    - 45, 78, 92, 78, 60
    - 45 78 92 78 60
    """
    if not raw_line or not raw_line.strip():
        raise ValueError("Input line cannot be empty.")

    line = raw_line.strip()
    # Strip comments
    line = re.sub(r"//.*$", "", line)
    line = re.sub(r"#.*$", "", line)

    bracket_match = re.search(r"\[(.*?)\]", line)
    if bracket_match:
        content = bracket_match.group(1)
    else:
        if "=" in line:
            content = line.split("=", 1)[1]
        else:
            content = line

    tokens = [t.strip() for t in re.split(r"[, \t]+", content.strip()) if t.strip()]
    if not tokens:
        raise ValueError("No scores found in input.")

    return [int(t) for t in tokens]


def format_scores(scores: List[int]) -> str:
    """Formats score list as [s1, s2, ..., sn]."""
    return "[" + ", ".join(str(s) for s in scores) + "]"


def main() -> None:
    """Interactive command-line interface."""
    print("=========================================================")
    print("  Category C · Problem 7: Second-Best Score (Python)     ")
    print("=========================================================")
    print("Constraints: 1 <= length <= 1000, 0 <= scores[i] <= 100")
    print("Algorithm: O(n) Single-Pass Linear Scan (No Sorting)")
    print("---------------------------------------------------------")
    print("Enter score array (e.g. 'scores = [45, 78, 92, 78, 60]' or 'exit'):")

    for raw_line in sys.stdin:
        line = raw_line.strip()
        if not line:
            continue
        if line.lower() in ("exit", "quit"):
            print("Exiting. Goodbye!")
            break

        try:
            scores = parse_scores_line(line)
            res = second_highest(scores)
            print(f"Input Scores:    {format_scores(scores)}")
            print(f"Expected Output: {res}")
            if res == -1:
                print("Explanation:     No second-highest distinct score exists.")
            else:
                print(f"Explanation:     {res} is the second-highest distinct score.")
            print("---------------------------------------------------------")
        except Exception as e:
            print(f"Error: {e}")
            print("---------------------------------------------------------")
        print("Enter score array (or type 'exit'):")


if __name__ == "__main__":
    main()
