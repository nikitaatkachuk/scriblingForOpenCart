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

public final class FileUploader 
{
	private static final String PATH_ON_SERVER = "/var/www/image/data/scribler/";
	
	public static File uploadFile(String category, String shopName, String imageUrl) throws IOException
	{
		URL url = new URL(imageUrl);
		File folderForCopy = new File(PATH_ON_SERVER + category + "/" + shopName);
		File result = new File(folderForCopy, UUID.randomUUID().toString());
		InputStream inputStream = url.openStream();
		OutputStream outputStream = new FileOutputStream(result);
		
		byte[] buffer = new byte[2048];
        
        int length = 0;
        
        while ((length = inputStream.read(buffer)) != -1) {
           System.out.println("Buffer Read of length: " + length);
           outputStream.write(buffer, 0, length);
        }
		return result;
		
	}
}
