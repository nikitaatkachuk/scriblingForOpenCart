package by.vigi.dao.impl;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.AttributeEntity;
import by.vigi.entity.OptionValueDescriptionEntity;
import by.vigi.entity.OptionValueDescriptionEntityPK;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by Nikita Tkachuk
 */
public class OptionValueDescriptionDao extends GenericDaoImpl<OptionValueDescriptionEntity, OptionValueDescriptionEntityPK>
{
	public OptionValueDescriptionDao()
	{
		super(OptionValueDescriptionEntity.class);
	}

	public Collection<OptionValueDescriptionEntity> findAll()
	{
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<OptionValueDescriptionEntity> cq = cb.createQuery(OptionValueDescriptionEntity.class);
		Root<OptionValueDescriptionEntity> rootEntry = cq.from(OptionValueDescriptionEntity.class);
		CriteriaQuery<OptionValueDescriptionEntity> all = cq.select(rootEntry);
		TypedQuery<OptionValueDescriptionEntity> allQuery = getEntityManager().createQuery(all);
		return allQuery.getResultList();
	}
}
