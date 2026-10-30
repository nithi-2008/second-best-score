# Problem 7: Second-Best Score

[![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.8%2B-3776AB?logo=python&logoColor=white)](https://www.python.org/)
[![Tests](https://img.shields.io/badge/Tests-53%20Passed-brightgreen)](tests/SecondBestScoreTest.java)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Complete, production-grade implementations, algorithmic proofs, complexity comparisons, and automated test suites for **Category C · Problem 7: Second-Best Score** (STEP SEM-3 · CodInClub | Powered by BridgeLabz).

---

## Repository Structure

```text
second-best-score/
├── .gitignore                          # Standard ignore rules for Java & Python artifacts
├── LICENSE                             # MIT Open Source License
├── README.md                           # Project documentation, problem summary & usage
├── SOLUTIONS.md                        # Complete QLA document, proofs, and complexity analysis
│
├── src/
│   └── SecondBestScore.java            # Core Java implementation & interactive CLI
├── tests/
│   └── SecondBestScoreTest.java        # Automated Java unit test suite (31 assertions)
│
└── python/
    ├── second_best_score.py            # Python reference implementation & CLI
    └── test_second_best_score.py       # Automated Python unit test suite (22 test cases)
```

---

## Problem Summary

A quiz app stores the scores of all players in an array. Prizes go to the top two different scores, so the app needs the second-highest distinct score. If every player has the same score, there is no second-highest score.

Implement a function `secondHighest(scores)` that returns the second-highest distinct score, or `-1` if it does not exist.

### Constraints
- $1 \le \text{length of scores} \le 1000$
- $0 \le scores[i] \le 100$
- **Do not sort the array**

### Examples

#### Sample 1
```text
scores = [45, 78, 92, 78, 60]

Expected Output: 78
```
*Explanation:* Distinct scores are $\{92, 78, 60, 45\}$. The highest is $92$, and the second-highest distinct score is $78$. Note that duplicate occurrences of $78$ do not create a separate rank.

#### Sample 2
```text
scores = [50, 50, 50]

Expected Output: -1
```
*Explanation:* Every player scored $50$. There is only one distinct score; hence, no second-highest distinct score exists, returning $-1$.

---

## Complexity Requirements & Prompt Answers

### 1. State the Time and Space Complexity

| Algorithm Approach | Time Complexity | Auxiliary Space | Respects "Do Not Sort"? | Max Operations ($n = 1000$) |
| :--- | :---: | :---: | :---: | :---: |
| **Two-Variable Single-Pass Linear Scan (Optimal)** | $\mathcal{O}(n)$ | $\mathcal{O}(1)$ | **Yes** | $\le 1{,}000$ ops ($< 1\,\mu\text{s}$) |
| **Sorting First (Dual-Pivot / QuickSort / TimSort)** | $\mathcal{O}(n \log n)$ | $\mathcal{O}(\log n)$ to $\mathcal{O}(n)$ | **No (Violates Rule)** | $\approx 10{,}000$ ops |
| **Two-Pass Max-Finding Approach** | $\mathcal{O}(n)$ | $\mathcal{O}(1)$ | **Yes** | $2n = 2{,}000$ ops |
| **Set Deduplication + Sorting** | $\mathcal{O}(n \log n)$ | $\mathcal{O}(n)$ | **No (Violates Rule)** | $> 10{,}000$ ops |

- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of `scores`. The algorithm scans the array exactly once from left to right, executing at most two constant-time comparisons per element.
- **Auxiliary Space:** $\mathcal{O}(1)$. The algorithm requires strictly two integer tracking variables (`highest` and `second`), operating entirely in-place without auxiliary arrays, sets, or recursive call stacks.

---

### 2. Comparison with Sorting the Array First

Sorting the array before extracting the second-highest distinct score has substantial algorithmic and architectural drawbacks:

1. **Direct Constraint Violation:**
   - The problem explicitly mandates: *"Do not sort the array"*. Sorting invalidates the solution in automated grading systems and coding assessments.

2. **Time Complexity Degrades from Linear to Superlinear:**
   - **Linear Scan:** Inspects each score once: $\mathcal{O}(n)$ time. For $n = 1000$, this executes $\le 1{,}000$ comparisons.
   - **Sorting:** General comparison sorts (Dual-Pivot QuickSort in Java, Timsort in Python) require $\mathcal{O}(n \log n)$ time. For $n = 1000$, $n \log_2 n \approx 1000 \times 10 = 10{,}000$ operations—an entire order of magnitude more expensive.

3. **Auxiliary Space & Mutation of Input:**
   - **Linear Scan:** Uses $\mathcal{O}(1)$ auxiliary space and preserves the caller's array unaltered.
   - **Sorting:** Modifies the caller's array in-place (which may produce race conditions or break caller expectations), or requires an $\mathcal{O}(n)$ memory allocation to clone the array before sorting. Furthermore, QuickSort uses $\mathcal{O}(\log n)$ recursive stack space, while MergeSort/TimSort requires $\mathcal{O}(n)$ auxiliary buffer space.

4. **Hardware Efficiency & Cache Locality:**
   - The single-pass scan reads contiguous memory sequentially, maximizing CPU L1 cache prefetching hits and eliminating branch penalties.
   - Sorting incurs cache misses, frequent pointer or index swaps, and data reorganization.

5. **Streaming / Online Computation:**
   - The two-variable linear scan supports continuous online processing: new scores can arrive dynamically in an unbounded real-time stream, maintaining `highest` and `second` in $\mathcal{O}(1)$ time per event without storing previous scores. Sorting requires all elements to be buffered upfront.

---

## How to Compile and Run

### Prerequisites
- **Java**: JDK 8 or higher
- **Python**: Python 3.8 or higher (`py` or `python`)

---

### Java Execution

#### 1. Compile Source and Tests
From the repository root (`second-best-score/`):
```bash
javac -d out src/SecondBestScore.java tests/SecondBestScoreTest.java
```

#### 2. Run the Automated Java Test Suite
```bash
java -cp out tests.SecondBestScoreTest
```

*Expected output:*
```text
=========================================================
  Running Test Suite: Second-Best Score (Problem 7)      
=========================================================
  [PASS] Sample 1: [45, 78, 92, 78, 60] => 78
  [PASS] Sample 2: [50, 50, 50] => -1
  ...
Test Results: 31 passed, 0 failed.
=========================================================
```

#### 3. Run the Interactive Java CLI
```bash
java -cp out src.SecondBestScore
```

*Interactive Example:*
```text
Enter score array (e.g. 'scores = [45, 78, 92, 78, 60]' or type 'exit'):
scores = [45, 78, 92, 78, 60]
Input Scores:    [45, 78, 92, 78, 60]
Expected Output: 78
Explanation:     78 is the second-highest distinct score.
---------------------------------------------------------
scores = [50, 50, 50]
Input Scores:    [50, 50, 50]
Expected Output: -1
Explanation:     No second-highest distinct score exists (all identical or single score).
```

---

### Python Execution

#### 1. Run Automated Python Tests
```bash
py -m unittest discover -s python -p "test_*.py"
```
*(or with `python` instead of `py` depending on system environment)*

*Expected output:*
```text
......................
----------------------------------------------------------------------
Ran 22 tests in 0.001s

OK
```

#### 2. Run Interactive Python CLI
```bash
py python/second_best_score.py
```

---

## Author
- **Nithi** ([GitHub: @nithi-2008](https://github.com/nithi-2008))
- **Course**: STEP SEM-3 · CodInClub | Powered by BridgeLabz
- **License**: MIT Open Source
