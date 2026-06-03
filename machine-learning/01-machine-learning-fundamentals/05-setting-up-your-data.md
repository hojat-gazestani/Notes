# SETTING UP YOUR DATA

+ Training data
+ Testing data


Test your model with the same data that you used for training. 70/30 or 80/20.

It is vital to split your data by rows and not columns.

![Training and testing](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/01-foundations/pics/06-setting-up-data/Figure-1-Training-and-test-partitioning-.png)

Before spliting your data,randomize all rows in the dataset to  avoid bias in your model.

## Test model performance

A common approach to analyzing prediction accuracy is a measure called mean
absolute error,

hyperparameters: these settings control and impact how fast the model learns patterns and which patterns to identify and analyze.

## Cross Validation

Will model work in new data?

+ Too small dataset: construct an accurate model 
+ raining/test partition of data is not appropriate, this can lead to poor estimations of performance in the wild.

**Cross validation**

maximizes the availability of training data by splitting data into various combinations and testing each specific combination.

**exhaustive cross validation:** involves finding and testing all possible combinations to divide the original sample into a training set and a test set. 
**non-exhaustive cross validation, known as k-fold validation:** k assigned buckets and reserving one of those buckets to test the training model at each round. One bucket is then reserved as the test bucket and is used to measure and evaluate the performance of the remaining (k-1) buckets.

![cross valication](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/01-foundations/pics/06-setting-up-data/Figure-2-k-fold-validation.png)

### How Much Data Do I Need?

training dataset includes a full range of feature combinations.

At a minimum, a machine learning model should typically have ten times as many data points as the total number of features. So for a small dataset with three features, the training data should ideally have at least thirty rows. The other point to remember is that more relevant data is usually better than less.
