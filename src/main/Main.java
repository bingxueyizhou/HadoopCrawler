package main;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.ToolRunner;


/**
 * 
 * @author zoubing
 * @date  2016/6/10
 * 
 */
public class Main{	
	@SuppressWarnings("resource")
	public static void main2(String args[]) throws Exception{
		File file = new File("res/index.html");
		byte[] allBytes = new byte[(int)file.length()];
		new FileInputStream(file).read(allBytes);
		String  allString = new String(allBytes);
		//"<a(\\s*)href=\"/story/(\\S+)\"(\\s*)class=\"link-button\">(\\s*)<img(\\s*)src=\"http://(\\S+)\"(\\s*)"+ class=\"preview-image\"(\\s*)/>(\\s*)"<span(\\s*)class=\"title\">((\\S+))</span>""</a>"
		Pattern pattern	  = Pattern.compile("<a(\\s*)href=\"/story/(\\S+)\"([\\S\\s]+?)</a>");
        Matcher matcher = pattern.matcher(allString);
        while(matcher.find()){
        	System.out.println(matcher.group());
        }
        System.out.println("结束");
	}
	
	
	
	public static void main(String[] args) throws Exception{
		System.out.println("学习用“Hadoop”爬虫-知乎v0.2");
		if(args.length < 1){
			System.out.println("使用默认文件夹");
			args = new String[]{Zhihu.BASE_URL,"res/output"};
		}
		
		final FileSystem filesystem = FileSystem.get(new URI("hdfs://localhost:9000"), 
											new Configuration());
		HDFSFile.mkdir(filesystem, "res");
		if (DownloadPage.HDFSExec(filesystem,args[0],DownloadPage.FILE_PATH)){
		
			int res = ToolRunner.run(new Configuration(),
						new CrawlerHtmlParserMapReduce(), args);
			System.out.println("程序结束："+res);
		}else{
			System.out.println("下载错误");
		}
		System.out.println("花费:"+Zhihu.TIME_COUNTER+"ms");
		//System.exit(res);
	}
}
