package maquina;

/**
 * Tipos de Molino que puede haber en función de la fuerza motriz.
 *
 * @author Profesor PROG
 */
public enum TipoMolino {

    /**
     * Tipo de Molino accionado por energía eólica (molino de viento).
     */
    DE_VIENTO,
    
    /**
     * Tipo de Molino accionado por la fuerza de una corriente de agua (molino
     * hidráulico)
     */
    DE_AGUA,
   
    /**
     * Tipo de Molino accionado por la fuerza de un ser vivo (fuerza animal)
     * como puede ser una noria a la que hace girar un burro, un molinillo de
     * café accionado manualmente por una manivela, etc.
     */
    FUERZA_ANIMAL,
   
    /**
     * Tipo de Molino accionado por un motor que funciona con energía eléctrica, conectado a la corriente.
     */
    ELECTRICO,
    
    /**
     * Tipo de Molino accionado por un motor de combustión (gasolina, diésel, etc.)
     */
    A_MOTOR_COMBUSTION

}
