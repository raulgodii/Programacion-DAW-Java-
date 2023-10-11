/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author raulg
 */
public class Bytes {
    public static void main(String[] args) {
        int valor = 3;
    
        File archivo = new File("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\Ejemplos\\escritura.dat");
        
        try{
            FileOutputStream fos = new FileOutputStream(archivo);
            
            ObjectOutputStream escribir = new ObjectOutputStream(fos);
            escribir.writeObject(valor);
            escribir.close();
            fos.close();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
