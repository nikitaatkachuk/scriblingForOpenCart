package by.vigi.entity;

import javax.persistence.*;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "product_option", schema = "", catalog = "vigiby_opencart")
public class ProductOptionEntity
{

	private Integer productOptionId;
	private Integer productId;
	private Integer optionId;
	private String optionValue;
	private boolean required;

	@Id
	@GeneratedValue
	@Column(name = "product_option_id")
	public Integer getProductOptionId()
	{
		return productOptionId;
	}

	public void setProductOptionId(Integer productOptionId)
	{
		this.productOptionId = productOptionId;
	}

	@Basic
	@Column(name = "product_id")
	public Integer getProductId()
	{
		return productId;
	}

	public void setProductId(Integer productId)
	{
		this.productId = productId;
	}

	@Basic
	@Column(name = "option_id")
	public Integer getOptionId()
	{
		return optionId;
	}

	public void setOptionId(Integer optionId)
	{
		this.optionId = optionId;
	}

	@Basic
	@Column(name = "option_value")
	public String getOptionValue()
	{
		return optionValue;
	}

	public void setOptionValue(String optionValue)
	{
		this.optionValue = optionValue;
	}

	@Column(name = "required")
	public boolean getRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductOptionEntity that = (ProductOptionEntity) o;

		if (optionId != null ? !optionId.equals(that.optionId) : that.optionId != null) return false;
		if (optionValue != null ? !optionValue.equals(that.optionValue) : that.optionValue != null) return false;
		if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
		if (productOptionId != null ? !productOptionId.equals(that.productOptionId) : that.productOptionId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = productOptionId != null ? productOptionId.hashCode() : 0;
		result = 31 * result + (productId != null ? productId.hashCode() : 0);
		result = 31 * result + (optionId != null ? optionId.hashCode() : 0);
		result = 31 * result + (optionValue != null ? optionValue.hashCode() : 0);
		return result;
	}
}
