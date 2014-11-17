//act as a interface, implemented in the HtmlParser.java
public interface LinkFilter {
	public boolean accept(String url);
}
