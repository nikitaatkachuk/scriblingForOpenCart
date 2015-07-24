package by.vigi.dao;

import java.io.Serializable;

/**
 * Created by Nikita Tkachuk
 */
public interface GenericDao <T, PK extends Serializable>
{
		T create(T t);
		T read(PK id);
		T update(T t);
		void delete(T t);
}
