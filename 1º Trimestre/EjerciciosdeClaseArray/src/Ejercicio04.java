/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * 4. Define tres arrays de 20 números enteros cada una, con nombres
 *    "numero", "cuadrado" y "cubo". Carga el array "numero" con valores
 *    aleatorios entre 0 y 100. En el array "cuadrado" se deben almacenar
 *    los cuadrados de los valores que hay en el array "numero". En el
 *    array "cubo" se deben almacenar los cubos de los valores que hay
 *    en "numero". A continuación, muestra el contenido de los tres arrays
 *    dispuesto en tres columnas.
 * 
 *
 * @author franc
 */
public class Ejercicio04 {
    
    public static void main (String [] args){
        // Declaración de variables;
        final int LONGITUD = 20;
        int [] numero, cuadrado, cubo;
        
        numero = new int[LONGITUD];
        cuadrado = new int[LONGITUD];
        cubo = new int[LONGITUD];
        
        // Procesamiento
        for(int i=0; i < numero.length; i++){
            numero[i] = (int)(Math.random()*101);
            cuadrado[i] = numero[i]*numero[i];
            cubo[i] = cuadrado[i]* numero[i];
        }
        
        // Salida
        System.out.println("  TABLA DE RESULTADOS  ");
        
        System.out.println("   n  |   n²  |    n³");
        System.out.println("------------------------");
        for(int i=0; i < numero.length; i++){
            System.out.printf(" %3d  | %5d |%8d \n", numero[i], cuadrado[i], cubo[i]);
        }
        
        
    }
}
