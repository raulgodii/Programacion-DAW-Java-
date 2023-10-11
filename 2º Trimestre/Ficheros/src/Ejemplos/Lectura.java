/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author raulg
 */
public class Lectura {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        try{
            FileReader file = new FileReader("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\Ejemplos\\hola.txt");
            BufferedReader br = new BufferedReader(file);
            
            String linea = "";
            
            while(linea != null){
                System.out.println(linea);
                linea = br.readLine();
            }
            
            br.close();
        } 
        catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
        
    }
}
