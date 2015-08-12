package by.vigi.scribling.workers;

import by.vigi.entity.*;
import by.vigi.service.CategoryService;
import by.vigi.service.OptionValueService;
import by.vigi.service.ProductOptionService;
import by.vigi.service.ProductService;
import by.vigi.utils.FileDownloader;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nikita Tkachuk
 */
public abstract class GenericScriblingWorker implements Runnable
{
	public static final int OPT_KOEF = 1;
	public static final int ROZNICA_KOEF = 2;
	protected boolean forOpt = false;
	protected int currentMargin;
	protected static final int RUSSAIN_LANGUAGE_ID = 1;
	protected static final int ARTICLE_ATTRIBUTE_ID = 13;
	protected static final int COST_ATTRIBUTE_ID = 15;
	protected static final int NAME_ATTRIBUTE_ID = 12;
	protected static final int COMPOSITION_ATTRIBUTE_ID = 14;
	protected static final int DESCRIPTION_ATTRIBUTE_ID = 16;
	protected static final String EMPTY_STRING = "";
	protected static final BigDecimal KOEF_FOR_OPT = new BigDecimal(1.2);
	protected static final BigDecimal KOEF = new BigDecimal(1.6);
	protected static final BigDecimal RUSSION_RUBLE_COURSE = new BigDecimal(270);
	protected static final int OPTION_ID = 11;
	protected Map<String, Integer> sizeOnOptionIdValue;


	public GenericScriblingWorker()
	{

	}

	public GenericScriblingWorker(boolean forOpt, int koefMargin)
	{
		this.forOpt = forOpt;
		this.currentMargin = koefMargin;
	}

	/**
	 * Parse links on category products form catalog.
	 * Now connection and authorization on site using in this method (Mb will be replaced in future)
	 * @return  Map Category ->  Category link on site. For example, "Shirt" -> "http://site.com/catalog/shirts"
	 * @throws IOException
	 */

	protected abstract Map<String, String> parseCategoryLinks() throws IOException;

	protected abstract Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException;

	protected abstract Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException;

	protected abstract String parseImageUrl(Document document);
	protected abstract String parseProductName(Document document);
	protected abstract String parseArticle(Document document);
	protected abstract String parseCost(Document document);

	protected void startDataBaseJob(Map<String, Collection<Product>> category2Product)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
		ProductService productService = context.getBean(ProductService.class);
		CategoryService categoryService = context.getBean(CategoryService.class);
		ProductOptionService productOptionService = context.getBean(ProductOptionService.class);
		OptionValueService optionValueService = context.getBean(OptionValueService.class);
		initOptionsId(optionValueService);
		for (Map.Entry<String, Collection<Product>> entry : category2Product.entrySet())
		{
			String categoryName = entry.getKey();
			Collection<Product> products = entry.getValue();
			//Collection<>
			//TODO: use service for find category!
			CategoryEntity category = categoryService.findCategoryByName(categoryName);
			if (category != null)
			{
				Collection<ProductEntity> productEntities = category.getCategoryProducts();
				for (ProductEntity entity : productEntities)
				{
					Collection<ProductAttributeEntity> attributes = entity.getProductAttributes();
					List<String> articleAttributes = attributes.stream().filter(item -> item.getAttribute().getAttributeId().equals(13) && item.getLanguageId().equals(RUSSAIN_LANGUAGE_ID)).map(ProductAttributeEntity::getText).collect(Collectors.toList());
					for (String article : articleAttributes)
					{
						for (Product product : products)
						{
							if (product.getArticle().equals(article))
							{
								product.setAlreadyUsed(true);
								entity.setQuantity(20);
								entity.setPrice(createPrice(product, false));
								entity.setDateModified(new Timestamp(System.currentTimeMillis()));
							}
						}
					}
					if (!isDateToday(entity.getDateModified().getTime()))
					{
						entity.setQuantity(0);
					}
				}
				productService.updateProducts(productEntities);
			}
			else
			{
				category = categoryService.createCategory(categoryName);
			}

			List<Product> newProducts = products.stream().filter(product -> !product.isAlreadyUsed()).collect(Collectors.toList());
			startImportNewProducts(newProducts, category, productService, categoryService,productOptionService, true);
		}
	}


	protected synchronized void startImportNewProducts(List<Product> newProducts, CategoryEntity category, ProductService productService, CategoryService categoryService, ProductOptionService productOptionService, boolean imageWillBeDownloaded)
	{
		for (Product product : newProducts)
		{
			if(product.getArticle() == null)
			{
				continue;
			}
			ProductEntity productEntity = productService.createNewProductEntity();

			if(forOpt && currentMargin == OPT_KOEF)
			{
				List<String> sizes = parseSizes(product.getSizes());
				productEntity.setModel(product.getArticle() + " Продается упаковками по " + sizes.size() + " шт. " + sizes.get(0) + "-" + sizes.get(sizes.size() - 1) + " размеров " );
			}
			else
			{
				productEntity.setModel(product.getArticle());
			}
			productEntity.setStatus(true);
			productEntity.setQuantity(20);
			productEntity.getCategories().add(category);

			if(product.getCost() != null)
			{
				productEntity.setPrice(createPrice(product, false));
			}

			if(!forOpt)
			{
				if(product.getSizes() != null && !EMPTY_STRING.equals(product.getSizes()))
				{
					//create option
					ProductOptionEntity productOption = new ProductOptionEntity();
					productOption.setProductId(productEntity.getProductId());
					productOption.setOptionId(OPTION_ID);
					productOption.setRequired(true);
					productOption.setOptionValue(EMPTY_STRING);
					productOption = productOptionService.createProductOption(productOption);

					List<String> sizes = parseSizes(product.getSizes());
					for (String size : sizes)
					{
						ProductOptionValueEntity productOptionValue = new ProductOptionValueEntity();
						productOptionValue.setProductOptionId(productOption.getProductOptionId());
						productOptionValue.setProductId(productEntity.getProductId());
						productOptionValue.setOptionId(OPTION_ID);
						Integer optionValueId = sizeOnOptionIdValue.get(size);
						if (optionValueId == null)
						{
							continue;
						}
						productOptionValue.setOptionValueId(optionValueId);
						productOptionValue.setQuantity(20);
						productOptionValue.setSubtract(true);
						productOptionValue.setPrice(createPrice(product, false));
						productOptionValue.setPricePrefix("+");
						productOptionValue.setPoints(0);
						productOptionValue.setPointsPrefix("+");
						productOptionValue.setWeight(BigDecimal.ZERO);
						productOptionValue.setWeightPrefix("+");
						productOptionService.createProductOptionValue(productOptionValue);
					}
				}
			}
			else
			{
				ProductOptionEntity productOption = new ProductOptionEntity();
				productOption.setProductId(productEntity.getProductId());
				productOption.setOptionId(OPTION_ID);
				productOption.setRequired(true);
				productOption.setOptionValue(EMPTY_STRING);
				productOption = productOptionService.createProductOption(productOption);

				List<String> sizes = parseSizes(product.getSizes());
				if(!sizes.isEmpty())
				{
					Integer optionValueId = sizeOnOptionIdValue.get(sizes.get(0) + "-" + sizes.get(sizes.size() - 1));
					if(optionValueId != null)
					{
						ProductOptionValueEntity  productOptionValue = new ProductOptionValueEntity();
						productOptionValue.setProductOptionId(productOption.getProductOptionId());
						productOptionValue.setProductId(productEntity.getProductId());
						productOptionValue.setOptionId(OPTION_ID);
						productOptionValue.setOptionValueId(optionValueId);
						productOptionValue.setQuantity(100);
						productOptionValue.setSubtract(true);
						productOptionValue.setPrice(createPrice(product, true));
						productOptionValue.setPricePrefix("+");
						productOptionValue.setPoints(0);
						productOptionValue.setPointsPrefix("+");
						productOptionValue.setWeight(BigDecimal.ZERO);
						productOptionValue.setWeightPrefix("+");
						productOptionService.createProductOptionValue(productOptionValue);
					}
				}
			}

			//Add description entity
			ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
			productDescriptionEntity.setProductId(productEntity.getProductId());
			productDescriptionEntity.setLanguageId(RUSSAIN_LANGUAGE_ID);
			productDescriptionEntity.setName(product.getName() != null ? product.getName() : EMPTY_STRING);
			productDescriptionEntity.setDescription(product.getDescription() != null ? product.getDescription() : EMPTY_STRING);
			String metaDescription = product.getMetaDescription();
			if(metaDescription == null || metaDescription.length() > 255)
			{
				productDescriptionEntity.setMetaDescription(EMPTY_STRING);
			}
			else
			{
				productDescriptionEntity.setMetaDescription(metaDescription);
			}
			String metaKeyword = product.getMetaKeyword();
			if(metaKeyword == null || metaKeyword.length() > 255)
			{
				productDescriptionEntity.setMetaKeyword(EMPTY_STRING);
			}
			else
			{
				productDescriptionEntity.setMetaKeyword(metaKeyword);
			}
			productDescriptionEntity.setSeoH1(EMPTY_STRING);
			productDescriptionEntity.setSeoTitle(EMPTY_STRING);
			productDescriptionEntity.setTag(EMPTY_STRING);
			productEntity.getProductDescriptions().add(productDescriptionEntity);

			//TODO: incapsulate in method

			Collection<ProductAttributeEntity> productAttributes = new ArrayList<>();
			ProductAttributeEntity articleAttributeEntity = new ProductAttributeEntity();
			articleAttributeEntity.setLanguageId(RUSSAIN_LANGUAGE_ID);
			articleAttributeEntity.setAttributeId(ARTICLE_ATTRIBUTE_ID);
			articleAttributeEntity.setText(product.getArticle());
			articleAttributeEntity.setProductId(productEntity.getProductId());
			productAttributes.add(articleAttributeEntity);

			productEntity.getProductAttributes().addAll(productAttributes);

			String composite = product.getComposite();
			if(composite != null)
			{
				ProductAttributeEntity compositionAttribute = createComposite(productEntity, composite);
				productEntity.getProductAttributes().add(compositionAttribute);
			}

			String description = product.getDescription();
			if(description != null)
			{
				if(description.toLowerCase().contains("нет в наличии"))
				{
					productEntity.setQuantity(0);
				}
				ProductAttributeEntity descriptionAttribute = createDescription(productEntity, description);
				productEntity.getProductAttributes().add(descriptionAttribute);
			}

			if (imageWillBeDownloaded)
			{
				try
				{
					File image = FileDownloader.uploadFile(product.getUrl(), forOpt);
					String absolutePath = image.getAbsolutePath();
					int indexDataFolder = absolutePath.indexOf("data");
					productEntity.setImage(absolutePath.substring(indexDataFolder));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			category.getCategoryProducts().add(productEntity);
			productService.updateProducts(Collections.singletonList(productEntity));
		}
		categoryService.updateCategory(category);
	}

	private ProductAttributeEntity createDescription(ProductEntity productEntity, String description)
	{
		ProductAttributeEntity descriptionAttribute = new ProductAttributeEntity();
		descriptionAttribute.setLanguageId(RUSSAIN_LANGUAGE_ID);
		descriptionAttribute.setText(description);
		descriptionAttribute.setProductId(productEntity.getProductId());
		descriptionAttribute.setAttributeId(DESCRIPTION_ATTRIBUTE_ID);
		return descriptionAttribute;
	}

	private ProductAttributeEntity createComposite(ProductEntity productEntity, String composite)
	{
		ProductAttributeEntity compositionAttribute = new ProductAttributeEntity();
		compositionAttribute.setLanguageId(RUSSAIN_LANGUAGE_ID);
		compositionAttribute.setText(composite);
		compositionAttribute.setProductId(productEntity.getProductId());
		compositionAttribute.setAttributeId(COMPOSITION_ATTRIBUTE_ID);
		return compositionAttribute;
	}

	protected BigDecimal createPrice(Product product, boolean onOption)
	{
//		BigDecimal finalPrice;
//		if(forOpt && onOption)
//		{
//			finalPrice = new BigDecimal(product.getCost()).multiply(RUSSION_RUBLE_COURSE).multiply(KOEF_FOR_OPT).multiply(KOEF_FOR_OPT).multiply(BigDecimal.valueOf(5));
//		}
//		else if(forOpt && !onOption)
//		{
//			finalPrice = new BigDecimal(product.getCost()).multiply(RUSSION_RUBLE_COURSE).multiply(KOEF_FOR_OPT).multiply(KOEF_FOR_OPT);
//		}
//		else
//		{
//			if(currentMargin == OPT_KOEF)
//			{
//				finalPrice = new BigDecimal(product.getCost()).multiply(RUSSION_RUBLE_COURSE).multiply(KOEF_FOR_OPT).multiply(KOEF_FOR_OPT);
//			}
//			else
//			{
//				finalPrice = new BigDecimal(product.getCost()).multiply(RUSSION_RUBLE_COURSE).multiply(KOEF).multiply(KOEF);
//			}
//		}
//		BigDecimal setScale = finalPrice.divide(BigDecimal.valueOf(1000));;
//		BigDecimal bigDecimal = setScale.setScale(0, BigDecimal.ROUND_HALF_UP);
//		finalPrice = bigDecimal.multiply(BigDecimal.valueOf(1000));
		BigDecimal finalPrice = new BigDecimal(product.getCost()).multiply(BigDecimal.valueOf(252)).multiply(KOEF_FOR_OPT).multiply(BigDecimal.valueOf(1.23));
		BigDecimal setScale = finalPrice.divide(BigDecimal.valueOf(1000));
		BigDecimal bigDecimal = setScale.setScale(0, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.multiply(BigDecimal.valueOf(1000));
		//return finalPrice;
	}

	private boolean isDateToday(long milliSeconds)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);

		Date getDate = calendar.getTime();

		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date startDate = calendar.getTime();

		return getDate.compareTo(startDate) > 0;

	}

	private List<String> parseSizes(String sizes)
	{
		//List<String> result = new ArrayList<>();
		return Arrays.asList(sizes.split(" "));
	}

	private void initOptionsId(OptionValueService optionValueService)
	{
		sizeOnOptionIdValue = new HashMap<>();
		//TODO: Replace filters to DAO impl to query
		Collection<OptionValueDescriptionEntity> descriptions = optionValueService.findAll();
		sizeOnOptionIdValue = descriptions.stream().filter(description -> description.getLanguageId().equals(RUSSAIN_LANGUAGE_ID))
				.filter(description -> description.getOptionId().equals(OPTION_ID))
				.collect(Collectors.toMap(OptionValueDescriptionEntity::getName, OptionValueDescriptionEntity::getOptionValueId));

//		if(forOpt)
//		{
//			sizeOnOptionIdValue.put("40-42", 46);
//			sizeOnOptionIdValue.put("40-44", 47);
//			sizeOnOptionIdValue.put("40-46", 48);
//			sizeOnOptionIdValue.put("40-48", 49);
//			sizeOnOptionIdValue.put("40-50", 50);
//		}
//		else
//		{
//			//to vigi.by
//			Integer startSize = 36;
//			for (int i = 0; i < 13; i++)
//			{
//				sizeOnOptionIdValue.put(startSize.toString(), 46 + i);
//				startSize = startSize + 2;
//			}
			//to opt.vigi.by
//			Integer startSize = 40;
//			for (int i = 0; i < 7; i++)
//			{
//				sizeOnOptionIdValue.put(startSize.toString(), 51 + i);
//				startSize = startSize + 2;
//			}
//		}

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
