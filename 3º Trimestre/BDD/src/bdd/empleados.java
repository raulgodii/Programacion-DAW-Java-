/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bdd;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/*
executeUpdate("insert ....")
sentencia.addBatch("insert ....")
sentencia.executeBatch



*/



/**
 *
 * @author raulg
 */
public class empleados {

    /**
     * Una empresa tiene la intención modernizar su software. Para empezar, 
     * quiere migrar los datos que maneja de ficheros a una base de datos relacional.
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3306/empleados";
        String user = "root";
        String pass = "MANAGER";
        final String ruta = "C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\3º Trimestre\\BDD\\src\\bdd\\";
        String departamentos = ruta + "departamentos.txt";
        String dept_emp = ruta + "dept_emp.txt";
        String dept_manager = ruta + "dept_manager.txt";
        String empleados = ruta + "empleados.txt";
        String salarios = ruta + "salarios.txt";
        String titulos = ruta + "titulos.txt";
        String linea = "";
        String [] lineaSplit;
        String codigoEscape = "\u001B[32m"; // Código de escape ANSI para el color verde
        String resetearColor = "\u001B[0m"; // Código de escape ANSI para restablecer el color
        
        
        
        try{
            // Creamos la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(url, user, pass);
            conexion.setAutoCommit(false);
            System.out.println("Conectando a base de datos...");
            // Creamos el Statment
            Statement sentencia = conexion.createStatement();
            System.out.println("Sentencia creada");
            
            // Abrimos los archivos para empezar con la lectura
            try{
                FileReader fr_departamentos = new FileReader(departamentos);
                BufferedReader br_departamentos = new BufferedReader(fr_departamentos);

                FileReader fr_dept_emp = new FileReader(dept_emp);
                BufferedReader br_dept_emp = new BufferedReader(fr_dept_emp);

                FileReader fr_dept_manager = new FileReader(dept_manager);
                BufferedReader br_dept_manager = new BufferedReader(fr_dept_manager);

                FileReader fr_empleados = new FileReader(empleados);
                BufferedReader br_empleados = new BufferedReader(fr_empleados);

                FileReader fr_salarios = new FileReader(salarios);
                BufferedReader br_salarios = new BufferedReader(fr_salarios);

                FileReader fr_titulos = new FileReader(titulos);
                BufferedReader br_titulos = new BufferedReader(fr_titulos);
                
                // COMENZAMOS A LEER

                
                // empleados.txt
                while(linea != null){
                    linea = br_empleados.readLine();
                    if(linea != null){
                        lineaSplit = linea.split(";");
                        sentencia.addBatch(String.format("INSERT INTO empleados (id_emp, fecha_nacimiento, nombre, apellidos, genero, fecha_alta) VALUES (%s, '%s', '%s', '%s', '%s', '%s')", lineaSplit[0], lineaSplit[1], lineaSplit[2], lineaSplit[3], lineaSplit[4], lineaSplit[5]));
                    }
                }
                linea = "";
                
                // departamentos.txt
                while(linea != null){
                    linea = br_departamentos.readLine();
                    if(linea != null){
                        lineaSplit = linea.split(";");
                        sentencia.addBatch(String.format("INSERT INTO departamentos (id_dep, nombre_departamento) VALUES ('%s', '%s')", lineaSplit[0], lineaSplit[1]));
                    }
                }
                linea = "";
                
                
                // dept_manager.txt
                while(linea != null){
                    linea = br_dept_manager.readLine();
                    if(linea != null){
                        lineaSplit = linea.split(";");
                        sentencia.addBatch(String.format("INSERT INTO dept_manager (id_emp, id_dep, fecha_inicio, fecha_fin) VALUES (%s, '%s', '%s', '%s')", lineaSplit[0], lineaSplit[1], lineaSplit[2], lineaSplit[3]));
                    }
                }
                linea = "";
                
                // dept_emp.txt
                while(linea != null){
                    linea = br_dept_emp.readLine();
                    if(linea != null){
                        lineaSplit = linea.split(";");
                        sentencia.addBatch(String.format("INSERT INTO dept_emp (id_emp, id_dep, fecha_inicio, fecha_fin) VALUES (%s, '%s', '%s', '%s')", lineaSplit[0], lineaSplit[1], lineaSplit[2], lineaSplit[3]));
                    }
                }
                linea = "";
                
                // titulos.txt
                while(linea != null){
                    linea = br_titulos.readLine();
                    if(linea != null){
                        lineaSplit = linea.split(";");
                        sentencia.addBatch(String.format("INSERT INTO titulos (id_emp, titulo, fecha_inicio, fecha_fin) VALUES (%s, '%s', '%s', '%s')", lineaSplit[0], lineaSplit[1], lineaSplit[2], lineaSplit[3]));
                    }
                }
                linea = "";
                
                // salarios.txt
                while(linea != null){
                    linea = br_salarios.readLine();
                    if(linea != null){
                        lineaSplit = linea.split(";");
                        sentencia.addBatch(String.format("INSERT INTO salarios (id_emp, salario, fecha_inicio, fecha_fin) VALUES (%s, %s, '%s', '%s')", lineaSplit[0], lineaSplit[1], lineaSplit[2], lineaSplit[3]));
                    }
                }
                
                System.out.println("Ejecutamos sentencia");
                sentencia.executeLargeBatch();
                conexion.commit();
                
                System.out.println(codigoEscape + "--> Datos migrados con exito" + resetearColor);
                
                br_departamentos.close();
                br_dept_emp.close();
                br_dept_manager.close();
                br_empleados.close();
                br_salarios.close();
                br_titulos.close();
            }
            catch(FileNotFoundException e){ // Por si no encuentra el archivo
            System.err.println("~ Error, el archivo no se ha encontrado: " + e.getMessage());
            }
            catch(IOException e){ // Por si ocurre un error de entrada/salida
                System.err.println("~ Error, ha ocurrido un problema de entrada/salida: " + e.getMessage());
            }
        }
        catch(SQLException e){
            System.err.println("~ Error, ha ocurrido un problema de SQL: " + e.getMessage());
        }
    }
    /*
    private static void insert_empleados(Connection connection, String[] datos){
        String query = "INSERT INTO empleados (id_emp, fecha_nacimiento, nombre, apellidos, genero, fecha_alta) VALUES (?, ?, ?, ?; ?, ?)";
        sentencia.executeUpdate(query);
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(datos[0])); //id_emp
            preparedStatement.setString(2, datos[1]); //fecha_nacimiento
            preparedStatement.setString(3, datos[2]); //nombre
            preparedStatement.setString(4, datos[3]); //apellidos
            preparedStatement.setString(5, datos[4]); //genero
            preparedStatement.setString(6, datos[5]); //fecha_alta
        }
        catch(SQLException e){
            System.err.println("~ Error, ha ocurrido un problema de SQL: " + e.getMessage());
        }
    }
*/
    
}
