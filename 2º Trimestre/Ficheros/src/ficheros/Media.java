/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author raulg
 */
public class Media {
    public static void main(String[] args) {
        try{
            FileReader fl = new FileReader("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\ficheros\\media.txt");
            BufferedReader br = new BufferedReader(fl);

            FileWriter fw = new FileWriter("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\ficheros\\resultado.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            double nLineas = -1;
            String linea = "";
            double suma = 0;
            double media;
            
            while(linea!=null){
                linea = br.readLine();
                
                if(linea!=null){
                    suma = suma + Double.parseDouble(linea);
                    nLineas++;
                }
            }
            
            media = suma/nLineas;
            
            bw.write(String.format("Media = %.2f", media));
            
            br.close();
            bw.close();
        }
        catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
