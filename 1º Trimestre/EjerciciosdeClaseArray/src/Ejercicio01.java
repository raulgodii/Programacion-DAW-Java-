/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *1.  Define un array de 12 números enteros con nombre num y asigna los valores
 *    según la tabla que se muestra a continuación. Muestra el contenido de todos
 *    los elementos del array. ¿Qué sucede con los valores de los elementos que
 *    no han sido inicializados?
 *    Posición  0   1   2   3   4   5   6   7   8   9   10   11
 *    Valor    39  -2           0      14       5  120
 * 
 * 
 * @author franc
 */
public class Ejercicio01 {
 
    public static void main (String [] agrs){
        // Declaración de variables
        int [] num = new int[12];
        
        // Procesamiento
        num[0] = 39;
        num[1] = -2;
        num[4] = 0;
        num[6] = 14;
        num[8] = 5;
        num[9] = 120;
        
        // Salida
        for(int i=0; i<num.length; i++){
            System.out.print(num[i] + " ");
        }
        System.out.println("");
        
        for(int i:num){
            System.out.print(i + " ");
        }
        System.out.println("");
        
        
    }
}
