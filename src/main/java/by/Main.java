package by;

import by.vigi.entity.*;
import by.vigi.scribling.workers.*;
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
		if(args.length > 0)
		{
			System.out.println("Parse for opt");
			//Thread mariMay = new Thread(new MarimayWorker(true, 1));
			//Thread allTextile = new Thread(new AlltextileWorker(false, 1));
			Thread mondigoThread = new Thread(new MondigoWorker());
			//mariMay.start();
			//allTextile.start();
			mondigoThread.start();
		}
		else
		{
			System.out.println("Pars for roznica");

//			Thread guindaThread = new Thread(new GuindaWorker());
//			Thread maryMay = new Thread(new MarimayWorker(false, 2));
//			Thread allTextile = new Thread(new AlltextileWorker(false, 2));
//			maryMay.start();
//			allTextile.start();
//			guindaThread.start();
		}
	}
}
