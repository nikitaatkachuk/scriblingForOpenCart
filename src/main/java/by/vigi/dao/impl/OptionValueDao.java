package by.vigi.dao.impl;

import by.vigi.dao.GenericDaoImpl;
import by.vigi.entity.OptionValueEntity;

/**
 * Created by Nikita Tkachuk
 */
public class OptionValueDao extends GenericDaoImpl<OptionValueEntity, Integer>
{
	public OptionValueDao()
	{
		super(OptionValueEntity.class);
	}


}
