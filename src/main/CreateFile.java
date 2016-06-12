package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateFile {

	private static String FILE_PATH  = "res/";
	private static String FILE_PAGES = "pages/";
	
	public static void setFilePath(String path){
		FILE_PATH = path;
	}
	
	public static String getPagesFolderById(String id){
		return FILE_PATH+FILE_PAGES+id+"/";
	}
	
	public static void Folder(String name){
		File file = new File(name);
		if (file.exists()){
			System.out.println("文件夹已经存在");
		}else{
			file.mkdirs();
		}
	}
	
	public static void WriteFile(String name,byte[] bytes){
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(name));
			out.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
