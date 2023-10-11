/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colecciones;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Elabora un programa que solicite al usuario los datos de ciudades
 * para almacenarlos en un archivo llamado "ciudades.txt" de forma ordenada.
 * El numero de ciudades no se especifica en ningun momento.
 * @author raulg
 */
public class Ejemplo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Ciudad> ciudades = new ArrayList<>();
        boolean salir = false;
        Scanner sc = new Scanner(System.in);
        String nombre, provincia, opcion;
        int poblacion;
        Ciudad aux;
        
        try{
            FileWriter fw = new FileWriter("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Colecciones\\src\\colecciones\\Ciudades");
            BufferedWriter bw = new BufferedWriter(fw);

            while(!salir){
                System.out.println("-- Introduce los datos de la ciudad --");
                
                
                System.out.print("Nombre: ");
                nombre = sc.next();

                System.out.print("Provincia: ");
                provincia = sc.next();
                
                try{
                    System.out.print("Poblacion: ");
                    poblacion = sc.nextInt();
                    aux = new Ciudad(nombre, provincia, poblacion);
                    ciudades.add(aux);
                }
                catch(InputMismatchException e){
                    System.out.println(e.getMessage());
                    sc.next();
                }              

                System.out.println("Ciudad introducida con exito");
                System.out.print("¿Desea continuar introduciendo ciudades? \nPulse n para salir, de lo contrario se seguirá introduciendo ciudades: ");
                opcion = sc.next();
                if(opcion.equalsIgnoreCase("n")){
                    salir = true;
                }
            }

            ciudades.sort(Comparator.comparing(Ciudad::getPoblacion));

            for(int i=0; i<ciudades.size(); i++){
                String cadena = ciudades.get(i).toString();
                bw.write(cadena + "\n");
            }
            
            bw.close();
            fw.close();
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
}
