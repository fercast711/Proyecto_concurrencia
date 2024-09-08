package org.frequencyAnalysis;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class DataPreprocessor {
    public void data_processing() {
        //Preprocesamiento
        Set<String> stopwords = new HashSet<>();
        String[] columna;

        String diccionario = "src/recursos/StopwordsDictionary.txt",
                dataset = "src/recursos/reddit_opinion_PSE_ISR.csv";

        ArrayList<String> wordslist = new ArrayList<>();

        //agrega las stopwords generales al HashSet
        try (BufferedReader br = new BufferedReader(new FileReader(diccionario))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line); // agrega cada palabra del archivo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CSVParser parser = new CSVParserBuilder()
                .withQuoteChar(CSVParser.NULL_CHARACTER)  // Desactiva el manejo estricto de comillas
                .build();

        System.out.println("LEYENDO DATOS...");

        //lee el SOLO la columna deseada del dataset(2) y aplica las validaciones
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(dataset)).withCSVParser(parser).build()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/recursos/datasetValidado.txt"));
            while ((columna = reader.readNext()) != null) {
                // Verificar si la columna deseada existe
                if (columna.length > 2) {
                    String line = columna[2], newline = "";

                    //quita signos de la linea
                    for (int i = 0; i < line.length(); i++) {
                        if ((line.toLowerCase().charAt(i) > 96 && line.toLowerCase().charAt(i) < 123) || line.charAt(i) == 32)
                            newline += line.charAt(i);
                    }

                    //divide las palabras de la linea
                    String[] palabras = newline.split(" ");

                    //valida que las palabras no esten en el diccionario
                    for (int i = 0; i < palabras.length; i++) {
                        if (!stopwords.contains(palabras[i].toLowerCase()) && !palabras[i].isBlank() && !palabras[i].contains("https")) {
                            bw.write(palabras[i].toLowerCase() + " "); //guarda las palabras ya validadas en un txt
                        }
                    }
                }
            }
            bw.flush();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        /*try (BufferedWriter bw = new BufferedWriter(new FileWriter("datasetValidado.txt"))){
            for (String word : wordslist)
                bw.write(word + " ");
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        System.out.println("PREPOCESAMIENTO TERMINADO CON ÉXITO");
    }
}
