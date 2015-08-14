package by.vigi.scribling.workers;

import by.vigi.entity.Product;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;

/**
 * Created by Nikita Tkachuk
 */
public class ProbelioWorker implements Runnable
{

	public static void main(String[] args)
	{
		ProbelioWorker worker = new ProbelioWorker();
		worker.getTemplateSheet();
	}

	public void getTemplateSheet()
	{
		File excelHotFolder = new File("E:\\ee\\forPuhly\\excel");
		File[] excels = excelHotFolder.listFiles();
		for (File excel : excels)
		{
			try(InputStream stream = new BufferedInputStream(new FileInputStream(excel)))
			{
				Map<Double,Product> products = new HashMap<>(2000);
				Workbook workbook = new HSSFWorkbook(stream);
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> it = sheet.iterator();
				while (it.hasNext())
				{
					Row row = it.next();
					Cell articleCell = row.getCell(4);
					Double article = articleCell.getNumericCellValue();
					if(article == null)
					{
						continue;
					}
					Product alreadyAddedProduct = products.get(article);
					System.out.println(article);
					if(alreadyAddedProduct != null)
					{
						alreadyAddedProduct.setName("olol");
					}
					else
					{
						products.put(article, new Product());
					}

				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}



	@Override
	public void run()
	{

	}
}
