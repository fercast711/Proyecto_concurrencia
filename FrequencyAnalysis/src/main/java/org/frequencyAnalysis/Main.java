package org.frequencyAnalysis;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        boolean isvalid = false;

        while(!isvalid){
            System.out.println("1. Preprocesamiento de los datos");
            System.out.println("2. Análisis de frecuencias");
            System.out.print("¿Qué desea realizar?: ");
            if(scanner.hasNextInt()){
                isvalid = true;
                num = scanner.nextInt();
                if(num != 1 && num != 2){
                    System.out.println(" ");
                    System.out.println("Solo puede ingresar los números 1 o 2");
                    isvalid = false;
                }
            } else {
                System.out.println(" ");
                System.out.println("El tipo de dato que ingresó no es válido");
                scanner.next();
            }
        }
        if(num == 1){
            DataPreprocessor preprocesamiento = new DataPreprocessor();
            preprocesamiento.data_processing();
        }
    }
}