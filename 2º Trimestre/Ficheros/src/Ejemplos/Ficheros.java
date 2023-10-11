/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.File;

/**
 *
 * @author raulg
 */
public class Ficheros {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\Ficheros\\src\\Ejemplos");
        String [] listArchivo = file.list();
        for(String nombreArchivo: file.list()){
            System.out.println(nombreArchivo);
        }
    }
}
