package by.vigi.service;

import java.util.Collection;

import by.vigi.dao.impl.AttributeDao;
import by.vigi.dao.impl.AttributeDescriptionDao;
import by.vigi.entity.AttributeEntity;

/**
 * Created by Nikita Tkachuk
 */
public class AttributeService
{
	private AttributeDao attributeDao;
	private AttributeDescriptionDao attributeDescriptionDao;
	
	
	
	public Collection<AttributeEntity> findAll()
	{
		//TODO: impl this shit
	}
	
	public Collection<AttributeEntity> findByLanguageId(Integer id)
	{
		
	}
	
	public AttributeEntity findAttributeEntityById(Integer id)
	{
		
	}
	
	public AttributeDao getAttributeDao() 
	{
		return attributeDao;
	}
	
	public void setAttributeDao(AttributeDao attributeDao) 
	{
		this.attributeDao = attributeDao;
	}
	
	public AttributeDescriptionDao getAttributeDescriptionDao()
	{
		return attributeDescriptionDao;
	}
	
	public void setAttributeDescriptionDao(
			AttributeDescriptionDao attributeDescriptionDao) 
	{
		this.attributeDescriptionDao = attributeDescriptionDao;
	}
	
	
	
	
	
}
