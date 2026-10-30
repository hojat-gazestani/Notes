import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AvgRating {

	public static class CSVSplitMapper
			extends Mapper<LongWritable, Text, Text, DoubleWritable> {

		private final Text movieId = new Text();
		private final DoubleWritable rating = new DoubleWritable();

        @Override
		public void map(
                LongWritable key, 
                Text value, 
                Context context
        ) throws IOException, InterruptedException {

            String[] fields = value.toString().split(",");

            if (fields.length < 3) {
                return;
            }

            try {
                movieId.set(fields[1]);
                rating.set(Double.parseDouble(fields[2]));

                context.write(movieId, rating);
            } catch (NumberFormatException e) {
                context.getCounter("AvgRating", "INVALID_RATING").increment(1);
            }
		}
	}

	public static class DoubleAVGReducer
			extends Reducer<Text, DoubleWritable, Text, DoubleWritable>{

		private final DoubleWritable result = new DoubleWritable();
	
        @Override
		public void reduce(
                Text key, 
                Iterable<DoubleWritable> values, 
                Context context
        ) throws IOException, InterruptedException {

            double sum = 0.0;
            long count = 0;

			for (DoubleWritable value : values) {
				sum += value.get();
                count ++;
			}

            if (count > 0) {
			    result.set(sum/count);
			    context.write(key, result);
            }
        }	
	}

	public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println(
                    "Usage: AvgRating <input path> <output path>"
            );
            System.exit(2);
        }

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Avg Rating");

		job.setJarByClass(AvgRating.class);
		job.setMapperClass(CSVSplitMapper.class);
		job.setReducerClass(DoubleAVGReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DoubleWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
