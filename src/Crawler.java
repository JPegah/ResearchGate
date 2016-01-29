import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class Crawler {
	Scheduler sch;
	Parser parser;
	
	public static void main(String[] args) {
		Crawler crl = new Crawler();
		try {
			crl.crawl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Crawler() {
		sch = new Scheduler();
		parser = new Parser();
	}
	
	public void crawl() throws Exception {
		int item_number = 0;
		while (item_number <= 1000) {
			String url = sch.getNextUrl();
			System.out.println(url);
			String fileName = HTTPRequestHandler.HTMLDownloader(url, Parser.getId(url) + ".html");
			Item item = parser.parsePaper(fileName, url);
			if (item != null) {
				sch.addUrl(item.citedByURLs);
				sch.addUrl(item.referencesURLs);
				item_number++;
			}
		}
	}
	
	

}
