package by.vigi.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.AttributeDescriptionEntity;
import by.vigi.entity.AttributeDescriptionEntityPK;
import by.vigi.entity.CategoryDescriptionEntity;

/**
 * Created by Nikita Tkachuk
 */
public class AttributeDescriptionDao extends GenericDaoImpl<AttributeDescriptionEntity, AttributeDescriptionEntityPK>
{
	public AttributeDescriptionDao()
	{
		super(AttributeDescriptionEntity.class);
	}

	public List<AttributeDescriptionEntity> findByLanguageId(Integer languageId)
	{
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AttributeDescriptionEntity> criteriaQuery = criteriaBuilder.createQuery(AttributeDescriptionEntity.class);
		Root<AttributeDescriptionEntity> root = criteriaQuery.from(AttributeDescriptionEntity.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.<String>get("languageId"), languageId));
		Query query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();

	}
}
