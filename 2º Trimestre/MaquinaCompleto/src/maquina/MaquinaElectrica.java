package maquina;

/**
 * Clase que representa un subtipo de Máquinas, las Máquinas elécricas.
 *
 * A la información heredada de la clase Maquina, añade un par de atributos:
 * <ul>
 * <li> Voltaje. Es el voltaje de esta máquina eléctrica. Expresado en voltios
 * (v.)</li>
 * <li> Potencia eléctrica. Es la potencia eléctrica de una máquina. Expresada
 * en kilowatios (kW.) eléctrica.</li>
 * </ul>
 *
 * @author Profesor PROG
 */
public abstract class MaquinaElectrica extends Maquina {

    /**
     * Voltaje mínimo de cualquier máquina eléctrica: {@value MIN_VOLTAJE} v.
     */
    public static final int MIN_VOLTAJE = 10;

    /**
     * Voltaje máximo de cualquier máquina eléctrica: {@value MAX_VOLTAJE} v.
     */
    public static final int MAX_VOLTAJE = 400;

    /**
     * Voltaje que se asignará por defecto a cualquier máquina eléctrica en el
     * momento de su fabricación: {@value DEFAULT_VOLTAJE} v.
     */
    public static final int DEFAULT_VOLTAJE = MaquinaElectrica.MIN_VOLTAJE;

    /**
     * Potencia mínima para cualquier máquina eléctrica:
     * {@value MIN_POTENCIA_ELECTRICA} kW.
     */
    public static final double MIN_POTENCIA_ELECTRICA = 700.0;

    /**
     * Potencia máxima para cualquier máquina eléctrica:
     * {@value MAX_POTENCIA_ELECTRICA} kW.
     */
    public static final double MAX_POTENCIA_ELECTRICA = 200_000.0;

    /**
     * Potencia que se asignará por defecto a cualquier máquina eléctrica:
     * {@value DEFAULT_POTENCIA_ELECTRICA} kW. Actualmente coincidirá con el
     * valor mínimo establecido para la potencia eléctrica:
     */
    public static final double DEFAULT_POTENCIA_ELECTRICA = MaquinaElectrica.MIN_POTENCIA_ELECTRICA;

    /**
     * Voltaje de la máquina eléctrica, expresado en voltios (v.) mediante un
     * número entero, comprendido entre {@value MIN_VOLTAJE} y
     * {@value MAX_VOLTAJE}. Su valor por defecto actualmente está fijado en
     * {@value DEFAULT_VOLTAJE}
     */
    protected int voltaje;

    /**
     * Potencia eléctrica de la máquina eléctrica, expresada en kilowatios (kW.)
     * mediante un número real con valor comprendido entre
     * {@value MIN_POTENCIA_ELECTRICA} y {@value MAX_POTENCIA_ELECTRICA}. Su
     * valor por defecto actualmente está fijado en {@value DEFAULT_POTENCIA_ELECTRICA} kW.
     */
    protected double potenciaElectrica;

    /**
     * Constructor para objetos de tipo Maquinaelectrica, a partir de los
     * atributos marca, modelo, voltaje y potencia eléctrica. El constructor
     * verifica que los valores suministrados como parámetro para voltaje y
     * potenciaElectrica sean válidos.
     *
     * @param marca La marca de la máquina.
     * @param modelo El modelo de la máquina
     * @param voltaje El voltaje de la máquina. Debe estar entre el mínimo de {@value MIN_VOLTAJE} v. y
     * el máximo de {@value MAX_VOLTAJE} v. (para corriente trifásica).
     * @param potenciaElectrica La potencia eléctrica de la máquina. No puede
     * ser negativa, ni superar los {@value MAX_POTENCIA_ELECTRICA} kW.
     * @throws IllegalArgumentException Cuando se intenta asignar un valor a
     * voltaje o a potencia eléctrica no permitido.
     */
    public MaquinaElectrica(String marca, String modelo, int voltaje, double potenciaElectrica)
            throws IllegalArgumentException {
        super(marca, modelo);
        this.inicializarVoltaje(voltaje);
        this.inicializarPotenciaElectrica(potenciaElectrica);
    }

    /**
     * Constructor para objetos de tipo MaquinaElectrica, a partir de los
     * atributos marca y modelo recibidos como parámetro. Se asignará los
     * valores por defecto para el voltaje de la máquina y para la potencia (que
     * a su vez serán los valores mínimos).
     *
     * @param marca La marca de la Máquina eléctrica.
     * @param modelo El modelo de Máquina eléctrica.
     */
    public MaquinaElectrica(String marca, String modelo) {
        this(marca, modelo, MaquinaElectrica.DEFAULT_VOLTAJE, MaquinaElectrica.DEFAULT_POTENCIA_ELECTRICA);
    }

    /**
     * Método para asignar valor al voltaje de una máquina eléctrica,
     * garantizando que se asigna un valor correcto. Valores válidos: Mínimo {@value MIN_VOLTAJE}
     * v. y máximo {@value MAX_VOLTAJE} v. en trifásico.
     *
     * @param voltaje. El valor de voltaje a asignar. Será un número entero.
     * Valores válidos: Mínimo {@value MIN_VOLTAJE} v. y máximo {@value MAX_VOLTAJE} v. en trifásico.
     * @throws IllegalArgumentException si se intenta asignar un valor de
     * voltaje fuera del rango permitido: Mínimo {@value MIN_VOLTAJE} v. y máximo {@value MAX_VOLTAJE} v. en
     * trifásico.
     */
    private void inicializarVoltaje(int voltaje) throws IllegalArgumentException {
        if (voltaje < MaquinaElectrica.MIN_VOLTAJE || voltaje > MaquinaElectrica.MAX_VOLTAJE) {
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException(String.format("Error de voltaje: %d v.  Mínimo %d v. y máximo %d v.",
                    voltaje,
                    MaquinaElectrica.MIN_VOLTAJE,
                    MaquinaElectrica.MAX_VOLTAJE));
        } else {
            this.voltaje = voltaje;
        }
    }

    /**
     * Método para asignar el valor a la potencia eléctrica de una máquina
     * eléctrica, garantizando que se asigna un valor correcto. (No puede ser
     * negativa, ni superar los {@value MAX_POTENCIA_ELECTRICA} kW.
     *
     * @param potenciaElectrica El valor de potencia eléctrica que se quiere
     * asignar a una máquina eléctrica. (No puede ser negativa, ni superar los
     * {@value MAX_POTENCIA_ELECTRICA} kW.
     */
    private void inicializarPotenciaElectrica(double potenciaElectrica) throws IllegalArgumentException {
        if (potenciaElectrica < MaquinaElectrica.MIN_POTENCIA_ELECTRICA || potenciaElectrica > MaquinaElectrica.MAX_POTENCIA_ELECTRICA) {
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException(String.format(
                    "Error de potencia:%.2f. No puede ser menor que %.2f w. ni superar los %.2f w.",
                    potenciaElectrica,
                    MaquinaElectrica.MIN_POTENCIA_ELECTRICA,
                    MaquinaElectrica.MAX_POTENCIA_ELECTRICA));
        } else {
            this.potenciaElectrica = potenciaElectrica;
        }
    }

    /**
     * Método para consultar el valor del voltaje de una máquina eléctrica.
     *
     * @return El voltaje de la máquina eléctrica.
     */
    public int getVoltaje() {
        return this.voltaje;
    }

    /**
     * Método para consultar el valor de la potencia eléctrica de una máquina
     * eléctrica.
     *
     * @return La potencia eléctrica de la máquina eléctrica.
     */
    public double getPotenciaElectrica() {
        return this.potenciaElectrica;
    }

    /**
     * Método para obtener la representación como String de un objeto de tipo
     * MaquinaElectrica, con el formato: { Marca: XXX; modelo: YYY; NS: ZZZ;
     * Voltaje: WWW v.; Potencia: VVVV W. } donde XXX representa la marca, YYY
     * representa el modelo, ZZZ representa el número de serie, WWW representa
     * el voltaje y VVV representa la potencia eléctrica.
     *
     * @return La representación como String de un objeto tipo MaquinaElectrica
     */
    @Override
    public String toString() {
        String toStringSuper = super.toString();
        return String.format("%s; Voltaje: %6d v.; Potencia: %6.2f W. }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.getVoltaje(),
                this.getPotenciaElectrica());
    }

}
