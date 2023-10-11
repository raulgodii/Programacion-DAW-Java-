/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conectadb;
import java.sql.*;
import java.util.Scanner;

/**
 * Elabora un programa que solicite al usuario la contraseña de acceso a la base
 * de datos y permita realizar consultas sobre ella.
    * 1. Mostrar los nombres y apellidos de los socios.
    * 2. Mostrar los id_socio e importe_cuota de todos los socios.
    * 3. Mostrar los nombres de los socios que se han dado de baja.
    * 4. Mostrar los nombres de los socios y el importe_cuota de la cuota.
 * 
 * @author raulg
 */
public class Ejemplo1 {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mariadb://localhost:3306/mi_club";
        String user = "root";
        String pass;
        String consulta = "";
        int opcion = -1;
        Scanner sc = new Scanner(System.in);
        ResultSet resultado;
        boolean modificar = false;

        try{
            System.out.print("Introduce la contraseña: ");
            pass = sc.next();
            if(!pass.equals("MANAGER")){
                System.err.println("CONTRASEÑA INCORRECTA");
                System.exit(0);
            }
            
            Connection conexion = DriverManager.getConnection(url, user, pass);
            
            Statement sentencia = conexion.createStatement();
            
            System.out.print("¿Deseas modificar? Introduza 'Si' o cualquier otra cosa para Consultar");
            pass = sc.next();
            if(pass.equalsIgnoreCase("Si")){
                modificar = true;
            }
            
            if(!modificar){
                System.out.println("1. Mostrar los nombres y apellidos de los socios.");
                System.out.println("2. Mostrar los id_socio e importe_cuota de todos los socios.");
                System.out.println("3. Mostrar los nombres de los socios que se han dado de baja.");
                System.out.println("4. Mostrar los nombres de los socios y el importe_cuota de la cuota.");
                System.out.print("Introduce una opcion: ");
                opcion = sc.nextInt();
            }
            
            while(opcion != 0){
                if(!modificar){
                    switch(opcion){
                    case 1:
                        resultado = sentencia.executeQuery("SELECT * FROM socios");
                        while(resultado.next()){
                            System.out.println("Nombre COMPLETO: " + resultado.getString("nombre") + " " + resultado.getString("apellidos"));
                        }
                        break;
                    case 2:
                        resultado = sentencia.executeQuery("SELECT id_socio, importe_cuota FROM cuotas");
                        while(resultado.next()){
                            System.out.println("ID: " + resultado.getInt("id_socio") + " " + "Cuota: " + resultado.getInt("importe_cuota"));
                        }
                        break;
                    case 3:
                        resultado = sentencia.executeQuery("SELECT nombre FROM socios WHERE fecha_baja IS NOT NULL");
                        while(resultado.next()){
                            System.out.println("Nombre: " + resultado.getString("nombre"));
                        }
                        break;
                    case 4:
                        resultado = sentencia.executeQuery("SELECT nombre, importe_cuota FROM cuotas, socios WHERE cuotas.id_socio=socios.id_socio");
                        while(resultado.next()){
                            System.out.println("Nombre: " + resultado.getString("nombre") + " " + "Cuota: " + resultado.getInt("importe_cuota"));
                        }
                        break;
                    default:
                        System.out.println("Introduce una opcion correcta");
                        break;
                    }
                }
                
                System.out.print("¿Deseas modificar? Introduza 'Si' o cualquier otra cosa para Consultar");
                pass = sc.next();
                if(pass.equalsIgnoreCase("Si")){
                    modificar = true;
                }
                System.out.println("1. Mostrar los nombres y apellidos de los socios.");
                System.out.println("2. Mostrar los id_socio e importe_cuota de todos los socios.");
                System.out.println("3. Mostrar los nombres de los socios que se han dado de baja.");
                System.out.println("4. Mostrar los nombres de los socios y el importe_cuota de la cuota.");
                System.out.print("Introduce una opcion: ");
                opcion = sc.nextInt();
            }
            

            
        }
        catch(Exception e){
            System.err.println("ERROR");
        }
    }
}
