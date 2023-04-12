import java.io.*;
import java.util.*;


public class debrev extends Trie{
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>(); // create a queue to store the values

        try {
            Scanner scanner = new Scanner(new File("file.csv"));    //input csv file, add file path here for the csv file
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
    }
    static String abbreviationReplacer2(String word){
        return Trie.replacer(word);

    }
}
