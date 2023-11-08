package rse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URLParserformat {

    // Validate the postal code
    public static boolean validatePostalCode(String postalCode) {
        Pattern pattern = Pattern.compile("[A-Za-z]\\d[A-Za-z]\\s?\\d[A-Za-z]\\d");
        Matcher matcher = pattern.matcher(postalCode);
        return matcher.find();
    }

    // Validate the address
    public static boolean validateAddress(String address) {
        String regex = "^([0-9\\s+a-zA-Z.\\d]*|[A-Za-z0-9]*|[A-Za-z\\s]*$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);
        return matcher.find();
    }

    // Get the property details from the html files
 // Get the property details from the html files
    public static void parserProperties(String inputDir) {
        File folder = new File(inputDir);
        File[] htmlFiles = folder.listFiles((dir, name) -> name.endsWith(".html"));

        Set<String> uniqueAddresses = new HashSet<>();
        Set<String> processedFileNames = new HashSet<>();
        String outputFilePath = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\output1.txt";
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(outputFilePath);
            buffer = new BufferedWriter(writer);

            for (File file : htmlFiles) {
                if (processedFileNames.contains(file.getName())) {
                    continue; // Skip processing if the file has already been processed
                }

                // Parse the document using Jsoup
                Document doc = Jsoup.parse(file, "UTF-8", "");

                // Getting property details from 4rent.ca
                if (file.getName().contains("rentca")) {
                    Set<String> addressesWritten = new HashSet<>();

                    Element liElement = doc.select("ul.header li").last();
                    if (liElement != null) {
                        String address3 = liElement.text().trim();
                        if (addressesWritten.contains(address3)) {
                            continue;
                        }
                        uniqueAddresses.add(address3);
                        boolean addressValid3 = validateAddress(address3);

                        Element bedsElement = doc.select("li.p-4 div.flex:nth-child(1)").first();
                        String bedrooms2 = bedsElement != null ? bedsElement.text().trim() : "N/A";

                        // Extract price
                        Element priceElement = doc.select("li.p-4 div.flex:nth-child(3)").first();
                        String price3 = priceElement != null ? priceElement.text().trim() : "N/A";

                        Element sqftElement = doc.select("ul.highlights li:containsOwn(sq. ft.)").first();
                        String area1 = sqftElement != null ? sqftElement.ownText().trim() : "N/A";

                        if (addressValid3) {
                            // Write the extracted data to the output file
                            String outputString3 = "Address: " + address3 + " | Bedrooms: " + bedrooms2 + " | Price: "
                                    + price3 + " | Area: " + area1 + "\n";
                            buffer.write(outputString3);
                            buffer.newLine();
                            addressesWritten.add(address3);
                        }
                    }
                }

                processedFileNames.add(file.getName()); // Mark the file as processed
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
