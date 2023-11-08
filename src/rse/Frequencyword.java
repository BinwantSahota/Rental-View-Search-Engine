package rse;

import java.util.*;
import java.io.*;
import java.net.*;

public class Frequencyword {
    private static final String txtDirectoryPath = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-TEXT";
    private static HashMap<String, Integer> frequencies = new HashMap<>();

    // get the file content
    public static String fileContent(String inFile) {
        StringBuilder SbContent = new StringBuilder();
        try {
            FileReader fr = new FileReader(inFile);
            BufferedReader brContent = new BufferedReader(fr);
            String content;
            while ((content = brContent.readLine()) != null) {
                SbContent.append(content);
            }

            brContent.close();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
        String filecontent = SbContent.toString();

        return filecontent;
    }

    // Populate the htmlTextFile HashMap with file names and their content
    public static HashMap<String, String> populateHtmlTextFileMap() {
        HashMap<String, String> htmlTextFile = new HashMap<>();
        File txtDirectory = new File(txtDirectoryPath);
        File[] files = txtDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String fileContent = fileContent(file.getAbsolutePath());
                htmlTextFile.put(fileName, fileContent);
            }
        }
        return htmlTextFile;
    }

    // Get the frequency count for the words
    public HashMap<String, Integer> frequencyCount(String word, String txtDirectoryPath,
            HashMap<String, String> htmlTextFile) throws Exception {
        InvertedIndexing index = new InvertedIndexing();
        List<String> indexFiles = index.invertedIndex(word, txtDirectoryPath);

        for (String fileName : indexFiles) {
            String text = fileContent(txtDirectoryPath + fileName);
            String[] words = text.toLowerCase().split(" ");
            // Count the frequency of the input word
            for (String w : words) {
                patternMatching pm = new patternMatching(word);
                if (pm.search(w)) {
                    if (frequencies.containsKey(fileName))
                        frequencies.put(fileName, frequencies.get(fileName) + 1);
                    else {
                        frequencies.put(fileName, 1);
                    }
                }
            }
        }
//        for (String fileName : frequencies.keySet()) {
//            System.out.println(word + " found in file " + htmlTextFile.get(fileName) + "  " + frequencies.get(fileName)
//                    + " times");
//        }
        return frequencies;
    }
}
