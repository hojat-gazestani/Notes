import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.io.IOUtils;

import java.net.URI;

public class FileSystemMultiFile {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java FileSystemMultiFileSimple <pattern>");
            System.exit(1);
        }

        String pattern = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(pattern), conf);

        // Expand wildcards
        Path[] paths = FileUtil.stat2Paths(fs.globStatus(new Path(pattern)));

        for (Path path : paths) {
            System.out.println("File: " + path);
            try (FSDataInputStream in = fs.open(path)) {
                IOUtils.copyBytes(in, System.out, 4096, false);
            }
            System.out.println("\n");
        }
    }
}
