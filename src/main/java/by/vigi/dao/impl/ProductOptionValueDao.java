package by.vigi.dao.impl;

import java.util.Collection;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.AttributeDescriptionEntity;
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

	public Collection<ProductOptionValueEntity> findByProductId(Integer productId)
	{
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ProductOptionValueEntity> criteriaQuery = criteriaBuilder.createQuery(ProductOptionValueEntity.class);
		Root<ProductOptionValueEntity> root = criteriaQuery.from(ProductOptionValueEntity.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.<String>get("productId"), productId));
		Query query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();
	}
}
