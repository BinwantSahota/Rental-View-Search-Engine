package rse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.jsoup.Jsoup;


public class URLParsing {
	public static HashMap<String,String> htmlTextMap = new HashMap<>();
	//Parsing the Html files
	public static HashMap<String,String> parsingHtmlFiles(File inputDir,File outputDir) throws IOException {		
		for (final File fileName : inputDir.listFiles()) {	
			if (fileName.isFile()) {
				convertHtmlFileToText(fileName.getName(),outputDir,inputDir);
			}
		}				
	System.out.println("files converted to text and saved..");
	 return htmlTextMap;
	}
	//Converting the Html file to text file using Jsuop
	private static void convertHtmlFileToText(String inputfilename,File outputDir,File inputDir) throws IOException {
		String[] fName = inputfilename.split("\\.html");
		String fileName = fName[0].replaceAll("https__", "");
		File newFile = new File(fileName);
		if (!outputDir.exists()) {
			System.out.println("creating directory: " + outputDir);
			boolean result = outputDir.mkdir();
			if (result) {
				System.out.println("DIR created");
			}
		}
		File inputFile = new File(inputDir + "/" + inputfilename);
		org.jsoup.nodes.Document doc = Jsoup.parse(inputFile, "UTF-8", "");	
		
		if(!newFile.exists())
		{
			String outputFileName = outputDir + "/"+fileName+ ".txt";
			htmlTextMap.put(fileName+ ".txt",inputfilename);
			String text = doc.text().toLowerCase();
			text = text.replaceAll("[^a-zA-z0-9\\s]", "");
			PrintWriter out = new PrintWriter(outputFileName);
			out.println(text);
			out.close();
			
		}
	}
}