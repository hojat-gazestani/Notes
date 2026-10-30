## Putting a file in the hdfs

I have 3 datanodes, then

```bash
cat /opt/hadoop/etc/hadoop/hdfs-site.xml
        <name>dfs.replication</name>
        <value>3</value>
```

When I put a new file to hdfs
```bash
hadoop@namenode:$ cat /tmp/file.txt
First line
Second line

hadoop@namenode:$ hdfs dfs -ls /user/hojat
Found 2 items
-rw-r--r--   3 hadoop supergroup         18 2026-10-29 11:18 /user/hojat/test.txt
drwxr-xr-x   - hadoop supergroup          0 2026-10-29 13:07 /user/hojat/wordcount

hadoop@namenode:$ hdfs dfs -put /tmp/file.txt /user/hojat/
hadoop@namenode:$ hdfs dfs -ls /user/hojat
Found 3 items
-rw-r--r--   3 hadoop supergroup         22 2026-10-29 15:46 /user/hojat/file.txt
-rw-r--r--   3 hadoop supergroup         18 2026-10-29 11:18 /user/hojat/test.txt
drwxr-xr-x   - hadoop supergroup          0 2026-10-29 13:07 /user/hojat/wordcount

```
I have three datanodes and the file on all of them

```bash
dps | grep data | awk '{print $14}'
datanode1
datanode2
datanode3

dps | grep data | awk '{print $14}' | xargs -I {} docker exec {} cat /data/hadoop/data/current/BP-1307039919-10.200.0.2-1793272215002/current/finalized/subdir0/subdir0/blk_1073741828
First line
Second lineFirst line
Second lineFirst line
Second line%
```

## reducing result 


