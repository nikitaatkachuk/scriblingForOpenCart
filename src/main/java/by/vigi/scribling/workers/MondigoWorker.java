package by.vigi.scribling.workers;

import by.vigi.entity.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nikita Tkachuk
 */
public class MondigoWorker extends GenericScriblingWorker
{
	private static final String SITE_URL = "http://www.mondigo.ru";

	private Map<String,String> cookies;

	@Override
	protected Map<String, String> parseCategoryLinks() throws IOException
	{
		Connection.Response loginForm = Jsoup.connect(SITE_URL)
				.method(Connection.Method.GET).timeout(10 * 100000)
				.execute();

		Connection connection = Jsoup.connect(SITE_URL + "/auth/ajax_auth.php")
				.header("User-Agent", "Mozilla/5.0")
				.data("login", "dimasin16@mail.ru")
				.data("password", "447917658")
				.data("mode", "login")
				.data("remember", "Y");
		Document mainPage = connection.cookies(loginForm.cookies()).post();
		Document womanCatalog = Jsoup.connect(SITE_URL + "/catalog/zhenskaya_odezhda/").cookies(loginForm.cookies()).get();
		Document manCatalog = Jsoup.connect(SITE_URL + "/catalog/modezhda/").cookies(loginForm.cookies()).get();
		setCookies(loginForm.cookies());

		List<Document> documents = new ArrayList<>();
		documents.add(womanCatalog);
		documents.add(manCatalog);

		Map<String, String> categoryOnLinkMap = new HashMap<>();

		//TODO: mb configured it's in another place
		//This category should be ignored.

		Collection<String> ignoreList = new ArrayList<>();
		ignoreList.add("Новинки");
		ignoreList.add("Вечерний коктейль");
		ignoreList.add("Вязаный трикотаж");
		ignoreList.add("Костюмы");
		ignoreList.add("Школьная форма");
		ignoreList.add("Шаровары");
		ignoreList.add("Капри");
		ignoreList.add("Шорты");
		ignoreList.add("Куртки");
		ignoreList.add("Спортивные костюмы");

		for(Document document : documents)
		{
			Element categoryLinksList = document.select(".list-inner").first();
			Elements links = categoryLinksList.select(".left-box__item");
			for (Element link : links)
			{
				Elements listItems = link.select("a");
				for (Element item : listItems)
				{

					String href = item.attr("href");
					String text = item.text();
					if(ignoreList.contains(text))
					{
						continue;
					}
					String existedCategory = categoryOnLinkMap.get(text);
					if(existedCategory != null)
					{
						categoryOnLinkMap.remove(text);
						categoryOnLinkMap.put("Женские " + text, existedCategory);
						categoryOnLinkMap.put("Мужские " + text, SITE_URL + href);
					}
					else
					{
						categoryOnLinkMap.put(text, SITE_URL + href);
					}
				}
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
			String pageUrl = url + "?show=102";
			Collection<String> productUrls = new ArrayList<>(2000);
			//insert info by reference because method can use recursion calling for pagination
			parseProductLinksWithPagination(pageUrl, productUrls);
			result.put(category, productUrls);
		}
		return result;
	}

	/**
	 * Recursive parsing product links with pagination. Parse firs page by @pageUrl.
	 * If @pageUrl contain pagination, recursive call on next page. Collection with links using by reference.
	 * @param pageUrl First page
	 * @param productUrls reference on Collection links
	 * @throws IOException If connection problems
	 */

	private void parseProductLinksWithPagination(String pageUrl, Collection<String> productUrls) throws IOException
	{
		Document document = Jsoup.connect(pageUrl).cookies(getCookies()).timeout(10*1000).get();
		Elements links = document.getElementsByClass("catalog__link");
		productUrls.addAll(links.stream().map(link -> SITE_URL + link.attr("href")).collect(Collectors.toList()));
		Elements nextPage = document.getElementsByClass("btn-paging_right");
		if(!nextPage.isEmpty())
		{
			parseProductLinksWithPagination(SITE_URL + nextPage.first().attr("href"), productUrls);
		}
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
				Document document = Jsoup.connect(productUrl).cookies(getCookies()).timeout(10*1000).get();
				Product product = new Product();
				product.setCategory(category);

				//parse image
				product.setUrl(parseImageUrl(document));

				//parse name
				product.setName(parseProductName(document));

				Elements mainInformationElements = document.getElementsByClass("box__row");
				for (Element infoElement : mainInformationElements)
				{
					String articleString = "Артикул: ";
					String compositeString = "Состав:";
					String value = infoElement.text();
					if(value.contains(articleString))
					{
						product.setArticle(value.replace(articleString, ""));
					}
					else if (value.contains(compositeString))
					{
						product.setComposite(value.replace(compositeString,""));
					}
				}

				Elements sizesAndPriceTableRows = document.getElementsByClass("tr-offer").select(".active");
				StringBuilder builder = new StringBuilder();
				for(Element sizesAndPriceTableRow : sizesAndPriceTableRows)
				{
					Elements sizes = sizesAndPriceTableRow.getElementsByClass("size-label__body");
					if(!sizes.isEmpty())
					{
						builder.append(sizes.first().text()).append(" ");
					}
				}
				if (!sizesAndPriceTableRows.isEmpty())
				{
					Elements prices = sizesAndPriceTableRows.first().getElementsByClass("itemPrice");
					if(prices != null && !prices.isEmpty())
					{
						product.setCost(prices.first().text());
					}
				}

				product.setSizes(builder.toString().toUpperCase());

				product.setMetaDescription(document.select("meta[name=description]").get(0).attr("content"));
				product.setMetaKeyword(document.select("meta[name=keywords]").first().attr("content"));

				products.add(product);


				System.out.println(product);
			}
			result.put(category, products);
		}
		return result;
	}

	protected String parseProductName(Document document)
	{
		Elements nameDivs = document.getElementsByClass("col_small");
		if(!nameDivs.isEmpty())
		{
			Element nameDiv = nameDivs.first();
			Elements h1Tags = nameDiv.getElementsByTag("h1");
			if(!h1Tags.isEmpty())
			{
				return h1Tags.first().text();
			}
		}
		return null;
	}

	@Override
	protected String parseImageUrl(Document document)
	{
		Element image = document.getElementById("img-zoom");
		return SITE_URL + image.attr("src");
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

	@Override
	protected BigDecimal createPrice(Product product, boolean onOption)
	{
		BigDecimal finalPrice = new BigDecimal(product.getCost()).multiply(BigDecimal.valueOf(252)).multiply(KOEF_FOR_OPT).multiply(BigDecimal.valueOf(1.23));
		BigDecimal setScale = finalPrice.divide(BigDecimal.valueOf(1000));
		BigDecimal bigDecimal = setScale.setScale(0, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.multiply(BigDecimal.valueOf(1000));
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
