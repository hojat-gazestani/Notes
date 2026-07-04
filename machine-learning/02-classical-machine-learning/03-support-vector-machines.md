<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Support Vector Machine (SVM)](#support-vector-machine-svm)
  - [SVM classify high-dimensional data,](#svm-classify-high-dimensional-data)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Support Vector Machine (SVM)

SVM is superior at drawing classification boundary lines.

![svm](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/08-Logistic-Regression/Figure-9%3A-Logistic-regression-versus-SVM.png)

+ (A) minimizes the distance between all data points and the hyperplane.
+ (B) maximum distance between itself and the two clusters.
    **gray area that denotes margin:**  distance between the hyperplane and the nearest data point, 

same scatterplot with the inclusion of a new data point.

![new_data](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/08-Logistic-Regression/Figure-10%3A-A-new-data-point-is-added-to-the-scatterplot.png)

+ new circle, incorrectly on the left side of the logistic regression hyperplane  (designated for stars). 
+ remains correctly located on the right side of the SVM hyperplane (designated for circles) 

The following is  mitigating anomalies

![another](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/08-Logistic-Regression/Figure-11%3A-Mitigating-anomalies.png)

is less sensitive to such data points and actually minimizes their impact on the final location of the boundary line. 

+  SVM’s real strength is found in high-dimensional data and handling multiple features

## SVM classify high-dimensional data,

+ kernels

+ linear SVC
+ polynomial SVC
+ Kernel Trick

Kernel Trick is an advanced solution to map data from a low-dimensional to a high-dimensional space. 

Transitioning from a two-dimensional to a three-dimensional space allows you to use a linear plane to split the data within a 3-D space, as seen in Figure 12

![kernel-trick](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pics/08-Logistic-Regression/Figure-12%3A-Example-of-linear-SVC.png)


