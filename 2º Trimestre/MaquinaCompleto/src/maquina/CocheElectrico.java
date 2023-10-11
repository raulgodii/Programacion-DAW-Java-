package maquina;

import java.util.Arrays;

/**
 * Clase que modela un tipo especial de coche: un coche eléctrico. Con capacidad
 * de ser enchufado, con una batería recargable, y con capacidad para
 * desplazarse.
 *
 * Añade a la clase Coche de la que hereda, los atributos siguientes:
 * <ul>
 * <li> Voltaje de la batería. Valores válidos serán: 12V en España y Portugal,
 * 24V en Francia y Bélgica y 48V en Inglaterra.</li>
 * <li> Capacidad máxima de la batería. Valores válidos (en kWh) serán: 35, 50,
 * 75, 100, 125, 150, 200. </li>
 * <li> Carga efectiva. La cantidad de carga que se estima que queda en el Coche
 * eléctrico. En futuras versiones, la carga efectiva se calculará en función de
 * los km recorridos y de la autonomía del vehículo. </li>
 * </ul>
 *
 * @author Profesor PROG
 */
public final class CocheElectrico  extends Coche implements Enchufable, Desplazable{ //no va a haber subclases

    /**
     * Voltaje por defecto para la batería de un Coche Eléctrico: {@value DEFAULT_VOLTAJE_BATERIA
     * } voltios. 12(v)
     */
    public final static int DEFAULT_VOLTAJE_BATERIA = 12;

    /**
     * Capacidad máxima por defecto de la batería de un Coche Eléctrico:
     * {@value DEFAULT_CAPACIDAD_MAXIMA_BATERIA} KiloWatios-Hora. 100(kWh)
     */
    public final static int DEFAULT_CAPACIDAD_MAXIMA_BATERIA = 100;

    /**
     * Potencia por defecto de un Coche Eléctrico: {@value DEFAULT_POTENCIA}
     * kiloWatios  100(kW)
     */
    public final static double DEFAULT_POTENCIA = 100;

    /**
     * Carga efectiva por defecto para un Coche Eléctrico. Se entiende que la
     * carga efectiva va a ser siempre la mitad de la capacidad máxima de la
     * batería, por lo que los coches saldrán de fábrica siempre con la batería
     * a media carga. Valor: {@value DEFAULT_CARGA_EFECTIVA}
     * KiloWatios-Hora.(kWh)
     */
    public final static double DEFAULT_CARGA_EFECTIVA = DEFAULT_CAPACIDAD_MAXIMA_BATERIA/2;
    
    /**
     * La autonomía mínima permitida que se podrá asignar a un Coche Eléctrico.
     * Valor: {@value MIN_AUTONOMIA} kilómetros 300(km).
     */
    public final static double MIN_AUTONOMIA = 300;

    /**
     * La autonomía máxima que se podrá asignar a un Coche Eléctrico, con las
     * limitaciones técnicas actuales. Valor:{@value MAX_AUTONOMIA} 600 kilómetros
     * (km).
     */
    public final static double MAX_AUTONOMIA = 600;
    
    /**
     * Array que en cada fila establece los países que comparten un determinado
     * voltaje estándar. Cada fila por tanto, corresponderá a un voltaje
     * estándar diferente. Este listado se usa internamente por la clase, por lo
     * que no es necesario que sea público.
     *
     */
    private final static String[][] LISTA_PAISES_COMPATIBLES = {
        {"España", "Portugal"}, // 12 v.
        {"Francia", "Bélgica"}, // 24 v.
        {"Inglaterra"} // 48 v.
    };

    /**
     * Voltaje de la batería de un Coche eléctrico. Valores válidos: 12V en
     * España y Portugal, 24V en Francia y Bélgica y 48V en Inglaterra.
     */
    private int voltajeBateria;

    /**
     * Capacidad Máxima de la batería de un Coche Eléctrico. Una vez asignada
     * esta capacidad al fabricar, no se podrá modificar. Valores válidos (en
     * kWh): 35.0, 50.0, 75.0, 100.0, 125.0, 150.0, 200.0.
     */
    private double capacidadMaximaBateria;

    /**
     * Carga efectiva de la batería de un Coche Eléctrico. Representa la carga
     * que el coche tendrá en cada momento. A la salida de fábrica será siempre
     * la mitad de la capacidad máxima de su batería.
     */
    private double cargaEfectiva;

    /**
     * La autonomía del Coche Eléctrico representa el total de km que se estima
     * que puede recorrer con una carga completa de la batería a su capacidad
     * máxima sin parar a repostar (recargar la batería). Por requerimientos
     * técnicos, no va a poder ,ser menos que {@value MIN_AUTONOMIA} kilómetros
     * ni mayor de {@value MAX_AUTONOMIA} kilómetros
     */
    private double autonomia;

    /**
     * Este constructor crea un coche eléctrico a partir de los parámetros
     * marca, modelo y fuerzaMotriz que recibe, y asigna a voltaje de la batería
     * y a capacidad de la batería los valores por defecto. Para la autonomía se
     * usa como valor por defecto el valor mínimo permitido. 
     *
     * @param marca La marca del Coche Eléctrico
     * @param modelo El modelo de Coche Eléctrico
     * @param fuerzaMotriz La fuerza motriz del coche eléctricom, que solo podrá
     * ser ELECTRICIDAD.
     *
     * @throws IllegalArgumentException Cuando se intenta asignar como fuerza
     * motriz un valor diferente de ELECTRICIDAD.
     */
    public CocheElectrico(String marca, String modelo, Fuerza fuerzaMotriz) throws IllegalArgumentException{
        super(marca, modelo, fuerzaMotriz);
        if(fuerzaMotriz != Fuerza.ELECTRICIDAD){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en fuerza Motriz: " + fuerzaMotriz + ". Para un coche eléctrico debe ser necesariamente ELECTRICIDAD.");
        }
        this.combustibleUsado = TipoCombustible.ELECTRICIDAD;
        this.autonomia = MIN_AUTONOMIA;
        this.capacidadMaximaBateria = DEFAULT_CAPACIDAD_MAXIMA_BATERIA;
        this.voltajeBateria = DEFAULT_VOLTAJE_BATERIA;
    }

    /**
     * Constructor público de la clase CocheElectrico.Crea un nuevo Coche
     * Eléctrico a partir de los datos recibidos como parámetros (marca, modelo,
     * fuerza Motriz, voltaje de la batería y capacidad máxima de la batería).
     * Como capacidad efectiva de carga, a la salida de fábrica, la batería
     * siempre irá a media carga respecto a su capacidad máxima
     *
     * @param marca La marca del Coche Eléctrico.
     * @param modelo El modelo del Coche Eléctrico.
     * @param fuerzaMotriz La fuerza motriz del Coche Eléctrico
     * @param voltajeBateria El voltaje de la batería del Coche Eléctrico.
     * @param capacidadMaximaBateria La capacidad máxima de la batería del Coche
     * Eléctrico.
     * @param autonomia La autonomía de este coche eléctrico. (Km que se pueden
     * recorrer sin repostar con una carga máxima de su batería)
     *
     * @throws NullPointerException Cuando el atributo fuerza motriz es
     * <code>null</code>
     * @throws IllegalArgumentException Cuando se intenta asignar un valor no
     * permitido como voltaje de la batería o como capacidad máxima o como
     * autonomía.
     */
    public CocheElectrico(String marca, String modelo, Fuerza fuerzaMotriz, int voltajeBateria, double capacidadMaximaBateria, double autonomia) throws NullPointerException, IllegalArgumentException{
        super(marca, modelo, fuerzaMotriz);
        if(fuerzaMotriz != Fuerza.ELECTRICIDAD){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en fuerza Motriz: " + fuerzaMotriz + ". Para un coche eléctrico debe ser necesariamente ELECTRICIDAD.");
        }
        this.inicializarVoltajeBateria(voltajeBateria);
        this.inicializarCapacidadMaximaBateria(capacidadMaximaBateria);
        this.inicializarAutonomia(autonomia);
        this.cargaEfectiva = capacidadMaximaBateria/2;
        this.combustibleUsado = TipoCombustible.ELECTRICIDAD;
    }

    /**
     * Método de consulta que devuelve el voltaje de la batería del Coche
     * Eléctrico.
     *
     * @return el voltaje de la batería del Coche Eléctrico.
     */
    
    @Override
    public int getVoltaje() {
        return this.voltajeBateria;
    }

    /**
     * Método para consultar la capacidad máxima de la Batería del Coche
     * Eléctrico.
     *
     * @return La capacidad máxima de la Batería del Coche Eléctrico.
     */
    public double getCapacidadMaximaBateria() {
        return this.capacidadMaximaBateria;
    }

    /**
     * Método para establecer el voltaje de la batería del Coche Eléctrico. Este
     * método se usará para asignar valores correctos desde el constructor, por
     * lo que es privado de la clase, ya que no es conveniente que otras clases
     * puedan modificar el voltaje de un Coche Eléctrico después de fabricado.
     *
     * @param voltajeBateria
     *
     * @throws IllegalArgumentException Cuando se intenta asignar un valor no
     * permitido al voltaje de la batería de un Coche Eléctrico.
     */
    private void inicializarVoltajeBateria(int voltajeBateria) throws IllegalArgumentException{
        if(voltajeBateria != 12 && voltajeBateria != 24 && voltajeBateria != 48){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en voltaje: " + voltajeBateria + ". v. Valores válidos=> 12 v. en España y Portugal, 24 v. en Francia y Bélgica  y 48 v. en Inglaterra.");
        } else {
            this.voltajeBateria = voltajeBateria;
        }
    }

    /**
     * Método para establecer la capacidad máxima de la batería del Coche
     * Eléctrico. Este método se usará para asignar valores correctos desde el
     * constructor, por lo que es privado de la clase, ya que no es conveniente
     * que otras clases puedan modificar la capacidad máxima de un Coche
     * Eléctrico después de fabricado.
     *
     * @param capacidadMaximaBateria
     *
     * @throws IllegalArgumentException Cuando se intenta asignar un valor no
     * permitido al voltaje de la batería de un Coche Eléctrico.
     * Valores válidos (en kWh) serán: 35.0, 50.0, 75.0, 100.0, 125.0, 150.0 y 200.0
     */
    private void inicializarCapacidadMaximaBateria(double capacidadMaximaBateria) throws IllegalArgumentException{
        if(capacidadMaximaBateria != 35 && capacidadMaximaBateria != 50 && capacidadMaximaBateria != 75 && capacidadMaximaBateria != 100 && capacidadMaximaBateria != 125 && capacidadMaximaBateria != 150 && capacidadMaximaBateria != 200){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en capacidad de batería:    " + capacidadMaximaBateria +" Valores válidos (en kWh)=> 35.0, 50.0, 75.0, 100.0, 125.0, 150.0, 200.0: ");
        } else {
            this.capacidadMaximaBateria = capacidadMaximaBateria;
        }
    }

    /**
     * Método para establecer la autonomía del CocheEléctrico. Este método se
     * usará para asignar valores correctos desde el constructor, por lo que es
     * privado de la clase, ya que no es conveniente que otras clases puedan
     * modificar la autonomía de un Coche Eléctrico después de fabricado.
     *
     * @param autonomia La autonomía o cantidad de kms que el Coche Eléctrico
     * podrá recorrer con una carga máxima de su batería
     *
     * @throws IllegalArgumentException Cuando se intenta asignar un valor no
     * permitido a la autonomía de un Coche Eléctrico.
     */
    private void inicializarAutonomia(double autonomia) throws IllegalArgumentException{
        if(autonomia < MIN_AUTONOMIA || autonomia > MAX_AUTONOMIA){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en autonomía del coche eléctrico: " + autonomia + ". Valores válidos=> entre 300,00 y  600,00 kms: ");
        } else {
            this.autonomia = autonomia;
        }
    }

    /**
     * Método que permite consultar la carga efectiva restante en la batería del
     * coche eléctrico
     *
     * @return La carga efectiva de la batería en un momento dado.(en kWh)
     */
    public double getCargaEfectiva() {
        return this.cargaEfectiva;
    }

    /**
     * Método de consulta que permite obtener el tipo de combustible usado por
     * un Coche Eléctrico.
     *
     * @return El tipo de combustible usado.
     */
   
    @Override
    public TipoCombustible getTipoCombustible() {
        return this.combustibleUsado;
    }

    /**
     * Método para obtener un array de Strings con el listado de países para los
     * que el voltaje es compatible con el de este Coche eléctrico.
     *
     * @return Devuelve un array de Strings con los nombres de los países
     * compatibles según el voltaje de la batería del Coche Eléctrico.
     */
    
    @Override
    public String[] getPaisesCompatibles() {  
        /*{"España", "Portugal"}, // 12 v.
        {"Francia", "Bélgica"}, // 24 v.
        {"Inglaterra"} // 48 v.*/
        String [] paisesCompatibles;
        
        switch(this.voltajeBateria){
            case 12:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[0];
                break;
            case 24:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[1];
                break;
            case 48:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[2];
                break;
            default:
                paisesCompatibles = new String[0];
                break;
        }
        
        return paisesCompatibles;
    }

    /**
     * Método que permite cargar la batería de un Coche Eléctrico, estableciendo
     * su carga efectiva al valor de la capacidad máxima de su batería. Tras
     * cargar la batería, los kilómetros sin repostar se inicializan a 0.
     */
    
    public void cargar() {
        this.cargaEfectiva = DEFAULT_CAPACIDAD_MAXIMA_BATERIA;
        this.kilometrosSinRepostar = 0;
    }

    /**
     * Método que calcula y devuelve la carga de batería que quedaría disponible
     * tras realizar un desplazamiento de la cantidad de kilómetros recibida
     * como parámetro
     *
     * @param kilometros Los kilómetros que se quieren recorrer y para los que
     * hay que calcular si la carga de batería efectiva es sufciente.
     * @return La carga de batería que quedaría disponible tras realizar ese
     * desplazamiento.
     */
    
    public double usarBateria(double kilometros) {
        double kmRestantes;
        double bateriaRestante = this.cargaEfectiva;
        double potenciaGastada;
        if(this.kilometrosSinRepostar+kilometros > this.autonomia){
            kmRestantes = -1;
        } else {
            kmRestantes = (this.autonomia)-(this.kilometrosSinRepostar+kilometros);
        }
        
        potenciaGastada = (kmRestantes*this.capacidadMaximaBateria)/this.autonomia;
        
        if(bateriaRestante-potenciaGastada < 0){
            bateriaRestante = 0;
        } else {
            bateriaRestante -= potenciaGastada;
        }
        
        return bateriaRestante;
    }

    /**
     * Método que comprueba en primer lugar si la carga efectiva de la batería
     * permitiría un desplazamiento de la cantidad de kilómetros indicados como
     * parámetro. Si hay batería suficiente, desplaza el coche esos kilómetros,
     * actualizando el total de kilómetros recorridos, los kilómetros recorridos
     * sin repostar, y la carga efectiva restante en la batería
     *
     * @param kilometros Los kilómetros que se quiere desplazar el coche.
     * @throws IllegalArgumentException Cuando no hay batería suficiente para
     * recorrer esos kilómetros o cuando el parámetro kilómetros es negativo, o
     * bien superior a la distancia máxima que puede recorrer un coche sin
     * repostar.
     */
    @Override
    public void desplazar(double kilometros) throws IllegalArgumentException{
       if(kilometros < 0 || kilometros > this.autonomia || this.usarBateria(kilometros)==-1){
           throw new IllegalArgumentException("Kilometros introducidos invalidos");
       }
       this.cargaEfectiva = this.usarBateria(kilometros);
       this.kilometrosSinRepostar += kilometros;
       this.kilometrosTotalesRecorridos += kilometros;
    }

    /**
     * Método que devuelve la representación como String de un Coche Eléctrico
     * con el formato
     *
     * @return La representación como String de un Coche Eléctrico con el
     * formato { Marca: XXX; modelo: YYY; NS: ZZZ; Fuerza Motriz: WWW;
     * Combustible: VVV; Km. sin repostar: UUU; Kilometraje: TTT; Voltaje: OOO;
     * Capacidad batería: PPP; Carga efectiva: QQQ; Países compatibles: RRR }
     * donde XXX representa la marca, YYY representa el modelo, ZZZ representa
     * el número de serie, WWW representa la fuerza motriz, VVV representa el
     * tipo de combustible usado, UUU representa los km. desde el último
     * respotaje, TTT representa el total de km. recorridos por el coche desde
     * su fabricación, OOO representa el voltaje de la batería, PPP representa
     * la capacidad de la batería, QQQ representa la autonomía del coche, RRR la
     * carga efectiva en ese momento y SSS la lista de países compatibles.
     */
    
    @Override
    public String toString() {
       String toStringSuper = super.toString();
       String [] paises = this.getPaisesCompatibles();
       String paisesCompatibles = "[";
       for(int i=0; i<paises.length; i++){
           if(i==paises.length-1){
               paisesCompatibles = paisesCompatibles.concat(paises[i]);
           } else {
               paisesCompatibles = paisesCompatibles.concat(paises[i] + ", ");
           }
       }
       paisesCompatibles = paisesCompatibles.concat("]");
        return String.format("%s; Voltaje: %d v; Capacidad batería: %.2f w; Autonomia: %.2f km; Carga efectiva: %.2f w; km Países compatibles: %s }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.voltajeBateria, this.capacidadMaximaBateria, this.autonomia, this.cargaEfectiva, paisesCompatibles);
    }
}
