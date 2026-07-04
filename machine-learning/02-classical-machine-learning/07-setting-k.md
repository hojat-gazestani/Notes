<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [What is k in clustering?](#what-is-k-in-clustering)
- [Elbow method](#elbow-method)
- [Step 1: Calculate WCSS for k = 1](#step-1-calculate-wcss-for-k--1)
  - [Compute the Centroid](#compute-the-centroid)
  - [Calculate Squared Distances](#calculate-squared-distances)
  - [Compute WCSS](#compute-wcss)
  - [Step 2: Calculate WCSS for k=2](#step-2-calculate-wcss-for-k2)
    - [Better clustering:](#better-clustering)
    - [Try k=2 optimally:](#try-k2-optimally)
    - [Compute WCSS:](#compute-wcss-1)
    - [Final Result:](#final-result)
  - [Step 2: Calculate WCSS for k=2](#step-2-calculate-wcss-for-k2-1)
    - [Cluster Assignment](#cluster-assignment)
    - [WCSS Calculation](#wcss-calculation)
    - [Final Result](#final-result-1)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# What is k in clustering?

**k** is the **number of clusters** (groups) you want to divide your data into.

+ k = 2 means splitting the 8 points into 2 groups.
+ k = 3 means splitting them into 3 groups.
+ k = 8 means each point is its own group.

You (the analyst) choose the value of k before running the algorithm (like k-means). The algorithm then finds the best way to group the data into that many clusters.

---

# Elbow method

To find the optimal number of clusters (k) using the elbow method, we need to calculate the Within-Cluster Sum of Squares (WCSS) for different values of k.

**What is WCSS?**

WCSS measures how **compact** or tight your clusters are.

It is calculated by:
1. Finding the **centroid** (center point) of each cluster.
2. For every point in a cluster, calculating its **squared distance** to that cluster's centroid.
3. Adding up all those squared distances across **all** clusters.

**Formula:**

```LaTeX
WCSS = Σᵢ₌₁ᵏ Σ_{x ∈ Cᵢ} distance(x, centroidᵢ)²
```


Where:
- k = number of clusters
- Cᵢ = cluster i
- x = a data point in that cluster

---


Here is the dataset

```text
(2, 10)

(2, 5)

(8, 4)

(5, 8)

(7, 5)

(6, 4)

(1, 2)

(4, 9)
```

Total points = 8

---
# Step 1: Calculate WCSS for k = 1

For **k = 1**, the single cluster center is the global centroid (mean of all points).

## Compute the Centroid

$$
\bar{x} = \frac{2+2+8+5+7+6+1+4}{8}
= \frac{35}{8}
= 4.375
$$

$$
\bar{y} = \frac{10+5+4+8+5+4+2+9}{8}
= \frac{47}{8}
= 5.875
$$

Centroid:

$$
(4.375,\; 5.875)
$$

## Calculate Squared Distances

| Point | Squared Distance | Distance |
|---------|---------|---------|
| (2, 10) | (2−4.375)² + (10−5.875)² = 5.64 + 17.02 | = **22.66** |
| (2, 5) | (−2.375)² + (−0.875)² = 5.64 + 0.77 | = **6.41** |
| (8, 4) | (3.625)² + (−1.875)² = 13.14 + 3.52 | = **16.66** |
| (5, 8) | (0.625)² + (2.125)² = 0.39 + 4.52 | = **4.91** |
| (7, 5) | (2.625)² + (−0.875)² = 6.89 + 0.77 | = **7.66** |
| (6, 4) | (1.625)² + (−1.875)² = 2.64 + 3.52 | = **6.16** |
| (1, 2) | (−3.375)² + (−3.875)² = 11.39 + 15.02 | = **26.41** |
| (4, 9) | (−0.375)² + (3.125)² = 0.14 + 9.77 | = **9.91** |

## Compute WCSS

$$
WCSS_1 =
22.66 + 6.41 + 16.66 + 4.91 + 7.66 + 6.16 + 26.41 + 9.91
$$

$$
WCSS_1 = 100.78
$$

**Result:**

$$
\boxed{WCSS_1 = 100.78}
$$

---

## Step 2: Calculate WCSS for k=2

We need to find the optimal 2 clusters. By inspection, natural groupings appear to be:

- Cluster A (left/lower): (2,10), (2,5), (1,2), (4,9) — but (4,9) might be closer to the right group? Let's check.

### Better clustering:

**Cluster 1 (Left side):** (2,10), (2,5), (1,2)  
→ Centroid = ((2+2+1)/3, (10+5+2)/3) = (5/3, 17/3) ≈ (1.67, 5.67)

**Cluster 2 (Right side):** (8,4), (5,8), (7,5), (6,4), (4,9)  
→ Centroid = ((8+5+7+6+4)/5, (4+8+5+4+9)/5) = (30/5, 30/5) = (6, 6)

But (4,9) is far from (6,6) — maybe better:

### Try k=2 optimally:

Using k-means (approximate):

**Cluster A:** (2,10), (5,8), (4,9)  
→ Centroid = (11/3, 27/3) = (3.67, 9)

**Cluster B:** (2,5), (8,4), (7,5), (6,4), (1,2)  
→ Centroid = (24/5, 20/5) = (4.8, 4)

### Compute WCSS:

**Cluster A:**

| Point | Distance² Calculation | Result |
|-------|----------------------|--------|
| (2,10) | (2-3.67)² + (10-9)² = (-1.67)² + 1² | 2.79 + 1 = **3.79** |
| (5,8) | (1.33)² + (-1)² | 1.77 + 1 = **2.77** |
| (4,9) | (0.33)² + 0² | **0.11** |

**Sum_A = 3.79 + 2.77 + 0.11 = 6.67**

**Cluster B:**

| Point | Distance² Calculation | Result |
|-------|----------------------|--------|
| (2,5) | (-2.8)² + 1² | 7.84 + 1 = **8.84** |
| (8,4) | (3.2)² + 0² | **10.24** |
| (7,5) | (2.2)² + 1² | 4.84 + 1 = **5.84** |
| (6,4) | (1.2)² + 0² | **1.44** |
| (1,2) | (-3.8)² + (-2)² | 14.44 + 4 = **18.44** |

**Sum_B = 8.84 + 10.24 + 5.84 + 1.44 + 18.44 = 44.80**

### Final Result:

**WCSS₂ = 6.67 + 44.80 = 51.47**

---

## Step 2: Calculate WCSS for k=2

### Cluster Assignment

**Cluster A:** (2,10), (5,8), (4,9)

$$
\text{Centroid}_A = \left(\frac{2+5+4}{3}, \frac{10+8+9}{3}\right) = (3.67, 9)
$$

**Cluster B:** (2,5), (8,4), (7,5), (6,4), (1,2)

$$
\text{Centroid}_B = \left(\frac{2+8+7+6+1}{5}, \frac{5+4+5+4+2}{5}\right) = (4.8, 4)
$$

### WCSS Calculation

**Cluster A:**

$$
\text{Sum}_A = (2-3.67)^2 + (10-9)^2 + (5-3.67)^2 + (8-9)^2 + (4-3.67)^2 + (9-9)^2
$$

$$
\text{Sum}_A = 3.79 + 2.77 + 0.11 = 6.67
$$

**Cluster B:**

$$
\text{Sum}_B = (2-4.8)^2 + (5-4)^2 + (8-4.8)^2 + (4-4)^2 + (7-4.8)^2 + (5-4)^2 + (6-4.8)^2 + (4-4)^2 + (1-4.8)^2 + (2-4)^2
$$

$$
\text{Sum}_B = 8.84 + 10.24 + 5.84 + 1.44 + 18.44 = 44.80
$$

### Final Result

$$
\text{WCSS}_2 = \text{Sum}_A + \text{Sum}_B = 6.67 + 44.80 = 51.47
$$
