/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colecciones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  Ejercicio MAPAS.
 *  Partiendo del fichero generado en el ejercicio anterior.
 *  desarrolla un programa que almacene en un mapa las ciudades
 *  que se encuentran en él. Para ello, utiliza el nombre de la
 *  ciudad como clave y el objeto "Ciudad" como valor.
 *  
 *  A continuación, se ofrecerá al usuario un busqueda por nombre de
 *  la ciudad y se mostraran los datos de la misma (toString()).
 *  El programa termina cuando el usuario introduce salir como nombre 
 *  de ciudad.
 * @author raulg
 */
public class EjercicioMapas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<String, Ciudad> ciudades = new HashMap<>();
        String leer, buscar;
        String [] leerSplit;
        Ciudad aux;
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        
        try{
            FileReader fr = new FileReader("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Colecciones\\src\\colecciones\\Ciudades");
            BufferedReader br = new BufferedReader(fr);
            
            leer = br.readLine();
            while(leer != null){
                leer = leer.replaceAll(" +", " ");
                leerSplit = leer.split(" ");
                aux = new Ciudad(leerSplit[2], leerSplit[5], Integer.parseInt(leerSplit[9]));
                ciudades.put(aux.getNombre(), aux);
                leer = br.readLine();
            }
            
            br.close();
            fr.close();
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        try{
            
            System.out.print("Introduce el nombre de la ciudad a buscar (introduce -salir- para salir): ");
            buscar = sc.next();
            if(buscar.equals("salir")){
                salir = true;
            }
            
            while(!salir){
                if(ciudades.get(buscar)!=null){
                    System.out.println("--> Ciudad existe");
                }
                else{
                    System.out.println("--> Ciudad no existe");
                }
                
                System.out.print("Introduce el nombre de la ciudad a buscar (introduce -salir- para salir): ");
                buscar = sc.next();
                if(buscar.equals("salir")){
                    salir = true;
                }
            }
            
            
        }
        catch (InputMismatchException e){
            System.out.println(e.getMessage());
            sc.next();
        }
        
        
    }
    
}
