/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package colecciones;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author raulg
 */
public class EjemploCiudad {
    
    public static void main(String[] args){
        // Declaración de variables
        boolean salida = false;
        String nombre, provincia, poblacion;
        Scanner sc = new Scanner(System.in);
        Ciudad auxiliar;
        
        // TreeSet
        TreeSet<Ciudad> setCiudades = new TreeSet<>(Comparator.comparing(Ciudad::getPoblacion));
        
        while(!salida){
            System.out.println("Introduce una ciudad: ");
            System.out.print("Nombre: ");
            nombre = sc.next();
            System.out.print("Provincia: ");
            provincia = sc.next();
            System.out.print("Poblacion: ");
            poblacion = sc.next();
            
            try{
                auxiliar = new Ciudad(nombre, provincia, Integer.parseInt(poblacion));
                
                // Añadir la ciudad en el TreeSet
                setCiudades.add(auxiliar);
                
                System.out.println("¿Desea continuar?. Pulse n para salir: ");
                if(sc.next().equalsIgnoreCase("n")){
                    salida = true;
                }
            }
            catch(NumberFormatException e){
                System.err.println("Error: no es un numero: " + e.getMessage());
            }
            catch(IllegalArgumentException e){
                System.err.println(e.getMessage());
            }
        }
        
        // Hemos terminado de introducir ciudades.
        
        System.out.println(setCiudades);
        
        Iterator<Ciudad> itr = setCiudades.iterator();
        
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
