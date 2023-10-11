/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arma;

import java.util.Scanner;

/**
 *
 * @author franc
 * 
 */
public class PruebaRevolver {
    
    /**
     * Programa prueba de la clase <code>Revólver</code>
     * El programa solicitará al usuario un número de revólveres aentre 1 y 10.
     * Una vez validada la entrada, se generará 1 Revolver con capacidad por defecto y el resto con capacidad aleatoria dentro de los márgenes definidos por la clase.
     * A continuación, el programa solicitará un número de balas a cargar para el primer Revólver. El resto se cargará completamente.
     * Para finalizar, se pedirá al usuario un número de disparos a realizar por el primer Revólver y se mostrará el resultado por pantalla.
     * @param args 
     */
    public static void main(String [] args){
        //Declaración de variables
        Revolver [] array;
        int numRevolver, aleatorioTambor, numBalas, numDisparos;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un número de revólveres entre 1 y 10: ");
        numRevolver = sc.nextInt();
        while(numRevolver < 1 || numRevolver > 10){
            System.out.println("Número de revólveres erroneo. \n"
            + "Introduce un número de revólveres entre 1 y 10: ");
            numRevolver = sc.nextInt();
        }
        
        array = new Revolver[numRevolver];
        
        //Inicializo por defecto el primer revólver
        array[0] = new Revolver();
        Utilidades.consultaInformacionObjeto(array[0]);
        for(int i=1; i<array.length; i++){
            aleatorioTambor = (int)(Math.random()*(Revolver.MAXIMA_CAPACIDAD-Revolver.MINIMA_CAPACIDAD + 1) + Revolver.MINIMA_CAPACIDAD);
            array[i] = new Revolver(aleatorioTambor);
            Utilidades.consultaInformacionObjeto(array[i]);
        }

        // Pedimos al usuario el número de balas a cargar
        System.out.println("Introduce el número de balas para el primer revólver: ");
        numBalas = sc.nextInt();
        Utilidades.cargarRevolver(array[0], numBalas);
        
        for(int i=1; i<array.length; i++){
            Utilidades.cargarRevolverCompletamente(array[i]);
        }
        
        // Pedimos al usuario el número de disparos a realizar
        System.out.println("Introduce el número de disparos a realizar con el primer revólver: ");
        numDisparos = sc.nextInt();
        
        Utilidades.dispararRevolver(array[0], numDisparos);
        Utilidades.consultaInformacionObjeto(array[0]);
    }
}
