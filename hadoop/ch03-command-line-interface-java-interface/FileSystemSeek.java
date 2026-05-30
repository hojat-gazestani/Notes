import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;
import java.net.URI;

public class FileSystemSeek {
    public static void main(String[] args) throws Exception {
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);

        try (FSDataInputStream in = fs.open(new Path(uri))) {
            // Read first 100 bytes
            byte[] buffer = new byte[10];
            in.readFully(0, buffer);  // Read from offset 0
            System.out.write(buffer);

            // Seek to position 500 and read next 50 bytes
            in.seek(50);
            in.readFully(buffer, 0, 5);
            System.out.write(buffer, 0, 5);

            // Get file metadata
            System.out.println("\nFile length: " + in.getPos());
        }
    }
}
