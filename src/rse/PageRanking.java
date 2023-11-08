package rse;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class PageRanking {
    /* This method takes two hashmaps: 'frequenciesCount' containing the frequency count of each keyword in all web pages
    and 'htmlTextFile' containing the HTML text content of each web page. It calculates the ranking of each web page
    based on the frequency count of the keywords and displays the ranked web pages in descending order.
    */
    public void PageRank(HashMap<String, Integer> frequenciesCount, HashMap<String, String> htmlTextFile) throws IOException {
        // Use a priority queue to keep track of web pages and their ranking
        PriorityQueue<WebPage> rankQueue = new PriorityQueue<>();

        // Add each web page and its frequency count to the priority queue
        for (Map.Entry<String, Integer> entry : frequenciesCount.entrySet()) {
            String fileName = entry.getKey();
            int frequency = entry.getValue();
            rankQueue.offer(new WebPage(fileName, frequency));
        }

        // display the ranked web pages
        int count = 0;
        if (!rankQueue.isEmpty()) {
            System.out.println("Ranked Web Pages:");
            while (!rankQueue.isEmpty()) {
                count++;
                // remove the next highest ranked web page from the queue
                WebPage webPage = rankQueue.poll();

                // Display the web page's rank, filename, and frequency count
                System.out.println("Rank is " + count + " for " + webPage.fileName + " -> " + webPage.frequency + " occurrences");
            }
        } else {
            System.out.println("Keyword not found in any web page.");
        }
    }

    // A private nested class to represent a web page and its frequency count
    private static class WebPage implements Comparable<WebPage> {
        private String fileName;
        private int frequency;

        public WebPage(String fileName, int frequency) {
            this.fileName = fileName;
            this.frequency = frequency;
        }

        // Compare the ranking of two WebPage objects
        @Override
        public int compareTo(WebPage other) {
            int cmp = Integer.compare(other.frequency, this.frequency);
            if (cmp == 0) {
                cmp = this.fileName.compareTo(other.fileName);
            }
            return cmp;
        }
    }
}
