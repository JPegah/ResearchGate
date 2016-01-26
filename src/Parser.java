import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;




public class Parser {
	
	public static void parsePaper() {
		
	}
	
	public static void parsePerson(String fileName) throws IOException {
		File input = new File(fileName);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		System.out.println(doc.title());
	}
	
}
