/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package colecciones;

/**
 *
 * @author raulg
 */
public class Ciudad {
    private String nombre;
    private String provincia;
    private int poblacion;

    /**
     * Constructor de ciudad con paramatros
     * @param nombre nombre de la ciudad
     * @param provincia provincia a la que pertenece la ciudad
     * @param poblacion numeo de habitantes de la ciudad
     * @throws IllegalArgumentException si la poblacion es menor de 0
     */
    public Ciudad(String nombre, String provincia, int poblacion) throws IllegalArgumentException{
        if(poblacion < 0){
            throw new IllegalArgumentException("Poblacion no puede ser un numero negativo");
        }
        this.nombre = nombre;
        this.provincia = provincia;
        this.poblacion = poblacion;
    }
    
    /**
     * Metodo para actualziar el numero de habitantes de la ciuad
     * @param poblacion es el nuevo numero de habitantes
     */
    public void setPoblacion(int poblacion){
        this.poblacion = poblacion;
    }
    
    /**
     * Metodo para obtener el numero de habitantes de la ciudad
     * @return Devuelve el numero de habitantes de la ciudad
     */
    public int getPoblacion(){
        return this.poblacion;
    }
    
    /**
     * Metodo para obetener el nombre de la ciudad
     * @return Devuelve un int con el numero de habitantes de la ciudad
     */
    public String getNombre(){
        return this.nombre;
    }
    
    /**
     * Metodo para obtener el nombre de la provincia
     * @return Devuelve un string con el nombre de la ciudad
     */
    public String getProvincia(){
        return this.provincia;
    }
    
    @Override
    public String toString(){
        return String.format("{ Nombre: %-14s; Provincia: %-14s, Nº Habitantes: %-7d }", this.nombre, this.provincia, this.poblacion);
    }
}
