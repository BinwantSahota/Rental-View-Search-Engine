package rse;

import java.io.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


    public class spellchecker { 
    	
    	static class TrieNode {
    	    Map<Character, TrieNode> children;
    	   // Flag to check if the current node is a complete word or not
    	    boolean isWord;

    	    TrieNode() {
    	        children = new HashMap<>();
    	        isWord = false;
    	    }
    	}
    	// class for TrieNode
    	static class Trie {
    	  public  static TrieNode root;

    	    Trie() {
    	        Trie.root = new TrieNode();
    	    }
    	}

    	// Method to insert a word into the Trie
		public static void insert(String word) {
    	        TrieNode node = Trie.root;
    	        for (char c : word.toCharArray()) {
    	            node.children.putIfAbsent(c, new TrieNode());
    	            node = node.children.get(c);
    	        }
    	        if (!node.isWord) { // check if the word already exists
    	            node.isWord = true;
    	        }
    	    }

    	  
		// Method to search for a word in the Trie
    	  public static boolean search(String word) {
  	        TrieNode node = Trie.root;
  	        for (char c : word.toCharArray()) {
  	            if (!node.children.containsKey(c)) {
  	                return false;
  	            }
  	            node = node.children.get(c);
  	        }
  	        return node.isWord;
  	    }

    	  // Method to suggest words for the misspelled words from trie
  	    public static Set<String> suggestWords(Trie trie, String word) {
  	        Set<String> suggestions = new HashSet<>();

  	        // check if the word is already correct
  	        if (search(word)) {
  	            suggestions.add(word);
  	            return suggestions;
  	        }

  	        // check if a character is missing
  	        for (int i = 0; i < word.length(); i++) {
  	            char[] chars = word.toCharArray();
  	            for (char c = 'a'; c <= 'z'; c++) {
  	                chars[i] = c;
  	                String newWord = new String(chars);
  	                if (search(newWord)) {
  	                    suggestions.add(newWord);
  	                }
  	            }
  	        }

  	        // check if an extra character is present
  	        for (int i = 0; i < word.length(); i++) {
  	            String newWord = word.substring(0, i) + word.substring(i + 1);
  	            if (search(newWord)) {
  	                suggestions.add(newWord);
  	            }
  	        }

  	        // check if a character needs to be replaced
  	        for (int i = 0; i < word.length(); i++) {
  	            char[] chars = word.toCharArray();
  	            for (char c = 'a'; c <= 'z'; c++) {
  	                if (c == word.charAt(i)) {
  	                    continue;
  	                }
  	                chars[i] = c;
  	                String newWord = new String(chars);
  	                if (search(newWord)) {
  	                    suggestions.add(newWord);
  	                }
  	            }
  	        }

  	        return suggestions;
  	    }
  	    // Main method to call the spell check functionality and returns true or false
    	public static boolean spellChecking(String inputword) throws IOException {
    	    String folderPath = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW\\inventory-HTML";
    	    File folder = new File(folderPath);
    	    File[] files = folder.listFiles(new FilenameFilter() {
    	        public boolean accept(File dir, String name) {
    	            return name.toLowerCase().endsWith(".html");
    	        }
    	    });
    	    List<String> words = new ArrayList<>();
    	    for (File file : files) {
    	    	Document doc = Jsoup.parse(file, "UTF-8");
              String text = doc.body().text().toLowerCase();
    	            if (!text.isEmpty()) {
    	                String[] tokens = text.split(" ");
    	                for (String token : tokens) {
    	                    words.add(token.toLowerCase());
    	                }
    	            }
    	    }

    	    Trie trie = new Trie();
    	    for (String word : words) {
    	        insert(word);
    	    }
    	        if (search(inputword.toLowerCase())) {
    	            System.out.println("Correct spelling.");
    	            return true;
    	        } else {
    	   
    	        	List<String> suggestions =  SuggestionED.autoSuggestion(inputword.toLowerCase());
    	            if (suggestions.isEmpty()) {
    	                System.out.println("Incorrect spelling. No suggestions found please Enter correct Word.");
    	                return false;
    	            } else {
    	                System.out.println("Incorrect spelling. Did you mean:");
    	                for (String suggestion : suggestions) {
    	                    System.out.println("- " + suggestion);
    	                }
    	                return false;
    	            }
    	        }
    	    }
    	
    	}
