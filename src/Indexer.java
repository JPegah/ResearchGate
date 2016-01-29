import static org.elasticsearch.node.NodeBuilder.*;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;



public class Indexer {
	public static void main(String[] args) {
		Node node =
			    nodeBuilder().local(true).node();
		Client client = node.client();

		// on shutdown

		node.close();

	}
}
