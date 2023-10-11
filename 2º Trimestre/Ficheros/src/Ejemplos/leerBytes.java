/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author raulg
 */
public class leerBytes {
    public static void main(String[] args) {
        File archivo = new File("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\Ejemplos\\escritura.dat");
        
        try{
            FileInputStream fis = new FileInputStream(archivo);
            
            ObjectInputStream leer;
            
            while(fis.available() > 0){
                leer = new ObjectInputStream(fis);
                
                try{
                    System.out.println(leer.readObject());
                }
                catch(ClassNotFoundException cnfe){
                    System.out.println(cnfe.getMessage());
                }
            }
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
