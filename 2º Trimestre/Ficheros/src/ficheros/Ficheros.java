/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
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
public class Ficheros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            FileReader lectura1 = new FileReader("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\ficheros\\file1.txt");
            BufferedReader br = new BufferedReader(lectura1);
            
            FileReader lectura2 = new FileReader("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\ficheros\\file2.txt");
            BufferedReader br2 = new BufferedReader(lectura2);
            
            FileWriter escritura = new FileWriter("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\ficheros\\file3.txt");
            BufferedWriter bw = new BufferedWriter(escritura);
            
            String linea1 = "";
            String linea2 = "";
            
            while((linea1!=null)||(linea2!=null)){
                linea1 = br.readLine();
                linea2 = br2.readLine();
                if(linea1 != null){
                    bw.write(linea1 + "\n");
                }
                
                if(linea2 != null){
                    bw.write(linea2 + "\n");
                }
            }
            
            br.close();
            br2.close();
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
