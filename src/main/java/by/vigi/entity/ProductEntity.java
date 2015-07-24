package by.vigi.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Nikita Tkachuk
 */
@Entity
@javax.persistence.Table(name = "product", schema = "", catalog = "vigiby_opencart")
public class ProductEntity
{

	private Integer productId;

	@Id
	@javax.persistence.Column(name = "product_id")
	public Integer getProductId()
	{
		return productId;
	}

	public void setProductId(Integer productId)
	{
		this.productId = productId;
	}

	private String model;

	@Basic
	@javax.persistence.Column(name = "model")
	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	private String sku;

	@Basic
	@javax.persistence.Column(name = "sku")
	public String getSku()
	{
		return sku;
	}

	public void setSku(String sku)
	{
		this.sku = sku;
	}

	private String upc;

	@Basic
	@javax.persistence.Column(name = "upc")
	public String getUpc()
	{
		return upc;
	}

	public void setUpc(String upc)
	{
		this.upc = upc;
	}

	private String ean;

	@Basic
	@javax.persistence.Column(name = "ean")
	public String getEan()
	{
		return ean;
	}

	public void setEan(String ean)
	{
		this.ean = ean;
	}

	private String jan;

	@Basic
	@javax.persistence.Column(name = "jan")
	public String getJan()
	{
		return jan;
	}

	public void setJan(String jan)
	{
		this.jan = jan;
	}

	private String isbn;

	@Basic
	@javax.persistence.Column(name = "isbn")
	public String getIsbn()
	{
		return isbn;
	}

	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

	private String mpn;

	@Basic
	@javax.persistence.Column(name = "mpn")
	public String getMpn()
	{
		return mpn;
	}

	public void setMpn(String mpn)
	{
		this.mpn = mpn;
	}

	private String location;

	@Basic
	@javax.persistence.Column(name = "location")
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	private Integer quantity;

	@Basic
	@javax.persistence.Column(name = "quantity")
	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	private Integer stockStatusId;

	@Basic
	@javax.persistence.Column(name = "stock_status_id")
	public Integer getStockStatusId()
	{
		return stockStatusId;
	}

	public void setStockStatusId(Integer stockStatusId)
	{
		this.stockStatusId = stockStatusId;
	}

	private String image;

	@Basic
	@javax.persistence.Column(name = "image")
	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	private Integer manufacturerId;

	@Basic
	@javax.persistence.Column(name = "manufacturer_id")
	public Integer getManufacturerId()
	{
		return manufacturerId;
	}

	public void setManufacturerId(Integer manufacturerId)
	{
		this.manufacturerId = manufacturerId;
	}

	private boolean shipping;

	@Basic
	@javax.persistence.Column(name = "shipping")
	public boolean getShipping()
	{
		return shipping;
	}

	public void setShipping(boolean shipping)
	{
		this.shipping = shipping;
	}

	private BigDecimal price;

	@Basic
	@javax.persistence.Column(name = "price")
	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	private Integer points;

	@Basic
	@javax.persistence.Column(name = "points")
	public Integer getPoints()
	{
		return points;
	}

	public void setPoints(Integer points)
	{
		this.points = points;
	}

	private Integer taxClassId;

	@Basic
	@javax.persistence.Column(name = "tax_class_id")
	public Integer getTaxClassId()
	{
		return taxClassId;
	}

	public void setTaxClassId(Integer taxClassId)
	{
		this.taxClassId = taxClassId;
	}

	private Date dateAvailable;

	@Basic
	@javax.persistence.Column(name = "date_available")
	public Date getDateAvailable()
	{
		return dateAvailable;
	}

	public void setDateAvailable(Date dateAvailable)
	{
		this.dateAvailable = dateAvailable;
	}

	private BigDecimal weight;

	@Basic
	@javax.persistence.Column(name = "weight")
	public BigDecimal getWeight()
	{
		return weight;
	}

	public void setWeight(BigDecimal weight)
	{
		this.weight = weight;
	}

	private Integer weightClassId;

	@Basic
	@javax.persistence.Column(name = "weight_class_id")
	public Integer getWeightClassId()
	{
		return weightClassId;
	}

	public void setWeightClassId(Integer weightClassId)
	{
		this.weightClassId = weightClassId;
	}

	private BigDecimal length;

	@Basic
	@javax.persistence.Column(name = "length")
	public BigDecimal getLength()
	{
		return length;
	}

	public void setLength(BigDecimal length)
	{
		this.length = length;
	}

	private BigDecimal width;

	@Basic
	@javax.persistence.Column(name = "width")
	public BigDecimal getWidth()
	{
		return width;
	}

	public void setWidth(BigDecimal width)
	{
		this.width = width;
	}

	private BigDecimal height;

	@Basic
	@javax.persistence.Column(name = "height")
	public BigDecimal getHeight()
	{
		return height;
	}

	public void setHeight(BigDecimal height)
	{
		this.height = height;
	}

	private Integer lengthClassId;

	@Basic
	@javax.persistence.Column(name = "length_class_id")
	public Integer getLengthClassId()
	{
		return lengthClassId;
	}

	public void setLengthClassId(Integer lengthClassId)
	{
		this.lengthClassId = lengthClassId;
	}

	private boolean subtract;

	@Basic
	@javax.persistence.Column(name = "subtract")
	public boolean getSubtract()
	{
		return subtract;
	}

	public void setSubtract(boolean subtract)
	{
		this.subtract = subtract;
	}

	private Integer minimum;

	@Basic
	@javax.persistence.Column(name = "minimum")
	public Integer getMinimum()
	{
		return minimum;
	}

	public void setMinimum(Integer minimum)
	{
		this.minimum = minimum;
	}

	private Integer sortOrder;

	@Basic
	@javax.persistence.Column(name = "sort_order")
	public Integer getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	private boolean status;

	@Column(name = "status")
	public boolean getStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	private Timestamp dateAdded;

	@Column(name = "date_added")
	public Timestamp getDateAdded()
	{
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded)
	{
		this.dateAdded = dateAdded;
	}

	private Timestamp dateModified;

	@javax.persistence.Column(name = "date_modified")
	public Timestamp getDateModified()
	{
		return dateModified;
	}

	public void setDateModified(Timestamp dateModified)
	{
		this.dateModified = dateModified;
	}

	private Integer viewed;

	@Basic
	@javax.persistence.Column(name = "viewed")
	public Integer getViewed()
	{
		return viewed;
	}

	public void setViewed(Integer viewed)
	{
		this.viewed = viewed;
	}

	private Set<ProductAttributeEntity> productAttributes;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	@Fetch(FetchMode.SELECT)
	public Set<ProductAttributeEntity> getProductAttributes()
	{
		return productAttributes;
	}

	public void setProductAttributes(Set<ProductAttributeEntity> productAttributes)
	{
		this.productAttributes = productAttributes;
	}

	private Collection<ProductDescriptionEntity> productDescriptions;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	@Fetch(FetchMode.SELECT)
	public Collection<ProductDescriptionEntity> getProductDescriptions()
	{
		return productDescriptions;
	}

	public void setProductDescriptions(Collection<ProductDescriptionEntity> productDescriptions)
	{
		this.productDescriptions = productDescriptions;
	}

	private Collection<CategoryEntity> categories;

	@ManyToMany(mappedBy = "categoryProducts", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public Collection<CategoryEntity> getCategories()
	{
		return categories;
	}

	public void setCategories(Collection<CategoryEntity> categories)
	{
		this.categories = categories;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductEntity that = (ProductEntity) o;

		if (dateAdded != null ? !dateAdded.equals(that.dateAdded) : that.dateAdded != null) return false;
		if (dateAvailable != null ? !dateAvailable.equals(that.dateAvailable) : that.dateAvailable != null)
			return false;
		if (dateModified != null ? !dateModified.equals(that.dateModified) : that.dateModified != null) return false;
		if (ean != null ? !ean.equals(that.ean) : that.ean != null) return false;
		if (height != null ? !height.equals(that.height) : that.height != null) return false;
		if (image != null ? !image.equals(that.image) : that.image != null) return false;
		if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
		if (jan != null ? !jan.equals(that.jan) : that.jan != null) return false;
		if (length != null ? !length.equals(that.length) : that.length != null) return false;
		if (lengthClassId != null ? !lengthClassId.equals(that.lengthClassId) : that.lengthClassId != null)
			return false;
		if (location != null ? !location.equals(that.location) : that.location != null) return false;
		if (manufacturerId != null ? !manufacturerId.equals(that.manufacturerId) : that.manufacturerId != null)
			return false;
		if (minimum != null ? !minimum.equals(that.minimum) : that.minimum != null) return false;
		if (model != null ? !model.equals(that.model) : that.model != null) return false;
		if (mpn != null ? !mpn.equals(that.mpn) : that.mpn != null) return false;
		if (points != null ? !points.equals(that.points) : that.points != null) return false;
		if (price != null ? !price.equals(that.price) : that.price != null) return false;
		if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
		if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
		if (sortOrder != null ? !sortOrder.equals(that.sortOrder) : that.sortOrder != null) return false;
		if (taxClassId != null ? !taxClassId.equals(that.taxClassId) : that.taxClassId != null) return false;
		if (upc != null ? !upc.equals(that.upc) : that.upc != null) return false;
		if (viewed != null ? !viewed.equals(that.viewed) : that.viewed != null) return false;
		if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
		if (weightClassId != null ? !weightClassId.equals(that.weightClassId) : that.weightClassId != null)
			return false;
		if (width != null ? !width.equals(that.width) : that.width != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = productId != null ? productId.hashCode() : 0;
		result = 31 * result + (model != null ? model.hashCode() : 0);
		result = 31 * result + (sku != null ? sku.hashCode() : 0);
		result = 31 * result + (upc != null ? upc.hashCode() : 0);
		result = 31 * result + (ean != null ? ean.hashCode() : 0);
		result = 31 * result + (jan != null ? jan.hashCode() : 0);
		result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
		result = 31 * result + (mpn != null ? mpn.hashCode() : 0);
		result = 31 * result + (location != null ? location.hashCode() : 0);
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		result = 31 * result + (stockStatusId != null ? stockStatusId.hashCode() : 0);
		result = 31 * result + (image != null ? image.hashCode() : 0);
		result = 31 * result + (manufacturerId != null ? manufacturerId.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (points != null ? points.hashCode() : 0);
		result = 31 * result + (taxClassId != null ? taxClassId.hashCode() : 0);
		result = 31 * result + (dateAvailable != null ? dateAvailable.hashCode() : 0);
		result = 31 * result + (weight != null ? weight.hashCode() : 0);
		result = 31 * result + (weightClassId != null ? weightClassId.hashCode() : 0);
		result = 31 * result + (length != null ? length.hashCode() : 0);
		result = 31 * result + (width != null ? width.hashCode() : 0);
		result = 31 * result + (height != null ? height.hashCode() : 0);
		result = 31 * result + (lengthClassId != null ? lengthClassId.hashCode() : 0);
		result = 31 * result + (minimum != null ? minimum.hashCode() : 0);
		result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
		result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
		result = 31 * result + (dateModified != null ? dateModified.hashCode() : 0);
		result = 31 * result + (viewed != null ? viewed.hashCode() : 0);
		return result;
	}
}
