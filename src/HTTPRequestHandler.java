import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;


public class HTTPRequestHandler {
	public final static String CITE_BASE_URL = "https://www.researchgate.net/publicliterature.PublicationIncomingCitationsList.html?publicationUid=&showCitationsSorter&showAbstract=true&showType=true&showPublicationPreview=true&swapJournalAndAuthorPositions=false&limit=100000";
	public final static String REF_BASE_URL = "https://www.researchgate.net/publicliterature.PublicationCitationsList.html?publicationUid=&showCitationsSorter&showAbstract=true&showType=true&showPublicationPreview=true&swapJournalAndAuthorPositions=false&limit=100000";;
	
	private static String makeURL(long pubID, String type) throws Exception {
		if (type.equals("REF"))
			return REF_BASE_URL.replace("publicationUid=", "publicationUid=" + pubID);
		else 
			return CITE_BASE_URL.replace("publicationUid=", "publicationUid=" + pubID);

	}
	
	public static String HTTPRequest(long pubID, String type) throws Exception {
		String url = makeURL(pubID, type);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("x-requested-with", "XMLHttpRequest");
		con.setRequestProperty("accept", "application/json");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
		return response.toString();
	}
	
	
	// download the html file of the given url 
		public static String HTMLDownloader(String url, String outputName)throws IOException{
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(outputName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			
			return outputName;
		}

}
