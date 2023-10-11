/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 9. Escribe un programa que genere 20 números enteros aleatorios entre 0 y 100
 *    y que los almacene en un array. El programa debe ser capaz de pasar todos
 *    los números pares a las primeras posiciones del array (del 0 en adelante)
 *    y todos los números impares a las celdas restantes. Utiliza arrays 
 *    auxiliares si es necesario.
 * 
 * @author franc
 */
public class Ejercicio09 {
    public static void main (String [] args){
        int[] vec = new int[20];
        int[] res = new int[20];
        int par = 0;
        int impar = 0;
    
        for(int i=0; i<vec.length; i++){
            vec[i] = (int)(Math.random()*100+1);
            System.out.printf("V[%d]=%d || ", i, vec[i]);
        }
        
        System.out.println();
        
        for(int i=0; i<vec.length; i++){
            if((vec[i]%2)==0){
                res[par]=vec[i];
                par++;
            } else {
                res[(res.length-1-impar)]=vec[i];
                impar++;
            }
        }
        
        for(int i=0; i<res.length; i++){
            System.out.printf("V[%d]=%d || ", i, res[i]);
        }
        System.out.println();
    }
}
