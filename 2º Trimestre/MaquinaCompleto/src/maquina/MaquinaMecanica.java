package maquina;

/**
 * Clase que representa un subtipo de Máquinas, las Máquinas mecánicas.
 *
 * A la información heredada de la clase Maquina, añade un par de atributos:
 * <ul>
 * 
 * <li> fuerzaMotriz. La fuente de energía concreta que usará una máquina
 * mecánica para funcionar. Puede tomar atributos de un tipo enumerado Fuerza,
 * declarado aparte y que representa las diferentes fuentes de energía que puede
 * usar una máquina mecánica. Puede tomar los valores ANIMAL, ELECTRICIDAD,
 * COMBUSTIBLE, VIENTO, CORRIENTE_AGUA</li>
 * <li> DEFAULT_FUERZA_MOTRIZ. Representa la fuerza motriz que se asignará por
 * defecto: COMBUSTIBLE.</li>
 * </ul>
 *
 * @author Profesor PROG
 */
public abstract class MaquinaMecanica extends Maquina {

    /**
     * Representa la fuerza motriz que se asignará por defecto: COMBUSTIBLE
     */
    public static final Fuerza DEFAULT_FUERZA_MOTRIZ = Fuerza.COMBUSTIBLE;

    /**
     * Representa la fuente de energía concreta que usará una máquina mecánica.
     */
    public final Fuerza fuerzaMotriz;

    /**
     * Constructor para crear una máquina mecánica a partir de la marca, el
     * modelo y la fuerza motriz recibidos como parámetros.
     *
     * @param marca La marca de la máquina mecánica.
     * @param modelo El modelo de la máguina mecánica.
     * @param fuerzaMotriz La fueraz motriz de la máquina mecánica.
     */
    public MaquinaMecanica(String marca, String modelo, Fuerza fuerzaMotriz) throws NullPointerException {
        super(marca, modelo);
        if(fuerzaMotriz==null){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new NullPointerException(String.format(
                "Error: %s. La fuerza motriz no puede ser nula, debe indicarse una fuerza motriz válida.",
                fuerzaMotriz)
            );
        } // solo en caso contrario, cuando no se lanza la excepción, continuamos asignando valores
        this.fuerzaMotriz = fuerzaMotriz;
        
    }

    /**
     * Constructor para crear una máquina mecánica a partir de la marca y el
     * modelo recibidos como parámetros, asignando el valor por defecto para
     * fuerza Motriz.
     *
     * @param marca La marca de la máquina mecánica.
     * @param modelo El modelo de la máguina mecánica.
     */
    public MaquinaMecanica(String marca, String modelo) {
        this(marca, modelo, MaquinaMecanica.DEFAULT_FUERZA_MOTRIZ);
    }

    /**
     * Método que devuelve el valor asignado como fuerza motriz a una máquina
     * mecánica.
     *
     * @return La representación como String de la fuerza Motriz.
     */
    public Fuerza getFuerzaMotriz() {
        //return this.fuerzaMotriz.toString();
        return this.fuerzaMotriz;
    }

    /**
     * Método que devuelve la representación como String de una máquina
     * mecánica.
     *
     * @return La representación como String de una máquina mecánica con el
     * formato { Marca: XXX; modelo: YYY; NS: ZZZ; Fuerza Motriz: WWW } donde
     * XXX representa la marca, YYY representa el modelo, ZZZ representa el
     * número de serie, y WWW representa la fuerza motriz.
     */
    @Override
    public String toString() {
        String toStringSuper = super.toString();
        return String.format("%s; Fuerza Motriz: %-10s }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.getFuerzaMotriz());
    }

}
