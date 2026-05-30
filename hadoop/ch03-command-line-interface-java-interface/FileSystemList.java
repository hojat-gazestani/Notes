import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import java.net.URI;

public class FileSystemList {
    public static void main(String[] args) throws Exception {
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);

        FileStatus[] statuses = fs.listStatus(new Path(uri));
        for (FileStatus status : statuses) {
            System.out.println(status.getPath() +
                "\tSize: " + status.getLen() +
                "\tPermissions: " + status.getPermission() +
                "\tIsDir: " + status.isDirectory());
        }
    }
}
