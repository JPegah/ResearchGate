import java.util.ArrayList;
import java.util.HashSet;


public class Scheduler {
	ArrayList<String> urlQ;
	ArrayList<String> SeenUrl;
	
	public Scheduler() {
		this.urlQ = new ArrayList<>();
		this.SeenUrl = new ArrayList<>();
	}
	
	
	public String getNextUrl(){
		if (urlQ.size() > 0)
			return urlQ.remove(0);
		else
			return "";
	}
	
	public void addUrl(String url){
		if (SeenUrl.contains(url))
			return;
		urlQ.add(url);

	}
}
