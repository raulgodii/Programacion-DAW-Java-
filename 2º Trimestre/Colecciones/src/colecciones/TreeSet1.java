/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colecciones;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author raulg
 */
public class TreeSet1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables.
        int entrada;
        boolean salida = false;
        
        // Colección de marcas
        TreeSet<Integer> setNumeros = new TreeSet<>();
        
        // Scanner de lectura de teclado
        Scanner sc = new Scanner(System.in);
        
        while(!salida){
            System.out.print("Introduce un nombre (-1 para salir): ");
            entrada = sc.nextInt();
            
            if(entrada == -1){
                salida = true;
            }
            else{
                setNumeros.add(entrada);
            }
        }
        
        // Impresión de toda la colección
        System.out.println(setNumeros);
        
        // Impresión con bucle
        Iterator<Integer> itr = setNumeros.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        
        // Buscar
        salida = false;
        while(!salida){
            System.out.print("Introduce marca a buscar: ");
            entrada = sc.nextInt();
            
            if(setNumeros.contains(entrada)){
                System.out.println("El elemento se encuentra en la colección");
                salida = true;
            }
            else{
                System.out.println("El elemento no se encuentra en la colección");
            }
        }
    }
    
}
