package by.vigi.dao.impl;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.AttributeDescriptionEntity;
import by.vigi.entity.AttributeDescriptionEntityPK;

/**
 * Created by Nikita Tkachuk
 */
public class AttributeDescriptionDao extends GenericDaoImpl<AttributeDescriptionEntity, AttributeDescriptionEntityPK>
{
	public AttributeDescriptionDao()
	{
		super(AttributeDescriptionEntity.class);
	}


}
