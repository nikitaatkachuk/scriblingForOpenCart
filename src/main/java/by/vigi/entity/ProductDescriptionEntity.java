package by.vigi.entity;

import javax.persistence.*;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "product_description", schema = "", catalog = "vigiby_opencart")
@IdClass(ProductDescriptionEntityPK.class)
public class ProductDescriptionEntity
{
	private Integer productId;
	private Integer languageId;
	private String name;
	private String description;
	private String metaDescription;
	private String metaKeyword;
	private String seoTitle;
	private String seoH1;
	private String tag;

	@Id
	@Column(name = "product_id")
	public Integer getProductId()
	{
		return productId;
	}

	public void setProductId(Integer productId)
	{
		this.productId = productId;
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

	@Basic
	@Column(name = "tag")
	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductDescriptionEntity that = (ProductDescriptionEntity) o;

		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;
		if (metaDescription != null ? !metaDescription.equals(that.metaDescription) : that.metaDescription != null)
			return false;
		if (metaKeyword != null ? !metaKeyword.equals(that.metaKeyword) : that.metaKeyword != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
		if (seoH1 != null ? !seoH1.equals(that.seoH1) : that.seoH1 != null) return false;
		if (seoTitle != null ? !seoTitle.equals(that.seoTitle) : that.seoTitle != null) return false;
		if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = productId != null ? productId.hashCode() : 0;
		result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (metaDescription != null ? metaDescription.hashCode() : 0);
		result = 31 * result + (metaKeyword != null ? metaKeyword.hashCode() : 0);
		result = 31 * result + (seoTitle != null ? seoTitle.hashCode() : 0);
		result = 31 * result + (seoH1 != null ? seoH1.hashCode() : 0);
		result = 31 * result + (tag != null ? tag.hashCode() : 0);
		return result;
	}
}
