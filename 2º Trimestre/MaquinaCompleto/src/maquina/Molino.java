    package maquina;

/**
 * Clase que representa un tipo de máquina mecánica, un Molino. Define
 * internamente un enumerado Tipo, con los posibles valores para el atributo
 * tipo de molino en función de cuál sea la fuerza motriz que usa para
 * funcionar. No se desea permitir que esta clase se extienda en el futuro, por
 * lo que se impide la creación de subclases.
 *
 * @author Profesor PROG
 */
public class Molino extends MaquinaMecanica {

    /**
     * Atributo para especificar el tipo de molino de que se trata, en función de la 
     * fuente de energía que use para funcionar. Tomará valores de entre los ofrecicos por
     * el tipo enumerado TipoMolino (definido aparte).
     */
    private TipoMolino tipoDeMolino;  
   

    /**
     * Constructor que crea un nuevo Molino a partir de los valores recibidos
     * mediante los parámetros marca, modelo y fuerza motriz, haciendo uso del
     * constructor. El tipo de molino se asigna en función de la fuerza motriz
     * de dicho molino para que sea congruente.
     *
     * @param marca La marca del Molino.
     * @param modelo El modelo del Molino.
     * @param fuerzaMotriz La fuerza motriz del Molino.
     * 
     * @throws  NullPointerException cuando fuerzaMotriz es null.
     * @throws IllegalArgumentException Cuando se recibe un valor para el argumento fuerzaMotriz que no es ninguno de los recogidos.
     */
    public Molino(String marca, String modelo, Fuerza fuerzaMotriz) throws IllegalArgumentException, NullPointerException {
        super(marca, modelo, fuerzaMotriz);
        switch (fuerzaMotriz) {
            case ANIMAL:
                this.tipoDeMolino = TipoMolino.FUERZA_ANIMAL;
                break;
            case ELECTRICIDAD:
                this.tipoDeMolino = TipoMolino.ELECTRICO;
                break;
            case COMBUSTIBLE:
                this.tipoDeMolino = TipoMolino.A_MOTOR_COMBUSTION;
                break;
            case VIENTO:
                this.tipoDeMolino = TipoMolino.DE_VIENTO;
                break;
            case CORRIENTE_AGUA:
                this.tipoDeMolino = TipoMolino.DE_AGUA;
                break;
            default:
                Maquina.cantidadDeMaquinasFabricadas--;
                throw new IllegalArgumentException (String.format(
                        "Error, Fuerza motriz no válida: %s.",
                        fuerzaMotriz.toString())
                );
        }
    }

    /**
     * Método que permite obtener el tipo de molino de que se trata.
     *
     * @return La representación como String del tipo de Molino.
     */
    public TipoMolino getTipoDeMolino() {
        return this.tipoDeMolino;
    }

    /**
     *
     * @return La representación como String de un Molino, con el formato
     * { Marca: XXX; modelo: YYY; NS: ZZZ; Fuerza Motriz: WWW; Molino
     * de: VVV } donde XXX representa la marca, YYY representa el modelo, ZZZ
     * representa el número de serie, WWW representa la fuerza motriz y VVV
     * representa el tipo de molino.
     */
    @Override
    public String toString() {
        String toStringSuper = super.toString();
        return String.format("%s; Molino de: %-18s }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.getTipoDeMolino());
    }

}
