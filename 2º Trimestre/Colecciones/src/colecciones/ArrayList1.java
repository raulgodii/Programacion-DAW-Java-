/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colecciones;

import java.util.ArrayList;

/**
 * Programa que genere 100.000.000 números reales aleatorios y los
 * guarde en un arraylist. Comparar diferencias usando array.
 * @author raulg
 */
public class ArrayList1 {
    /**
     * Programa que 
     * @param args 
     */
    public static void main(String[] args) {
        
        // Array List --> 11"
        /*
        ArrayList<Double> array = new ArrayList<>();
        
        for(int i=0; i<100000000; i++){
            array.add(Math.random());
        }
        */
        
        // Array --> 1"
        double [] array = new double [100000000];
        for(int i=0; i<array.length; i++){
            array[i] = Math.random();
        }
    }
    
}
