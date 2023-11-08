package rse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class URLsave {
	//Save the html files of crawled websites.
	public static void saveHtmlFile(String url) {
		 try {
			 //connect to the url
	            Connection connection = Jsoup.connect(url);
	            Document webpage = connection.get();
	            //If connection is success then save file to local folder
	            if (connection.response().statusCode() == 200) {
	                String folderName = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-HTML";
	                File folder = new File(folderName);
	                // Save the document to a file
//	                if(!folder.exists())
//	                {
//	                	folder.mkdir();
//	                }
	                File filename = new File(folderName + "/" + url.replaceAll("[^a-zA-Z]", "") + ".html");               
	                FileOutputStream outputStream = new FileOutputStream(filename);
	                OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
	                writer.write(webpage.html());
	                writer.close();
	                
	            }

	        } catch (IOException e) {
	            
	        }
		}

}
