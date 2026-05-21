# Testing HDFS Java URLStreamHandler Interface

Test Java's ability to read from HDFS using URL.openStream() with `hdfs://` protocol.

URLCat.java Example

## Running Inside Container
```bash
docker exec -it namenode bash
cd /tmp
javac -cp $(hadoop classpath) URLCat.java
java -cp $(hadoop classpath):. URLCat hdfs://localhost:9000/user/hojat/test.txt
```


