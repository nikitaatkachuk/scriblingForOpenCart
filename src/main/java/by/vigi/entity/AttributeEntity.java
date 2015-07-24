package by.vigi.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "attribute")
public class AttributeEntity
{
	private Integer attributeId;
	private Integer attributeGroupId;
	private Integer sortOrder;
	private Collection<AttributeDescriptionEntity> attributeDescriptions;
	private Collection<ProductAttributeEntity> attributeProducts;

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

	@Basic
	@Column(name = "attribute_group_id")
	public Integer getAttributeGroupId()
	{
		return attributeGroupId;
	}

	public void setAttributeGroupId(Integer attributeGroupId)
	{
		this.attributeGroupId = attributeGroupId;
	}

	@Basic
	@Column(name = "sort_order")
	public Integer getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AttributeEntity that = (AttributeEntity) o;

		if (attributeGroupId != null ? !attributeGroupId.equals(that.attributeGroupId) : that.attributeGroupId != null)
			return false;
		if (attributeId != null ? !attributeId.equals(that.attributeId) : that.attributeId != null) return false;
		if (sortOrder != null ? !sortOrder.equals(that.sortOrder) : that.sortOrder != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = attributeId != null ? attributeId.hashCode() : 0;
		result = 31 * result + (attributeGroupId != null ? attributeGroupId.hashCode() : 0);
		result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
		return result;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "attribute_id")
	@Fetch(FetchMode.SELECT)
	public Collection<AttributeDescriptionEntity> getAttributeDescriptions()
	{
		return attributeDescriptions;
	}

	public void setAttributeDescriptions(Collection<AttributeDescriptionEntity> attributeDescriptions)
	{
		this.attributeDescriptions = attributeDescriptions;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "attribute_id")
	@Fetch(FetchMode.SELECT)
	public Collection<ProductAttributeEntity> getAttributeProducts()
	{
		return attributeProducts;
	}

	public void setAttributeProducts(Collection<ProductAttributeEntity> attributeProducts)
	{
		this.attributeProducts = attributeProducts;
	}
}
