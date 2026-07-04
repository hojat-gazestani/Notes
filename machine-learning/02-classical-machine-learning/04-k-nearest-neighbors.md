<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [k-nearest-neighbors](#k-nearest-neighbors)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# k-nearest-neighbors

+ The simplest clustering algorithm 
+ supervised learning technique
+ classify new data points based on the relationship to nearby data points.

k-NN is similar to a voting system or a popularity contest.

Among the five classmates, three are geeks, one is a skater, and one is a jock. According to k-NN, you would choose to hang out with the geeks based on their numerical advantage.

![k-nn](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/figure1-you-knn.png)

In the next example if you set k=3 the new data point will become Class B, and if you set k=7,the new data point will become Class A

![example-2](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-1-An-example-of-k-NN-clustering.png)


k, is crucial in determining the results. test numerous k combinations to find the best fit and avoid setting k too low or too high. 

Setting k to an uneven number will also help to eliminate the possibility of a statistical stalemate and invalid result.

The default number of neighbors is five when using Scikit-learn.

**Cons**

+ heavy burden on computing resources.
+ recommended for use with large datasets.
+ challenging to apply k-NN to high-dimensional data (3-D and 4-D)

descending dimension algorithm and Principle Component Analysis (PCA) or merging variables, is a common strategy to simplify and prepare a dataset for k-NN analysis.


