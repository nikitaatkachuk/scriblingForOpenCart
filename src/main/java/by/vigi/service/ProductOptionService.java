package by.vigi.service;

import java.util.Collection;

import by.vigi.dao.impl.ProductOptionDao;
import by.vigi.dao.impl.ProductOptionValueDao;
import by.vigi.entity.ProductOptionEntity;
import by.vigi.entity.ProductOptionValueEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Nikita Tkachuk
 */
@Transactional
public class ProductOptionService
{
	private ProductOptionDao productOptionDao;
	private ProductOptionValueDao productOptionValueDao;


	public ProductOptionEntity createProductOption(ProductOptionEntity productOption)
	{
		return productOptionDao.create(productOption);
	}

	public ProductOptionValueEntity createProductOptionValue(ProductOptionValueEntity productOptionValue)
	{
		return productOptionValueDao.create(productOptionValue);
	}

	public ProductOptionValueEntity updateProductOptionValue(ProductOptionValueEntity productOptionValue)
	{
		return productOptionValueDao.update(productOptionValue);
	}

	public Collection<ProductOptionValueEntity> findByProductId(Integer productId)
	{
		return productOptionValueDao.findByProductId(productId);
	}

	public ProductOptionDao getProductOptionDao()
	{
		return productOptionDao;
	}

	public void setProductOptionDao(ProductOptionDao productOptionDao)
	{
		this.productOptionDao = productOptionDao;
	}

	public ProductOptionValueDao getProductOptionValueDao()
	{
		return productOptionValueDao;
	}

	public void setProductOptionValueDao(ProductOptionValueDao productOptionValueDao)
	{
		this.productOptionValueDao = productOptionValueDao;
	}
}
