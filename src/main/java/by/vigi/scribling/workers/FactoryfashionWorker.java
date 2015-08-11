package by.vigi.scribling.workers;

import by.vigi.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Nikita Tkachuk
 */
public class FactoryfashionWorker extends GenericScriblingWorker
{
	private static final String siteUrl = "http://factoryfashion.ru/";

	@Override
	protected Map<String, String> parseCategoryLinks() throws IOException
	{
		Document document = Jsoup.connect("http://factoryfashion.ru/shop").get();
		Elements categories = document.select(".catalog_item");
		Map<String, String> categoryOnLinkMap = new HashMap<String, String>();
		for(Element category : categories)
		{
			Elements elements = category.select("a");
			for (int i = 0; i < elements.size(); i++)
			{
				Element element = elements.get(i);
				categoryOnLinkMap.put(element.text(), element.attr("abs:href"));

			}
		}
		return categoryOnLinkMap;

	}


	@Override
	protected Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException
	{
		Map<String, Collection<String>> productsLinks = new HashMap<>();
		for (Map.Entry<String, String> entry : categoryOnLinkMap.entrySet())
		{
			productsLinks.put(entry.getKey(), Collections.singletonList(entry.getValue()));
		}
		return productsLinks;
	}

	@Override
	public Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException
	{
		Map<String, Collection<Product>> result = new HashMap<>();
		for (Map.Entry<String, Collection<String>> entry : categoryOnProductLinksMap.entrySet())
		{
			Collection<Product> products = new ArrayList<>(entry.getValue().size());
			String category = entry.getKey();
			for (String url : entry.getValue())
			{
				try
				{
					Document document = Jsoup.connect(url).get();
					Elements elements = document.select(".window");
					for (int i = 0; i < elements.size(); i++)
					{
						Element productTable = elements.get(i);
						Product product = new Product();

						//parse name
						Element name = productTable.select(".artikyl").first();
						String productName = name.text();
						product.setName(productName.replace("\"", ""));

						//parse article
						int firstArticleSymbol = productName.lastIndexOf(":");
						product.setArticle(productName.substring(firstArticleSymbol + 1));

						//parse cost
						String mainInfo = productTable.select(".cena").first().text();
						int costIndex = mainInfo.indexOf("Цена:");
						int costFinishIndex = mainInfo.indexOf(" руб");
						if(costIndex > -1 && costFinishIndex > -1)
						{
							product.setCost(mainInfo.substring(costIndex + 7, costFinishIndex));
						}
						//parse description
						int sizeIndex = mainInfo.indexOf("Определить размер");
						if(sizeIndex > -1 && costFinishIndex > -1)
						{
							product.setDescription(mainInfo.substring(sizeIndex + 17, costIndex));
						}

						//Element priceElements = tbody.select("font[font-size:27px;]").get(0);
						//product.setCost(priceElements.text());
						Element imageTD = productTable.select(".picture").first();
						Element image = imageTD.select("a").first();
						product.setUrl(siteUrl + image.attr("href"));
						products.add(product);
						System.out.println(product.toString());
					}
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
			result.put(category, products);
		}
		return result;
	}
}
