package maquina;

/**
 * Clase que modela una bicicleta como máquina mecánica con capacidad para
 * desplazarse. No se desea que se puedan crear más subtipos de bicicletas. A la
 * información heredada de la clase MaquinaMecanica, añade 3 atributos:
 * <ul>
 * <li> Radio de las ruedas. De tipo double, se mide en centímetros.</li>
 * <li> Total de kilómetros recorridos desde su fabricación. De tipo
 * double.</li>
 * </ul>
 *
 * @author Profesor PROG
 */
public final class Bicicleta extends MaquinaMecanica implements Desplazable {

    /**
     * El valor por defecto para el radio de la rueda de una bicicleta: {@value DEFAULT_RADIO_RUEDA}. cm.
     */
    public static final double DEFAULT_RADIO_RUEDA = 33.0;

    /**
     * El valor mínimo para el radio de una rueda: {@value MIN_RADIO_RUEDA} cm.
     */
    public static final double MIN_RADIO_RUEDA = 17.75; 

    /**
     *El valor máximo para el radio de una rueda:{@value MAX_RADIO_RUEDA} cm.
     */
    public static final double MAX_RADIO_RUEDA = 36.85;

    /**
     * El número máximo de kilómetros que se puede desplazar una bicicleta sin hacer paradas: {@value MAX_DESPLAZAMIENTO} km.
     */
   
    public static final double MAX_DESPLAZAMIENTO = 200.0;
    
    
    /**
     * Longitud del radio de la rueda en centímetros.
     */
    private double radioRueda;  //longitud del radio en cm.
    /**
     * Total de kilómetros recorridos por una bicicleta desde su fabricación.
     */
    private double totalKilometros; // total de kilómetros recorridos por esta bicicleta.

    /**
     * Constructor que crea una bicicleta a partir de los valores recibidos como
     * parámetros para marca y modelo, asignándole como radio el valor por
     * defecto, inicializando el contador de total de kilómetros recorridos a 0.
     *
     * @param marca La marca de la bicicleta.
     * @param modelo El modelo de la bicicleta.
     */
    public Bicicleta(String marca, String modelo) {
        this(marca, modelo, Bicicleta.DEFAULT_RADIO_RUEDA);              
    }

    /**
     * Constructor que crea una nueva bicicleta a partir de los valores
     * recibidos como parámetros para marca, modelo y radio de las ruedas. 
     * Inicializa el contador de total de kilómetros recorridos a 0.
     *
     * @param marca La marca de la bicicleta.
     * @param modelo El modelo de la bicicleta.
     * @param radioRueda El radio en cm. de la rueda.
     *
     * @throws IllegalArgumentException cuando el radio de la bicicleta es
     * menor que el valor mínimo definido por MIN_RADIO_RUEDA ({@value MIN_RADIO_RUEDA} cm.) y mayor que el
     * valor máximo definido por MAX_RADIO_RUEDA ({@value MAX_RADIO_RUEDA} cm.).
     */
    public Bicicleta(String marca, String modelo, double radioRueda) throws IllegalArgumentException {
        super(marca, modelo, Fuerza.ANIMAL);
        if (radioRueda >= Bicicleta.MIN_RADIO_RUEDA && radioRueda <= Bicicleta.MAX_RADIO_RUEDA) {
            this.radioRueda = radioRueda;
            this.totalKilometros = 0;
        } else {
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException (String.format(
                    "Error en valor del radio: %4.2f cm. Debe estar comprendido entre %4.2f cm. y %4.2f cm.", 
                    radioRueda, 
                    Bicicleta.MIN_RADIO_RUEDA,
                    Bicicleta.MAX_RADIO_RUEDA)
            ); 
        }

    }

    /**
     * Método que devuelve el radio de la rueda de la bicicleta que lo invoca.
     *
     * @return El valor del radio de la bicicleta, en cm.
     */
    public double getRadioRuedas() {
        return this.radioRueda;
    }

    /**
     * Método que aumenta el total de kilómetros recorridos por una bicilceta.
     * No se va a permitir desplazamientos negativos, ni mayores que el máximo fijado por MAX_DESPLAZAMIENTO ({@value MAX_DESPLAZAMIENTO} km.) sin parar en bicicleta.
     *
     * @param kilometros El total de kilómetros que se desplaza la bicicleta,
     * incrementándose el contador de kilómetros en esa cantidad.
     * 
     * @throws IllegalArgumentException Si el el la cantidad de kilómetros recibida como parámetro es negativa, o mayor que el desplazamiento máximo.
     */
    @Override
    public void desplazar(double kilometros) throws IllegalArgumentException {  // sustituir literales por constantes

        if (kilometros < 0 || kilometros > Bicicleta.MAX_DESPLAZAMIENTO) {
            throw new IllegalArgumentException(String.format(
                    "Cantidad de kilómetros negativa o "
                    + "excesiva (Máx: 4.2f kms): 4.2f kms. ",
                    Bicicleta.MAX_DESPLAZAMIENTO,
                    kilometros)
            );
        } else {
            this.totalKilometros += kilometros; //si el valor es correcto, se incrementa el kilometraje.
        }
    }

    /**
     * Método que devuelve el total de kilómetros que ha recorrido la bicicleta
     * sin repostar. Dado que las bicicletas no necesitan repostar, el valor
     * devuelto coincidirá exactamente con el total de kilómetros recorridos por
     * la bicicleta desde su fabricación.
     *
     * @return El total de kilómetros que la bicicleta ha recorrido desde su
     * fabricación.
     */
    @Override
    public double getKilometrosSinRepostar() {
        return this.getTotalKilometrosRecorridos();
    }

    /**
     * Método que devuelve el total de kilómetros que la bicicleta ha recorrido
     * desde su fabricación. Puesto que las bicicletas no necesitan respostar,
     * coincidirá exactamente con el valor devuelto por el método
     * getKilometrosSinRepostar().
     *
     * @return total de kilómetros que ha recorrido esta bicicleta.
     */
    @Override
    public double getTotalKilometrosRecorridos() {
        return this.totalKilometros;
    }

    /**
     * Método que devuelve la representación como String de una bicicleta.
     *
     * @return La representación como String de una bicicleta, con el formato
     * { Marca: XXX; modelo: YYY; NS: ZZZ; Fuerza Motriz: WWW;
     * Radio: VVV; Kilómetros: UUU } donde XXX representa la marca, YYY
     * representa el modelo, ZZZ representa el número de serie, y WWW representa
     * la fuerza motriz, VVV representa el radio de la rueda y UUU los kilómetros recorridos por la bicicleta
     */
    @Override
    public String toString() {
        String toStringSuper = super.toString();
        return String.format("%s; Radio: %4.2f; Kilómetros: %5.2f }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.getRadioRuedas(), this.getTotalKilometrosRecorridos());
    }

}
