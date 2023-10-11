/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea8;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Clase Contacto para almacenar la información de cada contacto de la agenda.
 * @author raulg
 */
public class Contacto {
    private String nombre; // Noombre del contacto
    private LocalDate fechaDeNacimiento; // Fecha de nacimiento del contacto
    private String movil; // Telefono movil del contacto
    private String direccion; // Direccion del contacto

    /**
     * Metodo constructor de la clase contacto. Todo contacto debe crearse de la siguiente manera:: 
     *      - nombre: cadena de texto de más de 2 caracteres.
     *      - fechaDeNacimiento: la fecha de nacimiento debe ser anterior a la fecha actual.
     *      - movil: debe estar compuesta por 9 caracteres numéricos
     *      - direccion: cadena de texto.
     * @param nombre Nombre del contacto (cadena de texto de más de 2 caracteres)
     * @param fechaDeNacimiento Fecha de Nacimiento del contacto (la fecha de nacimiento debe ser anterior a la fecha actual)
     * @param movil Telefono Movil del contacto (ebe estar compuesta por 9 caracteres numéricos)
     * @param direccion Direccion del contacto (cadena de texto)
     */
    public Contacto(String nombre, String fechaNacimiento, String movil, String direccion) throws IllegalArgumentException, DateTimeParseException{
        
        if (nombre.length()>=2){
            if(nombre.contains(";")){
                throw new IllegalArgumentException("El nombre no puede contener \";\"");
            }else{
                this.nombre = nombre;
            }
        }
        else{
            throw new IllegalArgumentException("El nombre debe de tener minimo 2 caracteres");
        }
        
        if(LocalDate.parse(fechaNacimiento).isBefore(LocalDate.now())){
            this.fechaDeNacimiento = LocalDate.parse(fechaNacimiento);
        } else{
            throw new IllegalArgumentException("La fecha introducida no es anterior a la fecha actual");
        }
        
        if (movil.length()==9){
            if(movil.contains(";")){
                throw new IllegalArgumentException("El movil no puede contener \";\"");
            }else{
                if(movil.matches("[0-9]*")){
                    this.movil = movil;
                }else{
                    throw new IllegalArgumentException("El movil solo puede contener caracteres numericos");
                }
                
            }
        }
        else{
            throw new IllegalArgumentException(" El numero movil debe de tener 9 digitos");
        }
        
        if(direccion.equals("")){
            this.direccion = "sin direccion";
        }else{
            if(direccion.contains(";")){
                throw new IllegalArgumentException("La direccion no puede contener \";\"");
            }else{
                this.direccion = direccion;
            }
        }
        
    }

    /**
     * Devuelve el nombre del contacto
     * @return Nombre del contacto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del contacto
     * @param nombre Nombre del contacto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la fecha de nacimiento del contacto
     * @return Fecha del contacto
     */
    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del contacto
     * @param fechaDeNacimiento Fecha de nacimiento del contacto
     */
    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    /**
     * Devuelve el telefono movil del contacto
     * @return Telefono movil del contacto
     */
    public String getMovil() {
        return movil;
    }

    /**
     * Establece el telefono movil del contacto
     * @param movil Telefono movil del contacto
     */
    public void setMovil(String movil) {
        this.movil = movil;
    }

    /**
     * Devuelve la direccion del contacto
     * @return Direccion del contacto
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la direccion del contacto
     * @param direccion Direccion del contacto
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    /**
     * Devuelve la edad actual del contacto, desde su fecha de Nacimiento hasta hoy
     * @return Edad del contacto
     */
    public int getEdad(){
        int edad = 0;
        return edad;
    }
    
    /**
     * Devuelve una cadena de texto con el formato: (nombre);(fechaDeNacimiento);(movil);(direccion);
     * @return Datos del contacto (cadena de texto)
     */
    @Override
    public String toString(){
        String cadena = this.nombre + ";" + this.fechaDeNacimiento + ";" + this.movil + ";" + this.direccion;
        return cadena;
    }
}
