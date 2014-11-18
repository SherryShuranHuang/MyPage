import java.util.HashMap;
import java.util.HashSet;  
  
public class GetPageMain {  
    private String startStr = "";  
    private LinkDB linkDB = new LinkDB();

    
    public GetPageMain(String ahost)  
    {  
        this.startStr = ahost;  
    }  
      
    // add all the unvisited urls to inisiate linkDB  
    private void initCrawlerWithSeeds(String[] seeds)  
    {  
        for(int i=0; i<seeds.length; i++)
            linkDB.addUnvisited(seeds[i]);  
    }  
      
    /** starting function*/  
    public void crawling(String[] seeds)  
    {  
        LinkFilter filter = new LinkFilter(){  
            public boolean accept(String url) {  
                if(url.startsWith(startStr))  
                    return true;  
                else  
                    return false;  
            }  
        };  
        
        initCrawlerWithSeeds(seeds);  

        while(!linkDB.unvisitedIsEmpty()){// condition needs more consideration
            //TODO recuesive retrieve data and stroe in to database
        }
         

    }  
    /** the Entrance*/
    public static void main(String[]args)  
    {  
    	GetPageMain crawler = new GetPageMain("http://www.bu.edu");   // find links start with" http://www.twt "
        crawler.crawling(new String[]{"http://www.bu.edu"}); 
    }  
}  