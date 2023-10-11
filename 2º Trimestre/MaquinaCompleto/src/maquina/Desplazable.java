package maquina;

/**
 * Interfaz que obliga a implementar métodos para objetos que pueden desplazarse una
 * cantidad de kilómetros propulsados por alguna fuente de energía.
 *
 * @author Profesor PROG
 */
public interface Desplazable {

    /**
     * Método que permite desplazar un objeto una cantidad de kilómetros
     * indicada como parámetro. Debe incrementar por tanto el total de
     * kilómetros recorridos hasta el momento por el objeto como el total de
     * kilómetros recorridos desde el último repostaje (sin repostar).
     *
     * @param Kilometros. Representa el total de kilómetros, con decimales, que
     * se va a desplazar una máquina. Debe incrementar por tanto el total de
     * kilómetros recorridos hasta el momento por la máquina.
     */
    void desplazar(double Kilometros);

       /**
     * Método que permite consultar el total de kilómetros que el objeto
     * desplazable ha recorrido desde su fabricación.
     *
     * @return el total de kilómetros que se ha desplazado el objeto desplazable
     * desde su fabricación.
     */
    double getTotalKilometrosRecorridos();

    /**
     * Método que permite consultar la cantidad de kilómetros que un objeto
     * desplazable ha recorrido desde su último repostaje. Si es un objeto que
     * no necesita repostar, coincidirá con la cantidad total de kilómetros
     * recorridos desde su fabricación.
     *
     * @return Los Kilómetros recorridos por el objeto desde su último
     * repostaje. Si es un objeto que no necesita repostar, coincidirá con la
     * cantidad total de kilómetros recorridos desde su fabricación
     */
    double getKilometrosSinRepostar();

}
