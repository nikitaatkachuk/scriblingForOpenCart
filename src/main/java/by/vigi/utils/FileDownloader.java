package by.vigi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

public final class FileDownloader
{
	private static final String PATH_ON_SERVER = "/var/www/image/data/demo/pic";
	public static File uploadFile(String imageUrl) throws IOException
	{
		URL url = new URL(imageUrl);
		File folderForCopy = new File(PATH_ON_SERVER);
		if (!folderForCopy.exists())
		{
			folderForCopy.mkdirs();
		}
		File result = new File(folderForCopy, UUID.randomUUID().toString().substring(1,10).replaceAll("-","_") + ".jpg");
		result.createNewFile();
		InputStream inputStream = url.openStream();
		OutputStream outputStream = new FileOutputStream(result);
		
		byte[] buffer = new byte[4086];
        
        int length = 0;
        
        while ((length = inputStream.read(buffer)) != -1)
        {
           outputStream.write(buffer, 0, length);
        }
		return result;
		
	}
}
