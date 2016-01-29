import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;



public class Indexer {
	public static void main(String[] args) throws FileNotFoundException {		
		BasicConfigurator.configure();
		Node node = NodeBuilder.nodeBuilder()
	    .settings(Settings.builder()
	        .put("path.home", "C:/elasticsearch-2.1.1")).node();

		
		Client client = node.client();
//		try {
//			client.admin().indices().prepareCreate("pegah_index").execute().actionGet();
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("indedx was created");
//		}
		

		// on shutdown
		System.out.println("index created");
		File[] files = new File("papers/").listFiles();
		
			Scanner sc = null;
			sc = new Scanner(files[0]);				
			String json = sc.nextLine();
			String jso = "{" +
			        "\"user\":\"kimchy\"," +
			        "\"postDate\":\"2013-01-30\"," +
			        "\"message\":\"trying out Elasticsearch\"" +
			    "}";
			System.out.println(json);

			IndexResponse response =
					client.prepareIndex().setIndex("documents").setType("").setSource(json).execute().actionGet(); 

//				IndexResponse response = client.prepareIndex("pegah_index", "pegah")
//				        .setSource(jso)
//				        .get();
				System.err.println(response.getIndex());
				System.err.println(response.getType());
				System.err.println(response.getId());
				System.err.println(response.getVersion());
				System.err.println(response.isCreated());

			
		
		
		
//		node.close();

	}	
}
