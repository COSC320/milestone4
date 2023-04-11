package second_algo;

import java.util.*;
import java.io.*;


public class trie {
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
    };
      
    static TrieNode root=new TrieNode();
     
    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    static void insert(String key, String full)
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
      
    // Returns true if key presents in trie, else false
    static boolean search(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;
      
        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
      
            if (pCrawl.children[index] == null)
                return false;
      
            pCrawl = pCrawl.children[index];
        }
      
        return (pCrawl.isEndOfWord);
    }
      
    public static void main(String args[]){
    	int numAbbrevs= 48;
	  
    	String keys[]=new String[numAbbrevs];
    	String[] full= new String[numAbbrevs];
    	
    	File abbreviations=new File("/Users/austin/Documents/COSC320/milestone4/second_algo/abbreviations.txt");
			Scanner in;
			try {
				in = new Scanner(abbreviations);
				
				in.useDelimiter(",|\\n");
				
				int check=0;
				int counter=0;
				while(in.hasNextLine()) {
					if (check%2==0)
					keys[counter]=in.next().toUpperCase();
					
					else {
						full[counter]=in.next();
						counter++;
					}
					check++;
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		for (int i=0;i<numAbbrevs;i++) {
			System.out.println(keys[i]);
			System.out.println(full[i]);
		}	
		
	for(int i=0;i<numAbbrevs;i++) {
		insert(keys[i],full[i]);
		
	}   
	
    }
}
  
	    
	

