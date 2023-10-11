/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *2.  Define un array de 10 símbolos con nombre "simbolo" y asigna
 *    valores a los elementos según la siguiente tabla:
 *    Posiión   0   1   2   3   4   5   6   7   8   9
 *    Valor    'a' 'x'         '@'    '  ' '+' 'Q'
 *
 *    Muestra el contenido de todos los elementos del array. ¿Qué sucede
 *    con los valores de los elementos que no han sido inicializados?
 * 
 * @author franc
 */
public class Ejercicio02 {

    public static void main (String [] args){
        // Declaración de variables
        char [] simbolo = new char[10];
        
        simbolo[0] = 'a';
        simbolo[1] = 'x';
        simbolo[4] = '@';
        simbolo[6] = ' ';
        simbolo[7] = '+';
        simbolo[8] = 'Q';
         
        for (int i=0; i<simbolo.length; i++){
            System.out.print(simbolo[i] + " ");
        }
        System.out.println("");
        
        for(char s:simbolo){
            System.out.print( s + " ");
        }
        System.out.println("");
        
    }
}
