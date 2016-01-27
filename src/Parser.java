import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Parser {
	
	public static void parsePaper() {
		
	}
	
	public static Item parsePerson(String fileName) throws IOException {
		File input = new File(fileName);
		Document doc = Jsoup.parse(input, "UTF-8", "");
//		System.out.println(getTitle(doc));
		getAuthors(doc);
		return null; /*new Item(getId(doc),
				getTitle(doc), 
				getAbs(doc),
				getAuthors(doc),
				getCitedBy(doc),
				getCitee(doc));*/
	}
	
	
//	private static long getId(Document doc) {
//		
//	}
	
	private static String getTitle(Document doc) {
		String title = doc.getElementsByClass("pub-title").get(0).text();
		return title;
	}
	
	private static String getAbstract(Document doc) {
		Elements e = doc.getElementsByClass("pub-abstract");
		if (e != null)
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
	
	private static ArrayList<Long> getCitedBy(Document doc) {
		
	}
//	
//	private static ArrayList<Long> getCitee(Document doc) {
//		
//	}
}

