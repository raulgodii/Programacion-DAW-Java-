/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lecturaescrituraentrega1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author raulg
 */
public class prueba {

    /**
     * Ruta del archivo faltan.txt
     */
    public static final String PathFaltan = "C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\LecturaEscrituraEntrega1\\src\\lecturaescrituraentrega1\\faltan.txt";
    /**
     * Ruta del archivo jugadores.txt
     */
    public static final String PathJugadores = "C:\\Users\\raulg\\OneDrive\\Escritorio\\DAW\\Programación\\2º Trimestre\\LecturaEscrituraEntrega1\\src\\lecturaescrituraentrega1\\jugadores.txt";
    /**
     * Ruta del archivo nuevaJugadores.txt
     */
    public static final String PathNueva = "C:\\\\Users\\\\raulg\\\\OneDrive\\\\Escritorio\\\\DAW\\\\Programación\\\\2º Trimestre\\\\LecturaEscrituraEntrega1\\\\src\\\\lecturaescrituraentrega1\\\\nuevaJugadores.txt";        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String lineaFaltan = "";
        String lineaJugadores;
        Jugador [] faltan = new Jugador [15];
        String [] Cabecera;
        Jugador [] jugador;
        boolean existe;
        
        try{
            // Creación de Flujos del Fichero "faltan.txt"
            File Faltan = new File(PathFaltan);
            FileReader frFaltan = new FileReader(PathFaltan);
            BufferedReader brFaltan = new BufferedReader(frFaltan);
            
            // Creación de Flujos del Fichero "jugadores.txt"
            File Jugadores = new File(PathJugadores);
            FileReader frJugadores = new FileReader(PathJugadores);
            BufferedReader brJugadores = new BufferedReader(frJugadores);
            
            // Creación de Flujos del Fichero "nuevaJugadores.txt"
            File nuevaJugadores = new File(PathNueva); 
            nuevaJugadores.createNewFile();
            FileWriter fwNueva = new FileWriter(PathNueva);
            BufferedWriter bwNueva = new BufferedWriter(fwNueva);
            
            /* Lectura del Fichero "faltan.txt" */
            while(lineaFaltan != null){
                lineaFaltan = brFaltan.readLine();
                
                // Creación de array para almacenar los jugadores que se han dado de baja
                if(lineaFaltan != null){  
                    faltan[Jugador.CONT_FALTAN] = new Jugador(lineaFaltan);
                    Jugador.CONT_FALTAN++;
                }
                
            }
            
            /* Lectura del Fichero "jugadores.txt" */
            
            // Leemos SOLO la primera linea (la cabecera)
            lineaJugadores = brJugadores.readLine();
            Cabecera = lineaJugadores.split(" ");
            
            // Creamos el array de jugadores con el primer valor de Cabecera (será el número de jugadores)
            jugador = new Jugador[Integer.parseInt(Cabecera[0])];
            
            
            while(lineaJugadores != null){
                lineaJugadores = brJugadores.readLine();
                
                    // Rellenamos el array para almacenar los jugadores comprobando que no sea uno de los que se ha dado de baja

                    // Primero nos aseguramos de que no es null
                    if(lineaJugadores != null){  
                        String [] jugadorSplit = lineaJugadores.split(" ");
                        
                        // Segundo comprobamos que no exista el jugador en faltan
                        existe = false; // Lo primero que hacemos es inicializar la variable existe a false para empezar el bucle con la suposición de que el jugador no existe en el archivo
                        for(int i=0; i<Jugador.CONT_FALTAN && !existe; i++){
                            // Si existe, inicializamos la variable a true
                            if(jugadorSplit[0].equals(faltan[i].getNombre())){
                                existe = true;
                            }
                        }
                        
                        // Si no existe, lo creamos
                        if(!existe){
                            jugador[Jugador.CONT_JUGADORES] = new Jugador(jugadorSplit[0], Integer.parseInt(jugadorSplit[1]), jugadorSplit[2]);
                            Jugador.CONT_JUGADORES++;
                        }
                    }
            }
            
            // Escribimos en nuevaJugadores.txt la cabecera
            bwNueva.write(String.format("%d Jugadores\n", Jugador.CONT_JUGADORES));
            
            // Escribimos en nuevaJugadores.txt el contenido con los jugadores
            for(int i = 0; i< Jugador.CONT_JUGADORES; i++){
                bwNueva.write(jugador[i].toString());
                if(i != Jugador.CONT_JUGADORES-1){
                    bwNueva.write("\n");
                }
            }
            
            // Cerramos los flujos
            bwNueva.close();
            brFaltan.close();
            brJugadores.close();
            
            // Eliminamos los ficheros
            Faltan.delete();
            Jugadores.delete();
        }
        
        // Capturamos FileNotFoundException por si existe algún problema para encontrar el archivo
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        
        // Capturamos IOException por si existe algún problema de entrada/salida
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
    
}
