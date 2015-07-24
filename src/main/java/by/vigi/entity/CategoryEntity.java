package by.vigi.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@Table(name = "category")
public class CategoryEntity
{
	private Integer categoryId;
	private String image;
	private Integer parentId;
	private boolean top;
	private Integer column;
	private Integer sortOrder;
	private boolean status;
	private Timestamp dateAdded;
	private Timestamp dateModified;
	private Collection<CategoryDescriptionEntity> categoryDescription;
	private Collection<ProductEntity> categoryProducts;

	@Id
	@GeneratedValue
	@Column(name = "category_id", nullable = false, scale = 0, unique = true, updatable = true)
	public Integer getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Integer categoryId)
	{
		this.categoryId = categoryId;
	}

	@Basic
	@Column(name = "image")
	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	@Basic
	@Column(name = "parent_id")
	public Integer getParentId()
	{
		return parentId;
	}

	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

	@Basic
	@Column(name = "top")
	public boolean getTop()
	{
		return top;
	}

	public void setTop(boolean top)
	{
		this.top = top;
	}

	@Basic
	@Column(name = "`column`")
	public Integer getColumn()
	{
		return column;
	}

	public void setColumn(Integer column)
	{
		this.column = column;
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

	@Basic
	@Column(name = "status")
	public boolean getStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	@Basic
	@Column(name = "date_added")
	public Timestamp getDateAdded()
	{
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded)
	{
		this.dateAdded = dateAdded;
	}

	@Basic
	@Column(name = "date_modified")
	public Timestamp getDateModified()
	{
		return dateModified;
	}

	public void setDateModified(Timestamp dateModified)
	{
		this.dateModified = dateModified;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CategoryEntity that = (CategoryEntity) o;

		if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
		if (column != null ? !column.equals(that.column) : that.column != null) return false;
		if (dateAdded != null ? !dateAdded.equals(that.dateAdded) : that.dateAdded != null) return false;
		if (dateModified != null ? !dateModified.equals(that.dateModified) : that.dateModified != null) return false;
		if (image != null ? !image.equals(that.image) : that.image != null) return false;
		if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
		if (sortOrder != null ? !sortOrder.equals(that.sortOrder) : that.sortOrder != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = categoryId != null ? categoryId.hashCode() : 0;
		result = 31 * result + (image != null ? image.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		result = 31 * result + (column != null ? column.hashCode() : 0);
		result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
		result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
		result = 31 * result + (dateModified != null ? dateModified.hashCode() : 0);
		return result;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@Fetch(FetchMode.SELECT)
	public Collection<CategoryDescriptionEntity> getCategoryDescription()
	{
		return categoryDescription;
	}

	public void setCategoryDescription(Collection<CategoryDescriptionEntity> categoryDescription)
	{
		this.categoryDescription = categoryDescription;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "product_to_category", joinColumns = @JoinColumn(name = "category_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id"))
	@Fetch(FetchMode.SELECT)
	public Collection<ProductEntity> getCategoryProducts()
	{
		return categoryProducts;
	}

	public void setCategoryProducts(Collection<ProductEntity> categoryProducts)
	{
		this.categoryProducts = categoryProducts;
	}
}
