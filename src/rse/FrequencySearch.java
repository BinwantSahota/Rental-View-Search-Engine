package rse;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrequencySearch {
    private static Map<String, Integer> wordSearchCount;
    private static Map<String, Integer> searchHistory;
    
    public FrequencySearch() {
      wordSearchCount = new HashMap<>();
        searchHistory = new HashMap<>();
    }
   //forming dictionary from the files.
    public static void addFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\W+"); // split on non-word characters
            for (String word : words) {
                String lowerCaseWord = word.toLowerCase();
                if (!wordSearchCount.containsKey(lowerCaseWord)) {
                    wordSearchCount.put(lowerCaseWord, 0);
                }
            }
        }
        reader.close();
    }
    //if word already exist increase the count and maintain search history
    public static void searchWord(String word) {
        String lowerCaseWord = word.toLowerCase();
        if (wordSearchCount.containsKey(lowerCaseWord)) {
            int count = wordSearchCount.get(lowerCaseWord);
            wordSearchCount.put(lowerCaseWord, count + 1);
           // add to search history
            if (!searchHistory.containsKey(lowerCaseWord)) {
                searchHistory.put(lowerCaseWord, count);
            }
            else
            {
            	int value = searchHistory.get(lowerCaseWord);
            	searchHistory.put(lowerCaseWord, value+1);
            }
                    } else {
           // System.out.println(word + " is not present in dictionary");
        }

    }

    //Method is for calculating the search frequency of searched word.
    public static void searchCount(String word) throws IOException {
        String folderPath = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-TEXT";
        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                addFile(file.getAbsolutePath());
            }
        }
        searchWord(word);

//         print search history
        if (searchHistory.containsKey(word.toLowerCase())) {
            System.out.println("Search history for " + word + ":");
            int prevCount = searchHistory.get(word.toLowerCase());
            for (int i = prevCount; i < wordSearchCount.get(word.toLowerCase()); i++) {
                System.out.println("- Searched " + (i + 1) + " times");
            }

    }
    }
}

