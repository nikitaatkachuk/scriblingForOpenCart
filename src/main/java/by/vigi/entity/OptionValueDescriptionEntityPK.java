package by.vigi.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Nikita Tkachuk
 */
public class OptionValueDescriptionEntityPK implements Serializable
{

	public Integer optionValueId;
	public Integer languageId;

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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof OptionValueDescriptionEntityPK)) return false;

		OptionValueDescriptionEntityPK that = (OptionValueDescriptionEntityPK) o;

		if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;
		if (optionValueId != null ? !optionValueId.equals(that.optionValueId) : that.optionValueId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = optionValueId != null ? optionValueId.hashCode() : 0;
		result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
		return result;
	}
}
