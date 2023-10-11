/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colecciones;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 *
 * @author raulg
 */
public class LinkedHashSet1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables.
        String entrada;
        boolean salida = false;
        
        // Colección de marcas
        LinkedHashSet<String> setMarcas = new LinkedHashSet<>();
        
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
        
        // Impresión de toda la colección
        System.out.println(setMarcas);
        
        // Impresión con bucle
        Iterator<String> itr = setMarcas.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
    
}
