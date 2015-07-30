package by.vigi.dao.impl;

import by.vigi.dao.GenericDao;
import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.ProductOptionEntity;

/**
 * Created by Nikita Tkachuk
 */
public class ProductOptionDao extends GenericDaoImpl<ProductOptionEntity, Integer>
{
	public ProductOptionDao()
	{
		super(ProductOptionEntity.class);
	}
}
