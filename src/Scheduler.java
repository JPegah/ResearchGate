import java.util.ArrayList;
import java.util.HashSet;


public class Scheduler {
	HashSet<Long> Visited;
	ArrayList<String> urlQ;
	ArrayList<String> SeenUrl;
	
	public Scheduler() {
		this.Visited = new HashSet<>();
		this.urlQ = new ArrayList<>();
		this.SeenUrl = new ArrayList<>();
	}
	
	public boolean isRepeated(long id){
		if (Visited.contains(id))
			return true;
		return false;
	}
	
	public String getNextUrl(){
		if (urlQ.size() > 0)
			return urlQ.remove(0);
		else
			return "";
	}
	
	public boolean finish(){
		if (Visited.size() > 1000)
			return true;
		return false;
	}
	
	public void addUrl(String url){
		if (SeenUrl.contains(url))
			return;
		urlQ.add(url);
	}
	
	public void VisitedId(long id){
		Visited.add(id);
	}
}
