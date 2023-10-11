/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author raulg
 */
public class Escritura {
    public static void main(String[] args) throws IOException, FileNotFoundException{
        try{
            FileWriter file = new FileWriter("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\Ejemplos\\hola.txt");
            BufferedWriter bw = new BufferedWriter(file);
            
            bw.write("Estamos escribiendo\n");
            bw.write("en un fichero");
            bw.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
