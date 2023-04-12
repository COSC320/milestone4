package second_algo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class trie_graph {
    
    static class TrieNode {
        boolean isEndOfWord;
        String meaning;
        TrieNode[] children;

        public TrieNode() {
            isEndOfWord = false;
            meaning = null;
            children = new TrieNode[26];
            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
        }
    }

    static TrieNode root;

    public static void main(String[] args) {

        // set the delimiter for the scanner
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\r\n|\\n");
    
        root = new TrieNode();
        Queue<String> queue = new LinkedList<String>(); // create a queue to store the values
    
        try {
            scanner = new Scanner(new File("file.csv"));    //input csv file, change name here for the csv file
            scanner.nextLine(); // ignore header
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // regex to split the csv but ignore the commas in that column
    
                if (values.length > 3) {
                    String abbreviation = values[3];
                    String meaning = values[4];
                    
                    queue.add(abbreviationReplacer(abbreviation)); // add the value to the queue and replace abbreviations
                }
            }
            scanner.close();
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    
        // write output to file
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"));   //output with values for graph
            writer.write("String length, Word Count, Execution Time\n");
            while (!queue.isEmpty()) {
                long startTime = System.nanoTime(); // start time of processing queue
                String output = queue.remove();
                int wordCount = output.split("\\s+").length; // count number of words
                int length = output.length(); // get length of string
                writer.write(length + "," + wordCount + "," + (System.nanoTime() - startTime)); // write length, word count, and time to csv
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    static void insert(TrieNode root, String key, String meaning) {
        TrieNode node = root;
        int index;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (!Character.isAlphabetic(ch)) {
                // skip non-alphabetic characters
                continue;
            }
            index = Character.toUpperCase(ch) - 'A';
    
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
        node.meaning = meaning;
    }
    
    static String findMeaning(TrieNode root, String key) {
        key = key.toUpperCase();
        TrieNode node = root;
        int index;
        for (int i = 0; i < key.length(); i++) {
            index = key.charAt(i) - 'A';
            if (node.children[index] == null) {
                return key;
            }
            node = node.children[index];
        }
        if (node != null && node.isEndOfWord) {
            return node.meaning;
        } else {
            return null;
        }
    }

    static String abbreviationReplacer(String tweet) {
        String[] tweetWords = tweet.split(" ");
        String finalTweet = "";
        for (int j = 0; j < tweetWords.length; j++) {
            finalTweet += abbreviationReplacerHelper(tweetWords[j]) + " ";
        }
        return finalTweet.trim();
    }
    static String abbreviationReplacerHelper(String word) {
        try {
            Scanner scanner = new Scanner(new File("abbreviations.txt")); //add path to abbreviations.txt
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] abbrev = line.split(",");
                if (abbrev.length == 2) {
                    String abbreviation = abbrev[0].trim();
                    String expanded = abbrev[1].trim();
                    // check for variations of the abbreviation
                    String regex = abbreviation.replaceAll("\\.", "\\\\.").replaceAll("\\s", "\\\\s?");
                    if (word.matches("(?i).*" + regex + ".*")) {
                        scanner.close();
                        return expanded + " (" + findMeaning(root, abbreviation) + ")";
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return word; // return the original word if abbreviation not found
    }
    
}
