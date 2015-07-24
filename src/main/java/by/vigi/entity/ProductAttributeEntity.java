package by.vigi.entity;

import javax.persistence.*;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "product_attribute")
@IdClass(ProductAttributeEntityPK.class)
public class ProductAttributeEntity
{
	private Integer productId;
	private Integer attributeId;
	private Integer languageId;
	private String text;
	private AttributeEntity attribute;

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

	@Column(name = "text")
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	//@AttributeOverride(name = "attributeId", column = @Column(name = "attribute_id"))
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "attribute_id", nullable = false, updatable = false, insertable = false)
	public AttributeEntity getAttribute()
	{
		return attribute;
	}

	public void setAttribute(AttributeEntity attribute)
	{
		this.attribute = attribute;
	}
}
