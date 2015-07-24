package by.vigi.dao;

import by.vigi.dao.impl.AttributeDao;
import by.vigi.dao.impl.CategoryDescriptionDao;
import by.vigi.entity.AttributeEntity;
import by.vigi.entity.CategoryDescriptionEntity;
import by.vigi.entity.ProductEntity;
import by.vigi.service.ProductService;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Nikita Tkachuk
 */
public class DaoTest
{
	public static void main(String[] args)
	{
//		CategoryDescriptionDao dao = new CategoryDescriptionDao();
//		CategoryDescriptionEntity entity = dao.findCategoryDescriptionByName("Юбки");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
		ProductService productService = context.getBean(ProductService.class);
		Collection<ProductEntity> productEntities = productService.getProductsByCategoryName("Платья");
		if(productEntities != null && !productEntities.isEmpty())
		{
			System.out.println(productEntities.iterator().next());
		}
	}
}
