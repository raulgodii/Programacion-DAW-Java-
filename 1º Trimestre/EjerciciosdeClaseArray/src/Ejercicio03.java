
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *3.  Escribe un programa que lea 10 número por teclado y que luego
 *    los muestre en orden inverso, es decir, el primero que se
 *    introduce es el último en mostrarse y viceversa.
 * 
 * @author franc
 */
public class Ejercicio03 {

    public static void main (String [] args){
        // Declaración de variables
        int [] num = new int[10];
        Scanner teclado;
        
        
        // Toma de datos
        teclado = new Scanner(System.in);
        System.out.print("introduce 10 números enteros:");
        for (int i=0; i < num.length; i++){
            num[i] = teclado.nextInt();
        }
        
        // Procesamiento y Salida
        System.out.print("Los números introducidos al revés son los siguientes: ");
        for (int i=num.length-1; i >= 0; i--){
            System.out.print(num[i] + " ");
        }
        System.out.println("");
    }
}
