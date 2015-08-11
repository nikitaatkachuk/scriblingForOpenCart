package by.vigi.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "product_option_value")
public class ProductOptionValueEntity
{
	private Integer productOptionValueId;
	private Integer productOptionId;
	private Integer productId;
	private Integer optionId;
	private Integer optionValueId;
	private Integer quantity;
	private boolean subtract;
	private BigDecimal price;
	private String pricePrefix;
	private Integer points;
	private String pointsPrefix;
	private BigDecimal weight;
	private String weightPrefix;

	@Id
	@GeneratedValue
	@Column(name = "product_option_value_id")
	public Integer getProductOptionValueId()
	{
		return productOptionValueId;
	}

	public void setProductOptionValueId(Integer productOptionValueId)
	{
		this.productOptionValueId = productOptionValueId;
	}

	@Basic
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
	@Column(name = "option_value_id")
	public Integer getOptionValueId()
	{
		return optionValueId;
	}

	public void setOptionValueId(Integer optionValueId)
	{
		this.optionValueId = optionValueId;
	}

	@Basic
	@Column(name = "quantity")
	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	@Basic
	@Column(name = "subtract")
	public boolean getSubtract()
	{
		return subtract;
	}

	public void setSubtract(boolean subtract)
	{
		this.subtract = subtract;
	}

	@Basic
	@Column(name = "price")
	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	@Basic
	@Column(name = "price_prefix")
	public String getPricePrefix()
	{
		return pricePrefix;
	}

	public void setPricePrefix(String pricePrefix)
	{
		this.pricePrefix = pricePrefix;
	}

	@Basic
	@Column(name = "points")
	public Integer getPoints()
	{
		return points;
	}

	public void setPoints(Integer points)
	{
		this.points = points;
	}

	@Basic
	@Column(name = "points_prefix")
	public String getPointsPrefix()
	{
		return pointsPrefix;
	}

	public void setPointsPrefix(String pointsPrefix)
	{
		this.pointsPrefix = pointsPrefix;
	}

	@Basic
	@Column(name = "weight")
	public BigDecimal getWeight()
	{
		return weight;
	}

	public void setWeight(BigDecimal weight)
	{
		this.weight = weight;
	}

	@Basic
	@Column(name = "weight_prefix")
	public String getWeightPrefix()
	{
		return weightPrefix;
	}

	public void setWeightPrefix(String weightPrefix)
	{
		this.weightPrefix = weightPrefix;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductOptionValueEntity that = (ProductOptionValueEntity) o;

		if (optionId != null ? !optionId.equals(that.optionId) : that.optionId != null) return false;
		if (optionValueId != null ? !optionValueId.equals(that.optionValueId) : that.optionValueId != null)
			return false;
		if (points != null ? !points.equals(that.points) : that.points != null) return false;
		if (pointsPrefix != null ? !pointsPrefix.equals(that.pointsPrefix) : that.pointsPrefix != null) return false;
		if (price != null ? !price.equals(that.price) : that.price != null) return false;
		if (pricePrefix != null ? !pricePrefix.equals(that.pricePrefix) : that.pricePrefix != null) return false;
		if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
		if (productOptionId != null ? !productOptionId.equals(that.productOptionId) : that.productOptionId != null)
			return false;
		if (productOptionValueId != null ? !productOptionValueId.equals(that.productOptionValueId) : that.productOptionValueId != null)
			return false;
		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
		if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
		if (weightPrefix != null ? !weightPrefix.equals(that.weightPrefix) : that.weightPrefix != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = productOptionValueId != null ? productOptionValueId.hashCode() : 0;
		result = 31 * result + (productOptionId != null ? productOptionId.hashCode() : 0);
		result = 31 * result + (productId != null ? productId.hashCode() : 0);
		result = 31 * result + (optionId != null ? optionId.hashCode() : 0);
		result = 31 * result + (optionValueId != null ? optionValueId.hashCode() : 0);
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (pricePrefix != null ? pricePrefix.hashCode() : 0);
		result = 31 * result + (points != null ? points.hashCode() : 0);
		result = 31 * result + (pointsPrefix != null ? pointsPrefix.hashCode() : 0);
		result = 31 * result + (weight != null ? weight.hashCode() : 0);
		result = 31 * result + (weightPrefix != null ? weightPrefix.hashCode() : 0);
		return result;
	}
}
