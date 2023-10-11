
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 7. Escribe un programa que genere 100 número aleatorios del 0 al 20 y que 
 *    los muestre por pantalla separados por espacios. El programa pedriá
 *    entonces por teclado dos valores y a continuación cambiará todas las 
 *    ocurrencias del primer valor por el segundo en la lista generada 
 *    anteriormente. Los números que se han cambiado deben aparecer 
 *    entrecomillados.
 * 
 * @author franc
 */
public class Ejercicio07 {
    public static void main (String [] args){
        int[] n = new int[100];
        Scanner leer = new Scanner(System.in);
        
        for(int i=0; i<n.length; i++){
            n[i] = (int)(Math.random()*20+1);
            System.out.printf("%d ", n[i]);
        }
        
        System.out.print("\nIntroduce el valor a buscar: ");
        int valor = leer.nextInt();
        System.out.print("Introduce el nuevo valor: ");
        int n_valor = leer.nextInt();
        
        for(int i=0; i<n.length; i++){
            if(n[i]==valor){
                System.out.printf("\"%d\" ", n_valor);
            } else {
                System.out.printf("%d ", n[i]);
            }
            n[i]=n_valor;
        }
        
    }
}
