package by.vigi.entity;

import javax.persistence.*;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "option_value_description")
@IdClass(OptionValueDescriptionEntityPK.class)
public class OptionValueDescriptionEntity
{
	private Integer optionValueId;
	private Integer languageId;
	private Integer optionId;
	private String name;

	@Id
	@Column(name = "option_value_id")
	public Integer getOptionValueId()
	{
		return optionValueId;
	}

	public void setOptionValueId(Integer optionValueId)
	{
		this.optionValueId = optionValueId;
	}

	@Id
	@Column(name = "language_id")
	public Integer getLanguageId()
	{
		return languageId;
	}

	public void setLanguageId(Integer languageId)
	{
		this.languageId = languageId;
	}

	@Column(name = "option_id")
	public Integer getOptionId()
	{
		return optionId;
	}

	public void setOptionId(Integer optionId)
	{
		this.optionId = optionId;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
