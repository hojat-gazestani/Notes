

```bash
hdfs dfs -mkdir /user
hdfs dfs -mkdir /user/hojat
```

```bash
hdfs dfs -put /opt/java-files/GHCND_sample_csv.csv /user/hojat
hdfs dfs -ls /user/hojat/
Found 1 items
-rw-r--r--   3 root supergroup       2557 2026-05-24 09:32 /user/hojat/GHCND_sample_csv.csv
```

```bash
hadoop fs -copyFromLocal /opt/java-files/ghcnd-readme-file.txt /user/hojat

hdfs dfs -ls /user/hojat/
Found 2 items
-rw-r--r--   3 root supergroup       2557 2026-05-24 09:32 /user/hojat/GHCND_sample_csv.csv
-rw-r--r--   3 root supergroup      28568 2026-05-24 09:37 /user/hojat/ghcnd-readme-file.txt
```

```bash
hadoop fs -copyToLocal /user/hojat/ghcnd-readme-file.txt /home
ls /home/
ghcnd-readme-file.txt
```


