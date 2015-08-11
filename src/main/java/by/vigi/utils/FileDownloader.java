package by.vigi.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

public final class FileDownloader
{
	private static final String PATH_ON_SERVER_FOR_OPT = "/var/www/opt/image/data/demo/pic";
	private static final String PATH_ON_SERVER = "/var/www/image/data/demo/pic";
	public static File uploadFile(String imageUrl, boolean forOpt) throws IOException
	{
		URL url = new URL(imageUrl);
		File folderForCopy;
		if(forOpt)
		{
			folderForCopy = new File(PATH_ON_SERVER_FOR_OPT);
		}
		else
		{
			folderForCopy = new File(PATH_ON_SERVER);
		}
		if (!folderForCopy.exists())
		{
			folderForCopy.mkdirs();
		}
		File result = new File(folderForCopy, UUID.randomUUID().toString().substring(1,10).replaceAll("-","_") + ".jpg");
		result.createNewFile();
		try(InputStream inputStream = url.openStream(); OutputStream outputStream = new FileOutputStream(result))
		{
			BufferedImage image = ImageIO.read(inputStream);
			image = image.getSubimage(0, 0, image.getWidth(), image.getHeight() - 50);
			ImageIO.write(image,"jpg", outputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}




		return result;
	}
}
