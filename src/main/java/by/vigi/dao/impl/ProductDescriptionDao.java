package by.vigi.dao.impl;

import org.springframework.stereotype.Component;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.ProductDescriptionEntity;
import by.vigi.entity.ProductDescriptionEntityPK;

/**
 * Created by Nikita Tkachuk
 */
public class ProductDescriptionDao extends GenericDaoImpl<ProductDescriptionEntity, ProductDescriptionEntityPK>
{
	public ProductDescriptionDao()
	{
		super(ProductDescriptionEntity.class);
	}
}
