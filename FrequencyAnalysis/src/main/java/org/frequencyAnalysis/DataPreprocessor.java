package org.frequencyAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataPreprocessor {
    public void data_processing(){
        //Preprocesamiento
        Set<String> stopwords = new HashSet<>();

        String filePath = "src/recursos/StopwordsDictionary.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line); // Imprime cada línea del archivo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Ejemplo de uso para filtrar stopwords de una línea de texto
        String line = "this is an example line";
        String[] words = line.split("\\s+");
        for (String word : words) {
            if (!stopwords.contains(word)) {
                System.out.print(word + " ");
            }
        }
    }
}
