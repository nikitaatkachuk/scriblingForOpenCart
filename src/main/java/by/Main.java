package by;

import by.vigi.entity.*;
import by.vigi.scribling.workers.AlltextileWorker;
import by.vigi.service.*;

import by.vigi.utils.FileDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Nikita Tkachuk
 */
public class Main
{
	public static final int RUSSAIN_LANGUAGE_ID = 1;
	public static final int ARTICLE_ATTRIBUTE_ID = 13;
	public static final int COST_ATTRIBUTE_ID = 15;
	public static final int NAME_ATTRIBUTE_ID = 12;
	public static final int COMPOSITION_ATTRIBUTE_ID = 14;
	public static final int DESCRIPTION_ATTRIBUTE_ID = 16;
	private static final String EMPTY_STRING = "";
	private static final BigDecimal KOEF = new BigDecimal(1.2);
	private static final BigDecimal RUSSION_RUBLE_COURSE = new BigDecimal(270);

	/**
	 * Entry point for start parsing. Will called from sh/bat scripts
	 * Argument parameters can be added later.
	 * @param args
	 */
	public static void main(String[] args)
	{
		Thread allTextileThread = new Thread(new AlltextileWorker());
		allTextileThread.start();
	}
}
