package maquina;

/**
 * Interfaz que obliga a implementar determinados métodos para objetos que estén
 * dotados de una batería con capacidad de ser recargada.
 *
 * @author profesor PROG
 */
public interface Recargable {

    /**
     * Método para especificar cómo se llevará a cabo la carga de la batería de
     * un objeto de las clases que implementen esta interfaz.
     */
    void cargar();

    /**
     * Método que consume parte de la carga efectiva de la batería del objeto de
     * la clase que implemente el interfaz debido al uso. La forma en que se reduce esa carga
     * a partir del parámetro recibido dependerá de la clase de objeto de que se
     * trate. 
     * 
     * @return la cantidad de uso de batería que nos quedaría después de usar la cantidad indicada. 
     * (Carga efectiva restante en un coche, horas de uso restantes en una calculadora, ...
     * @param cantidad. Variable de la que depende el consumo de batería 
     * (kilómetros en un coche, horas de uso de una calculadora, etc.)
     */
    double usarBateria(double cantidad);

}
