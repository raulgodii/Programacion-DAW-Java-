
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 6. Escribe un programa que lea 15 números por teclado y que los almacene en 
 *    un array. Rota los elementos de ese array, es decir, el elemento de la 
 *    posición 0 debe pasar a la posición 1, el de la 1 a la 2, etc. El número
 *    que se encuentra en la última posición debe pasar a la posición 0. 
 *    Finalmente, muestra el contenido del array.
 * 
 * @author franc
 */
public class Ejercicio06 {
    public static void main (String [] args){
        int[] n = new int[15];
        Scanner leer = new Scanner(System.in);
        
        for(int i=0; i<n.length; i++){
            System.out.printf("Posicion %d: ", i);
            n[i] = leer.nextInt();
        }
        
        System.out.println("----------------------------");
        n[0]=n[14];
        int aux;
        
        aux=n[n.length-1];
        for(int i=n.length-1; i>0; i--){
            n[i]=n[i-1];
        }
        
        for(int i=0; i<n.length; i++){
            System.out.printf("Posicion %d: %d \n", i, n[i]);
        }
    }
}
