package by.vigi.dao.impl;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.ProductOptionValueEntity;

/**
 * Created by Nikita Tkachuk
 */
public class ProductOptionValueDao extends GenericDaoImpl<ProductOptionValueEntity, Integer>
{
	protected ProductOptionValueDao()
	{
		super(ProductOptionValueEntity.class);
	}
}
