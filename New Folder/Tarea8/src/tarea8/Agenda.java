/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author raulg
 */
public class Agenda {

    /**
     * Gestión de una "Agenda de Contactos"
     * 
     * El programa permitirá al usuario realizar las siguientes acciones:
     *      - Añadir un nuevo contacto
     *      - Mostrar todos los contactos
     *      - Editar un contacto
     *      - Eliminar un contacto
     *      - Buscar un contacto pidiendo el nombre del contacto.
     * 
     * La agenda se guardará ordenada alfabéticamente en un archivo (agenda.txt) con el siguiente formato:
     *  (nombre);(fechaDeNacimiento);(movil);(direccion);
     * 
     * @param args the command line arguments
     */
    
    public static final String pathAgenda = "./agenda.txt";
    public static void main(String[] args) {
        boolean salir = false; // Constante para controlar la salida del programa
        Scanner sc = new Scanner(System.in); // Variable de entrada de texto por teclado
        int opcion = -1; // Seleccion del menu
        String cadena = ""; // Variable de control de lectura del fichero
        String [] cadenaSplit; // Variable para almacenar el array que devuelve la funcion SPLIT
        ArrayList<Contacto> contactoList = new ArrayList<>();Contacto auxContacto; // Variable de control para la creacion de contactos nuevos
        String nombre, movil, direccion, fechaNacimiento; // Variables para intanciar al objeto contacto
        boolean creado; // Variable para controlar si el contacto ha sido creado con exito o no
        int encontrado; // Variable para almacenar el indice del contacto encontrado al buscarlo
        
        // Lectura del fichero o creacion del mismo
        try{ // Control de excepciones
            FileReader fr = new FileReader(pathAgenda);
            BufferedReader br = new BufferedReader(fr);
            
            // Lectura de cada linea del fichero
            
            // Lectura primera y segunda linea (cabecera)
            br.readLine();
            br.readLine();
            
            // Lectura primera linea
            cadena = br.readLine();
            
            while(cadena != null){
                // Separación de cadena
                cadenaSplit = cadena.split(";");
                // Creación de contacto
                auxContacto = new Contacto(cadenaSplit[0], cadenaSplit[1], cadenaSplit[2], cadenaSplit[3]);
                // Se crea un nuevo elemento en la coleccion con los datos de contacto
                contactoList.add(auxContacto);
                // Lectura linea
                cadena = br.readLine();
            }
            
            // Se cierra el flujo y el fichero
            br.close();
            fr.close();
        }
        catch(FileNotFoundException e){ // Por si no encuentra el archivo
            System.err.println("~ Error, el archivo no se ha encontrado: " + e.getMessage());
        }
        catch(IOException e){ // Por si ocurre un error de entrada/salida
            System.err.println("~ Error, ha ocurrido un problema de entrada/salida: " + e.getMessage());
        }

        // Seleccion del menu
        while(!salir){
            imprimirmenu();
            
            // Control de excepcion, por si el usuario introduce un caracter inesperado
            try{
                opcion = sc.nextInt();
            }
            catch(InputMismatchException e){
                System.err.println("~ Error, se debe introducir un valor entero: " + e.getMessage());
                sc.next();
            }
            
            switch(opcion){
                case 0: // Añadir un nuevo contacto
                    System.out.println("\n Introduce los datos del nuevo contacto:");
                    System.out.print("\n --> Nombre: ");
                    sc.nextLine();
                    nombre = sc.nextLine();
                    System.out.print("\n --> Fecha de Nacimiento: ");
                    fechaNacimiento = sc.next();
                    System.out.print("\n --> Movil: ");
                    movil = sc.next();
                    System.out.print("\n --> Direccion: ");
                    sc.nextLine();
                    direccion = sc.nextLine();
                    
                    // Creacion del objeto
                    creado = true;
                    try{
                        auxContacto = new Contacto(nombre, fechaNacimiento, movil, direccion);
                        // Añadir a la coleccion
                        contactoList.add(auxContacto);
                    }
                    catch(IllegalArgumentException e){
                        System.err.println("~ Error, se ha introducido un formato invalido: " + e.getMessage());
                        creado = false;
                    }
                    catch(DateTimeParseException e){
                        System.err.println("~ Error, se ha introducido un formato de fecha inválido: " + e.getMessage());
                        System.err.println("~ Formato valido: yyyy-dd-mm");
                        creado = false;
                    }
                    
                    if(creado){
                        System.out.println("\n - - Contacto creado con exito - -");
                    }
                            
                    break;
                case 1: // Mostrar todos los contactos
                    // Ordenamos antes de mostrar
                    contactoList.sort(Comparator.comparing(Contacto::getNombre)); // Ordenación por nombre
                    
                    System.out.println("\n- - - -   AGENDA DE CONTACTOS    - - - -");
                    System.out.printf("--> Numero de contactos actuales: %d \n\n", contactoList.size());
                    for(int i = 0; i<contactoList.size(); i++){
                        System.out.printf("     Contacto %-8d \n", i);
                        System.out.printf("     --> Nombre: %s \n", contactoList.get(i).getNombre());
                        System.out.printf("     --> Fecha Nacimiento: %s \n", contactoList.get(i).getFechaDeNacimiento());
                        System.out.printf("     --> Movil: %8s \n", contactoList.get(i).getMovil());
                        System.out.printf("     --> Direccion: %s \n\n", contactoList.get(i).getDireccion());
                    }
                    break;
                case 2: // Editar un contacto
                    System.out.print("\n Introduce el nombre del contacto a buscar:");
                    sc.nextLine();
                    nombre = sc.nextLine();
                    encontrado = encontrar_contacto(contactoList, nombre);
                    
                    if(encontrado == -1){
                        System.out.println("\n - - El contacto no existe - -");
                    } else{
                        System.out.println("\n Introduce los nuevos datos del contacto:");
                        System.out.print("\n --> Nombre: ");
                        nombre = sc.nextLine();
                        System.out.print("\n --> Fecha de Nacimiento: ");
                        fechaNacimiento = sc.next();
                        System.out.print("\n --> Movil: ");
                        movil = sc.next();
                        System.out.print("\n --> Direccion: ");
                        sc.nextLine();
                        direccion = sc.nextLine();

                        // Creacion del objeto
                        creado = true;
                        try{
                            auxContacto = new Contacto(nombre, fechaNacimiento, movil, direccion);
                            // Añadir a la coleccion
                            contactoList.set(encontrado, auxContacto);
                        }
                        catch(IllegalArgumentException e){
                            System.err.println("~ Error, se ha introducido un formato invalido: " + e.getMessage());
                            creado = false;
                        }
                        catch(DateTimeParseException e){
                            System.err.println("~ Error, se ha introducido un formato de fecha inválido: " + e.getMessage());
                            System.err.println("~ Formato valido: yyyy-dd-mm");
                            creado = false;
                        }

                        if(creado){
                            System.out.println("\n - - Contacto editado con exito - -");
                        }
                    }
                    break;
                case 3: // Eliminar un contacto
                    System.out.print("\n Introduce el nombre del contacto a buscar:");
                    sc.nextLine();
                    nombre = sc.nextLine();
                    encontrado = encontrar_contacto(contactoList, nombre);
                    
                    if(encontrado == -1){
                        System.out.println("\n - - El contacto no existe - -");
                    } else{
                        contactoList.remove(encontrado);
                        System.out.println("\n - - Contacto borrado con exito - -");
                    }
                    break;
                case 4: // Buscar un contacto pidiendo el nombre del contacto.
                    System.out.print("\n Introduce el nombre del contacto a buscar:");
                    sc.nextLine();
                    nombre = sc.nextLine();
                    encontrado = encontrar_contacto(contactoList, nombre);
                    
                    if(encontrado == -1){
                        System.out.println("\n - - El contacto no existe - -");
                    } else{
                        System.out.println("\n - - El contacto si existe - -");
                    }
                    break;
                case 5: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("~ Introduce una opcion valida");
                    break;
            }
        }
        
        // Se vuelca la coleccion al fichero
        try{
            FileWriter fw = new FileWriter(pathAgenda);
            BufferedWriter bw = new BufferedWriter(fw);   
            
            // Escritura de cabecera
            bw.write("- - - -   AGENDA DE CONTACTOS    - - - -");
            bw.write(String.format("\n--> Numero de contactos actuales: %d", contactoList.size()));
            
            // Escritura de los contactos
            contactoList.sort(Comparator.comparing(Contacto::getNombre)); // Ordenación por nombre
            for(int i=0; i<contactoList.size(); i++){
                bw.write("\n" + contactoList.get(i).toString());
            }
            
            // Se cierra el flujo y el fichero
            bw.close();
            fw.close();
        }
        catch(FileNotFoundException e){ // Por si no encuentra el archivo
            System.err.println("~ Error, el archivo no se ha encontrado: " + e.getMessage());
        }
        catch(IOException e){ // Por si ocurre un error de entrada/salida
            System.err.println("~ Error, ha ocurrido un problema de entrada/salida: " + e.getMessage());
        }
        
    }

    private static void imprimirmenu() {
        System.out.println("\n-- Selecciona una opcion del menu --");
        System.out.println("(0) Añadir un nuevo contacto");
        System.out.println("(1) Mostrar todos los contactos");
        System.out.println("(2) Editar un contacto");
        System.out.println("(3) Eliminar un contacto");
        System.out.println("(4) Buscar un contacto pidiendo el nombre del contacto.");
        System.out.println("(5) Salir");
        System.out.print("--> ");
    }
    
    private static int encontrar_contacto(ArrayList<Contacto> contactoList, String nombre){
        int encontrado = -1;
        Contacto aux;
        
        for(int i=0; i<contactoList.size() && encontrado == -1; i++){
            aux = contactoList.get(i);
            
            if(aux.getNombre().equals(nombre)){
                encontrado = i;
            }
            
        }
        
        return encontrado;
    }
}
