import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LinkDB {
	private HashSet<String> visited = new HashSet<String>();
	private LinkedList<String> unvisited = new LinkedList<String>();
	/*for unvisited*/
	
	public LinkedList<String> getUnVisitedUrls(){
		return unvisited;
	}

	public void addUnvisited(String url){
		if(url != null 
				&& !url.trim().equals("")
				&& !visited.contains(url)  
				&& !unvisited.contains(url))
		{
			unvisited.add(url);
		}
	}
	public String getUnVisitedUrl(){
		return unvisited.getFirst();
	}
	public boolean unvisitedIsEmpty(){
		return unvisited.isEmpty();
	}
	
	/*for visited*/
	public void addVisited(String url){
		visited.add(url);
	}
	
	public void removeVisited(String url){
		visited.remove(url);
	}
	
	public int getVisitedUrlNum(){
		return visited.size();
	}
}
