

Build in namenode

```bash
dxcit namenode /bin/bash

cd /tmp/wordcount-build

ls
WordCount.java

# Compile the Java 
javac -cp "$(hadoop classpath)" -d . WordCount.java

# Compile will produce
ls *.class
'WordCount$SumReducer.class'  'WordCount$TokenizerMapper.class'   WordCount.class

# Package the compiled classes into a JAR
jar -cvf wordcount.jar *.class
added manifest
adding: WordCount$SumReducer.class(in = 1749) (out= 748)(deflated 57%)
adding: WordCount$TokenizerMapper.class(in = 1915) (out= 822)(deflated 57%)
adding: WordCount.class(in = 1670) (out= 937)(deflated 43%)

# Verify the JAR contains the classes
jar -tf wordcount.jar
META-INF/
META-INF/MANIFEST.MF
WordCount$SumReducer.class
WordCount$TokenizerMapper.class
WordCount.class
```

### Put test input

```bash
printf 'Hello Hadoop Hello World\nHadoop MapReduce World\n' > /tmp/input.txt

hdfs dfs -put -f /tmp/input.txt /user/hojat/wordcount/input/

hdfs dfs -cat /user/hojat/wordcount/input/input.txt
Hello Hadoop Hello World
Hadoop MapReduce World

```

## Run the MapReduce job

Remove the old output directory if it exists. Hadoop normally refuses to write to an existing output directory

```bash
hdfs dfs -rm -r -f /user/hojat/wordcount/output
```

Submit the job

```bash
hadoop jar /tmp/wordcount-build/wordcount.jar WordCount \
/user/hojat/wordcount/input \
/user/hojat/wordcount/output \
```

Inspect the result:

```bash
hdfs dfs -cat /user/hojat/wordcount/output/part-r-00000
hadoop	2
hello	2
mapreduce	1
world	2
```

And the result is available on every datanodes:
```bash
dps | grep data | awk '{print $14}'
datanode1
datanode2
datanode3

 dps | grep data | awk '{print $14}' | xargs -I {} docker exec {} cat /data/hadoop/data/current/BP-1307039919-10.200.0.2-1793272215002/current/finalized/subdir0/subdir0/blk_1073741829
hadoop	2
hello	2
mapreduce	1
world	2
hadoop	2
hello	2
mapreduce	1
world	2
hadoop	2
hello	2
mapreduce	1
world	2
```
