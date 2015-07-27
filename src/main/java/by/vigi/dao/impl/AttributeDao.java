package by.vigi.dao.impl;

import java.util.Collection;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.AttributeEntity;
import by.vigi.entity.CategoryEntity;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Nikita Tkachuk
 */
public class AttributeDao extends GenericDaoImpl<AttributeEntity, Integer>
{
	public AttributeDao()
	{
		super(AttributeEntity.class);
	}
	
	public Collection<AttributeEntity> findAll()
	{
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AttributeEntity> cq = cb.createQuery(AttributeEntity.class);
		Root<AttributeEntity> rootEntry = cq.from(AttributeEntity.class);
		CriteriaQuery<AttributeEntity> all = cq.select(rootEntry);
		TypedQuery<AttributeEntity> allQuery = getEntityManager().createQuery(all);
		return allQuery.getResultList();
	}

}
