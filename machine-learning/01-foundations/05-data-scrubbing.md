# DATA SCRUBBING
+ Feature Selection
+ Row Compression
+ One-hot Encoding
+ Binning
+ Missing Data

# DATA SCRUBBING
+ Refining your dataset to make it more workable.
+ Removing incomplete, incorrect formatted, irrelevant or duplicated data.
+ Converting text-based data to numerical values and the redesigning of feature.


## Feature Selection
+ being selective about the variables you select to design your model.
[image page 33]()
+ a language’s “Name in Spanish” will lead to any relevant insight.
+ “Countries” and “Country Code.” Including both holds duplicate information

+ reduce the number of features is to roll multiple features into one.
[first image page 34]()
+ merging similar features into fewer columns.
+ replace the eight product items with a lower number of categories or subtypes.
+ “Health Food,” “Apparel,” and “Digital.”
[second image page 34]()

## Row Compression
+ This can involve merging two or more rows into one
+ “Tiger” and “Lion” can be merged and renamed “Carnivore.”
[image page 35]()
+ it would be impossible to aggregate an animal with four legs and an animal with two legs!
+ We obviously can’t merge these two animals and set “three” as the aggregate number of legs.

## One-hot Encoding
+ for text-based features that can be converted into numbers
+ represented as “1” or “0”—“True” or “False.” A “0,”
[image page 37]()
+ column do not contain commas or spaces,
[image page 38]()

## Binning
+ convert numerical values into a category. variations irrelevant to the goals of your analysis
+ where the existence or non-existence of the variable is more influential than their specific measurements.
+ True/False feature or a categorical value such as “small,” “medium,” and “large.”

## Missing Data
+ Dealing with missing data
+ The mode represents the single most common variable value available in the dataset. This works best with categorical and binary variable types.
+ the median value, which adopts the value(s) located in the middle of the dataset.
[image page 40]()
