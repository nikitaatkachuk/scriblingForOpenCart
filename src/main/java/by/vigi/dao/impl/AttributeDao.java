package by.vigi.dao.impl;

import java.util.Collection;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.AttributeEntity;
import by.vigi.entity.CategoryEntity;

/**
 * Created by Nikita Tkachuk
 */
public class AttributeDao extends GenericDaoImpl<AttributeEntity, Integer>
{
	public AttributeDao()
	{
		super(AttributeEntity.class);
	}
	
	public Collection<AttributeEntity> findAttributesByLanguageId(Integer id)
	{
		
	}

}
