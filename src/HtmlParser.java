
import java.util.HashSet;

import javax.swing.text.html.HTML.Tag;

import org.htmlparser.NodeFilter;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParser{

	public static HashSet<String> extractLinks(String url, LinkFilter filter){
		HashSet<String> links = new HashSet<String>();
		try{
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			NodeFilter frameFilter = new NodeFilter() {

				@Override
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};// act as a condition
			
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			// get filtered tag
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)//find <a> tag
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// get url
					if(filter.accept(linkUrl)){
						links.add(linkUrl);
					}
				}else// frame tag  
	            {  
	                // extract link in frame src="test.html"  
	                String frame = tag.getText();  
	                int start = frame.indexOf("src=");  
	                frame = frame.substring(start);  
	                int end = frame.indexOf(" ");  
	                if (end == -1)  
	                    end = frame.indexOf(">");  
	                frame = frame.substring(5, end - 1);  
	                System.out.println(frame);  
	            }  
	        
			}
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return links;		
	}
	
	public static HashSet<String> extractMovieNames(String url, String HeadingTagName){
		HashSet<String> names = new HashSet<String>();
		try{
			Parser nameparser = new Parser(url);
			nameparser.setEncoding("gb2312");
//			NodeFilter aFilter = new NodeFilter(){
//				public boolean accept(Node node){
//					if(node.getText().contains("itemprop = \"url\"")){
//						return true;
//					}else{
//						return false;
//					}
//				}
//			};	
			NodeClassFilter headingFilter =new NodeClassFilter(HeadingTag.class);
			NodeList headingList = nameparser.extractAllNodesThatMatch(headingFilter);
			for (int i = 0; i < headingList.size(); i++) {
				String name = "";
				HeadingTag headingTag=(HeadingTag)headingList.elementAt(i);
				if(headingTag.getText().startsWith(HeadingTagName)){// h*, *=1,2,3,4,5 or 6
					name =headingTag.toPlainTextString();//get the content in the <h*> tag
				}
				if(name==null){
					continue;
				}
				names.add(name);
			}
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return names;	

		}
	public static HashSet<String> extractMainCast(String url){
		HashSet<String> castNames = new HashSet<String>();
		HashSet<String> castLinks = new HashSet<String>();
		try{
			Parser Linkparser = new Parser(url);
			Linkparser.setEncoding("gb2312");
			
//			NodeFilter filter1 = new TagNameFilter("div");
//			NodeFilter filter2 = new HasAttributeFilter("id","titleCast");
//			NodeFilter andfilter1 = new AndFilter(filter1,filter2);
//			
//			NodeFilter parentFilter = new HasParentFilter(andfilter1,true);
			
			NodeFilter filter3 = new TagNameFilter("td");  //or span
			NodeFilter filter4 = new HasAttributeFilter("itemprop","actor"); // or value = name
			NodeFilter andfilter2 = new AndFilter(filter3,filter4);
			NodeFilter parentFilter = new HasParentFilter(andfilter2);
			
			AndFilter linkFilter = new AndFilter(new NodeClassFilter(LinkTag.class), parentFilter);
			NodeList linkslist = Linkparser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < linkslist.size(); i++) 
			{
				Node tag = linkslist.elementAt(i);
				if (tag instanceof LinkTag)//find <a> tag
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// get url
					castLinks.add(linkUrl);
				}
				
//			/** this part is to get the names*/
//			NodeList castList = ageparser.parse(andfilter2);
//			for (int i = 0; i < castList.size(); i++) {
//				Node nameTag=(Node)castList.elementAt(i);
//				//String name =nameTag.getText();
//				String name =nameTag.toPlainTextString();//get the content in the <h*> tag
//				
//				cast.add(name);
//			}
			}
			
		}catch (ParserException e) {
			e.printStackTrace();
		}
		//return castnames;	
		return castLinks;

		}
	public static HashSet<Integer> extractAge(String url){
		HashSet<Integer> ages = new HashSet<Integer>();
		try{
			Parser ageparser = new Parser(url);
			ageparser.setEncoding("gb2312");
			
//			NodeFilter timeTagFilter = new AndFilter(  
//					new TagNameFilter("time"), new AndFilter(  
//							new HasAttributeFilter("datetime"),  
//							new HasAttributeFilter("itemprop"))); 
			NodeFilter filter = new HasAttributeFilter("datetime");
			NodeList timeList = ageparser.parse(filter);
			//NodeList timeList = ageparser.extractAllNodesThatMatch(timeTagFilter);
			for (int i = 0; i < timeList.size(); i++) {
				Node timeTag=(Node)timeList.elementAt(i);
				//if(timeTag.getText()!=null){
				String birth =timeTag.getText();//get the content in the <h*> tag
				birth = birth.substring(15, 19);
				if(birth==null){
					//birth = "0";
					continue;
				}
				int age = 2014 - Integer.parseInt(birth);
				ages.add(age);
			}
			
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return ages;	

		}
		
	
	/**main function to test HtmlParser class
	 * 
	 */
	public static void main(String[]args)
	{
		LinkFilter linkFilter = new LinkFilter(){
			@Override
			public boolean accept(String url) {
				if(url.startsWith("http://www.imdb.com/title"))
				return true;
			else
				return false;
			}
		};
		//HashSet<String> links = HtmlParser.extractLinks("http://www.imdb.com/movies-in-theaters", linkFilter);
		//HashSet<String> names = HtmlParser.extractMovieNames("http://www.imdb.com/movies-in-theaters", "h4");
		//HashSet<Integer> ages = HtmlParser.extractAge("http://www.imdb.com/name/nm0000120/?ref_=ttfc_fc_cl_t1");
		HashSet<String> names = HtmlParser.extractMainCast("http://www.imdb.com/title/tt2096672/?ref_=inth_ov_tt");
		for(String link : names)
			System.out.println(link);
	}
}
