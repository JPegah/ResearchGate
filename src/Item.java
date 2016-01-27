import java.util.ArrayList;


public class Item {
	private long id;
	private String title;
	private String abs;
	private ArrayList<String> authors;
	private ArrayList<Long> citedBy; 
	private ArrayList<Long> citee;
	
	public Item (long id, String title, String abs, ArrayList<String> authors, ArrayList<Long> citedBy, ArrayList<Long> citee) {
		
		this.id = id;
		this.title = title;
		this.abs = abs;
		this.authors = authors;
		this.citedBy = citedBy;
		this.citee = citee;
	}
	
}
