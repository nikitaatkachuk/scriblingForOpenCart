package by.vigi.dao.impl;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.CategoryDescriptionEntity;
import by.vigi.entity.CategoryDescriptionEntityPK;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Created by Nikita Tkachuk
 */
public class CategoryDescriptionDao extends GenericDaoImpl<CategoryDescriptionEntity, CategoryDescriptionEntityPK>
{
	public CategoryDescriptionDao()
	{
		super(CategoryDescriptionEntity.class);
	}

	public CategoryDescriptionEntity findCategoryDescriptionByName(String name)
	{
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<CategoryDescriptionEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryDescriptionEntity.class);
		Root<CategoryDescriptionEntity> root = criteriaQuery.from(CategoryDescriptionEntity.class);
		criteriaQuery.select(root).where(criteriaBuilder.like(root.<String>get("name"), "%" + name + "%"));
		Query query = getEntityManager().createQuery(criteriaQuery);
		List<CategoryDescriptionEntity> resultList = query.getResultList();
		if(!resultList.isEmpty())
		{
			return resultList.get(0);
		}
		return null;

	}
}
