package by.vigi.dao.impl;

import org.springframework.stereotype.Component;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.ProductEntity;

/**
 * Created by Nikita Tkachuk
 */
public class ProductDao extends GenericDaoImpl<ProductEntity, Integer>
{
	public ProductDao()
	{
		super(ProductEntity.class);
	}
}
