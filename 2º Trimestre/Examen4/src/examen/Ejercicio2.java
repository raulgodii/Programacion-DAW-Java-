/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author franc
 */
public class Ejercicio2 {
    
    /**
     *
     */
    public static final String PATH = "C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Examen4\\src\\examen\\";
    
    public static void main(String [] args){
        
        String linea = "";
        String [] lineaSplit;
        int suma;
        
    try{
        FileReader frNum = new FileReader(PATH + "numeros.txt");
        BufferedReader brNum = new BufferedReader(frNum);
        
        FileWriter fwSum = new FileWriter(PATH + "suma.txt");
        BufferedWriter bwSum = new BufferedWriter(fwSum);
        
        linea = brNum.readLine();
        bwSum.write(linea + "\n");
        
        while(linea != null){
            linea = brNum.readLine();
            if(linea != null){
                lineaSplit = linea.split(",");
                suma = Integer.parseInt(lineaSplit[0]) + Integer.parseInt(lineaSplit[1]);
                bwSum.write(suma + "\n");
            }
        }
        
        bwSum.close();
        brNum.close();
    }
    catch(FileNotFoundException e){
        e.getMessage();
    }
    catch(IOException e){
        e.getMessage();
    }
    }
    
}
