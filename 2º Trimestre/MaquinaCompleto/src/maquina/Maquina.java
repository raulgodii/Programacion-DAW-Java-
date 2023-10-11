package maquina;

/**
 * Clase abstracta para representar una Máquina. Toda máquina va a disponer de
 * tres atributos: numeroDeSerie, marca y modelo, cuyo valor no va a cambiar una
 * vez asignados. El número de serie será un número entero secuencial, que
 * usaremos para cualquier tipo de máquina que fabriquemos. La marca y el modelo
 * son solo dos cadenas de caracteres. Dispondrá de métodos para consultar estos
 * atributos, pero no para modificarlos, ya que no estará permitido una vez que
 * se les asigna un valor al fabricar la máquina.
 *
 * @author Profesor PROG
 */
public abstract class Maquina {

    /**
     * El número de serie que toda máquina tendrá. Va a ser un número que se
     * genera secuencialmente, para cualquier máquina, sea del tipo que sea.
     *
     */
    protected final int numeroSerie;

    /**
     * La marca de la máquina. Se admite cualquier cadena de caracteres como
     * nombre de la marca.
     */
    protected final String marca;

    /**
     * El modelo de la máquina. Se admite como modelo cualqier cadena de
     * caracteres.
     */
    protected final String modelo;

    /**
     * La cantidad de máquinas fabricadas.  solo lo usamos como contador para 
     * asignar el número de serie a cada nueva máquina fabricada. 
     * 
     */
    protected static int cantidadDeMaquinasFabricadas = 0;

    /**
     * Constructor que crea un objeto de tipo Maquina de una marca y modelo
     * determinados.
     *
     * @param marca La marca de la máquina. (Cualquier cadena de caracteres es
     * aceptable)
     *
     * @param modelo El modelo de la máquina. (Cualquier cadena de caracteres es
     * aceptable)
     */
    public Maquina(String marca, String modelo) {
        this.numeroSerie = Maquina.cantidadDeMaquinasFabricadas;
        Maquina.cantidadDeMaquinasFabricadas++;
        this.marca = marca;
        this.modelo = modelo;
    }

    /**
     * Permite consultar el número de serie del objeto que lo invoca.
     *
     * @return El número de serie de la máquina.
     */
    public int getNumeroDeSerie() {
        return this.numeroSerie;
    }

    /**
     * Permite consultar la marca del objeto que lo invoca.
     *
     * @return La marca de la máquina.
     */
    public String getMarca() {
        return this.marca;
    }

    /**
     * Permite consultar el modelo del objeto que lo invoca.
     *
     * @return El modelo de la máquina.
     */
    public String getModelo() {
        return this.modelo;
    }

    /**
     * Método para obtener el total de máquinas fabricadas.
     *
     * @return El número total de máquinas fabricadas, de cualquier tipo.
     */
    public static int getCantidadDeMaquinasFabricadas() {
        return Maquina.cantidadDeMaquinasFabricadas;
    }

    /**
     * Método que devuelve un String que representa a la máquina con el formato
     * formato { Marca: XXX; modelo: YYY; NS: ZZZ }, donde XXX representa la
     * marca, YYY representa el modelo, y ZZZ representa el número de serie.
     *
     * @return La representación como String de la máguina.
     */
    @Override
    public String toString() {
        return String.format("{ Marca: %-10s; Modelo: %-10s; NS: %-4d }",
                this.getMarca(),
                this.getModelo(),
                this.getNumeroDeSerie());
    }
}
