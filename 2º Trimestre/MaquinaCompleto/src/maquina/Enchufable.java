package maquina;

/**
 * Interfaz que obliga a implementar determinados métodos para objetos que
 * tengan la capacidad de ser enchufados a la corriente para funcionar.
 *
 * @author profesor PROG
 */
public interface Enchufable {

    /**
     * Método que permite consultar el voltaje del objeto de las clases que
     * implementen esta interfaz.
     *
     * @return El voltaje del objeto Enchufable.
     */
    int getVoltaje();

    /**
     * Método que permite consultar la lista de países cuyo voltaje es
     * compatible con el del objeto de las clases que implementen esta interfaz.
     *
     * @return Un array de String con la lista de países con voltaje compatible
     * al del objeto Enchufable.
     */
    String[] getPaisesCompatibles();
}
