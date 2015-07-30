package by;

import by.vigi.entity.*;
import by.vigi.scribling.workers.AlltextileWorker;
import by.vigi.scribling.workers.FactoryfashionWorker;
import by.vigi.scribling.workers.MarimayWorker;
import by.vigi.scribling.workers.OptPlatyarRFWorker;
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
	/**
	 * Entry point for start parsing. Will called from sh/bat scripts
	 * Argument parameters can be added later.
	 * @param args
	 */
	public static void main(String[] args)
	{
		//System.out.println(Arrays.asList("51,52,53,23,74".split(",")));
		Thread factoryfashionWorker = new Thread(new MarimayWorker());
		//Thread optPlatyarRFWorker = new Thread(new OptPlatyarRFWorker());
		factoryfashionWorker.start();
		//optPlatyarRFWorker.start();
	}
}
