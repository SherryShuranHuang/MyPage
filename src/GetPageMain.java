import java.sql.DriverManager;
import java.sql.Statement;
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
      
    private void averageAge(){
    	//extrac number from sql database
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
        SqlDB.createTbl("movietbl");
        
        if(!linkDB.unvisitedIsEmpty()){// condition needs more consideration
            //TODO recuesive retrieve data and stroe in to database
        	String visitUrl=linkDB.getUnVisitedUrl();  
//            if(visitUrl==null)  
//                continue;  
            PageDownLoader downpage=new PageDownLoader();  
            downpage.downloadFile(visitUrl);  
//            linkDB.addVisited(visitUrl);  
//            System.out.println(visitUrl);
            int index = 0;
            int castNum = 0;
            int totalAge =0;
            HashSet<String> movielinks=HtmlParser.extractLinks(visitUrl,filter);// the entrys to the second layer
            for(String link:movielinks)  
            {  
            	System.out.println("the"+ index +"th movie");
            	String movielink=HtmlParser.extractTheMovie(link,"h4");
                //linkDB.addUnvisited(link);  
            	String moviename = HtmlParser.extractMovieName(movielink,"h1");
            	//add movie name to db
            	SqlDB.insertIntoTable("movietbl", index +",'"+ moviename+"'");
            	HashSet<String> castlinks=HtmlParser.extractMainCast(movielink);
            	for(String castlink: castlinks){
            		int age = HtmlParser.extractAge(castlink);
            		if(age != 0){
            			totalAge+=age;
            			castNum++;
            			//SqlDB.insertIntoTable("casttbl", index +","+ age);	
            		}
            	}
            	int ave_age = totalAge/castNum;
            	//SqlDB.insertIntoTable("casttbl", index +","+ ave_age);
            	SqlDB.updateAge("movietbl", index, ave_age);
            	index++;

            }          
        }       
    }  
   
	

    /** the Entrance*/
    public static void main(String[]args)  
    {  
    	GetPageMain crawler = new GetPageMain("http://");   // find links start with" http://www.twt "
        crawler.crawling(new String[]{"http://www.imdb.com/showtimes/location?ref_=inth_ov_sh_sm"}); 
    }  
}  