package by.vigi.entity;

import javax.persistence.*;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "category_description")
@IdClass(CategoryDescriptionEntityPK.class)
public class CategoryDescriptionEntity
{
	private Integer categoryId;
	private Integer languageId;
	private String name;
	private String description;
	private String metaDescription;
	private String metaKeyword;
	private String seoTitle;
	private String seoH1;

	@Id
	@AttributeOverride(name = "categoryId", column = @Column(name = "category_id"))
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

	@Basic
	@Column(name = "name")
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Basic
	@Column(name = "description")
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Basic
	@Column(name = "meta_description")
	public String getMetaDescription()
	{
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription)
	{
		this.metaDescription = metaDescription;
	}

	@Basic
	@Column(name = "meta_keyword")
	public String getMetaKeyword()
	{
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword)
	{
		this.metaKeyword = metaKeyword;
	}

	@Basic
	@Column(name = "seo_title")
	public String getSeoTitle()
	{
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle)
	{
		this.seoTitle = seoTitle;
	}

	@Basic
	@Column(name = "seo_h1")
	public String getSeoH1()
	{
		return seoH1;
	}

	public void setSeoH1(String seoH1)
	{
		this.seoH1 = seoH1;
	}
}

