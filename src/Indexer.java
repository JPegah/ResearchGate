
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Indexer {
	public static void main(String[] args) {

		BasicConfigurator.configure();
		Node node = NodeBuilder
				.nodeBuilder()
				.settings(
						Settings.builder().put("path.home",
								"/home/pegah/elasticsearch-2.1.1/")).node();

		Client client = node.client();
		try {
			String indexName = "pegah_ind";
			IndicesExistsResponse res = client.admin().indices()
					.prepareExists(indexName).execute().actionGet();
			if (res.isExists()) {
				DeleteIndexRequestBuilder delIdx = client.admin().indices().prepareDelete(indexName);
				delIdx.execute().actionGet();
			}

			CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject().startObject("doc").startObject("properties")
					.startObject("abstract").
						field("type", "string").field("store", "yes").field("index", "not_analyzed").
					endObject()
					.startObject("authors").
						field("type", "string").field("store", "yes").field("index", "not_analyzed").
					endObject()
					.startObject("title").
					field("type", "string").field("store", "yes").field("index", "not_analyzed").
					endObject().
					startObject("pub_id").
					field("type", "long").field("store", "yes").field("index", "not_analyzed").
					endObject().
					startObject("citee").
					field("type", "long[]").field("store", "yes").field("index", "not_analyzed").
					endObject().
					startObject("citedBy").
					field("type", "long[]").field("store", "yes").field("index", "not_analyzed").
					endObject().
					startObject("URL").
					field("type", "string").field("store", "yes").field("index", "not_analyzed").
					endObject().
					
					// more mapping
					endObject().endObject().endObject();
			client.admin().indices().preparePutMapping(indexName).setType("doc")
					.setSource(builder).execute().actionGet();

		} catch (Exception e) {
			System.out.println("indedx was created");
		}

		Map<Long, Integer> pubToInd = new HashMap<Long, Integer>();
		paper[] papers = addDocs(client, pubToInd);
		PageRank pr = new PageRank(papers, (float)0.2);
		// add pageRank to index
		// node.close();

	}
	public static paper[] addDocs(Client client, Map<Long, Integer> map){
		File[] files = new File("papers/").listFiles();
		paper[] papers = new paper[files.length];
		
		for (int i = 0; i < files.length; i++) {
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(new FileReader(files[i].getPath()));
				JSONObject jsonObject = (JSONObject) obj;
		
				IndexResponse response = client
						.prepareIndex().setIndex("pegah_ind").setType("doc").setSource(jsonObject).setRefresh(true).execute().actionGet();
				System.err.println(response.getIndex());
				System.err.println(response.getType());
				System.err.println(response.getId());
				System.err.println(response.getVersion());
				System.err.println(response.isCreated());
				
				long pub_id = (long) jsonObject.get("pub_id");
				map.put(pub_id, Integer.parseInt(response.getId()));
				//create graph for pageRank
				papers[i] = new paper(pub_id);
				JSONArray jarr = (JSONArray)jsonObject.get("citee");
				for (Object object : jarr) {
					papers[i].addCitee((long) object);
				}
				

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return papers;
	}
}
