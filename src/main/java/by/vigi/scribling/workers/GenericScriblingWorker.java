package by.vigi.scribling.workers;

import by.vigi.entity.*;
import by.vigi.service.CategoryService;
import by.vigi.service.ProductOptionService;
import by.vigi.service.ProductService;
import by.vigi.utils.FileDownloader;
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
	protected static final int RUSSAIN_LANGUAGE_ID = 1;
	protected static final int ARTICLE_ATTRIBUTE_ID = 13;
	protected static final int COST_ATTRIBUTE_ID = 15;
	protected static final int NAME_ATTRIBUTE_ID = 12;
	protected static final int COMPOSITION_ATTRIBUTE_ID = 14;
	protected static final int DESCRIPTION_ATTRIBUTE_ID = 16;
	protected static final String EMPTY_STRING = "";
	protected static final BigDecimal KOEF = new BigDecimal(1.2);
	protected static final BigDecimal RUSSION_RUBLE_COURSE = new BigDecimal(270);
	protected static final int OPTION_ID = 11;
	protected Map<String, Integer> sizeOnOptionIdValue;


	protected abstract Map<String, String> parseCategoryLinks() throws IOException;

	protected abstract Map<String, Collection<String>> parseProductLinks(Map<String, String> categoryOnLinkMap) throws IOException;

	protected abstract Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> categoryOnProductLinksMap) throws IOException;

	protected void startDataBaseJob(Map<String, Collection<Product>> category2Product)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
		ProductService productService = context.getBean(ProductService.class);
		CategoryService categoryService = context.getBean(CategoryService.class);
		ProductOptionService productOptionService = context.getBean(ProductOptionService.class);
		initOptionsId();
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
								entity.setQuantity(10);
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


			productEntity.setModel(product.getArticle());
			productEntity.setStatus(true);
			productEntity.setQuantity(20);
			productEntity.getCategories().add(category);


			if(product.getSizes() == null || EMPTY_STRING.equals(product.getSizes()))
			{
				if(product.getCost() != null)
				{
					productEntity.setPrice(createPrice(product));
				}
			}
			else
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
					ProductOptionValueEntity  productOptionValue = new ProductOptionValueEntity();
					productOptionValue.setProductOptionId(productOption.getProductOptionId());
					productOptionValue.setProductId(productEntity.getProductId());
					productOptionValue.setOptionId(OPTION_ID);
					Integer optionValueId = sizeOnOptionIdValue.get(size);
					if(optionValueId == null)
					{
						continue;
					}
					productOptionValue.setOptionValueId(optionValueId);
					productOptionValue.setQuantity(10);
					productOptionValue.setSubtract(true);
					productOptionValue.setPrice(createPrice(product));
					productOptionValue.setPricePrefix("+");
					productOptionValue.setPoints(0);
					productOptionValue.setPointsPrefix("+");
					productOptionValue.setWeight(BigDecimal.ZERO);
					productOptionValue.setWeightPrefix("+");
					productOptionService.createProductOptionValue(productOptionValue);
				}

			}

			//Add description entity
			ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
			productDescriptionEntity.setProductId(productEntity.getProductId());
			productDescriptionEntity.setLanguageId(RUSSAIN_LANGUAGE_ID);
			productDescriptionEntity.setName(product.getName());
			productDescriptionEntity.setDescription(EMPTY_STRING);
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
				ProductAttributeEntity compositionAttribute = new ProductAttributeEntity();
				compositionAttribute.setLanguageId(RUSSAIN_LANGUAGE_ID);
				compositionAttribute.setText(composite);
				compositionAttribute.setProductId(productEntity.getProductId());
				compositionAttribute.setAttributeId(COMPOSITION_ATTRIBUTE_ID);
				productEntity.getProductAttributes().add(compositionAttribute);
			}

			String description = product.getDescription();
			if(description != null)
			{
				ProductAttributeEntity descriptionAttribute = new ProductAttributeEntity();
				descriptionAttribute.setLanguageId(RUSSAIN_LANGUAGE_ID);
				descriptionAttribute.setText(description);
				descriptionAttribute.setProductId(productEntity.getProductId());
				descriptionAttribute.setAttributeId(DESCRIPTION_ATTRIBUTE_ID);
				productEntity.getProductAttributes().add(descriptionAttribute);
			}

			if (imageWillBeDownloaded)
			{
				try
				{
					File image = FileDownloader.uploadFile(product.getUrl());
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

	private BigDecimal createPrice(Product product) {return new BigDecimal(product.getCost()).multiply(RUSSION_RUBLE_COURSE).multiply(KOEF).multiply(KOEF);}

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

	//TODO: remove fucking hardcode!!!

	private void initOptionsId()
	{
		sizeOnOptionIdValue = new HashMap<>();
		Integer startSize = 36;
		for (int i = 0; i < 13; i++)
		{
			sizeOnOptionIdValue.put(startSize.toString(), 46 + i);
			startSize = startSize + 2;
		}

	}
}
