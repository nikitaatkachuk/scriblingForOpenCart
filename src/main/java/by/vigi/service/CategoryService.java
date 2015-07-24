package by.vigi.service;

import org.springframework.transaction.annotation.Transactional;

import by.vigi.dao.impl.CategoryDao;
import by.vigi.dao.impl.CategoryDescriptionDao;
import by.vigi.entity.CategoryDescriptionEntity;
import by.vigi.entity.CategoryEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Nikita Tkachuk
 */
@Transactional
public class CategoryService
{
	private CategoryDao categoryDao;
	private CategoryDescriptionDao categoryDescriptionDao;

	public CategoryDao getCategoryDao()
	{
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao)
	{
		this.categoryDao = categoryDao;
	}

	public CategoryDescriptionDao getCategoryDescriptionDao()
	{
		return categoryDescriptionDao;
	}

	public void setCategoryDescriptionDao(CategoryDescriptionDao categoryDescriptionDao)
	{
		this.categoryDescriptionDao = categoryDescriptionDao;
	}

	public CategoryService()
	{
	}

	public CategoryEntity findCategoryByName(String name)
	{
		CategoryDescriptionEntity description = categoryDescriptionDao.findCategoryDescriptionByName(name);
		if(description != null)
		{
			return categoryDao.read(description.getCategoryId());
		}
		return null;
	}

	public CategoryDescriptionEntity saveDescription(CategoryDescriptionEntity descriptionEntity)
	{
		return categoryDescriptionDao.create(descriptionEntity);
	}

	public CategoryEntity createCategory(String categoryName)
	{
		CategoryEntity categoryEntity = new CategoryEntity();
		CategoryDescriptionEntity descriptionEntity = new CategoryDescriptionEntity();
		descriptionEntity.setName(categoryName);
		descriptionEntity.setLanguageId(1);
		String emptyString = "";
		descriptionEntity.setDescription(emptyString);
		descriptionEntity.setMetaDescription(emptyString);
		descriptionEntity.setMetaKeyword(emptyString);
		descriptionEntity.setSeoH1(emptyString);
		descriptionEntity.setSeoTitle(emptyString);
		categoryEntity.setColumn(0);
		Timestamp date = new Timestamp(System.currentTimeMillis());
		categoryEntity.setDateAdded(date);
		categoryEntity.setDateModified(date);
		categoryEntity.setParentId(0);
		categoryEntity.setStatus(false);
		categoryEntity.setTop(false);
		categoryEntity.setSortOrder(0);
		Collection<CategoryDescriptionEntity> categoryDescriptionEntityCollection = new ArrayList<>();
		categoryEntity.setCategoryDescription(categoryDescriptionEntityCollection);
		categoryEntity = categoryDao.create(categoryEntity);
		categoryDescriptionEntityCollection.add(descriptionEntity);
		descriptionEntity.setCategoryId(categoryEntity.getCategoryId());
		saveDescription(descriptionEntity);
		return categoryEntity;
	}
}
