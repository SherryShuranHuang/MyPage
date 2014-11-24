
import java.sql.Time;
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
			parser.setEncoding("UTF-8");
			
			NodeFilter filter1 = new TagNameFilter("div");
			NodeFilter filter2 = new HasAttributeFilter("class","title");
			AndFilter andfilter = new AndFilter(filter1,filter2);
			
			AndFilter linkFilter = new AndFilter(new NodeClassFilter(LinkTag.class), new HasParentFilter(andfilter));
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
				}
	        
			}
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return links;		
	}
	
	public static String extractTheMovie(String url, String HeadingTag){
		String linkUrl = "";
		try{
			Parser parser = new Parser(url);
			parser.setEncoding("UTF-8");
			
			NodeFilter filter1 = new TagNameFilter(HeadingTag);
			NodeFilter filter2 = new HasAttributeFilter("itemprop","name");
			AndFilter andfilter = new AndFilter(filter1,filter2);
			
			AndFilter linkFilter = new AndFilter(new NodeClassFilter(LinkTag.class), new HasParentFilter(andfilter));
			// get filtered tag
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)//find <a> tag
				{
					LinkTag linktag = (LinkTag) tag;
					linkUrl = linktag.getLink();// get url
				}
	        
			}
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return linkUrl;	
		
	}
	/**extractMovieNames: to extract a list of all the movie names*/
//	public static HashSet<String> extractMovieNames(String url, String HeadingTagName){
//		HashSet<String> names = new HashSet<String>();
//		try{
//			Parser nameparser = new Parser(url);
//			nameparser.setEncoding("UTF-8");
//
//			NodeClassFilter headingFilter =new NodeClassFilter(HeadingTag.class);
//			NodeList headingList = nameparser.extractAllNodesThatMatch(headingFilter);
//			for (int i = 0; i < headingList.size(); i++) {
//				String name = "";
//				HeadingTag headingTag=(HeadingTag)headingList.elementAt(i);
//				if(headingTag.getText().startsWith(HeadingTagName)){// h*, *=1,2,3,4,5 or 6
//					name =headingTag.toPlainTextString();//get the content in the <h*> tag
//				}
//				if(name==null){
//					continue;
//				}
//				names.add(name);
//			}
//		}catch (ParserException e) {
//			e.printStackTrace();
//		}
//		return names;	
//
//		}
	
	public static String extractMovieName(String url, String HeadingTagName){
		//HashSet<String> names = new HashSet<String>();
		String name = "";
		try{
			Parser nameparser = new Parser(url);
			nameparser.setEncoding("UTF-8");

			NodeClassFilter headingFilter =new NodeClassFilter(HeadingTag.class);
			NodeList headingList = nameparser.extractAllNodesThatMatch(headingFilter);
			for (int i = 0; i < headingList.size(); i++) {
				
				HeadingTag headingTag=(HeadingTag)headingList.elementAt(i);
				if(headingTag.getText().startsWith(HeadingTagName)){// h*, *=1,2,3,4,5 or 6
					name =headingTag.toPlainTextString();//get the content in the <h*> tag
					name = name.replaceAll("[\n\t]", " ");
					name = name.replaceAll("[\']", "-");
				}
			}
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return name;	

		}
	public static HashSet<String> extractMainCast(String url){
		HashSet<String> castNames = new HashSet<String>();
		HashSet<String> castLinks = new HashSet<String>();
		try{
			Parser Linkparser = new Parser(url);
			Linkparser.setEncoding("UTF-8");
			
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
	public static int extractAge(String url){
//		HashSet<Integer> ages = new HashSet<Integer>();
		int age = 0;
		try{
			Parser ageparser = new Parser(url);
			ageparser.setEncoding("UTF-8");

			NodeFilter filter = new HasAttributeFilter("datetime");
			NodeList timeList = ageparser.extractAllNodesThatMatch(filter);
			//NodeList timeList = ageparser.extractAllNodesThatMatch(timeTagFilter);
			for (int i = 0; i < timeList.size(); i++) {
				Node timeTag=(Node)timeList.elementAt(i);
				//if(timeTag.getText()!=null){
				String birth =timeTag.getText();//get the content in the <h*> tag
				String test = birth.substring(15,16); // to avoid the case when the year = 0
				if(test.equals("1") || test.equals("2")){
					birth = birth.substring(15, 19);
				}else{
					birth = "2014";
				}
				if(birth==null){
					birth = "2014";
				}
				age = 2014 - Integer.parseInt(birth);
			}
			
		}catch (ParserException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage()+"--cannot parse");
		}
		return age;	

		}
		
	
	/**main function to test HtmlParser class
	 * 
	 */
//	public static void main(String[]args)
//	{
//		LinkFilter linkFilter = new LinkFilter(){
//			@Override
//			public boolean accept(String url) {
//				if(url.startsWith("http://www.imdb.com/"))
//				return true;
//			else
//				return false;
//			}
//		};
//		//HashSet<String> links = HtmlParser.extractLinks("http://www.imdb.com/showtimes/location?ref_=inth_ov_sh_sm", linkFilter);
//		//String link = HtmlParser.extractTheMovie("http://www.imdb.com/showtimes/title/tt1971325/?ref_=shlc_li_tt","h4");
//		//HashSet<String> names = HtmlParser.extractMovieNames("http://www.imdb.com/movies-in-theaters", "h4");
//		//String names = HtmlParser.extractMovieName("http://www.imdb.com/title/tt2245084/?ref_=inth_ov_tt", "h1");
//		int age = HtmlParser.extractAge("http://www.imdb.com/name/nm0641747/?ref_=tt_cl_t5");
//		//HashSet<String> names = HtmlParser.extractMainCast("http://www.imdb.com/title/tt2096672/?ref_=inth_ov_tt");
//		//for(int link : ages)
//			System.out.println(age);
//	}
}
