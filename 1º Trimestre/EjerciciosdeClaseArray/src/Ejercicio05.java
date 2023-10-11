
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * 
 *5.  Escribe un programa que pida 10 números enteros y muestre la posición y 
 *    el valor del mayor y el menor de todos ellos.
 * 
 * 
 * @author franc
 */
public class Ejercicio05 {
    
    public static void main (String [] args){
        // Declaración de variables
        int[] numero = new int[10];
        int maximo = Integer.MIN_VALUE;
        int minimo = Integer.MAX_VALUE;
        int posMin = -1;
        int posMax = -1;
        Scanner teclado;
        
        
        // Toma de datos
        teclado = new Scanner(System.in);
        System.out.print("Introduce 10 números enteros: ");
        for (int i=0; i< numero.length; i++){
            numero[i]=teclado.nextInt();
            
            if(numero[i] < minimo){
                minimo=numero[i];
                posMin=i;
            }
            
            if(numero[i] > maximo){
                maximo = numero[i];
                posMax = i;
            }            
        }
        
        // Salida
        System.out.print("El máximo introducido ha sido " + maximo );
        System.out.println(" en la posición " + (posMax+1));
        System.out.print("El mínimo introducido ha sido " + minimo );
        System.out.println(" en la posición " + (posMin+1));
        
    }
    
}
