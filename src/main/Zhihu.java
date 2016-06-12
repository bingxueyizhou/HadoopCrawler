package main;

public class Zhihu {
	
	private String url    = "";
	private String title  = "";
	private String picture= "";
	
	public static long TIME_COUNTER = 0;

	public final static String BASE_URL = "http://zhihudaily.ahorn.me/";
	public final static String BASE_URL2= "http://daily.zhihu.com/";
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setPicture(String picture){
		this.picture = picture;
	}
	
	public String toString(){
		return "{\"title\":\""		+this.title+
				"\",\"url\":\""		+this.url+
				"\",\"picture\":\""	+this.picture+"\"}";
	}
	
}
