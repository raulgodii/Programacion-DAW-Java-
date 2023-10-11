/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.Serializable;

/**
 *
 * @author raulg
 */
public class Persona implements Serializable{
    private int edad;
    private String nombre;
    
    public Persona(int edad, String nombre){
        this.edad = edad;
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return "Edad: " + this.edad + " \n" + "Nombre: " + this.nombre;
    }
}
