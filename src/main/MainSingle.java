package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

public class MainSingle {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
		System.out.println("对比用“单线程”爬虫-知乎v0.2");
		long now = new Date().getTime();
		if(args.length < 1){
			System.out.println("使用默认文件夹");
			args = new String[]{Zhihu.BASE_URL,"res/output"};
		}
		
		CreateFile.Folder("res2");
		CreateFile.setFilePath("res2/");
		if (DownloadPage.exec(args[0],DownloadPage.FILE_PATH)){
			File file = new File(DownloadPage.FILE_PATH);
			byte[] bys= new byte[(int) file.length()];
			new FileInputStream(file).read(bys);
			String[] htmls = new String(bys).split(" ");
			for(int i=0;i<htmls.length;i++){
				if (PageParser.hasStory(htmls[i].toString()) ){
				String id 		= PageParser.getStoryId(htmls[i]);
				String folder 	= CreateFile.getPagesFolderById(id);
				CreateFile.Folder(folder);
				DownloadPage.exec(Zhihu.BASE_URL2+"story/"+id,folder+"index.html");
			
				}
			}
			System.out.println("程序结束");
		}else{
			System.out.println("下载错误");
		}
		long spent = new Date().getTime() - now;
		System.out.println("花费:"+spent+"ms");
		//System.exit(res);
	}
}
