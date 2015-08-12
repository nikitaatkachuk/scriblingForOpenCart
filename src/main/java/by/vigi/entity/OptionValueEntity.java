package by.vigi.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "option_value")
public class OptionValueEntity
{

	private Integer optionValueId;
	private Integer optionId;
	private String image;
	private Integer sortOrder;
	private Collection<OptionValueDescriptionEntity> optionValueDescriptions;

	@Id
	@GeneratedValue
	@Column(name = "option_value_id")
	public Integer getOptionValueId()
	{
		return optionValueId;
	}

	public void setOptionValueId(Integer optionValueId)
	{
		this.optionValueId = optionValueId;
	}

	@Column(name = "option_id")
	public Integer getOptionId()
	{
		return optionId;
	}

	public void setOptionId(Integer optionId)
	{
		this.optionId = optionId;
	}

	@Column(name = "image")
	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	@Column(name = "sort_order")
	public Integer getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "option_value_id")
	@Fetch(FetchMode.SELECT)
	public Collection<OptionValueDescriptionEntity> getOptionValueDescriptions()
	{
		return optionValueDescriptions;
	}

	public void setOptionValueDescriptions(Collection<OptionValueDescriptionEntity> optionValueDescriptions)
	{
		this.optionValueDescriptions = optionValueDescriptions;
	}
}
