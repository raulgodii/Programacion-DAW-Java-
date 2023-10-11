/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author franc
 */
public class Ejercicio1 {
    
    public static void main (String [] args){
        int [] numero = new int[5];
        int index1 = -1, index2 = -1;
        int resultado;        
        String opcion = "";
        boolean valido=false;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Programa de operaciones matemáticas: Máximo, Mínimo, Multiplicación y División");
        
        System.out.println("Introduce 5 número reales");
        for(int i = 0; i < numero.length; i++){
            try{
                System.out.printf("Introduce el número %d :", i+1);
                numero[i] = sc.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Error, numero introducido no valido");
                sc.nextLine();
                i--;
            }
        }
        
        while (!valido){
            
            System.out.print("Selecciona la operación a realizar (*, /, +, -): ");
            try{
                opcion = sc.next();
            } catch(InputMismatchException e){
                System.out.println("Error, operacion introducida no valida");
                sc.nextLine();
            }
            
            System.out.print("Selecciona el primer número (0 - 5): " );
            
            try{
                index1 = sc.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Error, numero introducido no valido");
                sc.nextLine();
            }
            
            
            
            System.out.print("Selecciona el segundo número (0 - 5): ");
            try{
                index2 = sc.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Error, numero introducido no valido");
            }
            
            
            switch(opcion){
                case "*":
                    
                    System.out.println("Multiplicamos los números entre " + index1 + " y " + index2);
                    resultado = multiplica(numero, index1, index2);
                    
                    if(resultado == -1){
                        System.out.println("La operacion no pudo realizarse, vuelva a intentarlo");
                    }
                    
                    if(resultado != -1){
                        valido = true;
                    }
                    
                    System.out.println("El resultado de la multiplicación es : " + resultado);                    
                    break;
                    
                case "/":                    
                   
                    System.out.println("Dividimos los números en posiciones " + index1 + " y " + index2);
                    resultado = divide(numero, index1, index2);   
                    
                    if(resultado == -1){
                        System.out.println("La operacion no pudo realizarse, vuelva a intentarlo");
                    }
                    if(resultado != -1){
                        valido = true;
                    }
                    
                    System.out.println("El resultado de la división es : " + resultado); 
                    break;
                    
                case "+":
                    
                    System.out.println("Máximo de los números entre " + index1 + " y " + index2);
                    resultado = maximo(numero, index1, index2);
                    
                    if(resultado == -1){
                        System.out.println("La operacion no pudo realizarse, vuelva a intentarlo");
                    }
                    if(resultado != -1){
                        valido = true;
                    }
                    
                    System.out.println("El mayor de los números es : " + resultado);                    
                    break;
                    
                case "-":
                    
                    System.out.println("Mínimo de los números entre " + index1 + " y " + index2);
                    resultado = minimo(numero, index1, index2);
                    
                    if(resultado == -1){
                        System.out.println("La operacion no pudo realizarse, vuelva a intentarlo");
                    }
                    if(resultado != -1){
                        valido = true;
                    }
                    
                    System.out.println("El menor de los números es : " + resultado);               
                    break;
                    
                default:
                    System.out.println("Opción no válida.");
            }
            
        }
    }
    
    /**
     * Multiplica todos los números de un array entre dos índices.
     * index1 debe ser menor o igual que index2
     * @param array array de números enteros
     * @param index1 primer índice
     * @param index2 segundo índice
     * @return Devuele el resultado de multiplicar los números comprendidos entre los dos índices.
     */
    public static int multiplica(int [] array, int index1, int index2) {
        int resultado = -1;
        
        try{
            for(int i = index1; i <= index2; i++){
                resultado *= array[i];
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error, se ha intentado acceder a valores indeterminados del array");
        }
        
        return resultado;
    }
    
    
    /**
     * Divide dos números de un array indicados por índices.
     * @param array array de números enteros
     * @param index1 Inidica la posición del numerador
     * @param index2 Indica la posición del denominador 
     * @return Devuele el resultado de dividir los números indicados por los índices.
     */
    public static int divide(int [] array, int index1, int index2) {
        int resultado = -1;
        
        try{
            resultado = array[index1] / array[index2];
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error, se ha intentado acceder a valores indeterminados del array");
        }
        catch (ArithmeticException e){
            System.out.println("Error, la operación es ilegal");
        }
        
        return resultado;
    }
    
    /**
     * Devuelve el mayor entero de un array entre dos índices.
     * index1 debe ser menor que index2
     * @param array array de números enteros
     * @param index1 primer índice
     * @param index2 segundo índice
     * @return Devuelve el mayor número del array entre los dos índices.
     */
    public static int maximo(int [] array, int index1, int index2) {
        int resultado = -1;
        
        try{
            resultado = array[index1];
            for(int i = index1 + 1; i < index2; i++){
            if(array[i] > resultado)
                resultado = array[i];
        }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error, se ha intentado acceder a valores indeterminados del array");
        }
        
        
        return resultado;
    }
    
    /**
     * Devuelve el menor entero de un array entre dos índices.
     * index1 debe ser menor que index2
     * @param array array de números enteros
     * @param index1 primer índice
     * @param index2 segundo índice
     * @return Devuelve el mayor número del array entre los dos índices.
     */
    public static int minimo(int [] array, int index1, int index2) {
        int resultado = -1;        
        
        
        try{
            resultado = array[index1];
            for(int i = index1 + 1; i < index2; i++){
            if(array[i] < resultado)
                resultado = array[i];
        }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error, se ha intentado acceder a valores indeterminados del array");
        }
        
        return resultado;
    }
    
}
