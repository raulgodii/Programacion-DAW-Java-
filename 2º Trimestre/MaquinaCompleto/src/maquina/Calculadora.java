package maquina;

/**
 * Clase que permite representar una Calculadora como un tipo especial de
 * Máquina Eléctrica que es recargable, con la particularidad de que en este
 * caso, su batería son pilas.
 *
 * A los atributos heredados de la clase MaquinaElectrica, añade el atributo
 * tipoPila, que tomará su valor de un tipo enumerado TipoPila, siendo válidos
 * para la Calculadora solo algunos de los valores que incluye ese enumerado
 * (pilas AA de 1,5 v. y pilas botón CR1025 de 3 v.
 *
 * @author Profesor PROG
 */
public class Calculadora extends MaquinaElectrica implements Recargable{

    /**
     * Tipo por defecto para la pila de la calculadora: AA_1_5V
     *
     */
    public static TipoPila DEFAULT_TIPO_PILA = TipoPila.AA_1_5V;

    /**
     * Por defecto la calculadora estará sin pilas, así que se considera que la
     * pila está agotada, ya que necesita que se le introduzca una nueva pila.
     * Valor por defecto: {@value DEFAULT_PILA_AGOTADA} true
     */
    public static boolean DEFAULT_PILA_AGOTADA = true;

    /**
     * Se establece que toda calculadora debe tener las pilas adecuadas para que
     * cuando se le repongan permitan su uso durante 100 horas. Valor:
     * {@value HORAS_DE_USO}
     */
    
    public static double HORAS_DE_USO = 100;

    /**
     * Atributo de instancia que indica qué tipo de pila usa esta calculadora
     * concreta.
     */
    private TipoPila tipoPila;

    /**
     * Atributo de instancia que indica cuántas horas de uso lleva la
     * calculadora desde el último cambio de pilas.
     */
    private double horas;

    /**
     * Atributo de instancia que toma el valor true cuando la calculadora no
     * tiene pila, o la tiene agotada (necesita una nueva pila), o false en otro
     * caso.
     */
    private boolean pilaAgotada;

    /**
     * Constructor que permite asignar, además de la marca, modelo y tipo de
     * pila de la calculadora, un "estado de la pila", indicando si lleva o no
     * una pila con carga para funcionar. Las horas de uso se inicializan a 0.
     *
     * @param marca La marca de la Calculadora.
     * @param modelo El modelo de la Calculadora.
     * @param tipoPila. El tipo de pila de la Calculadora (Valores válidos:
     * AA_1_5V y BOTON_CR1025_3V).
     * @param pilaAgotada true si la calculadora se crea sin pilas, o con pilas
     * agotadas, y false en caso contrario.
     *
     * @throws NullPointerException Cuando el tipo de pila vale null.
     * @throws IllegalArgumentException Cuando se intenta asignar un tipo de
     * pila no permitido para una calculadora. Suponemos que las calculadoras
     * solo usan pilas AA de voltio y medio (AA_1_5V) y pilas botón CR1025 de 3
     * voltios (BOTON_CR1025_3V).
     */
    public Calculadora(String marca, String modelo, TipoPila tipoPila, boolean pilaAgotada) throws NullPointerException, IllegalArgumentException {
        super(marca, modelo);
        if(tipoPila == null){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new NullPointerException("Error en tipo de pila: " + tipoPila + ". El tipo de pila no puede ser nulo.");
        }
        if(tipoPila != TipoPila.AA_1_5V && tipoPila != TipoPila.BOTON_CR1025_3V){
            Maquina.cantidadDeMaquinasFabricadas--;
            throw new IllegalArgumentException("Error en tipo de pila: " + tipoPila + ". Las calculadoras solo admiten pilas de tipo AA_1_5V y BOTON_CR1025_3V ");
        }
        
        this.tipoPila = tipoPila;
        this.pilaAgotada = pilaAgotada;
        if(this.pilaAgotada){
            this.horas = 100;
        } else{
            this.horas = 0;
        }
    }

    /**
     * Método Constructor que crea una nueva calculadora de una marca, modelo y
     * tipo de pila. El atributo pilaAgotada recibirá el valor por defecto. El
     * atributo horasDeUso recibirá el valor 0, puesto que la calculadora aún
     * está sin usar.
     *
     * @param marca La marca de la Calculadora.
     * @param modelo El modelo de la Calculadora.
     * @param tipoPila. El tipo de pila de la Calculadora (Valores válidos:
     * AA_1_5V y BOTON_CR1025_3V).
     *
     * @throws IllegalArgumentException Cuando se intenta asignar un tipo de
     * pila no permitido para una calculadora. Suponemos que las calculadoras
     * solo usan pilas AA de voltio y medio (AA_1_5V) y pilas botón CR1025 de 3
     * voltios (BOTON_CR1025_3V).
     * @throws NullPointerException Cuando se intenta asignar como tipo de pila
     * el valor null.
     */
    public Calculadora(String marca, String modelo, TipoPila tipoPila) throws NullPointerException, IllegalArgumentException{
       this(marca, modelo, tipoPila, DEFAULT_PILA_AGOTADA);
    }

    
    /**
     * Método que permite cargar una calculadora. Al tratarse de calculadoras
     * solo de pilas, la operación consistirá en reponer las pilas usadas con
     * pilas nuevas, actualizando convenientemente el estado de la pila,
     * asignando el valor false al atributo pilaAgotada y reiniciando las horas
     * de uso a cero.
     */
    @Override
    public void cargar() {
      this.pilaAgotada = false;
      this.horas = 0;
    }
    
    /**
     * Método que devuelve el número de horas de uso restantes para la
     * calculadora, tras haber sido usada el número de horas que se recibe como
     * parámetro.Se estima que todas las calculadoras vienen equipadas con las
     * pilas necesarias para un mmismo número de horas de funcionamiento,
     * expresado por la constante de clase HORAS_DE_USO.
     *
     * @param horas las horas de uso de la calculadora
     * @return Las horas restantes de uso de la calculadora, tras haber sido
     * usada un determinado número de horas. Si la batería retante de la
     * calculadora no es suficiente para funcionar todas esas horas, se usará
     * todas las horas que sea posible hasta agotarla.
     */
    @Override
    public double usarBateria(double horas) {
        double horasRestantes;
        if(this.horas+horas > HORAS_DE_USO){
            this.horas = HORAS_DE_USO;
        }else{
            this.horas += horas;
        }
        horasRestantes = HORAS_DE_USO - this.horas;
        return horasRestantes;
    }

    /**
     * Método que devuelve la representación como String de una Calculadora.
     *
     * @return La representación como String de una Calculadora con el formato {
     * Marca: XXX; modelo: YYY; NS: ZZZ; Voltaje: WWW v.; Potencia: VVVV W; Tipo
     * de pila: UUU; Horas de uso restantes: } donde XXX representa la marca,
     * YYY representa el modelo, ZZZ representa el número de serie, WWW
     * representa el voltaje, VVV representa la potencia eléctrica, UUU
     * representa el tipo de pila usado y RRR representa las horas de uso
     * restantes.
     */
    @Override
    public String toString() {
        String toStringSuper = super.toString();
        return String.format("%s; Tipo de pila: %s; Horas de uso restantes: %.2f }",
                toStringSuper.substring(0, toStringSuper.length() - 2),
                this.tipoPila, HORAS_DE_USO - this.horas);
    }
}
