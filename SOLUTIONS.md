# Problem 7: Second-Best Score — Complete Solutions (QLA)

**Category:** Category C · Coding Assignment  
**Program:** STEP SEM-3 · CodInClub | Powered by BridgeLabz  
**Topic:** Arrays, Linear Scans, Order Statistics, Invariant Proofs & Complexity Analysis  

---

## Table of Contents
1. [Part A — Problem Statement & Specifications](#part-a--problem-statement--specifications)
2. [Part B — Mathematical Foundation & Algorithmic Design](#part-b--mathematical-foundation--algorithmic-design)
   - [Formal Definition of Distinct Order Statistics](#formal-definition-of-distinct-order-statistics)
   - [Algorithm: Single-Pass Two-Variable Linear Scan](#algorithm-single-pass-two-variable-linear-scan)
   - [Loop Invariant & Mathematical Correctness Proof](#loop-invariant--mathematical-correctness-proof)
   - [Alternative Approaches Evaluated](#alternative-approaches-evaluated)
3. [Part C — Complexity Analysis & Core Question Answer](#part-c--complexity-analysis--core-question-answer)
   - [Time and Space Complexity](#time-and-space-complexity)
   - [Exhaustive Comparison: Linear Scan vs. Sorting First](#exhaustive-comparison-linear-scan-vs-sorting-first)
   - [Quantitative Performance Comparison Table](#quantitative-performance-comparison-table)
4. [Part D — Step-by-Step Execution Traces](#part-d--step-by-step-execution-traces)
   - [Trace 1: Sample 1 `[45, 78, 92, 78, 60]`](#trace-1-sample-1-45-78-92-78-60)
   - [Trace 2: Sample 2 `[50, 50, 50]`](#trace-2-sample-2-50-50-50)
   - [Trace 3: Duplicate Highest Elements `[95, 95, 80]`](#trace-3-duplicate-highest-elements-95-95-80)
   - [Trace 4: Zeros as Valid Scores `[0, 5, 0]`](#trace-4-zeros-as-valid-scores-0-5-0)
5. [Part E — Edge Cases & Boundary Validation Matrix](#part-e--edge-cases--boundary-validation-matrix)
6. [Part F — Complete Source Implementations](#part-f--complete-source-implementations)
   - [Java Implementation](#java-implementation)
   - [Python Implementation](#python-implementation)
7. [Part G — Conceptual Q&A & Interview Discussion Points](#part-g--conceptual-qa--interview-discussion-points)

---

# Part A — Problem Statement & Specifications

### Problem Description
A quiz app stores the scores of all players in an array. Prizes go to the top two different scores, so the app needs the second-highest distinct score. If every player has the same score, there is no second-highest score.

Implement a function `secondHighest(scores)` that returns the second-highest distinct score, or `-1` if it does not exist.

### Function Signatures
- **Java:** `public static int secondHighest(int[] scores)`
- **Python:** `def second_highest(scores: List[int]) -> int`

### Constraints
- $1 \le \text{length of scores} \le 1000$
- $0 \le scores[i] \le 100$
- **Do not sort the array**

### Examples

#### Sample 1
- **Input:** `scores = [45, 78, 92, 78, 60]`
- **Output:** `78`
- **Explanation:** Distinct scores are $\{92, 78, 60, 45\}$. The maximum score is $92$. The second-highest distinct score is $78$. Duplicate values of $78$ belong to the same rank.

#### Sample 2
- **Input:** `scores = [50, 50, 50]`
- **Output:** `-1`
- **Explanation:** All scores in the array are equal to $50$. The set of distinct scores is $\{50\}$ with cardinality $1$. Because fewer than $2$ distinct scores exist, there is no second-highest distinct score; returns $-1$.

---

# Part B — Mathematical Foundation & Algorithmic Design

### Formal Definition of Distinct Order Statistics
Let $A = [s_0, s_1, \dots, s_{n-1}]$ be an array of $n$ integer scores with $s_i \in [0, 100]$.

Define the set of distinct elements present in $A$ as:
$$S = \{ s_i \mid 0 \le i < n \}$$

Let $m = |S|$ be the number of unique scores:
1. If $m < 2$, no second-highest distinct score exists. By definition, the function must return $-1$.
2. If $m \ge 2$, arrange the elements of $S$ in strictly descending order:
   $$u_1 > u_2 > \dots > u_m$$
   Here, $u_1 = \max(S)$ is the highest distinct score, and $u_2$ is the **second-highest distinct score**. The function must return $u_2$.

---

### Algorithm: Single-Pass Two-Variable Linear Scan

To satisfy the constraint **"Do not sort the array"** and achieve optimal efficiency, we maintain two tracking variables throughout a single pass:
- `highest`: stores the maximum distinct value encountered so far.
- `second`: stores the second-highest distinct value encountered so far.

Since all scores $s_i \ge 0$, we initialize both variables to a sentinel value `-1` ($ -1 < \min_{i} s_i $):
$$\text{highest} \gets -1, \quad \text{second} \gets -1$$

For each score $s$ in $A$:
1. **Case 1: $s > \text{highest}$**  
   A new strictly largest score has been discovered. The previous `highest` becomes the new candidate for `second`, and `highest` is updated to $s$:
   $$\text{second} \gets \text{highest}$$
   $$\text{highest} \gets s$$
2. **Case 2: $s < \text{highest}$ and $s > \text{second}$**  
   The score $s$ is strictly smaller than the current highest, but strictly larger than the current second highest. Therefore, update `second`:
   $$\text{second} \gets s$$
3. **Case 3: $s == \text{highest}$ or $s \le \text{second}$**  
   $s$ is either a duplicate of the maximum score or is strictly less than or equal to the second-highest score found so far. No updates are required; skip $s$.

After traversing all $n$ elements, the variable `second` holds $u_2$ if $m \ge 2$, or remains `-1` if $m < 2$.

---

### Loop Invariant & Mathematical Correctness Proof

#### Invariant Statement
At the start of iteration $k$ (where $0 \le k < n$), after inspecting prefix subarray $A[0 \dots k-1]$:
1. $\text{highest} = \max(\{A[i] \mid 0 \le i < k\})$ if $k > 0$, or $-1$ if $k = 0$.
2. $\text{second}$ is the second-highest distinct score in $\{A[i] \mid 0 \le i < k\}$, or $-1$ if fewer than two distinct scores exist in the prefix.

#### Proof by Mathematical Induction

- **Base Case ($k = 0$):**  
  The prefix is empty ($\emptyset$). $\text{highest} = -1$ and $\text{second} = -1$. The invariant holds vacuously.

- **Inductive Step ($k \to k + 1$):**  
  Assume the invariant holds for prefix $A[0 \dots k-1]$. Let the incoming element be $s = A[k]$.
  - If $s > \text{highest}$: Since $\text{highest}$ was the strictly largest distinct element in $A[0 \dots k-1]$, $s$ is strictly larger than all previous elements. The old `highest` is now the largest element strictly smaller than $s$. Thus, setting $\text{second} = \text{highest}$ and $\text{highest} = s$ preserves the invariant for $A[0 \dots k]$.
  - If $s < \text{highest}$ and $s > \text{second}$: $s$ cannot replace `highest` because $\text{highest} > s$. However, $s$ is strictly larger than all other distinct elements seen so far except `highest`. Setting $\text{second} = s$ ensures `second` correctly reflects the second-highest distinct element in $A[0 \dots k]$.
  - If $s = \text{highest}$: $s$ is a duplicate maximum. It does not introduce a new rank, so neither `highest` nor `second` should change.
  - If $s \le \text{second}$: $s$ is either a duplicate second score or strictly inferior to the top two distinct scores. Neither variable should change.

- **Termination ($k = n$):**  
  The loop terminates after $n$ iterations. The prefix is the entire array $A[0 \dots n-1]$. By induction, `second` equals the second-highest distinct score of $A$, or $-1$ if no such distinct score exists. $\blacksquare$

---

### Alternative Approaches Evaluated

1. **Two-Pass Linear Scan:**
   - *Pass 1:* Find $M = \max(A)$ in $\mathcal{O}(n)$.
   - *Pass 2:* Find $\max(\{s \in A \mid s < M\})$ in $\mathcal{O}(n)$.
   - *Evaluation:* Correct and $\mathcal{O}(n)$ time, but requires $2n$ comparisons instead of $n$, and cannot be used in single-pass streaming.
2. **Min-Heap of Size 2 + Hash Set:**
   - Track unique elements using a hash set, maintain the top 2 elements in a min-heap.
   - *Evaluation:* Correct, but incurs unnecessary dynamic memory overhead ($\mathcal{O}(n)$ auxiliary space) and hash collision overhead compared to two primitive variables.
3. **Sorting First:**
   - Sort array in descending order, find first element strictly smaller than $A[0]$.
   - *Evaluation:* Violates the explicit constraint, takes $\mathcal{O}(n \log n)$ time, and modifies/copies the array.

---

# Part C — Complexity Analysis & Core Question Answer

### Time and Space Complexity

- **Time Complexity:** $\mathcal{O}(n)$  
  The algorithm performs a single linear iteration over the $n$ elements of `scores`. In each step, at most two integer comparisons ($s > \text{highest}$, and $s < \text{highest} \land s > \text{second}$) are evaluated.  
  For $n = 1000$, total operations are at most $2000$ primitive CPU instructions, executing in less than $1$ microsecond ($< 0.001\text{ ms}$).

- **Auxiliary Space Complexity:** $\mathcal{O}(1)$  
  Only two integer registers (`highest` and `second`) and loop counter variables are utilized. No heap allocations, auxiliary arrays, or recursive stack frames are created.

---

### Exhaustive Comparison: Linear Scan vs. Sorting First

The assignment prompt specifically requires:
> *"State the time and space complexity, and compare it with sorting the array first."*

| Feature / Dimension | Two-Variable Linear Scan | Sorting First Approach |
| :--- | :--- | :--- |
| **Asymptotic Time Complexity** | $\mathbf{\mathcal{O}(n)}$ (Strictly Linear) | $\mathbf{\mathcal{O}(n \log n)}$ (Superlinear / Log-linear) |
| **Operation Count ($n = 1000$)** | $\mathbf{\le 1{,}000 \text{ comparisons}}$ | $\mathbf{\approx 10{,}000 \text{ comparisons}}$ ($10\times$ slower) |
| **Auxiliary Space Complexity** | $\mathbf{\mathcal{O}(1)}$ (Zero extra memory) | $\mathbf{\mathcal{O}(\log n)}$ to $\mathbf{\mathcal{O}(n)}$ (Stack or buffer memory) |
| **Input Mutability (Side Effects)** | **Pure / Non-destructive** (Array unchanged) | **Destructive** (In-place sort mutates array, or requires $\mathcal{O}(n)$ copy) |
| **Adherence to Problem Constraint** | **Fully Compliant** | **Direct Violation** ("Do not sort the array") |
| **Cache Locality & CPU Architecture** | **Optimal** (Pure sequential streaming read) | **Poor** (Repeated random partition swaps, cache eviction) |
| **Streaming / Online Compatibility** | **Yes** (Processes unbounded incoming score streams) | **No** (Requires full array in memory prior to sort) |

#### In-Depth Rationale:
1. **Asymptotic Efficiency:**
   Sorting spends excessive effort establishing a total order across all $n$ scores ($s_{(1)} \ge s_{(2)} \ge \dots \ge s_{(n)}$), even though the quiz app only requires the top two distinct scores ($s_{(1)}$ and $s_{(2)}$). Establishing a complete ordering requires $\Omega(n \log n)$ comparisons by the information-theoretic lower bound for comparison sorting, whereas partial selection of the 2nd order statistic requires only $\mathcal{O}(n)$ comparisons.
2. **Side Effects & Software Architecture:**
   Sorting the caller's array directly can introduce subtle bugs in caller code that assumes original player submission order is preserved. Avoiding sorting keeps the method side-effect-free without needing expensive defensive cloning.
3. **Branch Predictability and Hardware Prefetch:**
   In modern pipelined architectures, scanning an array linearly allows the CPU memory controller to prefetch cache lines with near 100% hit rate. QuickSort, by contrast, performs divergent branch jumps and non-sequential writes.

---

### Quantitative Performance Comparison Table

| Array Size $n$ | Single-Pass Linear Scan ($\approx n$ ops) | Sorting First ($\approx n \log_2 n$ ops) | Relative Speedup |
| :---: | :---: | :---: | :---: |
| $10$ | $10$ ops | $33$ ops | $3.3\times$ faster |
| $100$ | $100$ ops | $664$ ops | $6.6\times$ faster |
| **$1,000$ (Max Constraint)** | **$1,000$ ops** | **$9,966$ ops** | **$\approx 10\times$ faster** |
| $10^5$ | $100,000$ ops | $1,660,964$ ops | $\approx 16.6\times$ faster |
| $10^7$ | $10,000,000$ ops | $232,534,966$ ops | $\approx 23.3\times$ faster |

---

# Part D — Step-by-Step Execution Traces

### Trace 1: Sample 1 `[45, 78, 92, 78, 60]`
Initial State: `highest = -1`, `second = -1`

| Step | Index $i$ | Current Score $s$ | Condition Triggered | Action Taken | `highest` | `second` |
| :---: | :---: | :---: | :--- | :--- | :---: | :---: |
| Start | — | — | Initialization | None | `-1` | `-1` |
| 1 | 0 | 45 | $45 > -1$ ($s > \text{highest}$) | `second = -1; highest = 45;` | `45` | `-1` |
| 2 | 1 | 78 | $78 > 45$ ($s > \text{highest}$) | `second = 45; highest = 78;` | `78` | `45` |
| 3 | 2 | 92 | $92 > 78$ ($s > \text{highest}$) | `second = 78; highest = 92;` | `92` | `78` |
| 4 | 3 | 78 | $78 < 92 \land 78 \le 78$ | Duplicate second score; skip | `92` | `78` |
| 5 | 4 | 60 | $60 < 92 \land 60 \le 78$ | Smaller than second; skip | `92` | `78` |

**Final Output:** `second = 78` (Matches Expected Output: 78).

---

### Trace 2: Sample 2 `[50, 50, 50]`
Initial State: `highest = -1`, `second = -1`

| Step | Index $i$ | Current Score $s$ | Condition Triggered | Action Taken | `highest` | `second` |
| :---: | :---: | :---: | :--- | :--- | :---: | :---: |
| Start | — | — | Initialization | None | `-1` | `-1` |
| 1 | 0 | 50 | $50 > -1$ ($s > \text{highest}$) | `second = -1; highest = 50;` | `50` | `-1` |
| 2 | 1 | 50 | $50 == 50$ ($s == \text{highest}$) | Duplicate highest; skip | `50` | `-1` |
| 3 | 2 | 50 | $50 == 50$ ($s == \text{highest}$) | Duplicate highest; skip | `50` | `-1` |

**Final Output:** `second = -1` (Matches Expected Output: -1).

---

### Trace 3: Duplicate Highest Elements `[95, 95, 80]`
Initial State: `highest = -1`, `second = -1`

| Step | Index $i$ | Current Score $s$ | Condition Triggered | Action Taken | `highest` | `second` |
| :---: | :---: | :---: | :--- | :--- | :---: | :---: |
| 1 | 0 | 95 | $95 > -1$ | `second = -1; highest = 95;` | `95` | `-1` |
| 2 | 1 | 95 | $95 == 95$ | Equal to highest; skip | `95` | `-1` |
| 3 | 2 | 80 | $80 < 95 \land 80 > -1$ | `second = 80;` | `95` | `80` |

**Final Output:** `second = 80`. Notice that duplicate highest values do NOT overwrite `second` with `95`.

---

### Trace 4: Zeros as Valid Scores `[0, 5, 0]`
Initial State: `highest = -1`, `second = -1`

| Step | Index $i$ | Current Score $s$ | Condition Triggered | Action Taken | `highest` | `second` |
| :---: | :---: | :---: | :--- | :--- | :---: | :---: |
| 1 | 0 | 0 | $0 > -1$ | `second = -1; highest = 0;` | `0` | `-1` |
| 2 | 1 | 5 | $5 > 0$ | `second = 0; highest = 5;` | `5` | `0` |
| 3 | 2 | 0 | $0 < 5 \land 0 \le 0$ | Smaller or equal to second; skip | `5` | `0` |

**Final Output:** `second = 0`. Correctly recognizes `0` as the second-highest distinct score.

---

# Part E — Edge Cases & Boundary Validation Matrix

| Category | Input Scenario | Expected Output | Algorithmic Rationale |
| :--- | :--- | :---: | :--- |
| **Sample 1** | `[45, 78, 92, 78, 60]` | `78` | Standard case with duplicate intermediate scores. |
| **Sample 2** | `[50, 50, 50]` | `-1` | All elements identical; cardinality of distinct set is 1. |
| **Single Element** | `[100]` | `-1` | Length $n = 1$; impossible to have two distinct scores. |
| **Single Zero** | `[0]` | `-1` | Length $n = 1$ with minimum constraint boundary value. |
| **Two Elements (Ascending)** | `[45, 90]` | `45` | Minimum length with distinct scores. |
| **Two Elements (Descending)** | `[90, 45]` | `45` | Order independence verification. |
| **Two Elements (Equal)** | `[70, 70]` | `-1` | Minimum length with identical values. |
| **Duplicate Maximums** | `[95, 95, 80, 70]` | `80` | Duplicate top scores must not push each other into `second`. |
| **Duplicate Second Highest**| `[95, 80, 70, 80]` | `80` | Multiple instances of second highest score correctly ignored. |
| **Strictly Increasing** | `[10, 20, 30, 40, 50]` | `40` | `highest` and `second` update at every single element. |
| **Strictly Decreasing** | `[50, 40, 30, 20, 10]` | `40` | `highest` set on index 0, `second` set on index 1, rest skipped. |
| **Zero as Second Highest** | `[0, 5, 0]` | `0` | Verifies `0` is treated as valid score, not confused with unset. |
| **All Zeros** | `[0, 0, 0, 0]` | `-1` | All elements equal to constraint minimum $0$. |
| **Extreme Range [0, 100]** | `[0, 100]` | `0` | Absolute minimum and maximum boundary values together. |
| **Max Constraint ($n = 1000$)** | 1000 elements ($0 \dots 98, 99, 100$) | `99` | Upper constraint limit test. |
| **Invalid Input: Null/Empty** | `null` or `[]` | Exception | Triggers `IllegalArgumentException`. |
| **Invalid Input: Out of Range** | `[-5]` or `[105]` | Exception | Triggers constraint violation check ($0 \le score \le 100$). |

---

# Part F — Complete Source Implementations

### Java Implementation

```java
package src;

public class SecondBestScore {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 1000;
    public static final int MIN_SCORE = 0;
    public static final int MAX_SCORE = 100;

    /**
     * Finds and returns the second-highest distinct score in O(n) time and O(1) space.
     * Strictly complies with constraint: Do not sort the array.
     */
    public static int secondHighest(int[] scores) {
        validateScores(scores);

        int highest = -1;
        int second = -1;

        for (int s : scores) {
            if (s > highest) {
                second = highest;
                highest = s;
            } else if (s < highest && s > second) {
                second = s;
            }
        }

        return second;
    }

    public static void validateScores(int[] scores) {
        if (scores == null || scores.length == 0) {
            throw new IllegalArgumentException("Scores array cannot be null or empty.");
        }
        if (scores.length > MAX_LENGTH) {
            throw new IllegalArgumentException("Length exceeds 1000.");
        }
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] < MIN_SCORE || scores[i] > MAX_SCORE) {
                throw new IllegalArgumentException("Score out of bounds [0, 100]: " + scores[i]);
            }
        }
    }
}
```

### Python Implementation

```python
from typing import List

def second_highest(scores: List[int]) -> int:
    """
    Finds and returns the second-highest distinct score in O(n) time and O(1) space.
    Strictly complies with constraint: Do not sort the array.
    """
    if scores is None or len(scores) == 0:
        raise ValueError("Scores list cannot be None or empty.")
    if len(scores) > 1000:
        raise ValueError("Length exceeds 1000.")

    for s in scores:
        if not isinstance(s, int) or s < 0 or s > 100:
            raise ValueError(f"Invalid score: {s}")

    highest = -1
    second = -1

    for s in scores:
        if s > highest:
            second = highest
            highest = s
        elif s < highest and s > second:
            second = s

    return second
```

---

# Part G — Conceptual Q&A & Interview Discussion Points

#### Q1: Why initialize `highest` and `second` to `-1` instead of `Integer.MIN_VALUE`?
**Answer:** The constraint states $0 \le scores[i] \le 100$. Any value strictly less than $0$ serves as an unambiguous sentinel denoting "no valid score has been observed yet." Initializing to `-1` perfectly matches the return specification (return `-1` if no second-highest distinct score exists). If the score range allowed negative numbers, we would either initialize to `Integer.MIN_VALUE` or use an `Integer` wrapper / optional boolean flag.

#### Q2: Can this algorithm be generalized to find the $k$-th highest distinct score?
**Answer:**
- For small constant $k$ (e.g., $k = 3, 4$), we can maintain $k$ variables in $\mathcal{O}(k \cdot n) = \mathcal{O}(n)$ time and $\mathcal{O}(k) = \mathcal{O}(1)$ space.
- For arbitrary $k$, we can maintain a Min-Heap of size $k$ containing unique distinct elements along with a Hash Set in $\mathcal{O}(n \log k)$ time and $\mathcal{O}(k)$ space.
- Alternatively, the Quickselect algorithm finds the $k$-th order statistic in $\mathcal{O}(n)$ average time after deduplication, but mutates the array order unless copied.

#### Q3: How does the condition `s < highest && s > second` safeguard against duplicate maximums?
**Answer:** If an incoming score $s$ is equal to `highest`, the first condition `s > highest` evaluates to `false`. The second condition `s < highest && s > second` also evaluates to `false` because $s < \text{highest}$ fails. Therefore, duplicate instances of the highest score are automatically and silently ignored without corrupting `second`.
