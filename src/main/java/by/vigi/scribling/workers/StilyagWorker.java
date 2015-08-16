package by.vigi.scribling.workers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.vigi.entity.Product;

public class StilyagWorker extends GenericScriblingWorker {

	private static final String SITE_URL = "http://stilyag.ru/";
	
	@Override
	protected Map<String, String> parseCategoryLinks() throws IOException 
	{
		LOGGER.info("Start Stilyag worker");
		Map<String, String> category2CategoryUrl = new HashMap<String, String>();

		Document document = Jsoup.connect(SITE_URL).timeout(10 * 100000).get();
		
		Elements linksDivs = document.getElementsByClass("menublock");
		//use only two first elements, because other elements doesn't
		//contains information about products
		for(int i = 0; i < 2; i++)
		{
			Element linksDiv = linksDivs.get(i);
			String postfix = linksDiv.getElementsByClass("openmenu").first().text();
			Elements categoryUrlsDiv = linksDiv.getElementsByClass("catalog-section-list");
			if(!categoryUrlsDiv.isEmpty() )
			{
				continue;
			}
			Elements categoryUlrs = categoryUrlsDiv.first().select("a");
			for(Element categoryUrl : categoryUlrs)
			{
				category2CategoryUrl.put(categoryUrl.text(), categoryUrl.attr("abs:href"));
			}
			
		}
		return category2CategoryUrl;
	}

	@Override
	protected Map<String, Collection<String>> parseProductLinks(
			Map<String, String> categoryOnLinkMap) throws IOException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, Collection<Product>> parseProducts(
			Map<String, Collection<String>> categoryOnProductLinksMap)
			throws IOException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String parseImageUrl(Document document) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String parseProductName(Document document) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String parseArticle(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String parseCost(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

}
