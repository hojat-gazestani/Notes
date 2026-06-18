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
WCSS = Σᵢ₌₁ᵏ Σ_{x ∈ Cᵢ} distance(x, centroidᵢ)²



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




