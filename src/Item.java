import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;


public class Item {
	long id;
	String URL;
	String title;
	String abs;
	ArrayList<String> authors;
	ArrayList<String> citedByURLs; 
	ArrayList<String> referencesURLs;
	
	
	public Item (long id, String URL, String title, String abs, ArrayList<String> authors, ArrayList<String> citedByURLs, ArrayList<String> referencesURLs) {
		
		this.id = id;
		this.URL = URL;
		this.title = title;
		this.abs = abs;
		this.authors = authors;
		this.citedByURLs = citedByURLs;
		this.referencesURLs = referencesURLs;
	}
	
	private static ArrayList<Long> getIDs(ArrayList<String> urls) throws IOException {
		ArrayList<Long> ids = new ArrayList<Long>();
		for(int i = 0; i < urls.size(); i++) {
			System.out.println(urls.get(i));
			ids.add(Parser.getId(urls.get(i)));
		}
		return ids;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject ItemtoJSON(Item item) throws Exception {
		JSONObject obj = new JSONObject();
		
		obj.put("_id", item.id);
		obj.put("URL", item.URL);
		obj.put("title", item.title);
		obj.put("abstract", item.abs);
		obj.put("authors", item.authors);
		obj.put("citedBy", getIDs(item.citedByURLs));
		obj.put("citee", getIDs(item.referencesURLs));
		
		StringWriter out = new StringWriter();
	    obj.writeJSONString(out);
		
		return obj;
	}
	
	public static void saveItem(Item item) throws Exception {
		PrintWriter pw = new PrintWriter(new File("papers/" + item.id + ".json"));
		JSONObject obj = ItemtoJSON(item);
		obj.writeJSONString(pw);
		pw.close();
	}
	
}
