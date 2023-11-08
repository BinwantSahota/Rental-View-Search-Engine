package rse;

import java.io.*;
import java.util.*;

public class InvertedIndexing {
	// Create a Ternary Search Tree to hold the inverted index
	private TST<Set<String>> index;
	
	 // Constructor for the InvertedIndexing class
	public InvertedIndexing() {
		this.index = new TST<>();
	}
	// A Ternary Search Tree implementation
	public class TST<Value> {
		private int size;
		private Node root;
		
		// A node class in the TST, which has a character, a left, middle, and right node,
        // and a value
		private class Node {
			private char c;
			private Node left, mid, right;
			private Value val;
		}
		 // Get the size of the TST
		public int size() {
			return size;
		}
		 // Check if the TST contains a given key
		public boolean contains(String key) {
			return get(key) != null;
		}
		// Get the value for the given key in the TST
		public Value get(String key) {
			Objects.requireNonNull(key);
			if (key.length() < 1) {
				throw new IllegalArgumentException("Key must have length >= 1");
			}
			Node x = get(root, key, 0);
			return (x == null) ? null : x.val;
		}

		//  Method to recursively get the node associated with a given key in the TST
		private Node get(Node x, String key, int d) {
			Objects.requireNonNull(key);
			if (d == key.length()) {
				return null;
			}
			char c = key.charAt(d);
			if (x == null) {
				return null;
			}
			if (c < x.c) {
				return get(x.left, key, d);
			} else if (c > x.c) {
				return get(x.right, key, d);
			} else if (d < key.length() - 1) {
				return get(x.mid, key, d + 1);
			} else {
				return x;
			}
		}
		
		// Insert a key-value pair into the TST
		public void put(String key, Value val) {
			if (!contains(key)) {
				size++;
			}
			root = put(root, key, val, 0);
		}
		
		//Method recursively insert a key-value pair into the TST
		private Node put(Node x, String key, Value val, int d) {
			char c = key.charAt(d);
			if (x == null) {
				x = new Node();
				x.c = c;
			}
			if (c < x.c) {
				x.left = put(x.left, key, val, d);
			} else if (c > x.c) {
				x.right = put(x.right, key, val, d);
			} else if (d < key.length() - 1) {
				x.mid = put(x.mid, key, val, d + 1);
			} else {
				x.val = val;
			}
			return x;
		}
	}
	// Build the inverted index for a given directory of HTML files
	public void buildIndex(String htmlDir) throws IOException {
		File dir = new File(htmlDir);
		File[] files = dir.listFiles();
		for (File file : files) {
			String text = fileContent(file.getAbsolutePath());
			String[] words = text.split(" ");
			for (String word : words) {
				if (word != "") {
					Set<String> documents = index.get(word);
					if (documents == null) {
						documents = new HashSet<>();
					}
					documents.add(file.getName());
					index.put(word, documents);
				}
			}
		}
	}

	public List<String> search(String query) {
		List<String> result = new ArrayList<>();
		Set<String> documents = index.get(query);
		if (documents != null) {
			result.addAll(documents);
		}
		return result;
	}

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

	public List<String> invertedIndex(String result,String textFilesDir) {
		 InvertedIndexing index = new InvertedIndexing();
		List<String> indexing = new ArrayList<>();
		try {
			index.buildIndex(textFilesDir);
			indexing =index.search(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return indexing;
	}
}
