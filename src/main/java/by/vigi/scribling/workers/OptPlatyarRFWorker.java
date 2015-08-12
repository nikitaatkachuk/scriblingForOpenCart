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
public class OptPlatyarRFWorker extends GenericScriblingWorker
{

	private static final String siteUrl = "http://xn--80avchafcvf4h7a.xn--p1ai/";

	@Override
	protected Map<String, String> parseCategoryLinks() throws IOException
	{
		Document document = Jsoup.connect("http://xn--80avchafcvf4h7a.xn--p1ai/cms.php?type=page&id=18").get();
		Elements tables = document.select("table[width=550]");
		Map<String, String> categoryOnLinkMap = new HashMap<String, String>();
		for(Element table : tables)
		{
			Elements elements = table.select("a");
			for (int i = 0; i < elements.size(); i++)
			{
				Element element = elements.get(i);
				if(element.text().length() > 2 && !element.text().equalsIgnoreCase("распродажа"))
				{
					categoryOnLinkMap.put(element.text(), element.attr("abs:href"));
				}

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
					Elements elements = document.select("table[width=540]");
					for (int i = 1; i < elements.size(); i++)
					{
						Element productTable = elements.get(i);
						Product product = new Product();

						Element tbody = productTable.select("tbody").get(0);
						Element row = tbody.select("tr").get(0);
						Element informationTable = row.select("table[width=255]").first();

						if(!"Есть в наличии!".equals(informationTable.select("nobr").first().text()))
						{
							continue;
						}

						//parse name
						Element name = informationTable.select("td[height=55]").first();
						product.setName(name.text().replace("\"", ""));

						//parse article
						Element article = informationTable.select("td[width=135]").first();
						product.setArticle(article.text().replace("Art: ", ""));

						//parse cost
						Elements fonts = informationTable.select("font");
						for (Element font : fonts)
						{

							String text = font.text();
							if(text.contains("руб"))
							{
								continue;
							}
							product.setCost(text);
						}

						//parse description
						Element description = informationTable.select("p[align=left]").first();
						String descriptionText = description.text();
						product.setDescription(descriptionText);

						//Element priceElements = tbody.select("font[font-size:27px;]").get(0);
						//product.setCost(priceElements.text());
						Element imageTD = row.select("td[width=285]").first();
						Element image = imageTD.select("img").first();
						product.setUrl(siteUrl + image.attr("src"));
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

	@Override
	protected String parseImageUrl(Document document)
	{
		return null;
	}

	@Override
	protected String parseProductName(Document document)
	{
		return null;
	}

	@Override
	protected String parseArticle(Document document)
	{
		return null;
	}

	@Override
	protected String parseCost(Document document)
	{
		return null;
	}
}
