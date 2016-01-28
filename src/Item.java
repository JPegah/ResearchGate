import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;


public class Item {
	private long id;
	private String URL;
	private String title;
	private String abs;
	private ArrayList<String> authors;
	private ArrayList<String> citedBy; 
	private ArrayList<String> references;
	
	public Item (long id, String URL, String title, String abs, ArrayList<String> authors, ArrayList<String> citedBy, ArrayList<String> references) {
		
		this.id = id;
		this.URL = URL;
		this.title = title;
		this.abs = abs;
		this.authors = authors;
		this.citedBy = citedBy;
		this.references = references;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject ItemtoJSON(Item item) throws Exception {
		JSONObject obj = new JSONObject();
		
		obj.put("_id", item.id);
		obj.put("URL", item.URL);
		obj.put("title", item.title);
		obj.put("abstract", item.abs);
		obj.put("authors", item.authors);
		obj.put("citedBy", item.citedBy);
		obj.put("citee", item.references);
		
		StringWriter out = new StringWriter();
	    obj.writeJSONString(out);
	      
	    String jsonText = out.toString();
	    System.out.print(jsonText);
		
		return obj;
	}
	
}
