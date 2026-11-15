# 알고리즘 학습 플랜

스타트업 면접에서 알고리즘은 **빅테크보다 낮은 난이도**를 요구한다.  
목표: LeetCode Medium을 40~50분 안에 안정적으로 풀 수 있는 수준.

<br>

## 학습 원칙

**패턴 암기가 아닌 패턴 인식**  
문제를 많이 푸는 것보다, 같은 패턴의 문제를 다양한 방식으로 접근하는 게 낫다.

**풀이 후 반드시 복기**  
- 더 나은 풀이는 없는가?
- 시간/공간 복잡도를 개선할 수 있는가?
- 비슷한 문제 유형이 무엇인가?

<br>

## 핵심 자료구조 & 알고리즘 패턴

### 1. 해시맵 (HashMap)
**언제**: O(1) 탐색이 필요할 때, 빈도 카운팅, 중복 검사

핵심 문제 유형:
- Two Sum 계열
- 아나그램 판별
- 빈도수 기반 문제

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Two Sum | Easy | https://leetcode.com/problems/two-sum/ |
| Group Anagrams | Medium | https://leetcode.com/problems/group-anagrams/ |
| Top K Frequent Elements | Medium | https://leetcode.com/problems/top-k-frequent-elements/ |
| Longest Consecutive Sequence | Medium | https://leetcode.com/problems/longest-consecutive-sequence/ |

```
패턴: 배열/문자열 문제에서 O(n²) → O(n)으로 줄이려면 해시맵을 쓴다.
```

---

### 2. 투 포인터 / 슬라이딩 윈도우
**언제**: 연속된 구간(subarray, substring) 문제

- **투 포인터**: 정렬된 배열에서 합/차 조건 탐색
- **슬라이딩 윈도우**: 고정/가변 크기 윈도우의 최대/최소

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Two Sum II (투 포인터) | Medium | https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/ |
| Container With Most Water | Medium | https://leetcode.com/problems/container-with-most-water/ |
| Longest Substring Without Repeating Characters | Medium | https://leetcode.com/problems/longest-substring-without-repeating-characters/ |
| Minimum Window Substring | Hard | https://leetcode.com/problems/minimum-window-substring/ |
| Maximum Average Subarray I (고정 윈도우) | Easy | https://leetcode.com/problems/maximum-average-subarray-i/ |

```
패턴: "연속된 ~" "부분 배열에서 ~" 키워드가 나오면 이 패턴 고려
```

---

### 3. 스택 / 큐
**언제**: 괄호 검증, 단조 스택, BFS

- **스택**: LIFO, 괄호/수식, 단조 증가/감소 스택
- **큐**: FIFO, BFS, 슬라이딩 윈도우 최대값(Deque)

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Valid Parentheses | Easy | https://leetcode.com/problems/valid-parentheses/ |
| Daily Temperatures (단조 스택) | Medium | https://leetcode.com/problems/daily-temperatures/ |
| Largest Rectangle in Histogram | Hard | https://leetcode.com/problems/largest-rectangle-in-histogram/ |
| Sliding Window Maximum (Deque) | Hard | https://leetcode.com/problems/sliding-window-maximum/ |

---

### 4. 트리 순회
**언제**: 트리 구조 탐색

- **DFS**: 재귀(전위/중위/후위), 경로 탐색
- **BFS**: 레벨 순회, 최단 거리(가중치 없는 그래프)

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Maximum Depth of Binary Tree | Easy | https://leetcode.com/problems/maximum-depth-of-binary-tree/ |
| Path Sum | Easy | https://leetcode.com/problems/path-sum/ |
| Binary Tree Level Order Traversal (BFS) | Medium | https://leetcode.com/problems/binary-tree-level-order-traversal/ |
| Binary Tree Right Side View | Medium | https://leetcode.com/problems/binary-tree-right-side-view/ |
| Lowest Common Ancestor of a Binary Tree | Medium | https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/ |
| Serialize and Deserialize Binary Tree | Hard | https://leetcode.com/problems/serialize-and-deserialize-binary-tree/ |

```java
// DFS 재귀 템플릿
void dfs(TreeNode node) {
    if (node == null) return;
    // 전위: 여기서 처리
    dfs(node.left);
    // 중위: 여기서 처리
    dfs(node.right);
    // 후위: 여기서 처리
}

// BFS 템플릿
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
while (!queue.isEmpty()) {
    int size = queue.size(); // 레벨 단위 처리 시
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

---

### 5. 이진 탐색 (Binary Search)
**언제**: 정렬된 배열 탐색, 최적값 탐색 (파라메트릭 서치)

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Binary Search | Easy | https://leetcode.com/problems/binary-search/ |
| Find Minimum in Rotated Sorted Array | Medium | https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/ |
| Search in Rotated Sorted Array | Medium | https://leetcode.com/problems/search-in-rotated-sorted-array/ |
| Koko Eating Bananas (파라메트릭) | Medium | https://leetcode.com/problems/koko-eating-bananas/ |
| Median of Two Sorted Arrays | Hard | https://leetcode.com/problems/median-of-two-sorted-arrays/ |

```
패턴: "최대/최솟값을 구하라" + "단조 조건" → 이진 탐색으로 탐색 공간 줄이기
```

---

### 6. 동적 프로그래밍 (DP)
**언제**: 최적 부분 구조 + 중복 부분 문제

핵심 유형:
- 1D DP: 피보나치, 계단 오르기, 도둑질
- 2D DP: 최장 공통 부분 수열(LCS), 배낭 문제
- DP + 문자열: 편집 거리, 팰린드롬

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Climbing Stairs (1D DP 입문) | Easy | https://leetcode.com/problems/climbing-stairs/ |
| House Robber | Medium | https://leetcode.com/problems/house-robber/ |
| Coin Change | Medium | https://leetcode.com/problems/coin-change/ |
| Partition Equal Subset Sum (배낭) | Medium | https://leetcode.com/problems/partition-equal-subset-sum/ |
| Longest Common Subsequence | Medium | https://leetcode.com/problems/longest-common-subsequence/ |
| Edit Distance | Medium | https://leetcode.com/problems/edit-distance/ |
| Longest Palindromic Substring | Medium | https://leetcode.com/problems/longest-palindromic-substring/ |

```
접근법:
1. 부분 문제 정의: dp[i]가 의미하는 것은?
2. 점화식 도출: dp[i] = f(dp[i-1], ...)
3. 초기값 설정
4. 순서 결정 (top-down vs bottom-up)
```

---

### 7. 그래프 탐색
**언제**: 연결 관계, 최단 경로, 사이클 감지

- **BFS**: 가중치 없는 최단 경로
- **Dijkstra**: 가중치 있는 최단 경로 (음수 간선 없음)
- **Union-Find**: 연결 요소, 사이클 감지
- **위상 정렬**: 의존성 순서 (빌드 순서, 선수 과목)

| 문제 | 난이도 | 링크 |
|------|--------|------|
| Number of Islands (BFS/DFS) | Medium | https://leetcode.com/problems/number-of-islands/ |
| Clone Graph | Medium | https://leetcode.com/problems/clone-graph/ |
| Course Schedule (위상 정렬) | Medium | https://leetcode.com/problems/course-schedule/ |
| Course Schedule II | Medium | https://leetcode.com/problems/course-schedule-ii/ |
| Network Delay Time (Dijkstra) | Medium | https://leetcode.com/problems/network-delay-time/ |
| Number of Connected Components (Union-Find) | Medium | https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/ |
| Word Ladder (BFS 최단경로) | Hard | https://leetcode.com/problems/word-ladder/ |

<br>

## 주차별 학습 계획

### 1~2주차: 기초 복기
| 주제 | 문제 수 | 목표 |
|------|---------|------|
| 배열 + 해시맵 | 10문제 | Easy 5분, Medium 20분 |
| 문자열 | 5문제 | 슬라이딩 윈도우 익히기 |
| 스택/큐 | 5문제 | 패턴 인식 |

### 3~4주차: 트리 + 탐색
| 주제 | 문제 수 | 목표 |
|------|---------|------|
| 이진 트리 DFS/BFS | 10문제 | 재귀 템플릿 체화 |
| 이진 탐색 | 5문제 | 경계 조건 정확히 |
| 그래프 기초 | 5문제 | BFS/DFS 응용 |

### 5~6주차: DP + 고급
| 주제 | 문제 수 | 목표 |
|------|---------|------|
| 1D DP | 8문제 | 점화식 도출 연습 |
| 2D DP | 5문제 | LCS, 배낭 |
| 그래프 심화 | 5문제 | Dijkstra, Union-Find |

### 7주차~: 실전 모의
- 시간 제한(45분) 두고 LeetCode Medium 랜덤 풀기
- 틀린 문제 유형 집중 보강

<br>

## 스타트업 면접 빈출 유형

코딩 테스트 없이 **화이트보드/라이브 코딩**으로 보는 경우가 많다.

| 유형 | 빈도 | 비고 |
|------|------|------|
| 해시맵 활용 | ★★★★★ | 거의 항상 나옴 |
| 트리/그래프 BFS/DFS | ★★★★☆ | 실무 연관성 높음 |
| 문자열 처리 | ★★★★☆ | |
| 이진 탐색 | ★★★☆☆ | |
| DP | ★★★☆☆ | Medium 이하 수준 |
| 구현/시뮬레이션 | ★★★☆☆ | 실무 감각 평가 |

<br>

## 코딩 인터뷰 팁

1. **문제를 먼저 이해한다**: 바로 코딩하지 않고 예제로 확인
2. **Brute Force부터 시작**: 최적 풀이를 먼저 말하려 하지 않는다
3. **말하면서 풀기**: "지금 이렇게 생각하고 있습니다"를 계속 말한다
4. **엣지 케이스 언급**: 빈 배열, null, 음수, 오버플로우
5. **시간/공간 복잡도 스스로 분석**: 면접관이 묻기 전에 먼저 말한다

<br>

## 추천 플랫폼

- **LeetCode**: 기본, 회사별 문제 필터 활용
- **프로그래머스**: 국내 기업 코딩 테스트 준비에 최적
