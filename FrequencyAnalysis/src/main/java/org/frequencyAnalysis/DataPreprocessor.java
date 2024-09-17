package org.frequencyAnalysis;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class DataPreprocessor {
    public void data_processing() {
        //Preprocesamiento
        Set<String> stopwords = new HashSet<>();
        String[] columna;

        String diccionario = "src/recursos/StopwordsDictionary.txt",
                dataset = "src/recursos/reddit_opinion_PSE_ISR.csv";

        //agrega las stopwords generales al HashSet
        try (BufferedReader br = new BufferedReader(new FileReader(diccionario))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line); // agrega cada palabra del archivo
            }
        } catch (IOException e) {
            System.out.println("No se encontró o no se pudo leer el diccionario");
            return;
        }

        System.out.println("PREPROCESAMIENTO INICIADO...");

        //lee el SOLO la columna deseada del dataset(2) y aplica las validaciones
        String urlPattern = "(https?|ftp)://[^\\s/$.?#].[^\\s]*";
        Pattern pattern = Pattern.compile(urlPattern);
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(dataset))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(',')
                        .withQuoteChar('"')
                        .build())
                .build();
             BufferedWriter bw = new BufferedWriter(new FileWriter("src/recursos/datasetValidado.txt"))) {

            while (true) {
                try {
                    columna = reader.readNext();
                    if (columna == null) break; // Fin del archivo

                    // Verificar si la columna deseada existe
                    if (columna.length > 2) {
                        String line = columna[2];

                        line = line.toLowerCase();

                        // Divide las palabras de la línea
                        String [] palabras = line.split(" ");
                        String lineValid = "";

                        for (String palabra : palabras) {
                            Matcher matcher = pattern.matcher(palabra);
                            if(matcher.find()) continue;

                            String newPalabra = "";

                            for (int i = 0; i < palabra.length(); i++) {
                                // Quita signos de la palabra
                                if (palabra.charAt(i) > 96 && palabra.charAt(i) < 123)
                                    newPalabra += palabra.charAt(i);
                            }

                            // Valida que las palabras no estén en el diccionario
                            if (!stopwords.contains(newPalabra) && !newPalabra.isBlank()) {
                                lineValid += newPalabra + " "; // Guarda las palabras ya validadas en un txt
                            }
                        }

                        if (!lineValid.equals("")) {
                            bw.write(lineValid.trim());
                            bw.write("\n");
                        }

                    }
                } catch (IOException  | CsvValidationException e) {
                    // Ignorar o registrar las líneas mal formadas
                    System.out.println("Línea mal formada ignorada");
                }
            }
            bw.flush();
        } catch (IOException e) {
            System.err.println("PREPROCESAMIENTO FALLO");
            return;
        }

        System.out.println("PREPOCESAMIENTO TERMINADO CON ÉXITO");
    }
}