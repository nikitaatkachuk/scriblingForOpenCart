package by.vigi.scribling.workers;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;

import by.vigi.entity.Product;

/**
 * Use template method for parse sites
 * @author Nikita
 *
 */

public interface ScriblingWorker extends Runnable
{
	Map<String, String> parseCategoryLinks();
	Map<String, Collection<String>> parseProductLinks(Map<String, String> category2link);
	Map<String, Collection<Product>> parseProducts(Map<String, Collection<String>> category2ProductLinks);
}
