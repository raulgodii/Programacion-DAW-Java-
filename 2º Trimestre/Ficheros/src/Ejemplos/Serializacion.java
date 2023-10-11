/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author raulg
 */
public class Serializacion {
    public static final String PATH = "C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\Ejemplos\\persona.dat";
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException{
        try{
            FileOutputStream fos = new FileOutputStream(PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            Persona persona = new Persona(30, "Lucas");
            
            oos.writeObject(persona);
            
            oos.close();
            
            // Leemos el archivo
            FileInputStream fis = new FileInputStream(PATH);
            
            while(fis.available() > 0){
                ObjectInputStream ois = new ObjectInputStream(fis);
                
                Persona persona_leida = (Persona) ois.readObject();
                System.out.println(persona_leida.toString());
            }
            
            fis.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch(ClassNotFoundException | IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
