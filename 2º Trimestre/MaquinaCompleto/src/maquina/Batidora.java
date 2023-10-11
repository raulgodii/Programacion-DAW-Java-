package maquina;

import java.util.Arrays;

/**
 * Clase Batidora, que representa una batidora como tipo de máquina eléctrica
 * enchufable (pero no recargable).
 * <p>
 * Los objetos de esta clase modifican y detallan ciertos aspectos del
 * funcionamiento de una máquina eléctrica genérica, pero no contienen ningún
 * atributo adicional.
 *
 * @author Profesor PROG
 */
public class Batidora extends MaquinaElectrica implements Enchufable { // por hacer

    /**
     * Establece un valor para el voltaje por defecto de una Batidora.
     * Valor:{@value  DEFAULT_VOLTAJE_BATIDORA} voltios 230(v.)
     */
    public static int DEFAULT_VOLTAJE_BATIDORA = 230;
    
    /**
     * Establece un valor para la potencia por defecto de una Batidora.
     * Valor:{@value  DEFAULT_POTENCIA_BATIDORA} watios 700(W.)
     */
    public static double DEFAULT_POTENCIA_BATIDORA = 700;

    /**
     * Array que en cada fila establece los países que comparten un determinado
     * voltaje estándar. Cada fila por tanto, corresponderá a un voltaje
     * estándar diferente. Este listado se usa internamente por la clase, por lo
     * que no es necesario que sea público.
     *
     */
    private final static String[][] LISTA_PAISES_COMPATIBLES = {
        {"Japón", "Corea"}, //para 110 v.
        {"USA"}, //para 120 v.
        {"China"},//para 220 v.
        {"España", "Alemania", "Francia", "Bélgica"}//para 230 v.
    };

    /**
     * Crea un nuevo objeto de tipo Batidora, con los valores indicados como
     * parámetros para marca y modelo, con un voltaje estándar de
     * {@value  DEFAULT_VOLTAJE_BATIDORA} V. y una potencia estándar de
     * {@value  DEFAULT_POTENCIA_BATIDORA} W.
     *
     * @param marca La marca de la Batidora.
     * @param modelo El modelo de Batidora.
     * @param voltaje El voltaje a asignar a la Batidora. (Valores válidos:
     * Japón/Corea: 110v.; USA: 120v.; China: 220v.;
     * España/Alemania/Francia/Bélgica: 230v.)
     * @param potenciaElectrica. La potencia eléctrica a asignar a la
     * Batidora.(Valores válidos: 500/600/700/800/1000/1200/1500 w.) 110: Japón
     * y Corea. 120: USA. 220 China. 230: España, Alemania, Francia, Bélgica.
     *
     * @throws IllegalArgumentException Cuando se intenta asignar un voltaje o
     * una potencia no válidos para una Batidora.
     */
    public Batidora(String marca, String modelo, int voltaje, double potenciaElectrica) throws IllegalArgumentException{
        super(marca, modelo, DEFAULT_VOLTAJE_BATIDORA, DEFAULT_POTENCIA_BATIDORA);
        this.inicializarVoltaje(voltaje);
        this.inicializarPotenciaElectrica(potenciaElectrica);
    }

    /**
     * Método que establece el voltaje de la Batidora, garantizando que solo se
     * puedan asignar valores válidos: (Japón/Corea: 110v.; USA: 120v.; China:
     * 220v.; España/Alemania/Francia/Bélgica: 230v.)
     *
     * @param voltaje El voltaje a asignar. Valores válidos: => Japón/Corea:
     * 110v.; USA: 120v.; China: 220v.; España/Alemania/Francia/Bélgica: 230v.
     * @throws IllegalArgumentException. Cuando se intenta asignar un valor de
     * voltaje no permitido para una Batidora.
     */
    private void inicializarVoltaje(int voltaje) throws IllegalArgumentException{
        if(voltaje != 120 && voltaje != 220 && voltaje != 230){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en voltaje: " + voltaje + " (Valores válidos: Japón/Corea: 110v.; USA: 120v.; China: 220v.; España/Alemania/Francia/Bélgica: 230v.).");
        }
    }

    /**
     * Método que establece la potencia eléctrica de la Batidora, garantizando
     * que solo se puedan asignar valores válidos:
     * (500/600/700/800/1000/1200/1500 w.)
     *
     * @param potenciaElectrica. La potencia eléctrica a asignar.
     *
     * @throws IllegalArgumentException Si se intenta asignar un valor no válido
     * a la potencia eléctrica para una Batidora. (Valores válidos: =>
     * 500/600/700/800/1000/1200/1500 w.)
     */
    private void inicializarPotenciaElectrica(double potenciaElectrica) throws IllegalArgumentException{
        if(potenciaElectrica != 500 && potenciaElectrica != 600 && potenciaElectrica != 700 && potenciaElectrica != 800 && potenciaElectrica != 1000 && potenciaElectrica != 1200 && potenciaElectrica != 1500){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en potencia eléctrica: " + potenciaElectrica + ". (Valores válidos: 500/600/700/800/1000/1200/1500 w.). ");
        }
    }

    /**
     * Método que permite obtener un array de String con la lista de países en
     * los que el voltaje es compatible con el de la Batidora.
     *
     * @return El listado de países en los que el voltaje es compatible con el
     * de la Batidora, con formato de array de String.
     */
    @Override
    public String[] getPaisesCompatibles() {
        String[] paisesCompatibles;
        
        switch(this.voltaje){
            case 110:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[0];
                break;
            case 120:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[1];
                break;
            case 220:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[2];
                break;
            case 230:
                paisesCompatibles = LISTA_PAISES_COMPATIBLES[3];
                break;
            default:
                paisesCompatibles = new String[0];
                break;
        }
        
        return paisesCompatibles;
    }

    /**
     * Método que permite obtener la representación como String de una Batidora,
     * con el formato: { Marca: XXX; modelo: YYY; NS: ZZZ; Voltaje: WWW v.;
     * Potencia: VVVV W.; Países Compatibles: WWW} donde XXX representa la marca,
     * YYY representa el modelo, ZZZ representa el número de serie, WWW
     * representa el voltaje, VVV representa la potencia eléctrica y RRR
     * representa la lista de países compatibles con el voltaje de esta batidora
     *
     * @return La representación como String de una Batidora.
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
        return String.format("%s; Países Compatibles: %s }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                paisesCompatibles);
    }
}
