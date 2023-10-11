/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colecciones;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author raulg
 */
public class HashSet1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables.
        String entrada;
        boolean salida = false;
        Object [] array;
        
        // Colección de marcas
        HashSet<String> setMarcas = new HashSet<>();
        
        // Scanner de lectura de teclado
        Scanner sc = new Scanner(System.in);
        
        while(!salida){
            System.out.print("Introduce un nombre (- para salir): ");
            entrada = sc.next();
            
            if(entrada.equals("-")){
                salida = true;
            }
            else{
                setMarcas.add(entrada);
            }
        }
        
        setMarcas.add(null);
        array = setMarcas.toArray();
        
        // Impresión array
        for(int i=0; i<array.length; i++){
            System.out.println(array[i]);
        }
        
        // Impresión de toda la colección
        System.out.println(setMarcas);
        
        // Impresión con bucle
        Iterator<String> itr = setMarcas.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
    
}
