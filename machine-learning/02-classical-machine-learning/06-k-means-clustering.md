<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [k-Means Clustering](#k-means-clustering)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# k-Means Clustering

+ popular unsupervised learning algorithm

divide data into k discrete groups and is effective at uncovering basic data patterns.

Examples of potential groupings 
+ animal species
+ customers with similar features
+ housing market segmentation

The k-means clustering algorithm works by first splitting data into k number of clusters with k representing the number of clusters you wish to create.

3 -> 3 cluster
4 -> 5 cluster

![clustered_data](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-2-Comparison-of-original-data-and-clustered-data-using-k-means.png)


Steps:

1. Having data on a scatterplot

![scatterplot](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-5-Sample-data-points-are-plotted-on-a-scatterplot.png)

2. Examine the unclustered data and manually select a centroid for each k cluster.

![centroid](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-6-Two-data-points-are-nominated-as-centroids.png)

3. The centroid then forms the epicenter of an individual cluster. (can be manually and random)

![forms](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-7-Two-clusters-are-formed-after-calculating-the-Euclidean-distance-of-the-remaining-data-points-to-the-centroids.png)

The following shows the calculating euclidean distnance

![calculation](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-7-Two-clusters-are-formed-after-calculating-the-Euclidean-distance-of-the-remaining-data-points-to-the-centroids.png)

4. Each data point can be assigned to only one cluster and each cluster is discrete. 

+ no overlap between clusters and no case of nesting a cluster inside another cluster. 

+ Also, all data points assigned to a centroid irrespective of how they impact the final shape of the cluster.

![cluster](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-8-The-centroid-coordinates-for-each-cluster-are-updated-to-reflect-the-cluster%E2%80%99s-mean-value.-As-one-data-point-has-switched-from-the-right-cluster-to-the-left-cluster%2C-the-centroids-of-both-clusters-are-recalculated.png)


**elliptical or spherical shape**

![example](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-4-Example-of-an-ellipse-cluster.png)

Next, take the mean value of the data points in each cluster and plug in those x and y values to update your centroid coordinates. 

5. aggregate the mean value of all data points for each cluster, which can be found by calculating the average x and y values of all data points in that	cluster.

This will most likely result in a change to your centroids’ location. Your total number of clusters, however, will remain the same. You are not creating new clusters, rather updating their position on the scatterplot. Like musical chairs, the remaining data points will then rush to the closest centroid to form k number of clusters.

![shape](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/05-clustering-introduction/Figure-9-Two-final-clusters-are-produced-based-on-the-updated-centroids-for-each-cluster.png)
