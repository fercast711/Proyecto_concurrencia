package org.frequencyAnalysis;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FrequencyAnalyzer {
    public Map<String, Integer> wordFreq = new HashMap<>();
    public List<Map.Entry<String, Integer>> topWords = new ArrayList<>();

    public void Frequency(int top){
        String file = "src/recursos/part-00000";
        String file2 = "src/recursos/part-00000-pair";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/recursos/TopWords.txt"))){
            bw.write("-------------- ITEMSETS ONE WORD --------------\n");
            Analysis(file,bw);
            wordFreq = new HashMap<>();
            topWords = new ArrayList<>();
            bw.write("\n-------------- ITEMSETS TWO WORD --------------\n");
            Analysis(file2,bw);
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void Sort(Map.Entry<String, Integer> entry){
        topWords.add(entry);
        topWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
    }
    public void Analysis(String file,BufferedWriter bw){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                int count = Integer.parseInt(fields[1]);

                if(count>=5000){
                    wordFreq.put(fields[0],count);
                    //System.out.println(fields[0]+" - "+count);
                }
            }
            // Add to the list and keep sorted with just 20 words
            for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
                Sort(entry);
                if (topWords.size() > top) {
                    topWords.remove(topWords.size() - 1);  // Remove the lowest frequency element
                }
            }
            // Print the top 20 frequent words
            int count = 0;
            for (Map.Entry<String, Integer> entry : topWords) {
                count++;
                String bw_line = count+". "+entry.getKey() + ": " + entry.getValue() + "\n";
                //String bw_line = count+". "+entry.getKey() + "\t" + entry.getValue() + " times repeated\n"; //me rendi con esto pipipipi
                System.out.println(entry.getKey() + ": " + entry.getValue());
                bw.write(bw_line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
