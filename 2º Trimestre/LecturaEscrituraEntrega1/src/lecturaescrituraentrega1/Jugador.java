/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lecturaescrituraentrega1;

/**
 *
 * @author raulg
 */
public class Jugador {

    /**
     * Variable para almacenar un contador de los jugadores del archivo faltan.txt
     */
    public static int CONT_FALTAN = 0;
    
    /**
     * Variable para almacenar un contador de los jugadores del archivo jugadores.txt
     */
    public static int CONT_JUGADORES = 0;
    
    private int dorsal;
    private String nombre;
    private String mote;
    
    private static final int DEFAULT_DORSAL = 0;
    private static final String DEFAULT_MOTE = "Sin_Mote";
    
    /**
     * Constructor para crear un jugador nuevo
     * @param nombre nombre del jugador
     * @param dorsal dorsal del jugador
     * @param mote mote del jugador
     */
    public Jugador(String nombre, int dorsal, String mote){
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.mote = mote;
    }
    
    /**
     * Constructor para crear un jugador
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre){
        this(nombre, DEFAULT_DORSAL, DEFAULT_MOTE);
    }

    /**
     * Devuelve el dorsal del jugador
     * @return numero del dorsal
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * Establece un nuevo numero de dorsal al jugador
     * @param dorsal dorsal del jugador
     */
    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    /**
     * Devuelve el nombre del jugador
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre al jugador
     * @param nombre nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el mote del jugador
     * @return mote del jugador
     */
    public String getMote() {
        return mote;
    }

    /**
     * Establece un nuevo mote al jugador
     * @param mote mote del jugador
     */
    public void setMote(String mote) {
        this.mote = mote;
    }

    /**
     * Devuelve una cadena con el nombre, dorsal y mote del jugador con la siguiente estructura:
     * "nombre" "dorsal" "mote"
     * @return "nombre" "dorsal" "mote"
     */
    @Override
    public String toString() {
        return this.nombre + " " + this.dorsal + " " + this.mote;
    }
}
