package by.vigi.scribling.workers;

import by.vigi.entity.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikita Tkachuk
 */
public class GuindaWorker extends GenericScriblingWorker
{
	private static final String SITE_URL = "http://guinda.ru/";

	private Map<String,String> cookies;

	@Override
	protected Map<String, String> parseCategoryLinks() throws IOException
	{
		Connection.Response loginForm = Jsoup.connect(SITE_URL)
				.method(Connection.Method.GET).timeout(10 * 100000)
				.execute();

		Document res = loginForm.parse();
		Map<String, String> data = new HashMap<>();
		Element cdlogin_form_login = res.getElementById("cdlogin_form_login");
		Elements children = cdlogin_form_login.select("input[type=hidden]");
		for(Element hidden : children)
		{
			String value = hidden.attr("value");
			String name = hidden.attr("name");
			data.put(name, value);
		}


		Connection connection = Jsoup.connect(SITE_URL)
				.header("User-Agent", "Mozilla/5.0")
				.data("username", "toofee")
				.data("password", "2881842")
				.data("Submit", "")
				.data("remember", "yes")
				.data("option", "com_users")
				.data("task", "user.login");
		for(Map.Entry<String, String> entry : data.entrySet())
		{
			connection.data(entry.getKey(), entry.getValue());
		}
		Document mainPage = connection.cookies(loginForm.cookies()).post();
		Document document = Jsoup.connect(SITE_URL + "/katalog").cookies(loginForm.cookies()).get();
		setCookies(loginForm.cookies());
		Elements elements = document.select(".module-content");
		Map<String, String> categoryOnLinkMap = new HashMap<>();
		for (Element element : elements)
		{
			Elements listItems = element.select("a");
			for (Element item : listItems)
			{

				String href = item.attr("href");
				String text = item.text();
				if(text.contains("Cancel") || text.contains("Лето"))
				{
					continue;
				}
				categoryOnLinkMap.put(text, SITE_URL + href);
			}
		}
		return categoryOnLinkMap;
	}

	@Override
	protected Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException
	{
		Map<String, Collection<String>> result = new HashMap<>();
		for(Map.Entry<String, String> entry : categoryOnLinkMap.entrySet())
		{
			String category = entry.getKey();
			String url = entry.getValue();
			Document document = Jsoup.connect(url).cookies(getCookies()).timeout(10*10000).get();
			Elements links = document.getElementsByClass("foto");
			Collection<String> categoryUrls = new ArrayList<>();
			for(Element link : links)
			{
				Element href = link.select("a").first();
				categoryUrls.add(SITE_URL + href.attr("href"));
			}
			result.put(category, categoryUrls);
			//Element login = document.getElementsByClass("cdlogin-logout-greeting").first();
			//System.out.println(login.text());
		}
		return result;
	}

	@Override
	protected Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException
	{
		Map<String, Collection<Product>> result = new HashMap<>();
		for(Map.Entry<String, Collection<String>> entry : categoryOnProductLinksMap.entrySet())
		{
			String category = entry.getKey();
			Collection<String> productUrls = entry.getValue();
			Collection<Product> products = new ArrayList<>(productUrls.size());
			for(String productUrl : productUrls)
			{
				Document document = Jsoup.connect(productUrl).cookies(getCookies()).timeout(10*10000).get();
				Product product = new Product();
				product.setCategory(category);
				Element imageDiv = document.getElementsByClass("foto").first();
				Element image = imageDiv.select("img").first();
				product.setUrl(SITE_URL + image.attr("src"));

				Element nameDiv = document.getElementsByClass("item-page").first();
				String name = nameDiv.getElementsByTag("h2").first().text();
				product.setName(name);

				Elements artikuls = document.getElementsByClass("artikul");
				if(artikuls.size() > 1)
				{
					Element articleDiv = artikuls.get(1);
					if(articleDiv != null)
					{
						Element article = articleDiv.select(".value").first();
						product.setArticle(article.text());
					}
				}

				Element sizeDiv = document.getElementsByClass("sizes").first();
				if(sizeDiv != null)
				{
					Element size = sizeDiv.select(".value").first();
					product.setSizes(size.text().replace(", "," "));
				}

				Element compositeDiv = document.getElementsByClass("sostav").first();
				if(compositeDiv != null)
				{
					Element composite = compositeDiv.select(".value").first();
					product.setComposite(composite.text());
				}

				String description = "";
				Element colorsDiv = document.getElementsByClass("colors").first();
				if(colorsDiv != null)
				{
					Element composite = colorsDiv.select(".value").first();
					description += composite.text() + " ";
				}
				Element comentDiv = document.getElementsByClass("coment").first();
				if(comentDiv != null)
				{
					Element coment = comentDiv.select(".value").first();
					description += coment.text();
				}
				product.setDescription(description);

				Element costDiv = document.getElementsByClass("prise").first();
				Element cost = costDiv.select(".value").first();
				product.setCost(cost.text().replace(" руб.",""));

				products.add(product);

				//product.setMetaDescription(document.select("meta[name=description]").get(0).attr("content"));
				//product.setMetaKeyword(document.select("meta[name=keywords]").first().attr("content"));

				System.out.println(product);
			}
			result.put(category, products);
		}
		return result;
	}

	@Override
	protected BigDecimal createPrice(Product product, boolean onOption)
	{
		BigDecimal finalPrice = new BigDecimal(product.getCost()).multiply(BigDecimal.valueOf(255)).multiply(KOEF).multiply(KOEF_FOR_OPT);
		BigDecimal setScale = finalPrice.divide(BigDecimal.valueOf(1000));;
		BigDecimal bigDecimal = setScale.setScale(0, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.multiply(BigDecimal.valueOf(1000));
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

	private Map<String, String> getCookies()
	{
		return cookies;
	}

	private void setCookies(Map<String, String> cookies)
	{
		this.cookies = cookies;
	}
}
