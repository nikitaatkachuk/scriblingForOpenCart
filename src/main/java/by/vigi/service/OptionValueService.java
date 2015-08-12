package by.vigi.service;

import by.vigi.dao.impl.OptionValueDao;
import by.vigi.dao.impl.OptionValueDescriptionDao;
import by.vigi.entity.OptionValueDescriptionEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Nikita Tkachuk
 */
@Transactional
public class OptionValueService
{
	private OptionValueDao valueDao;
	private OptionValueDescriptionDao valueDescriptionDao;

	public Collection<OptionValueDescriptionEntity> findAll()
	{
		return valueDescriptionDao.findAll();
	}

	public OptionValueDao getValueDao()
	{
		return valueDao;
	}

	public void setValueDao(OptionValueDao valueDao)
	{
		this.valueDao = valueDao;
	}

	public OptionValueDescriptionDao getValueDescriptionDao()
	{
		return valueDescriptionDao;
	}

	public void setValueDescriptionDao(OptionValueDescriptionDao valueDescriptionDao)
	{
		this.valueDescriptionDao = valueDescriptionDao;
	}
}
