package by.vigi.entity;

import javax.persistence.*;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "attribute_description")
@IdClass(AttributeDescriptionEntityPK.class)
public class AttributeDescriptionEntity
{
	private Integer attributeId;
	private Integer languageId;

	private String name;

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

}
