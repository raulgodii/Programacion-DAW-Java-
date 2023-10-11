package maquina;

   /**
     * Tipo enumerado, que representa las diferentes fuentes de energía que puede usar una  máquina mecánica.
     * Puede tomar los valores: ANIMAL, ELECTRICIDAD, COMBUSTIBLE, VIENTO, CORRIENTE_AGUA
     * 
     * @author Profesor PROG
     */
public enum Fuerza {   
 
    /**
     * Fuerza ejercida por cualquier ser vivo (un animal, una persona)
     */
    ANIMAL, 

    /**
     * Fuerza ejercida mediante el uso de energía eléctrica.
     */
    ELECTRICIDAD, 

    /**
     * Fuerza ejercida mediante el consumo de un determinado combustible, como puede ser gasolina, diésel, ...
     */
    COMBUSTIBLE, 

    /**
     * Fuerza ejercida por el viento.
     */
    VIENTO, 

    /**
     * Fuerza ejercida por una corriente de agua.
     */
    CORRIENTE_AGUA 
}
