package by.vigi.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Nikita Tkachuk
 */
public class AttributeDescriptionEntityPK implements Serializable
{
	private Integer attributeId;
	private Integer languageId;

	@Id
	@Column(name = "attribute_id")
	public Integer getAttributeId()
	{
		return attributeId;
	}

	public void setAttributeId(Integer attributeId)
	{
		this.attributeId = attributeId;
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
		if (o == null || getClass() != o.getClass()) return false;

		AttributeDescriptionEntityPK that = (AttributeDescriptionEntityPK) o;

		if (attributeId != null ? !attributeId.equals(that.attributeId) : that.attributeId != null) return false;
		if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = attributeId != null ? attributeId.hashCode() : 0;
		result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
		return result;
	}
}
