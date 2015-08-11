package by.vigi.scribling.workers;

import by.vigi.entity.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Created by Nikita Tkachuk
 */
public class MarimayWorker extends GenericScriblingWorker
{
	private static final String SITE_URL = "http://marimay.ru";

	private Map<String,String> cookies;

	//Use for parsing product's cost from category page, because on product page cost loaded by js
	private Map<String, String> productLinkToCost = new HashMap<>();

	public MarimayWorker(boolean forOpt, int marginKoef)
	{
		super(forOpt, marginKoef);
	}

	public MarimayWorker()
	{
	}

	@Override
	protected Map<String, String> parseCategoryLinks() throws IOException
	{
		Connection.Response res = Jsoup.connect("http://marimay.ru/index.php?login=yes")
				.data("AUTH_FORM", "y")
				.data("Login", "Войти")
				.data("TYPE", "AUTH")
				.data("USER_LOGIN", "dimasin16@mail.ru")
				.data("USER_PASSWORD", "1523dimasin1523")
				.data("backurl", "/index.php")
				.method(Connection.Method.POST).execute();
		Document document = res.parse();
		setCookies(res.cookies());
		Elements elements = document.select("ul");
		Map<String, String> categoryOnLinkMap = new HashMap<>();
		for(Element element : elements)
		{
			if (element.cssSelector().contains("root-item"))
			{
				Elements listItems = element.select("a");
				for (Element item : listItems)
				{
					categoryOnLinkMap.put(item.text(), SITE_URL + item.attr("href"));
				}
			}
		}
		return categoryOnLinkMap;
	}

	@Override
	protected Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException
	{
		Map<String, Collection<String>> categoryOnProductLinksMap = new HashMap<String, Collection<String>>();
		for (Map.Entry<String, String> entry : categoryOnLinkMap.entrySet())
		{
			String category = entry.getKey();
			String url = entry.getValue();
			Document document = Jsoup.connect(url).cookies(getCookies()).get();
			Elements productContainers = document.getElementsByClass("bx_catalog_item_container");
			for (Element element : productContainers)
			{
				Element productTitles = element.getElementsByClass("bx_catalog_item_title").first();
				String productCost = element.getElementsByClass("single-item-price").first().text().replace(" руб.", "");
				Collection<String> productLinks = categoryOnProductLinksMap.get(category);
				String productLink = productTitles.child(0).attr("abs:href");
				productLinkToCost.put(productLink, productCost);
				if(productLinks != null)
				{
					productLinks.add(productLink);
				}
				else
				{
					productLinks = new ArrayList<String>(productContainers.size());
					productLinks.add(productLink);
					categoryOnProductLinksMap.put(category, productLinks);
				}
			}
		}
		return categoryOnProductLinksMap;
	}

	@Override
	protected Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException
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
					int relativePathStartIndex = url.indexOf("/catalog");
//					Connection.Response res = Jsoup.connect(url)
//							.data("AUTH_FORM", "y")
//							.data("Login", "Войти")
//							.data("TYPE", "AUTH")
//							.data("USER_LOGIN", "dimasin16@mail.ru")
//							.data("USER_PASSWORD", "1523dimasin1523")
//							.data("backurl", url.substring(relativePathStartIndex) + "index.php")
//							.userAgent("Mozilla/5.0")
//							.method(Connection.Method.POST).execute();
//					Document document = res.parse();
					Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").cookies(getCookies()).get();
					Product product = new Product();
					Element productTitle = document.getElementsByClass("title-str").first();

					String name = productTitle.text();
					product.setName(name);

					product.setArticle(name.split(" ")[0]);

					Element descriptionElement = document.getElementsByClass("bx_item_description").first();
					if(descriptionElement != null)
					{
						product.setDescription(descriptionElement.text());
					}
					Element compositeElement = document.getElementsByClass("item_info_section").get(1);
					if(compositeElement != null)
					{
						product.setComposite(compositeElement.child(0).text());
					}
					product.setCost(productLinkToCost.get(url));

					product.setMetaDescription(document.select("meta[name=description]").get(0).attr("content"));
					product.setMetaKeyword(document.select("meta[name=keywords]").first().attr("content"));
					product.setCategory(category);
					product.setSizes("40 42 44 46 48");
					Element imageBlock = document.getElementsByClass("bx_bigimages_aligner").first();
					Element imageElement = imageBlock.select("img").first();
					product.setUrl("http:" + imageElement.attr("src"));
					products.add(product);
					System.out.println(product.toString());
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



	protected Map<String, String> getCookies()
	{
		return cookies;
	}

	protected void setCookies(Map<String, String> cookies)
	{
		this.cookies = cookies;
	}
}
