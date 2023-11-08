package rse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Rentalcrawler {
	//crawling the websites using selunium
	public static void webCrawling() {
			RentalWebCrawling.rentalWebCrawling("https://4rent.ca/");
		
	}

	
	
}
