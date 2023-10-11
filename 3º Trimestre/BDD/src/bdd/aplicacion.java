/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bdd;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author raulg
 */
public class aplicacion {

    /**
     * aplicación que permite realizar modificaciones y consultas sobre la base de Datos. 
     * Para las modificaciones, el usuario debe tener conocimientos SQL. 
     * Para las consultas, habrá 5 consultas preestablecidas y como sexta opción, 
     * el usuario podrá introducir su propia sentencia SQL.
     */
    public static void main(String[] args) {
        // Declaracion de variables
        String url = "jdbc:mysql://localhost:3306/empleados";
        String user = "root";
        String pass = "MANAGER";
        int opcion;
        Scanner sc = new Scanner(System.in);
        ResultSet resultado;
        String query1 = "SELECT e.nombre, e.apellidos, d.nombre_departamento FROM empleados e JOIN dept_emp de ON e.id_emp = de.id_emp JOIN departamentos d ON de.id_dep = d.id_dep LIMIT 10;";
        String query2 = "SELECT nombre, apellidos FROM empleados WHERE genero = 'F' LIMIT 10;";
        String query3 = "SELECT d.nombre_departamento, COUNT(*) AS cantidad_empleados FROM departamentos d JOIN dept_emp de ON d.id_dep = de.id_dep GROUP BY d.nombre_departamento;";
        String query4 = "SELECT e.nombre, e.apellidos FROM empleados e JOIN titulos t ON e.id_emp = t.id_emp WHERE t.titulo = 'Manager';";
        String query5;
        
        // "\u001B[38;5;<codigo>m"  // Para cambiar el color del texto
        // "\u001B[48;5;<codigo>m"  // Para cambiar el color de fondo
        String codigoEscapeVerde = "\u001B[32m"; // Código de escape ANSI para el color verde
        String codigoEscapeBlanco = "\u001B[37m"; // Código de escape ANSI para el color blanco
        String resetearColor = "\u001B[0m"; // Código de escape ANSI para restablecer el color
        
        // Comienzo del programa
        try{
            Connection conexion = DriverManager.getConnection(url, user, pass);
            Statement sentencia = conexion.createStatement();
            
            System.out.println(codigoEscapeBlanco + "Bienvenido al programa de gestion de empleados" + resetearColor);
            System.out.println("Elige una opcion del menu:" );
            System.out.println("0. Salir");
            System.out.println("1. Obtener 10 empleados y sus respectivos departamentos");
            System.out.println("2. Obtener 10 empleados que son mujeres (género femenino)");
            System.out.println("3. Obtener todos los departamentos y la cantidad de empleados en cada uno");
            System.out.println("4. Obtener todos los empleados que tienen el título de \"Manager\"");
            System.out.println("5. Introducir tu propia consulta");
            System.out.print("--> ");
            opcion = sc.nextInt();

            while (opcion != 0){
                switch(opcion){
                    case 0: // Salir
                        break;
                    case 1: // Obtener todos los empleados y sus respectivos departamentos
                        resultado = sentencia.executeQuery(query1);
                        while(resultado.next()){
                            System.out.println(codigoEscapeVerde + "Nombre: " + resultado.getString("nombre") + "    " + "   Dpto: " + resultado.getString("nombre_departamento") + resetearColor);
                        }
                        break;
                    case 2: // Obtener todos los empleados que son mujeres (género femenino)
                        resultado = sentencia.executeQuery(query2);
                        while(resultado.next()){
                            System.out.println(codigoEscapeVerde + "Nombre COMPLETO: " + resultado.getString("nombre") + " " + resultado.getString("apellidos") + resetearColor);
                        }
                        break;
                    case 3: // Obtener todos los departamentos y la cantidad de empleados en cada uno
                        resultado = sentencia.executeQuery(query3);
                        while(resultado.next()){
                            System.out.println(codigoEscapeVerde + "Departamento: " + resultado.getString("nombre_departamento") + " Cantidad: " + resultado.getString("cantidad_empleados") + resetearColor);
                        }
                        break;
                    case 4: // Obtener todos los empleados que tienen el título de "Manager"
                        resultado = sentencia.executeQuery(query4);
                        while(resultado.next()){
                            System.out.println(codigoEscapeVerde + "Nombre COMPLETO: " + resultado.getString("nombre") + " " + resultado.getString("apellidos") + resetearColor);
                        }
                        break;
                    case 5: // Introducir tu propia consulta
                        
                        System.out.print("Introduce a continuacion tu consulta SQL: ");
                        sc.nextLine();
                        query5 = sc.nextLine();

                        resultado = sentencia.executeQuery(query5);
                        
                        ResultSetMetaData rm;
                        rm = resultado.getMetaData();
                        while(resultado.next()){

                            for(int i=1; i<=rm.getColumnCount(); i++){
                                System.out.print( codigoEscapeVerde + rm.getColumnName(i) + ": " + resultado.getString(i) + "; " + resetearColor);
                            }
                            System.out.println();
                        }
                        
                        break;
                    default:
                        System.out.println("Introduce una opcion correcta");
                        break;
                }
                System.out.println("Elige una opcion del menu:" );
                System.out.println("0. Salir");
                System.out.println("1. Obtener 10 empleados y sus respectivos departamentos");
                System.out.println("2. Obtener 10 empleados que son mujeres (género femenino)");
                System.out.println("3. Obtener todos los departamentos y la cantidad de empleados en cada uno");
                System.out.println("4. Obtener todos los empleados que tienen el título de \"Manager\"");
                System.out.println("5. Introducir tu propia consulta");
                System.out.print("--> ");
                opcion = sc.nextInt();
            }
        }
        catch(SQLException e){
            System.err.println("~ Error, ha ocurrido un problema de SQL: " + e.getMessage());
        }
        
        /*
        ResulSet r;
        ResultSetMetaData rm;
        rm = r.getMetaData();
        while(r.next()){
            String row = "";
            for(int i=1; i<rm.getColumnCount; i++){
                String c = r.getString(i);
                row = row + " " + c;
            }
            System.out.println(row);
        }
        
        */
    }
    
}
