# Testing HDFS Java URLStreamHandler Interface


## Reading Data from a Hadoop URL (URLStreamHandler)

Test Java's ability to read from HDFS using URL.openStream() with `hdfs://` protocol.

[URLCat.java](https://github.com/hojat-gazestani/Notes/blob/main/hadoop/ch3-command-line-interface-java-interface/URLCat.java) Example

**Limitations:**
+ Can only read files (not list directories)
+ No access to file metadata (permissions, block locations)
+ Must register handler at most once per JVM (can cause issues in some containers)

### First test
```bash
docker exec -it namenode bash
cd /tmp
javac -cp $(hadoop classpath) URLCat.java
java -cp $(hadoop classpath):. URLCat hdfs://localhost:9000/user/hojat/test.txt
```

Or using  Using `hadoop jar`
```bash
docker exec -it namenode bash
javac -cp $(hadoop classpath) URLCat.java
jar cvf urlcat.jar URLCat.class
hadoop jar urlcat.jar URLCat hdfs://localhost:9000/user/hojat/test.txt
```

### Second test

```bash
echo "This is a test line" > local.txt
hdfs dfs -put local.txt /user/hojat/local.txt
hdfs dfs -ls /user/hojat
Found 2 items
-rw-r--r--   3 root supergroup         20 2026-05-21 18:18 /user/hojat/local.txt
-rw-r--r--   3 root supergroup         28 2026-05-21 17:03 /user/hojat/test.txt

java -cp $(hadoop classpath):. URLCat hdfs://localhost:9000/user/hojat/local.txt
This is a test line
```

##  Reading Data Using the FileSystem API (Recommended)

[FileSystemCat.java](https://github.com/hojat-gazestani/Notes/blob/main/hadoop/ch3-command-line-interface-java-interface/FileSystemCat.java)

```bash
root@4d9a04f527bd:/# cd /tmp/
root@4d9a04f527bd:/tmp# javac -cp $(hadoop classpath) FileSystemCat.java
root@4d9a04f527bd:/tmp# java -cp $(hadoop classpath):. FileSystemCat hdfs://localhost:9000/user/hojat/test.txt
Hello from Java HDFS client
```


| Feature | URL Interface | FileSystem API |
|---|---|---|
| Setup complexity | Simple (one-time static registration) | Moderate (need Configuration) |
| Directory listing | ❌ Not supported | ✅ Full support |
| File seeking | ❌ Limited | ✅ Full random access (`seek`) |
| File metadata | ❌ Not available | ✅ Permissions, size, blocks, replication |
| Wildcards / Glob | ❌ No | ✅ Yes (`globStatus`) |
| Create / Write files | ❌ Read-only | ✅ Full read/write |
| Delete / Move files | ❌ No | ✅ Full file operations |
| Performance | Good for sequential read | Excellent with seek and positioning |
| Best for | Simple file reading, legacy Java code | Production applications needing full HDFS access |
