package rse;

public class patternMatching {
	// A final integer representing the maximum possible character value
	private final int finalR;
	
	// An array to store the rightmost occurrence of each character in the pattern
	private static int[] rightMost;

	private static String pattern;

	public patternMatching(String pattern) {
		this.finalR = 10000;
		this.pattern = pattern;

		//Initializing the array
		rightMost = new int[finalR];
		for (int ch = 0; ch < finalR; ch++)
			rightMost[ch] = -1;
		for (int j = 0; j < pattern.length(); j++)
			rightMost[pattern.charAt(j)] = j;
	}

	//If match found then it will return otherwise false
	public static boolean search(String text) {
		int M = pattern.length();
		int N = text.length();
		int skip;
		for (int i = 0; i <= N - M; i += skip) {
			skip = 0;
			for (int j = M - 1; j >= 0; j--) {
				if (pattern.charAt(j) != text.charAt(i + j)) {
					skip = Math.max(1, j - rightMost[text.charAt(i + j)]);
					break;
				}
			}
			if (skip == 0)
				return true;
		}
		return false;
	}
	//Main function for searching the pattern in text
	public static boolean patternSearch(String text) throws Exception {
		boolean match = false;
		match = search(text);
		return match;

	}
}
