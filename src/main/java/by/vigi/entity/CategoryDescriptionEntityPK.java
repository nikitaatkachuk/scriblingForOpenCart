package by.vigi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CategoryDescriptionEntityPK implements Serializable
{
	public Integer categoryId;
	public Integer languageId;

	@Id
	@Column(name = "category_id")
	public Integer getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Integer categoryId)
	{
		this.categoryId = categoryId;
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

		CategoryDescriptionEntityPK that = (CategoryDescriptionEntityPK) o;

		if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
		if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = categoryId != null ? categoryId.hashCode() : 0;
		result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
		return result;
	}
}
