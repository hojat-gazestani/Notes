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

### Seeking and Reading Specific Portions:

[FileSystemSeek.java](https://github.com/hojat-gazestani/Notes/blob/main/hadoop/ch3-command-line-interface-java-interface/FileSystemSeek.java) file
```bash
javac -cp $(hadoop classpath) FileSystemSeek.java
java -cp $(hadoop classpath):. FileSystemSeek hdfs://localhost:9000/data/gsom_sample_csv.csv
STATION,NAD,CLD
File length: 55
```
### Listing Directory Contents

[FileSystemList.java](https://github.com/hojat-gazestani/Notes/blob/main/hadoop/ch3-command-line-interface-java-interface/FileSystemList.java) file

```bash
root@4d9a04f527bd:/tmp# javac -cp $(hadoop classpath) FileSystemList.java
root@4d9a04f527bd:/tmp# java -cp $(hadoop classpath):. FileSystemList hdfs://localhost:9000/data/
hdfs://localhost:9000/data/gsom_sample_csv.csv	Size: 1917	Permissions: rw-r--r--	IsDir: false
```

### Reading Multiple Files with Wildcards

[FileSystemMultiFile.java]() file

```bash
root@4d9a04f527bd:/tmp# javac -cp $(hadoop classpath) FileSystemMultiFile.java
root@4d9a04f527bd:/tmp# java -cp $(hadoop classpath):. FileSystemMultiFile "hdfs://localhost:9000/data/*.csv"
File: hdfs://localhost:9000/data/gsom_sample_csv.csv
2026-05-21 19:06:39,027 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
STATION,NAME,LATITUDE,LONGITUDE,ELEVATION,DATE,CLDD,CLDD_ATTRIBUTES,HTDD,HTDD_ATTRIBUTES,DX32,DX32_ATTRIBUTES,DX70,DX70_ATTRIBUTES,DX90,DX90_ATTRIBUTES,DT00,DT00_ATTRIBUTES,DT32,DT32_ATTRIBUTES,DP01,DP01_ATTRIBUTES,DP05,DP05_ATTRIBUTES,DP10,DP10_ATTRIBUTES,EMXP,EMXP_ATTRIBUTES,EMSD,EMSD_ATTRIBUTES,EMSN,EMSN_ATTRIBUTES,DSND,DSND_ATTRIBUTES,DSNW,DSNW_ATTRIBUTES,PRCP,PRCP_ATTRIBUTES,SNOW,SNOW_ATTRIBUTES,CDSD,CDSD_ATTRIBUTES,EMXT,EMXT_ATTRIBUTES,EMNT,EMNT_ATTRIBUTES,HDSD,HDSD_ATTRIBUTES,TMAX,TMAX_ATTRIBUTES,TMIN,TMIN_ATTRIBUTES,AWND,AWND_ATTRIBUTES,WDF2,WDF2_ATTRIBUTES,WDF5,WDF5_ATTRIBUTES,WSF2,WSF2_ATTRIBUTES,WSF5,WSF5_ATTRIBUTES
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-01,0,",W",517,",W",3,",W",0,",W",0,",W",0,",W",28,",W",8,",W",2,",W",2,",W",1.53,",,W,22,",11.8,",,W,23,",13.4,",,W,22,",8,",W",1,",W",3.29,",,,W",13.8,",,,W",0,,61,",W,31,",11,",W,19,",1137,,45,",,,W",25,",,,W",8.1,",W",330,",W",330,",W",36,",W",47,",W"
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-02,0,",W",403,",W",4,",W",0,",W",0,",W",0,",W",17,",W",9,",W",5,",W",2,",W",2.15,",,W,03,",0,",T,W,26,+",0.1,",,W,13,",0,",W",0,",W",5.69,",,,W",0.1,",,,W",0,,67,",W,29,+",16,",W,10,",1540,,49,",,,W",31,",,,W",8.7,",W",190,",W",180,",W",38,",W",55,",W"
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-03,0,",W",204,",W",0,",W",12,",W",0,",W",0,",W",6,",W",10,",W",1,",W",0,",W",0.59,",,W,13,",0,",,W,31,+",0,",T,W,03,",0,",W",0,",W",1.56,",,,W",0,",T,,W",0,,80,",W,16,",26,",W,03,",1744,,65,",,,W",41,",,,W",7.8,",W",330,",W",330,",W",32,",W",40.9,",W"
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-04,9,",W",144,",W",0,",W",14,",W",0,",W",0,",W",2,",W",7,",W",2,",W",1,",W",1.07,",,W,30,",0,",,W,30,+",0,",,W,30,+",0,",W",0,",W",2.5,",,,W",0,",,,W",9,,84,",W,28,",27,",W,10,",1888,,69,",,,W",44,",,,W",7.4,",W",340,",W",340,",W",36,",W",48.1,",W"


root@4d9a04f527bd:/tmp# java -cp $(hadoop classpath):. FileSystemMultiFile "hdfs://localhost:9000/data/*"
File: hdfs://localhost:9000/data/gsom_sample_csv.csv
2026-05-21 19:06:49,308 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
STATION,NAME,LATITUDE,LONGITUDE,ELEVATION,DATE,CLDD,CLDD_ATTRIBUTES,HTDD,HTDD_ATTRIBUTES,DX32,DX32_ATTRIBUTES,DX70,DX70_ATTRIBUTES,DX90,DX90_ATTRIBUTES,DT00,DT00_ATTRIBUTES,DT32,DT32_ATTRIBUTES,DP01,DP01_ATTRIBUTES,DP05,DP05_ATTRIBUTES,DP10,DP10_ATTRIBUTES,EMXP,EMXP_ATTRIBUTES,EMSD,EMSD_ATTRIBUTES,EMSN,EMSN_ATTRIBUTES,DSND,DSND_ATTRIBUTES,DSNW,DSNW_ATTRIBUTES,PRCP,PRCP_ATTRIBUTES,SNOW,SNOW_ATTRIBUTES,CDSD,CDSD_ATTRIBUTES,EMXT,EMXT_ATTRIBUTES,EMNT,EMNT_ATTRIBUTES,HDSD,HDSD_ATTRIBUTES,TMAX,TMAX_ATTRIBUTES,TMIN,TMIN_ATTRIBUTES,AWND,AWND_ATTRIBUTES,WDF2,WDF2_ATTRIBUTES,WDF5,WDF5_ATTRIBUTES,WSF2,WSF2_ATTRIBUTES,WSF5,WSF5_ATTRIBUTES
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-01,0,",W",517,",W",3,",W",0,",W",0,",W",0,",W",28,",W",8,",W",2,",W",2,",W",1.53,",,W,22,",11.8,",,W,23,",13.4,",,W,22,",8,",W",1,",W",3.29,",,,W",13.8,",,,W",0,,61,",W,31,",11,",W,19,",1137,,45,",,,W",25,",,,W",8.1,",W",330,",W",330,",W",36,",W",47,",W"
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-02,0,",W",403,",W",4,",W",0,",W",0,",W",0,",W",17,",W",9,",W",5,",W",2,",W",2.15,",,W,03,",0,",T,W,26,+",0.1,",,W,13,",0,",W",0,",W",5.69,",,,W",0.1,",,,W",0,,67,",W,29,+",16,",W,10,",1540,,49,",,,W",31,",,,W",8.7,",W",190,",W",180,",W",38,",W",55,",W"
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-03,0,",W",204,",W",0,",W",12,",W",0,",W",0,",W",6,",W",10,",W",1,",W",0,",W",0.59,",,W,13,",0,",,W,31,+",0,",T,W,03,",0,",W",0,",W",1.56,",,,W",0,",T,,W",0,,80,",W,16,",26,",W,03,",1744,,65,",,,W",41,",,,W",7.8,",W",330,",W",330,",W",32,",W",40.9,",W"
USW00003812,"ASHEVILLE AIRPORT, NC US",35.4319,-82.5375,645.3,2016-04,9,",W",144,",W",0,",W",14,",W",0,",W",0,",W",2,",W",7,",W",2,",W",1,",W",1.07,",,W,30,",0,",,W,30,+",0,",,W,30,+",0,",W",0,",W",2.5,",,,W",0,",,,W",9,,84,",W,28,",27,",W,10,",1888,,69,",,,W",44,",,,W",7.4,",W",340,",W",340,",W",36,",W",48.1,",W"


root@4d9a04f527bd:/tmp# java -cp $(hadoop classpath):. FileSystemMultiFile "hdfs://localhost:9000/user/hojat/*.txt"
File: hdfs://localhost:9000/user/hojat/local.txt
2026-05-21 19:06:55,615 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
This is a test line


File: hdfs://localhost:9000/user/hojat/test.txt
Hello from Java HDFS client
```
