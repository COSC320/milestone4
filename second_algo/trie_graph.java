import java.io.*;
import java.util.*;


public class trie_graph extends trie{
    public static void main(String[] args) {
        int numAbbrevs= 48;
	  
    	String[] keys=new String[numAbbrevs];
    	String[] full= new String[numAbbrevs];
    	
    	
		try {
            Scanner scanner;
			scanner = new Scanner(new File("")); //add absolute path for abbreviations.txt
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
        for(int i=0;i<numAbbrevs;i++){
            trie.insert(keys[i],full[i]);


        }
         Queue<String> queue = new LinkedList<String>(); // create a queue to store the values

        try {
            Scanner scanner = new Scanner(new File(""));    //input csv file, add absolute file path here for the csv file
            scanner.nextLine(); // ignore header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // regex to split the csv but ignore the commas in that column

                if (values.length > 3) {
                    queue.add(abbreviationReplacer2(values[3])); // add the value to the queue and replace abbreviations
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
    static String abbreviationReplacer2(String tweet) {
        String[] tweetWords = tweet.split(" ");
        String finalTweet = "";
        for (int j = 0; j < tweetWords.length; j++) {
            finalTweet += abbreviationReplacerHelper2(tweetWords[j]) + " ";
        }
        return finalTweet.trim();
    }
    static String abbreviationReplacerHelper2(String word){
        return trie.replacer(word);

    }
}
