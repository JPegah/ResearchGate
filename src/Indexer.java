import static org.elasticsearch.node.NodeBuilder.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.vividsolutions.jts.io.ParseException;



public class Indexer {
	public static void main(String[] args) {

		BasicConfigurator.configure();
		Node node = NodeBuilder.nodeBuilder()
	    .settings(Settings.builder()
	        .put("path.home", "/home/pegah/elasticsearch-2.1.1/")).node();

		Client client = node.client();
		try {
			client.admin().indices().prepareCreate("pegah_index").execute().actionGet();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("indedx was created");
		}
		

		// on shutdown
		System.out.println("index created");
		File[] files = new File("papers/").listFiles();
		for (int i = 0; i < 2; i++){
//			Scanner sc = null;
//			try {
//				sc = new Scanner(files[i]);				
//			} catch (Exception e) {
//				System.err.println("could not read file");
//			}
////			System.err.println(sc.nextLine());
//			String json = sc.nextLine();

		
			JSONParser parser = new JSONParser();

			try {

				Map<String, Object> json = new HashMap<>();
//				json.put("postDate",new Date());
				json.put("message","trying out Elasticsearch");
				
				Object obj = parser.parse(new FileReader(files[i].getPath()));
				JSONObject jsonObject = (JSONObject) obj;
				String Abst = (String) jsonObject.get("abstract");
				json.put("abstract",Abst);
				System.err.println(Abst);
//
//				long age = (Long) jsonObject.get("age");
//				System.out.println(age);
//
//				// loop array
				JSONArray auths = (JSONArray) jsonObject.get("authors");
				Iterator<String> iterator = auths.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
				}
				IndexResponse response = client.prepareIndex("pegah_index", "pegah")
				        .setSource(obj)
				        .get();
				System.err.println(response.getIndex());
				System.err.println(response.getType());
				System.err.println(response.getId());
				System.err.println(response.getVersion());
				System.err.println(response.isCreated());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		     }
		
		
		System.out.println(files.length);
//		node.close();

	}
}
