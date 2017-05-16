
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Search {

	//This class provides possibility to search without using the API
	
	public String searchTerm;
        public Search(String search)
{
searchTerm=search;

//Taking search term input from console
		
		
		
	
		int num = 10;

		
		String searchURL = "https://github.com/search?utf8=✓&q=platform%3A+"+ searchTerm+"ref=simplesearch";
		//without proper User-Agent, we will get 403 error
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		
		//the class of the items we need to derive is topics-row...
		Elements results = doc.getElementsByClass("topics-row-container col-9 d-inline-flex flex-wrap flex-items-center f6 my -l");

		for (Element result : results) {
			String linkHref = result.attr("href");
			String linkText = result.text();
			System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
		}
	}


	
		

}
