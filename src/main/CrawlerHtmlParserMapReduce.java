package main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;


public class CrawlerHtmlParserMapReduce extends Configured implements Tool {

	public static class MapClass extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private Text content = new Text();
		private final static IntWritable one = new IntWritable(1);
		
		// Map Method
		public void map(LongWritable key, Text filePathInfo, Context context)
				throws IOException, InterruptedException {

			try {
				final FileSystem filesystem = FileSystem.get(new URI("hdfs://localhost:9000"), new Configuration());
			
			
				StringTokenizer tokenizer = new StringTokenizer(
						filePathInfo.toString());
				while (tokenizer.hasMoreTokens()) {
					content.set(tokenizer.nextToken());
					if (content.toString() != null	&& 
						content.toString().length() > 0 && 
						PageParser.hasStory(content.toString()) ) {
						long now = new Date().getTime();
						String id 		= PageParser.getStoryId(content.toString());
						String folder 	= CreateFile.getPagesFolderById(id);
						HDFSFile.mkdir(filesystem, folder);
						DownloadPage.HDFSExec(filesystem ,Zhihu.BASE_URL2+"story/"+id, 
															folder+"index.html");
						Zhihu.TIME_COUNTER += (new Date().getTime() - now);
						
						//WriteURLToHDFS.writeURLToHDFS(linkList, urlFilePath);
						/*
						//for (int i = 0; i < linkList.size(); i++) {
							//System.out.println("test");
							context.write(new Text("test"), one);
							// we write the file to
							//context.write(new Text(linkList.get(i).toString()),one);
							//System.out.println(linkList.get(i));
						}
						*/
					}
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void cleanup(Context context) {
			// this function is called when the mapreduce is finished
		}
	}

	public static class CrawlerHtmlParserReduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		// Reduce Method
		public void reduce(Text url, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			System.out.println("ur"+url);
			//context.write(url, one);
		}
		/*
		@Override
		protected void cleanup(Context context) throws IOException,
				InterruptedException {
			//maybe I can put all the url to one at this place,or I can write a function to get all the 
		}
		*/
	}

	public int run(String[] args) throws Exception {
		@SuppressWarnings("deprecation")
		Job job = new Job();
		job.setJarByClass(CrawlerHtmlParserMapReduce.class);

		FileInputFormat.addInputPath(job, new Path(DownloadPage.FILE_PATH));
		//FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		
		job.setMapperClass(MapClass.class);
		//job.setCombinerClass(CrawlerHtmlParserReduce.class);
		job.setReducerClass(CrawlerHtmlParserReduce.class);
		
		job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(NullOutputFormat.class);  

		//job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		//job.setOutputKeyClass(Text.class);
		//job.setOutputValueClass(IntWritable.class);
		
		//job.waitForCompletion(true);
		//return 0;
		return job.waitForCompletion(true) ? 0 : 1;
	}

}
