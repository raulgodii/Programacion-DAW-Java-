package maquina;

/**
 * Clase abtracta para modelar un Coche como máquina mecánica desplazable.
 * Define un parámetro combustibleUsado, que puede tomar los valores del tipo
 * enumerado TipoCombustible. Valores válidos son GASOLINA, DIESEL O
 * ELECTRICIDAD. Como atributos, añade a los heredados por ser una subclase de
 * máquina mecánica los siguientes:
 * <ul>
 * <li>combustibleUsado. Es el tipo de combustible que usa el Coche.</li>
 * <li>kilometrosSinRepostar. Es la distancfia recorrida en km. por el Coche
 * desde que se repostó por última vez.</li>
 * <li>kilometrosTotalesRecorridos. Es la distancia recorrida en km. por el
 * Coche desde su fabricación. por</li>
 * </ul>
 *
 * @author Profesor PROG
 */
public abstract class Coche extends MaquinaMecanica implements Desplazable {

    /**
     * Máximo desplazamiento sin repostar permitido para cualquier Coche:
     * {@value MAX_DESPLAZAMIENTO} 1500km.
     */
    public static final double MAX_DESPLAZAMIENTO = 1500;

    /**
     * Total de kilómetros recorridos por el Coche sin repostar (desde el
     * repostaje anterior).
     */
    protected double kilometrosSinRepostar;

    /**
     * Total de Km. recorridos por el Coche desde su fabricación.
     */
    protected double kilometrosTotalesRecorridos;

    /**
     * Combustible usado por el Coche.
     */
    protected TipoCombustible combustibleUsado;

    /**
     * Constructor que crea un nuevo Coche a partir de los valores para su
     * marca, modelo y fuera motriz recibidos como parámetros.
     *
     * @param marca La marca del Coche.
     * @param modelo El modelo del Coche.
     * @param fuerzaMotriz. Fuerza motriz del coche. Solo admitirá los valores
     * ELECTRICIDAD y COMBUSTIBLE.
     *
     * @throws IllegalArgumentException Cuando se intenta asignar como fuerza
     * motriz un valor no válido para un Coche.
     * @throws NullPointerException Cuando fuerzaMotriz es null.
     */
    public Coche(String marca, String modelo, Fuerza fuerzaMotriz) throws IllegalArgumentException, NullPointerException{
        super(marca, modelo, fuerzaMotriz);
        if (fuerzaMotriz != Fuerza.ELECTRICIDAD && fuerzaMotriz != Fuerza.COMBUSTIBLE) {
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("FuerzaMotriz no es un valor válido para un Coche");
        }
    }

    /**
     * Método abstracto para garantizar que que cualquier subclase de Coche
     * disponga de este medio de consulta del tipo de combustible.
     *
     * @return El tipo de combustible del coche
     */
    public abstract TipoCombustible getTipoCombustible();

    /**
     * Método que establece una manera genérica de desplazar cualquier Coche.
     * Incrementará el número de kilómetros recorridos por el coche en la
     * cantidad indicada por el parámetro kilometros. No se va a permitir
     * desplazamientos negativos ni mayores que el valor expresado por
     * MAX_DESPLAZAMIENTO ({@value MAX_DESPLAZAMIENTO} km.) en ningún Coche. En caso de que el parámetro reibido
     * sea válido, se producirá el desplazamiento y se actualizará el total de
     * kilómetros recorridos por el Coche y el total de kilómetros sin repostar.
     *
     * @param kilometros La cantidad de kilómetros que el coche se va a
     * desplazar, es decir, la cantidad de km. que va a incrementarse el
     * contador de kilómetros totales y el contador de kilómetros sin repostar.
     *
     * @throws IllegalArgumentException Cuando la cantidad de kilómetros es
     * negativa, o excesiva para un Coche. Se estima que ningún coche va a poder
     * recorrer más del máximo de km. ({@value MAX_DESPLAZAMIENTO}) sin repostar.
     */
    
    @Override
    public void desplazar(double kilometros) throws IllegalArgumentException {
        if(kilometros < 0 || kilometros + this.kilometrosSinRepostar > MAX_DESPLAZAMIENTO){
            throw new IllegalArgumentException("Valor de kilometros invalido");
        }
        this.kilometrosSinRepostar += kilometros;
        this.kilometrosTotalesRecorridos += kilometros;
    }

    /**
     * Método que devuelve el total de kilómetros que se han recorrido desde el
     * último respostaje del Coche.
     *
     * @return El total de kilómetros que ha recorrido este Coche hasta el
     * momento.
     */
    
    @Override
    public double getKilometrosSinRepostar() {
        return this.kilometrosSinRepostar;
    }

    /**
     * Método que devuelve el total de kilómetros que se han recorrido desde la
     * fabricación del coche. Este método va a estar disponible para todos los
     * coches, sin posibilidad de redefinirlo.
     *
     * @return El total de kilómetros que ha recorrido este coche desde su
     * fabricación.
     *
     */
    
    @Override
    public final double getTotalKilometrosRecorridos() {
        return this.kilometrosTotalesRecorridos;
    }

    /**
     * Método que realiza el repostaje de un Coche. Dada la simplicidad del
     * modelo, simplemente pone a cero el contador de kilómetros sin repostar.
     */
    public void repostar() {
       this.kilometrosSinRepostar = 0;
    }

    /**
     * Método que devuelve la representación como String de un Coche.
     *
     * @return La representación como String de un Coche con el formato { Marca:
     * XXX; modelo: YYY; NS: ZZZ; Fuerza Motriz: WWW; Combustible: VVV; Km. sin
     * repostar: UUU; Kilometraje: TTT } donde XXX representa la marca, YYY
     * representa el modelo, ZZZ representa el número de serie, WWW representa
     * la fuerza motriz, VVV representa el tipo de combustible usado, UUU
     * representa los km. desde el último repostaje y TTT representa el total de
     * km. recorridos por el coche desde su fabricación.
     */
    
    @Override
    public String toString() {
        String toStringSuper = super.toString();
        return String.format("%s; Combustible: %s; Km. sin repostar: %.2f; Kilometraje: %.2f  }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.combustibleUsado, this.kilometrosSinRepostar, this.kilometrosTotalesRecorridos);
    }
}
