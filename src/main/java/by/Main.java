package by;

import by.vigi.entity.*;
import by.vigi.service.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Nikita Tkachuk
 */
public class Main
{
	public static final int RUSSAIN_LANGUAGE_ID = 1;

	/**
	 * Entry point for start parsing. Will called from sh/bat scripts
	 * Argument parameters can be added later.
	 * @param args
	 */
	public static void main(String[] args)
	{
		ConcurrentHashMap<String,Collection<Product>> mainProductCollection;
		if(args.length > 2)
		{
			//TODO: add functional for copy
		}
		try
		{
			Map<String, String> category2Link = parseCategoryLinks();
			Map<String, Collection<String>> category2ProductLinks = parseProductLinks(category2Link);
			Map<String, Collection<Product>> category2Product = parseProducts(category2ProductLinks);
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
			ProductService productService = context.getBean(ProductService.class);
			CategoryService categoryService = context.getBean(CategoryService.class);
			for (Map.Entry<String, Collection<Product>> entry : category2Product.entrySet())
			{
				String categoryName = entry.getKey();
				Collection<Product> products = entry.getValue();
				//Collection<>
				//TODO: use service for find category!
				CategoryEntity category = categoryService.findCategoryByName(categoryName);
				if(category != null)
				{
					Collection<ProductEntity> productEntities = category.getCategoryProducts();
					for (ProductEntity entity : productEntities)
					{
						entity.setPoints(0);
						Collection<ProductAttributeEntity> attributes = entity.getProductAttributes();
						List<String> articleAttributes = attributes.stream().filter(item -> item.getAttribute().getAttributeId().equals(13) && item.getLanguageId().equals(RUSSAIN_LANGUAGE_ID)).map(ProductAttributeEntity :: getText).collect(Collectors.toList());
						for (String article : articleAttributes)
						{
							for(Product product : products)
							{
								if(product.getArticle().equals(article))
								{
									product.setAlreadyUsed(true);
									entity.setPoints(10);
								}
							}
						}
						entity.setDateModified(new Timestamp(System.currentTimeMillis()));
					}
					productService.updateProducts(productEntities);
				}
				else
				{
					category = categoryService.createCategory(categoryName);
				}
				
				List<Product> newProducts = products.stream().filter(product -> !product.isAlreadyUsed()).collect(Collectors.toList());
				startImportNewProducts(newProducts, category, productService , false);
				
				//TODO: add creating new products

				//for (Product product : products){}
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void startImportNewProducts(List<Product> newProducts,
			CategoryEntity category, ProductService productService, boolean imageWiilBeUploaded) 
	{
		for(Product product : newProducts)
		{
			ProductEntity productEntity = productService.createNewProductEntity();
			//Add description entity
			ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
			productDescriptionEntity.setProductId(productEntity.getProductId());
			productDescriptionEntity.setLanguageId(RUSSAIN_LANGUAGE_ID);
			productEntity.getProductDescriptions().add(productDescriptionEntity);
			
			//find attributes to insert
			//TODO: use spring 
			AttributeService attributeSevrice = new AttributeService();
			Collection<AttributeEntity> attributesByLanguageId = attributeSevrice.findByLanguageId(RUSSAIN_LANGUAGE_ID);
			for(AttributeEntity attribute : attributesByLanguageId)
			{
				
			}
			
			//Add product attributes 
			ProductAttributeEntity productAttributeEntity = new ProductAttributeEntity();
			productAttributeEntity.setLanguageId(RUSSAIN_LANGUAGE_ID);
			productAttributeEntity.setText(text);
			
			//Add attribute entity
			//AttributeEntity attributeEntity = new AttributeEntity();
		}
		
	}

	private static Map<String, String> parseCategoryLinks() throws IOException
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

	public static Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException
	{
		Map<String, Collection<String>> categoryOnProductLinksMap = new HashMap<String, Collection<String>>();
		for (Map.Entry<String, String> entry : categoryOnLinkMap.entrySet())
		{
			if(categoryOnProductLinksMap.size() > 2)
			{
				break;
			}
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

	public static Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException
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
					//System.out.println(product.toString());
				}
			}
			result.put(category, products);
		}
		return result;
	}

	public static Product buildProductByString(String mainInformation)
	{
		Product result = new Product();
		String [] splitedString = mainInformation.split("арт:");
		result.setName(splitedString[0].replace(". ", ""));
		int startArticleIndex = mainInformation.indexOf("арт:");
		int startAttrIndex = mainInformation.indexOf("Ткань:");
		if (startArticleIndex > -1)
		{
			result.setArticle(mainInformation.substring(startArticleIndex + 3, startAttrIndex - 1).replace(".", "").replace(":", "").replace(" ", ""));
		}
		if(startAttrIndex > - 1)
		{
			result.setAttributes(mainInformation.substring(startAttrIndex));
		}
		//result.setAttributes(splitedString[2]);
		return result;
	}

	public static void productToEntity(Product product)
	{
		ProductEntity entity = new ProductEntity();
		//entity.set
	}
}
