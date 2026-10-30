

```bash
wget https://files.grouplens.org/datasets/movielens/ml-32m.zi

unzip ml-32m.zip
Archive:  ml-32m.zip
   creating: ml-32m/
  inflating: ml-32m/tags.csv
  inflating: ml-32m/links.csv
  inflating: ml-32m/README.txt
  inflating: ml-32m/checksums.txt
  inflating: ml-32m/ratings.csv
  inflating: ml-32m/movies.csv

docker cp ml-32m namenode:
Successfully copied 956MB (transferred 956MB) to namenode:


hadoop@namenode:~/ml-32m$ hdfs dfs -mkdir /user/hojat/movie/
hadoop@namenode:~/ml-32m$ hdfs dfs -mkdir /user/hojat/movie/input
hadoop@namenode:~/ml-32m$ hdfs dfs -put movies.csv /user/hojat/movie/input
hadoop@namenode:~/ml-32m$ hdfs dfs -put ratings.csv /user/hojat/movie/input
hadoop@namenode:~/ml-32m$ hdfs dfs -put tags.csv /user/hojat/movie/input
hadoop@namenode:~/ml-32m$ hdfs dfs -put links.csv /user/hojat/movie/input

hadoop@namenode:~/ml-32m$ hdfs dfs -ls /user/hojat/movie/input
Found 4 items
-rw-r--r--   3 hadoop supergroup    1950748 2026-10-30 09:10 /user/hojat/movie/input/links.csv
-rw-r--r--   3 hadoop supergroup    4242926 2026-10-30 09:08 /user/hojat/movie/input/movies.csv
-rw-r--r--   3 hadoop supergroup  877076222 2026-10-30 09:09 /user/hojat/movie/input/ratings.csv
-rw-r--r--   3 hadoop supergroup   72353890 2026-10-30 09:10 /user/hojat/movie/input/tags.csv




```


```bash
tail -n +2 ratings.csv > prepared/ratings_no_header.csv

split -l 100000 -d -a 5 prepared/ratings_no_header.csv split_100k/ratings_
split -l 500000 -d -a 5 prepared/ratings_no_header.csv split_500k/ratings_
split -l 1000000 -d -a 5 prepared/ratings_no_header.csv split_1m/ratings_
split -l 2000000 -d -a 5 prepared/ratings_no_header.csv split_2m/ratings_
split -l 4000000 -d -a 5 prepared/ratings_no_header.csv split_4m/ratings_


docker cp split_100k namenode:/home/hadoop
docker cp split_500k namenode:/home/hadoop
docker cp split_1m namenode:/home/hadoop
docker cp split_2m namenode:/home/hadoop
docker cp split_4m namenode:/home/hadoop

dxcit namenode /bin/bash

hadoop@namenode:~$ hdfs dfs -mkdir /user/hojat/movie/input/experiments
hadoop@namenode:~$ hdfs dfs -put split_100k /user/hojat/movie/input/experiments
hadoop@namenode:~$ hdfs dfs -put split_500k /user/hojat/movie/input/experiments
hadoop@namenode:~$ hdfs dfs -put split_1m /user/hojat/movie/input/experiments
hadoop@namenode:~$ hdfs dfs -put split_2m /user/hojat/movie/input/experiments
hadoop@namenode:~$ hdfs dfs -put split_4m /user/hojat/movie/input/experiments


```

## Design the MapReduce algorithm

calculate the average rating of every movie.


### Average rating per movie

The input:

```bash
userId,movieId,rating,timestamp
1,1,4.0,964982703
1,3,4.0,964981247
2,1,5.0,964982931
3,1,3.0,964982000
```

#### Mapper: extracts the movieId and rating.

```bash
(movieId=1, (rating=4.0, count=1))
(movieId=3, (rating=4.0, count=1))
(movieId=1, (rating=5.0, count=1))
(movieId=1, (rating=3.0, count=1))
```

#### Shuffle and sort

Hadoop groups the records by key:

```bash
movieId=1 -> [(4.0,1), (5.0,1), (3.0,1)]

movieId=3 -> [(4.0,1)]
```

#### Reducer

The reducer calculates:

Reducer
The reducer calculates:
\[
AverageRating = \frac{\sum Ratings}{Count}
\]

For movie 1:
\[
\frac{4+5+3}{3}=4.0
\]

Output:

```bash
movieId,averageRating,ratingCount
1,4.0,3
3,4.0,1
```

Notice that we also preserve the number of ratings. This will be useful later.


---

```bash
javac -classpath "$(hadoop classpath)" AvgRating.java

 ls
'AvgRating$CSVSplitMapper.class'  'AvgRating$DoubleAVGReducer.class'   AvgRating.class   AvgRating.java

jar cf avg_splitter.jar AvgRating*.class
ls avg_splitter.jar
avg_splitter.jar

hdfs dfs -ls /user/hojat/movie/input/experiments/split_1m

hadoop jar avg_splitter.jar AvgRating  /user/hojat/movie/input/experiments/split_1m /user/hojat/movie/output

hdfs dfs -ls /user/hojat/movie/output
Found 2 items
-rw-r--r--   3 hadoop supergroup          0 2026-10-30 17:08 /user/hojat/movie/output/_SUCCESS
-rw-r--r--   3 hadoop supergroup    1447653 2026-10-30 17:08 /user/hojat/movie/output/part-r-00000

hdfs dfs -cat /user/hojat/movie/output/part-r-00000
```


