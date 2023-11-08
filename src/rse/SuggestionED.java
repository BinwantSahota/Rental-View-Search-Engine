package rse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SuggestionED {
	private Set<String> words;

	public SuggestionED() {
		words = new HashSet<>();
	}

	
	public void addWordsFromFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.isDirectory()) {
			System.err.println("Error: " + folderPath + " is not a directory.");
			return;
		}

		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				addWordsFromFile(file.getAbsolutePath());
			}
		}
	}

	//Add words/form dictionary based on the text files
	public void addWordsFromFile(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				String[] wordsInLine = line.split("\\s+");
				for (String word : wordsInLine) {
					word = word.toLowerCase().replaceAll("[^a-zA-Z]", "");
					if (!word.isEmpty()) {
						words.add(word);
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Error reading file: " + filename);
			e.printStackTrace();
		}
	}
	//Method to retrieve the words from dictionary
	public Set<String> getWords() {
		return words;
	}
	//Min edit distance 
		public  static int min(int len1, int len2, int len3 )
	    {
	        if (len1 <= len2 && len1 <= len3)
	            return len1;
	        if (len2 <= len1 && len2 <= len3)
	            return len2;
	        else
	            return len3;
	    }
		
		//calculate the distance between two words 
		public static int editDist(String str1, String str2, int lenS1,
			            int lenS2)
			{
			//If length of string 1 is zero then return length of string 2 as distance
			if (lenS1 == 0)
			return lenS2;
			//If length of string 2 is zero then return length of string 1 as distance
			
			if (lenS2 == 0)
			return lenS1;
			//if there is a match of character at position call the edit distance method for the next set of characters
			if (str1.charAt(lenS1 - 1) == str2.charAt(lenS2 - 1))
			return editDist(str1, str2, lenS1 - 1, lenS2 - 1);
			return 1
			+ min(editDist(str1, str2, lenS1, lenS2 - 1), 
			      editDist(str1, str2, lenS1 - 1, lenS2), 
			      editDist(str1, str2, lenS1 - 1, lenS2 - 1) 
			  );
			}
	//method to give the list of suggested words
	public List<String> suggestWordCompletions(String prefix) {
		List<String> suggestions = new ArrayList<>();
		patternMatching pm = new patternMatching(prefix);
		for (String word : words) {
//			try {
//				if (pm.patternSearch(word)) {
//					suggestions.add(word);
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			int d= editDist(word,prefix,word.length(),prefix.length());
			//System.out.println(d);
			if(d<=2)
				suggestions.add(word);
			//suggestions.addAll(getWordsWithinEditDistance(prefix, d));
		}
		if (suggestions.isEmpty()) {
			suggestions.addAll(getWordsWithinEditDistance(prefix, 1));
		}
		return suggestions;
	}
	
	public Set<String> getWordsWithinEditDistance(String word, int distance) {
		Set<String> candidates = new HashSet<>();
		getWordsWithinEditDistance(word, distance, "", candidates);
		return candidates;
	}
	//Get the words using edit distance
	private void getWordsWithinEditDistance(String word, int distance, String current, Set<String> candidates) {
		if (distance == 0) {
			if (words.contains(current)) {
				candidates.add(current);
			}
			return;
		}
		int len = word.length();
		for (int i = 0; i < len; i++) {
			String newWord = current + word.charAt(i);
			if (words.contains(newWord)) {
				candidates.add(newWord);
			}
			getWordsWithinEditDistance(word.substring(i + 1), distance - 1, newWord, candidates);
			getWordsWithinEditDistance(word.substring(i), distance - 1, newWord, candidates);
			getWordsWithinEditDistance(word.substring(i + 1), distance - 1, current, candidates);
			getWordsWithinEditDistance(word.substring(i), distance - 1, current, candidates);
		}
		if (len == 0) {
			for (String w : words) {
				if (w.length() == 1) {
					candidates.add(w);
				}
			}
		}
	}

	// start method to call the word completion  
	public static List<String> autoSuggestion(String word) {
		SuggestionED dict = new SuggestionED();
		dict.addWordsFromFolder("C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-TEXT");
		List<String> suggestions = dict.suggestWordCompletions(word);

		return suggestions;
	}
}
