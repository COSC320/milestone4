import java.util.*;
import java.io.*;
import java.io.*;

public class Trie {
	// Alphabet size (# of symbols)
    static final int ALPHABET_SIZE = 26;
     
    // trie node
    static class TrieNode {
       TrieNode[] children = new TrieNode[ALPHABET_SIZE];
       String full;
        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;
         
        TrieNode(){
            isEndOfWord = false;
            full=null;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    }
      
    static TrieNode root=new TrieNode();
     
    // If not present, inserts abbreviation into trie
    // If the abbreviation is prefix of trie node,
    // just marks leaf node
    static void insert(String key,String full)
    {
        int level;
        int length = key.length();
        int index;
      
        TrieNode pCrawl = root;
      
        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'A';
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();
      
            pCrawl = pCrawl.children[index];
        }
      
        // mark last node as leaf
        pCrawl.isEndOfWord = true;
        pCrawl.full=full;
        
    }
      
    // Returns the debreviated abbreviation if the word matches abbrev, else reutrns the original word
    static String replacer(String key) 
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;
      
        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'A';
      
            if (pCrawl.children[index] == null)
                return key;
      
            pCrawl = pCrawl.children[index];
        }
      if(pCrawl.isEndOfWord)
       return pCrawl.full;
      
      else
    	  return key;
    }
      
    public static void main(String args[]){
    	int numAbbrevs= 48;
	  
    	String keys[]=new String[numAbbrevs];
    	String[] full= new String[numAbbrevs];
    	
    	
		try {
            Scanner scanner;
			scanner = new Scanner(new File("abbreviations.txt"));
			int counter=0;
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] abbrev = line.split(",");
	            if (abbrev.length == 2) {
	                keys[counter] = abbrev[0].trim().toUpperCase();
	                full[counter] = abbrev[1].trim().toUpperCase();
	            }
	                counter++;
	             Queue<String> queue = new LinkedList<String>(); // create a queue to store the values
        }
    } catch (FileNotFoundException e) {
			e.printStackTrace();
	}
    }   
}
