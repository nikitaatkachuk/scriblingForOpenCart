package by.vigi.scribling.workers;

import by.vigi.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikita Tkachuk
 */
public class AlltextileWorker extends GenericScriblingWorker
{
	protected Map<String, String> parseCategoryLinks() throws IOException
	{
		Document document = Jsoup.connect("http://alltextile.info/").get();
		Elements elements = document.select("a");
		Elements categoryLink = new Elements();
		for (Element element : elements)
		{
			String cssSelector = element.cssSelector();
			if (cssSelector.contains("#left"))
			{
				categoryLink.add(element);
			}
		}
		Map<String, String> categoryOnLinkMap = new HashMap<String, String>();
		for (int i = 1; i < categoryLink.size() - 2; i++)
		{
			Element element = categoryLink.get(i);
			categoryOnLinkMap.put(element.text(), element.attr("abs:href"));
		}
		return categoryOnLinkMap;

	}

	protected Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException
	{
		Map<String, Collection<String>> categoryOnProductLinksMap = new HashMap<String, Collection<String>>();
		for (Map.Entry<String, String> entry : categoryOnLinkMap.entrySet())
		{
			String category = entry.getKey();
			String url = entry.getValue();
			Document document = Jsoup.connect(url).get();
			Elements elements = document.select("a");
			Elements productElements = new Elements();
			for (Element element : elements)
			{
				if(element.cssSelector().contains("browseProductContainer") && element.attr("abs:href").endsWith(".html"))
				{
					productElements.add(element);
				}
			}
			for (Element element : productElements)
			{
				Collection<String> productLinks = categoryOnProductLinksMap.get(category);
				if(productLinks != null)
				{
					productLinks.add(element.attr("abs:href"));
				}
				else
				{
					productLinks = new ArrayList<String>(productElements.size());
					productLinks.add(element.attr("abs:href"));
					categoryOnProductLinksMap.put(category, productLinks);
				}
			}
		}
		return categoryOnProductLinksMap;
	}

	public Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException
	{
		Map<String, Collection<Product>> result = new HashMap<>();
		for (Map.Entry<String, Collection<String>> entry : categoryOnProductLinksMap.entrySet())
		{
			Collection<Product> products = new ArrayList<>(entry.getValue().size());
			String category = entry.getKey();
			for (String url : entry.getValue())
			{
				Document document = Jsoup.connect(url).get();
				Elements elements = document.getElementsByClass("product_desc");
				if(!elements.isEmpty())
				{
					String mainInformation = elements.get(0).text();
					Product product = buildProductByString(mainInformation);
					product.setCategory(category);
					Elements priceElements = document.getElementsByClass("hs_product_price");
					if(!priceElements.isEmpty())
					{
						String costString = priceElements.get(0).text();
						int centIndex = costString.lastIndexOf(".00");
						if(centIndex > - 1)
						{
							product.setCost(costString.substring(centIndex - 3, centIndex));
							products.add(product);
						}
					}
					Element imageElement = document.getElementById("highslide-html");
					product.setUrl(imageElement.attr("src"));
					System.out.println(product.toString());
				}
			}
			result.put(category, products);
		}
		return result;
	}

	private Product buildProductByString(String mainInformation)
	{
		Product result = new Product();
		String [] splitedString = mainInformation.split("арт:");
		result.setName(splitedString[0].replace(". ", EMPTY_STRING));
		int startArticleIndex = mainInformation.indexOf("арт:");
		int startAttrIndex = mainInformation.indexOf("Ткань:");
		if (startArticleIndex > -1)
		{
			result.setArticle(mainInformation.substring(startArticleIndex + 3, startAttrIndex - 1).replace(".", EMPTY_STRING).replace(":", EMPTY_STRING).replace(" ", EMPTY_STRING));
		}
		if(startAttrIndex > - 1)
		{
			result.setAttributes(mainInformation.substring(startAttrIndex + 7));
		}
		return result;
	}

	@Override
	public void run()
	{
		try
		{
			Map<String, String> category2Link = parseCategoryLinks();
			Map<String, Collection<String>> category2ProductLinks = parseProductLinks(category2Link);
			Map<String, Collection<Product>> category2Product = parseProducts(category2ProductLinks);
			startDataBaseJob(category2Product);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}