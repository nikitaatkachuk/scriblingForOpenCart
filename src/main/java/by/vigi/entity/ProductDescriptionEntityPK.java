package by.vigi.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Nikita Tkachuk
 */
public class ProductDescriptionEntityPK implements Serializable
{
	private Integer productId;
	private Integer languageId;

	@Column(name = "product_id")
	@Id
	public Integer getProductId()
	{
		return productId;
	}

	public void setProductId(Integer productId)
	{
		this.productId = productId;
	}

	@Column(name = "language_id")
	@Id
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

		ProductDescriptionEntityPK that = (ProductDescriptionEntityPK) o;

		if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;
		if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = productId != null ? productId.hashCode() : 0;
		result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
		return result;
	}
}
