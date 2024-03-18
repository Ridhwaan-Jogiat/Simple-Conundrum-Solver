import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {	
	
	/**
	 * A method for recursively search for a String in an array of Strings
	 * 
	 * @param word  - The word of type String that we are looking for.
	 * @param words - The String array containing all the words
	 * @param lo    - The lower bound for the search
	 * @param hi    - The upper bound for the search
	 * @return the index of the found word in the words array 
	 * ********************************************
	 */
	public static int recursiveBinarySearch(String word, String[] words, int lo, int hi) {
		// Calculate mid point
		int midPoint = lo + (hi - lo) / 2;

		 // If mid element matches return index
		if (words[midPoint].equals(word)) {
			return midPoint;
		}

		// Search lower half if word is alphabetically before mid
		if (words[midPoint].compareTo(word) < 0) {
			return recursiveBinarySearch(word, words, midPoint + 1, hi);
		// Otherwise search upper half
		} else {
			return recursiveBinarySearch(word, words, lo, midPoint - 1);
		}
	}

	/**
	 * @param word - The String which its characters will be shuffled around
	 * @return The shuffled String 
	 * ********************************************
	 */
	public static String mixCharacterOrder(String word){	
		// Create an empty list to store the characters of the word.
		List<Character> chars = new ArrayList<Character>();
		
		 // Add each character of the word to the list.
		for(char c:word.toCharArray())
		{
			chars.add(c);
		}
		// Create a StringBuilder object to build the scrambled word.
		StringBuilder output = new StringBuilder(word.length());
		 // Loop until all characters are removed from the list.
		while(chars.size()!=0) {
			int randomIndex = (int)(Math.random()*chars.size());
			 // Extract character at random index and append it to output.
			output.append(chars.remove(randomIndex));	
		}
		return output.toString();
	}
	/**
	 * The conundrum solver that uses the array dictionary, mixCharacterOrder 
	 * and recursive binary search.
	 * @param conundrum - The current conundrum that needs to be solved.
	 * @param words - The String array containing all the dictionary words
	 * @return A valid word that can be found in the conundrum
	 */
	public static String solveConundrum1(String conundrum, String[] words){				
		// Continuously loop until a solution is found or no further attempts are possible.
		while(recursiveBinarySearch(conundrum, words,0,words.length) == -1)
		{
			// If no solution is found in the current conundrum form, scramble its characters.
			conundrum = mixCharacterOrder(conundrum);
			
		}
		return conundrum;
	
	}
	
	/**
	 * The conundrum solver that uses the DList dictionary, mixCharacterOrder 
	 * and the DList search.
	 * @param conundrum - The current conundrum that needs to be solved.
	 * @param words - The String array containing all the dictionary words
	 * @return A valid word that can be found in the conundrum
	 */
	public static String solveConundrum2(String conundrum, DList<String> words){		
		while(words.search(conundrum) == null){
			conundrum = mixCharacterOrder(conundrum);
		}
		return conundrum;				
	}
	/**
	 * @param path - The location of the dictionary
	 * @return The String array of words  containing all the dictionary words
	 */
	public static String[] loadPotentialDicitonary1(String path){	
		// Open file  
		File file = new File(path);
		// Declare a array to store words
		String[] array=null;
		try {
			// Create a Scanner object to read the file.
			Scanner scan = new Scanner(file);
			// Initialize the array 
			array=new String[(int)file.length()];
			int i=0;
			// Loop through the scanner
			while(scan.hasNext()) {
				array[i]=scan.nextLine();
				i++;
			}
			//Close scanner
			 scan.close();
			//Error handling
		}catch (Exception e) {
			System.out.println("Error loading the dictionary"); 
		}
		return array;
	}	
	
	/**
	 * @param path - The location of the dictionary
	 * @return The String array of words  containing all the dictionary words
	 */
	public static DList<String> loadPotentialDictionary2(String path){		
		DList<String> wordsList = new DList<String>();
		File f = new File(path);		
		 
		try {
			Scanner s = new Scanner(f);
			
			while(s.hasNextLine()){
				wordsList.addLast(s.nextLine());					
			}			
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		System.out.println(wordsList.size()+ " entries loaded");
		return wordsList;
	}
	/*
	 * The main method
	 */
	public static void main(String[] args) {
		final String PATH = "src/test.dict"; 
		String conundrum = "ionlitabo";
		
		System.out.println("Dictionary Load 1 begin");
		long startTime = System.currentTimeMillis();
		String[] words = loadPotentialDicitonary1(PATH);
		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime)/1000.0;
		System.out.println("Dictionary Load 1 completed in "+ totalTime+" seconds");
		
		System.out.println("Dictionary Load 2 begin");
		startTime = System.currentTimeMillis();
		DList<String> wordList = loadPotentialDictionary2(PATH);
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime)/1000.0;
		System.out.println("Dictionary Load 2 completed in "+ totalTime+" seconds");
		
		System.out.println("Algorithm 1 Test begin");
		startTime = System.currentTimeMillis();
		//System.out.println("The found word is: "+solveConundrum1(conundrum, words));
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime)/1000.0;
		System.out.println("Algorithm 1 Test completed in "+ totalTime+" seconds");
		
		System.out.println("Algorithm 2 Test begin");
		startTime = System.currentTimeMillis();
		System.out.println("The found word is: "+solveConundrum2(conundrum, wordList));
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime)/1000.0;
		System.out.println("Algorithm 2 Test completed in "+ totalTime+" seconds");
	}

}
