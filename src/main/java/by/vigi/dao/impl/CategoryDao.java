package by.vigi.dao.impl;

import org.springframework.stereotype.Component;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.CategoryEntity;

/**
 * Created by Nikita Tkachuk
 */
public class CategoryDao extends GenericDaoImpl<CategoryEntity, Integer>
{
	public CategoryDao()
	{
		super(CategoryEntity.class);
	}
}
