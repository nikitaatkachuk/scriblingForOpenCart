package by.vigi.service;

import by.vigi.dao.impl.ProductDao;
import by.vigi.dao.impl.ProductDescriptionDao;
import by.vigi.entity.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Nikita Tkachuk
 */

@Transactional
public class ProductService
{
	private ProductDao productDao;
	private ProductDescriptionDao productDescriptionDao;
	private CategoryService categoryService;



	public ProductService()
	{
	}

	public void updateProducts(Collection<ProductEntity> products)
	{
		products.stream().forEach(getProductDao():: update);
	}

	public Collection<ProductEntity> getProductsByCategoryName(String categoryName)
	{
		CategoryEntity categoryEntity = categoryService.findCategoryByName(categoryName);
		if(categoryEntity != null)
		{
			return categoryEntity.getCategoryProducts();
		}
		else
		{
			return Collections.emptyList();
		}
//		else
//		{
//			categoryEntity = new CategoryEntity();
//			CategoryDescriptionEntity descriptionEntity = new CategoryDescriptionEntity();
//			descriptionEntity.setName(categoryName);
//			descriptionEntity.setLanguageId(1);
//			String emptyString = "";
//			descriptionEntity.setDescription(emptyString);
//			descriptionEntity.setMetaDescription(emptyString);
//			descriptionEntity.setMetaKeyword(emptyString);
//			descriptionEntity.setSeoH1(emptyString);
//			descriptionEntity.setSeoTitle(emptyString);
//			categoryEntity.setColumn(0);
//			Timestamp date = new Timestamp(System.currentTimeMillis());
//			categoryEntity.setDateAdded(date);
//			categoryEntity.setDateModified(date);
//			categoryEntity.setParentId(0);
//			categoryEntity.setStatus(false);
//			categoryEntity.setTop(false);
//			categoryEntity.setSortOrder(0);
//			categoryEntity = categoryService.createCategory(categoryEntity);
//			Collection<CategoryDescriptionEntity> categoryDescriptionEntityCollection = new ArrayList<>();
//			categoryDescriptionEntityCollection.add(descriptionEntity);
//			categoryEntity.setCategoryDescription(categoryDescriptionEntityCollection);
//			descriptionEntity.setCategoryId(categoryEntity.getCategoryId());
//			categoryService.saveDescription(descriptionEntity);
//		}
//		return Collections.emptyList();
	}

	public ProductEntity createNewProductEntity()
	{
		ProductEntity result = new ProductEntity();
		Timestamp date = new Timestamp(System.currentTimeMillis());
		String emptyString = "";
		BigDecimal zeroBigDec = new BigDecimal(0);
		result.setModel(emptyString);
		result.setSku(emptyString);
		result.setEan(emptyString);
		result.setIsbn(emptyString);
		result.setJan(emptyString);
		result.setMpn(emptyString);
		result.setLocation(emptyString);
		result.setQuantity(0);
		result.setStockStatusId(0);
		result.setManufacturerId(0);
		result.setShipping(false);
		result.setPrice(zeroBigDec);
		result.setPoints(0);
		result.setTaxClassId(0);
		result.setDateAvailable(new Date(System.currentTimeMillis()));
		result.setWeight(zeroBigDec);
		result.setUpc(emptyString);
		result.setWeightClassId(0);
		result.setLength(zeroBigDec);
		result.setWidth(zeroBigDec);
		result.setHeight(zeroBigDec);
		result.setLengthClassId(0);
		result.setSubtract(false);
		result.setMinimum(0);
		result.setSortOrder(0);
		result.setStatus(false);
		result.setDateAdded(date);
		result.setDateModified(date);
		result.setViewed(0);
		result.setCategories(new ArrayList<CategoryEntity>());
		result.setProductDescriptions(new ArrayList<ProductDescriptionEntity>());
		result.setProductAttributes(new HashSet<ProductAttributeEntity>());
		result = productDao.create(result);
		Query query = getProductDao().getEntityManager().createNamedQuery("linkToStore");
		query.setParameter(1, result.getProductId());
		query.setParameter(2, 0);
		query.executeUpdate();
		return result;
	}

	public ProductDao getProductDao()
	{
		return productDao;
	}

	public void setProductDao(ProductDao productDao)
	{
		this.productDao = productDao;
	}

	public ProductDescriptionDao getProductDescriptionDao()
	{
		return productDescriptionDao;
	}

	public void setProductDescriptionDao(ProductDescriptionDao productDescriptionDao)
	{
		this.productDescriptionDao = productDescriptionDao;
	}

	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}
}
