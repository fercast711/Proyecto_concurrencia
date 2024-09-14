package org.frequencyAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FrequencyAnalyzer {
    public Map<String, Integer> wordFreq = new HashMap<>();
    public List<Map.Entry<String, Integer>> topWords = new ArrayList<>();
    private int top = 20;

    public void Frequency(int top){
        String file = "src/recursos/part-00000";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;

            System.out.println("\n--------------------------------\n" + "MINIMUM SUPPORT");
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                int count = Integer.parseInt(fields[1]);

                if(count>=5000){
                    wordFreq.put(fields[0],count);
                    System.out.println(fields[0]+" - "+count);
                }
            }
            System.out.println("\n--------------------------------\nTOP 20");

            // Add to the list and keep sorted with just 20 words
            for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
                Sort(entry);
                if (topWords.size() > top) {
                    topWords.remove(topWords.size() - 1);  // Remove the lowest frequency element
                }
            }
            // Print the top 20 frequent words
            for (Map.Entry<String, Integer> entry : topWords) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Sort(Map.Entry<String, Integer> entry){
        topWords.add(entry);
        topWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
    }
}
