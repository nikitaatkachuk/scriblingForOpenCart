package by.vigi.entity;

/**
 * Created by Nikita Tkachuk
 */
public class Product
{
	private String category;
	private String name;
	private String article;
	private String cost;
	private String description;
	private String composite;
	private String sizes;
	private String metaKeyword;
	private String metaDescription;
	private String url;
	private boolean alreadyUsed;

	public Product()
	{
	}

	public Product(String category, String name, String article, String cost, String description)
	{
		this.category = category;
		this.name = name;
		this.article = article;
		this.cost = cost;
		this.description = description;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getArticle()
	{
		return article;
	}

	public void setArticle(String article)
	{
		this.article = article;
	}

	public String getCost()
	{
		return cost;
	}

	public void setCost(String cost)
	{
		this.cost = cost;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getComposite()
	{
		return composite;
	}

	public void setComposite(String composite)
	{
		this.composite = composite;
	}

	public String getSizes()
	{
		return sizes;
	}

	public void setSizes(String sizes)
	{
		this.sizes = sizes;
	}

	public String getMetaKeyword()
	{
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword)
	{
		this.metaKeyword = metaKeyword;
	}

	public String getMetaDescription()
	{
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription)
	{
		this.metaDescription = metaDescription;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String  getUrl()
	{
		return url;
	}
	
	public boolean isAlreadyUsed()
	{
		return alreadyUsed;
	}

	public void setAlreadyUsed(boolean alreadyUsed)
	{
		this.alreadyUsed = alreadyUsed;
	}

	@Override
	public String toString()
	{
		return "Product{" +
				"category='" + category + '\'' +
				", name='" + name + '\'' +
				", article='" + article + '\'' +
				", cost='" + cost + '\'' +
				", description='" + description + '\'' +
				", metaKeyword='" + metaKeyword + '\'' +
				", metaDescription='" + metaDescription + '\'' +
				", url='" + url + '\'' +
				", alreadyUsed=" + alreadyUsed +
				'}';
	}
}
