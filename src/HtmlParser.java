
import java.util.HashSet;

import org.htmlparser.NodeFilter;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParser{

	public static HashSet<String> extractLinks(String url, LinkFilter filter){
		HashSet<String> links = new HashSet<String>();
		HashSet<String> names = new HashSet<String>();
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
						String name = link.getLinkText();
						names.add(name);
					}
				}
//				else// <frame> 
//				{
//					// such as <frame src="test.html"/>
//					String frame = tag.getText();
//					int start = frame.indexOf("src=");
//					frame = frame.substring(start);
//					int end = frame.indexOf(" ");
//					if (end == -1)
//						end = frame.indexOf(">");
//					String frameUrl = frame.substring(5, end - 1);
//					if(filter.accept(frameUrl))
//						links.add(frameUrl);
//				}
			}
		}catch (ParserException e) {
			e.printStackTrace();
		}
		return names;		
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
		HashSet<String> links = HtmlParser.extractLinks("http://www.imdb.com/movies-in-theaters", linkFilter);
		for(String link : links)
			System.out.println(link);
	}
}
