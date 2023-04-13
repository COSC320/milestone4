import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class first_implementation_graph {

    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<String>(); // create a queue to store the values

        try {
            Scanner scanner = new Scanner(new File(""));    //input csv file, add absolute file path here for the csv file
            scanner.nextLine(); // ignore header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // regex to split the csv but ignore the commas in that column

                if (values.length > 3) {
                    queue.add(abbreviationReplacer(values[3])); // add the value to the queue and replace abbreviations
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // write output to file
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("output2.csv"));   //output with values for graph
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
            Scanner scanner = new Scanner(new File("")); //add absolute path for abbreviations.txt 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] abbrev = line.split(",");
                if (abbrev.length == 2) {
                    String abbreviation = abbrev[0].trim();
                    String expanded = abbrev[1].trim();
                    // check for variations of the abbreviation
                    String regex = abbreviation.replaceAll("\\.", "\\\\.").replaceAll("\\s", "\\\\s?");
                    if (word.matches(regex)) {
                        scanner.close();
                        return expanded;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return word;
    }
}
