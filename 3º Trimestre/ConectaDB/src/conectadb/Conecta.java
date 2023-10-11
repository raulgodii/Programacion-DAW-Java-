/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package conectadb;
import java.sql.*;

/**
 *
 * @author raulg
 */
public class Conecta {

    public static void main(String[] args) throws SQLException {
        
        String url = "jdbc:mariadb://localhost:3306/mi_club";
        String user = "root";
        String pass = "MANAGER";
        String consulta = "SELECT * FROM socios";
        
        try{
            // 1. Creamos la conexión
            Connection conexion = DriverManager.getConnection(url, user, pass);
            
            // 2. Creamos el Statment
            Statement sentencia = conexion.createStatement();
            
            // 3. Ejecutamos la instrucción SQL
            ResultSet resultado = sentencia.executeQuery(consulta);
            
            // 4. Recorrer el ResultSet
            while(resultado.next()){
                System.out.println("Nombre: " + resultado.getString("nombre") + " " + resultado.getString("apellidos"));
            }
        }
        catch(Exception e){
            
        }
        
        
        
    }
}
