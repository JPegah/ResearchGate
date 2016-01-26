import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
	public static void main(String[] args) throws IOException {
		Parser.parsePerson(Downloader("https://www.researchgate.net/researcher/8159937_Zoubin_Ghahramani", "first"));
	}
	
	// download the html file of the given url 
	public static String Downloader(String url, String outputName)throws IOException{
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(outputName+ ".html");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
		
		return outputName + ".html";
	}
}
