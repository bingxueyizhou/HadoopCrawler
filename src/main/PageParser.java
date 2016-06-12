package main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {
	public static ArrayList<String> getTest(String htmlString){
		ArrayList<String> list = new ArrayList<>();
		if (htmlString.length() <= 1) return list;
		//"/story/(\\S+?)\"
		//<img(\\s*)src=\"http://(\\S+)\"(\\s*)"
		//<span(\\s*)class=\"title\">((\\S+))</span>
		Pattern pattern	  = Pattern.compile("<a(\\s*)href=\"/story/(\\S+)\"([\\S\\s]+?)</a>");
        Matcher matcher = pattern.matcher(htmlString);
        while(matcher.find()){
        	list.add(matcher.group());
        }
        return list;
	}
	
	public static boolean hasStory(String text){
		if (text.contains("/story/"))
			return true;
		else 
			return false;
	}
	
	public static String getStoryUrl(String text){
		if (text.length() > 10){
			Pattern pattern = Pattern.compile("/story/[0-9]+");
			Matcher matcher = pattern.matcher(text);
			matcher.find();
			String result = matcher.group();
			return result;
		}
		return null;
	}
	
	public static String getStoryId(String text){
		String url = getStoryUrl(text);
		return url==null?null:url.substring(7,url.length());
	}
}
