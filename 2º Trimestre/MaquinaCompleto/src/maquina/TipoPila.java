package maquina;

/**
 * Tipos de Pilas que podemos usar como batería en algunos aparatos eléctricos.
 *
 * @author Profesor PROG
 */

public enum TipoPila {
   
    /**
     * Pila convencional, tipo AA, de 1.5 voltios.
     */
    AA_1_5V,

    /**
     * Pila convencional, tipo AAA, de 1.5 voltios. (Un poco más estrechas que las tipo AA, usuales en mandos a distancia, et.)
     */
    AAA_1_5V,

    /**
     * Tipo de pila C, de 1.5 voltios.
     */
    C_1_5V,

    /**
     * Tipo de pila D, de 1,5 voltios.
     */
    D_1_5V,

    /**
     * Pila tipo Botón, modelo CR1025 de 3 voltios.
     */
    BOTON_CR1025_3V,

    /**
     *  Pila tipo Botón, modelo CR1130 de 3 voltios.
     */
    BOTON_CR1130_3V
    
}
