package rse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Index {
    private static FrequencySearch dictionary;
    private static String htmlFilesDir = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-HTML\\";
    private static String textFilesDir = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-TEXT\\";
    public static File inputDir = new File("C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-HTML");
    public static File outputDir = new File("C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-TEXT");
    static HashMap<String, String> htmlTextFile = new HashMap<>();

    public static void main(String[] args) throws IOException {
        dictionary = new FrequencySearch();
        try {
        	System.out.println("----------------------------------------------");
    		System.out.println("|                                            |");
    		System.out.println("|                                            |");
    		System.out.println("|                                            |");
    		System.out.println("|      Welcome to RentalVIEW search Engine   |");
    		System.out.println("|                                            |");
    		System.out.println("|                                            |");
    		System.out.println("|                                            |");
    		System.out.println("----------------------------------------------");
            boolean exit = false;
            do {
                // Prompt the user to choose an option
                System.out.println(
                        "Press \n1 TO INITIATE CRAWLING\n2 INTIATE PARSING\n3 TO WATCH AVAILABLE RENTAL LISTINGS\n4 FOR WORD SEARCH ENGINE\n0 TO STOP");
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                sc.nextLine();
                // Process the user's input
                switch (input) {
                    case "1":
                        // Run the web crawler
                        Scanner scanner = new Scanner(System.in);
                        Scanner scan = new Scanner(System.in);
                        System.out.print("YOU READY TO PROCEED ? PRESS 'Y' TO START");
                        String ch = scan.nextLine();
                        if (ch.equalsIgnoreCase("Y")) {
                            System.out.println("-----initiated-----");
                            Rentalcrawler.webCrawling();
                            System.out.println("Crawling is finished and files are saved in inventory-HTML folder");
                        }
                        break;
                    case "2":
                        // Parse the HTML files
                        URLParsing parsing = new URLParsing();
                        htmlTextFile = parsing.parsingHtmlFiles(inputDir, outputDir);
                        break;
                    case "3":
                        // Get details of a rental property based on street name
                        boolean result = true;
                        System.out.println("Type in locality you wish to seek availibility for :");
                        do {
                            Scanner scann = new Scanner(System.in);
                            String streetName = scann.nextLine();
                            dictionary.searchCount(streetName);
                            if (spellchecker.spellChecking(streetName)) {
                            	URLParserformat parser = new URLParserformat();
                                parser.parserProperties(htmlFilesDir);
                                URLavailableListings AL = new URLavailableListings();
                                AL.propertyDeal(streetName);
                                result = false;
                            } else {
                                System.out.println("Enter any word from given suggestions");
                            }
                        } while (result);
                        break;
                    case "4":
                        // Perform a search query
                        System.out.println("Enter the word that you want to search:");
                        Scanner scan1 = new Scanner(System.in);
                        String inputWord = scan1.nextLine();
                        dictionary.searchCount(inputWord);
                        Frequencyword wf = new Frequencyword();
                        HashMap<String, Integer> frequencyCount = wf.frequencyCount(inputWord, textFilesDir, htmlTextFile);
                        PageRanking pages = new PageRanking();
                        pages.PageRank(frequencyCount, htmlTextFile);
                        break;
                    case "0":
                        // Exit the program
                        System.out.println("Terminating the process...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Option selected is invalid");
                        break;
                }
            } while (!exit);
        } catch (Exception ex) {
            System.err.println("An exception occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
