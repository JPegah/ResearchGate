import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Parser {

	
	public Item parsePaper(String fileName, String url) throws Exception {
		File input = new File(fileName);
		Document doc = Jsoup.parse(input, "UTF-8", url);
		
		long pubID = getId(url);
		String title = getTitle(doc);
		String abs = getAbstract(doc);
		
		if (title == null || abs == null) {
			System.out.println("helllloooooooooooooooooooo");
			return null;
		}
		else {
			Item newItem =  new Item(pubID,
					url, 
					title, 
					abs,
					getAuthors(doc),
					getCitedBy(pubID),
					getReferences(pubID));
			Item.saveItem(newItem);
			return newItem;
		}
	}
	
	
	public static long getId(String url) throws IOException {
		URL urlObj = new URL(url);
		String nameId = urlObj.getPath().split("/")[2]; 
		String id = nameId.split("_")[0];
		return Long.parseLong(id);
	}
	
	private static String getTitle(Document doc) {
		Elements e = doc.getElementsByClass("pub-title");
		if (e.size() > 0)
			return e.get(0).text();
		else
			return "";
	}
	
	private static String getAbstract(Document doc) {
		Elements e = doc.getElementsByClass("pub-abstract");
		if (e.size() > 0)
			return e.get(0).child(0).getElementsByTag("div").get(1).text();
		else 
			return "";
	}
	
	private static ArrayList<String> getAuthors(Document doc) {
		ArrayList<String> authors = new ArrayList<String>();
		
		Elements authDiv = doc.getElementsByClass("author-item");
		for (Element e: authDiv) {
			Element spanDiv = e.child(1).child(0).child(0);
			String authName = spanDiv.text();
			authors.add(authName);
		}
		
		
		return authors;
		
	}
	
	private static ArrayList<String> getCitedBy(long pubID) throws Exception {
		String json = HTTPRequestHandler.HTTPRequest(pubID, "CITE");
		return JSONToString(json);
	}
	
	private static ArrayList<String> getReferences(long pubID) throws Exception {
		String json = HTTPRequestHandler.HTTPRequest(pubID, "REF");
		return JSONToString(json);
	}
	
	
	private static ArrayList<String> JSONToString(String json) {
		ArrayList<String> citations = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		String base = "https://www.researchgate.net/";
		
		try {
			JSONObject obj = (JSONObject)parser.parse(json);
			JSONObject result = (JSONObject) obj.get("result");
			JSONObject data	= (JSONObject) result.get("data");
			JSONArray citationItems = (JSONArray) data.get("citationItems");
			for (int i = 0; i < citationItems.size(); i++) {
				JSONObject citationData = (JSONObject) ((JSONObject) citationItems.get(i)).get("data");
				String url = (String) citationData.get("publicationUrl");
				if(url != null)
					citations.add(base + url);
				System.out.println(base + url);
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return citations;
	}
	
	
}

