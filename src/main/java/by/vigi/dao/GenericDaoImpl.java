package by.vigi.dao;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Nikita Tkachuk
 */
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T,PK>
{
	private Class<T> persistenceClass;

	protected GenericDaoImpl(Class<T> persistenceClass)
	{
		assert persistenceClass != null;
		this.persistenceClass = persistenceClass;
	}

	protected Class<T> getPersistenceClass()
	{
		return persistenceClass;
	}

	@Resource(name ="entityManager")
	private EntityManager entityManager;
	//private static EntityManager


	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	@Override
	public T create(T t) {
		getEntityManager().persist(t);
		return t;
	}

	@Override
	public T read(PK id) {
		return getEntityManager().find(getPersistenceClass(), id);
	}

	@Override
	public T update(T t) {
		return getEntityManager().merge(t);
	}

	@Override
	public void delete(T t) {
		t = this.getEntityManager().merge(t);
		getEntityManager().remove(t);
	}

	public void flush()
	{
		getEntityManager().flush();
	}
}
