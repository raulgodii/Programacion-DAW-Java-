/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arma;

// ------------------------------------------------------------

import java.time.LocalDate;

//                   Clase Revolver
// ------------------------------------------------------------
/**
 * Clase que representa un <strong>revolver</strong>.
 * <p>
 * Los objetos de esta clase permiten almacenar y gestionar información
 * sobre:</p>
 * <ul>
 * <li><strong>Número de serie</strong> del revolver. Este valor se establecerá
 * al crear el objeto revolver y ya no podrá cambiar. Es un valor
 * constante.</li>
 * <li><strong>Tambor</strong> del revolver: balas y casquillos que contiene en
 * cada momento el tambor del revolver, así como su disposición en el tambor y
 * qué orificio del tambor está en cada momento ante el percutor para recibir el
 * impacto al apretar el gatillo del arma.</li>
 * <li><strong>Disparos efectivos</strong> realizados por el revolver a lo largo
 * de su historia.</li>
 * </ul>
 * <p>
 * La clase también dispone de información general independiente de los objetos
 * concretos que se hayan creado. Es el caso de:</p>
 * <ul>
 * <li><strong>Número de disparos totales</strong> realizados por todos los
 * revólveres hasta el momento actual.</li>
 * <li><strong>Número de revólveres descargados</strong> en el momento
 * actual.</li>
 * </ul>

 * @author franc
 */
public class Revolver {
    // ------------------------------------------------------------
    //                 ATRIBUTOS ESTÁTICOS (de clase)
    // ------------------------------------------------------------
    // Atributos estáticos constantes públicos
    // (rangos y requisitos de los atributos de objeto)
    // Son públicos, disponibles para que cualquier código cliente pueda acceder a ellos
    // ---------------------------------------------------------------------------------
    
    /**
     * Minima capacidad del tambor permitida a la hora de crear un nuevo revolver:
     * (@value MINIMA_CAPACIDAD) balas
     */
    public static final int MINIMA_CAPACIDAD = 5;
    
    /**
     * Maxima capacidad del tambor permitida a la hora de crear un nuevo revolver:
     * (@value MAXIMA_CAPACIDAD) balas
     */
    public static final int MAXIMA_CAPACIDAD = 10;
    
    /**
     * Valor por omisión para la capacidad del tambor de un nuevo revolver:
     * (@value DEFAULT_CAPACIDAD) balas
     */
    public static final int DEFAULT_CAPACIDAD = 6;
    
    /**
     * Último número de serie que se puede crear por año: (@value MAXIMO_NUM_SERIE).
     */
    public static final int MAXIMO_NUM_SERIE = 99;
    
    // Atributos estáticos variables (privados).
    // Representan "estado" de la clase en general. No de objetos en particular
    // ------------------------------------------------------------------------
    private static int numTotalDisparos;                        // Total de disparos entre todos los revolveres
    private static int numRevolveresDescargados;                // Total de revolveres descargados
    private static int siguienteNumSerie = 0;                   // Número de serie para el siguiente
    private static int lastYear = LocalDate.now().getYear();    // Año actual cuando se cargue la clase en memoria
    
    
    // ------------------------------------------------------------
    //               ATRIBUTOS DE OBJETO (todos privados)
    // ------------------------------------------------------------
    // Atributos de objeto constantes durante la vida del objeto (desde que se crea objeto)
    // No hace falta declararlas como variables pues no van a cambiar una vez creado el objeto.
    // Representan "características inmutables" o "de naturaleza" del objeto.
    // (usamos notación "camelCase" aunque sean final, pues son "internos" y no los "ve" nadie más)
    // ------------------------------------------------------------------------
    private final String numSerie;      // Numero de serie del revolver formado XXXX-ZZ (XXXX: Año - ZZ: 00-99)
    private final Estado[] Tambor;      // Array que representa el tambor del revolver
    
    
    // Atributos de objeto variables
    // Representan el "estado" del objeto en un instante dado.
    // ------------------------------------------------------------------------
    private int posicion;       // Posición del tambor (que orificio del tambor está ante el percutor)
    private int disparos;       // Número de disparos efectivos realizados
    
    
    
    // ------------------------------------------------------------
    //                        CONSTRUCTORES
    // ------------------------------------------------------------
    /**
     * Constructor basado en la capacidad del tambor. Crea un nuevo objeto
     * <code>Revolver</code> con el tamaño de tambor indicado en el parámetro.
     *
     * @param capacidad capacidad del tambor (número de orificios)
     * 
     */
    public Revolver(int capacidad) throws IllegalArgumentException, IllegalStateException{
        int currentYear = LocalDate.now().getYear();
        
        if(capacidad < Revolver.MINIMA_CAPACIDAD || capacidad > Revolver.MAXIMA_CAPACIDAD){
            throw new IllegalArgumentException("Capacidad inválida: " + capacidad);
        } else if(Revolver.siguienteNumSerie > Revolver.MAXIMO_NUM_SERIE && currentYear == Revolver.lastYear){
            throw new IllegalStateException("Alcanzado el máximo de revolveres creados en el año actual " + currentYear + ": " + Revolver.MAXIMO_NUM_SERIE);
        } else {
            // No hay errores, se procede a crear el objeto
            
            this.Tambor = new Estado[capacidad];
            this.posicion = 0;
            for(int i = 0; i<this.Tambor.length; i++){
                this.Tambor[i] = Estado.VACIO;
            }
            
            // Comprobamos si hemos cambiado de año respecto al último objeto creado
            if(currentYear > Revolver.lastYear){
                Revolver.siguienteNumSerie = 0;
            }
            
            // Construimos el número de serie
            this.numSerie = String.format("%4d-%2d", currentYear, Revolver.siguienteNumSerie);
            
            // Actualizamos los atributos de la clase
            Revolver.numRevolveresDescargados ++;
            Revolver.siguienteNumSerie++;
        }
    }
    
    /**
     * Constructor copia. Crea un nuevo objeto <code>Revolver</code> con las
     * mismas características que el que se ha pasado como parámetro. El nuevo
     * objeto tendrá el tambor vacío y un nuevo número de serie.
     *
     * @param r objeto que se va a usar para realizar la copia
     * @throws IllegalStateException Si se ha superado la cantidad maxima de numeros de serie para el año actual
     */
    public Revolver(Revolver r) throws IllegalStateException{
        this(r.getCapacidad());
    }
    
    
    /**
     * Constructor sin parámetros. Crea un nuevo objeto <code>Revolver</code>
     * con el tamaño de tambor por omisión. El tamaño por omisión para el tambor
     * es de {@value DEFAULT_CAPACIDAD} balas.
     * @throws IllegalStateException Si se ha superado la cantidad maxima de numeros de serie para el año actual
     */
    public Revolver() throws IllegalStateException{
        this(Revolver.DEFAULT_CAPACIDAD);
    }
    
    
    // ------------------------------------------------------------
    //          MÉTODOS "FÁBRICA" O PSEUDOCONSTRUCTORES
    // ------------------------------------------------------------
    /**
     * Método "fábrica" creador de un revolver ya cargado. Crea un nuevo objeto
     * <code>Revolver</code> con el tamaño de tambor indicado en el parámetro y
     * cargado de balas.
     *
     * @param capacidad capacidad del tambor
     * @return revólver con tambor del tamaño indicado y cargado
     * @throws IllegalStateException Si se ha superado la cantidad maxima de numeros de serie para el año actual
     */
    public static Revolver crearRevolverCargado(int capacidad) throws IllegalArgumentException, IllegalStateException{
        Revolver r =  new Revolver(capacidad);
        r.cargar();
        return r;
    }
    
    /**
     * Método "fábrica" creador de un revolver ya cargado con el tamaño del
     * tambor por omisión. Crea un nuevo objeto <code>Revolver</code> con el
     * tamaño de tambor por omisión y cargado de balas. El tamaño por omisión
     * para el tambor es de {@value DEFAULT_CAPACIDAD} balas.
     *
     * @return revólver con tambor del tamaño por omisión y cargado
     * @throws IllegalStateException Si se ha superado la cantidad maxima de numeros de serie para el año actual
     */
    public static Revolver crearRevolverCargado() throws IllegalStateException{
        return crearRevolverCargado(Revolver.DEFAULT_CAPACIDAD);
    }
    
    
    
    // ------------------------------------------------------------
    //                 Getters:  Métodos GET
    // ------------------------------------------------------------
    /**
     * Obtiene el número de serie del revólver.
     *
     * @return número de serie del revólver
     */
    public String getNumSerie(){
        return this.numSerie;
    }
    
    
    /**
     * Obtiene la capacidad del tambor del revólver.
     *
     * @return capacidad del tambor del revólver
     */
    public int getCapacidad(){
        return this.Tambor.length;
    }
    
    
    /**
     * Obtiene la cantidad de balas que contiene actualmente el tambor del
     * revólver. Los casquillos no cuentan.
     *
     * @return cantidad de balas que contiene en ese momento el tambor del
     * revólver
     */
    public int getNumBalas(){
        int resultado = 0;
        
        for(Estado hueco : this.Tambor){
            resultado += (hueco == Estado.BALA ? 1:0);
        }
        
        /** Es lo mismo:
        for (int i = 0; i<this.Tambor.length; i++){
            if(this.Tambor[i]==Estado.BALA){
                resultado++;
            }
        }
        */
        
        return resultado;
    }
    
    
    /**
     * Indica si el revólver se encuentra totalmente descargado.
     *
     * @return si el revolver no contiene ninguna bala
     */
    public boolean isDescargado(){
        return this.getNumBalas() == 0;
    }
    
    
    /**
     * Devuelve la cantidad de disparos efectivos realizados por el revolver
     * desde que se creó.
     *
     * @return cantidad de disparos efectivos realizados por el revolver desde
     * que se creó
     */
    public int getNumDisparos(){
        return this.disparos;
    }
    
    
    /**
     * Devuelve la cantidad de disparos efectivos realizados por todos los
     * revólveres hasta el momento.
     *
     * @return cantidad de disparos efectivos realizados por todos los
     * revólveres hasta el momento
     */
    public static int getNumTotalDisparos(){
        return Revolver.numTotalDisparos;
    }
    
    
    /**
     * Devuelve la cantidad de revólveres descargados que hay en el momento
     * actual.
     *
     * @return cantidad de revólveres descargados que hay en el momento actual
     */
    public static int getNumRevolveresDescargados(){
        return Revolver.numRevolveresDescargados;
    }
    
    
    
    
    
    
    // ------------------------------------------------------------
    //          MÉTODOS "SET"  (opcionales)
    // ------------------------------------------------------------
    
    
    
    // ------------------------------------------------------------
    //                 Métodos de "ACCIÓN"
    // ------------------------------------------------------------
    /**
     * Carga el revólver con una determinada cantidad de balas. Se va
     * recorriendo el tambor desde su posición 0 y se van introduciendo
     * proyectiles en los orificios que aún no tengan bala (tanto si hay hueco
     * como si hay casquillo). Si en algún orificio ya hay una bala aún sin
     * utilizar, se pasa al siguiente orificio. Si sobran balas, simplemente no
     * se tienen en cuenta. Se devolverá el número de balas que efectivamente se
     * han introducido en el tambor.
     *
     * @param numBalas Número de balas con las que se quiere cargar el revólver
     * @return cantidad de balas que se han podido introducir efectivamente en
     * el tambor
     * @throws IllegalArgumentException
     */
    public int cargar(int numBalas) throws IllegalArgumentException{
        int cargaEfectiva;
        int balasCargadas = 0;
        boolean estabaDescargado = this.isDescargado();
        
        if(numBalas<0){
            throw new IllegalArgumentException("Cantidad de balas invalida: " + numBalas);
        }
        
        // El máximo de carga es la capacidad del tambor
        cargaEfectiva = Math.min(numBalas, this.getCapacidad());
        
        //Recorremos el tambor y lo vamos cargando
        for(int i=0; i<this.Tambor.length && cargaEfectiva>0; i++){
            // Si el orificio está vacío o tiene casquillo
            if(this.Tambor[i]==Estado.VACIO || this.Tambor[i]==Estado.CASQUILLO){
                this.Tambor[i] = Estado.BALA;
                balasCargadas++;
                cargaEfectiva--;
            }
        }
        
        //Actualizamos atributos clase
        if(estabaDescargado && !this.isDescargado()){
            Revolver.numRevolveresDescargados--;
        }
        
        return balasCargadas;
    }
    
    
    /**
     * Carga el tambor del revólver completamente. Se va recorriendo el tambor
     * desde su posición 0 y se van introduciendo proyectiles en los orificios
     * que aún no tengan bala (tanto si hay hueco como si hay casquillo). Si en
     * algún orificio ya hay una bala aún sin utilizar, se pasa al siguiente
     * orificio. Se devolverá el número de balas que efectivamente se han
     * introducido en el tambor.
     *
     * @return cantidad de balas que se han podido introducir efectivamente en
     * el tambor
     */
    public int cargar(){
        return cargar(this.getCapacidad());
    }
    
    
    /**
     * Descarga el tambor del revólver completamente. Se vacía completamente el
     * tambor del revólver, tanto de casquillos como de proyectiles sin
     * utilizar.
     *
     * @return cantidad de balas (no casquillos) que había en el tambor
     */
    public int descargar(){
        int balasDescargadas = 0;
        int totalDescargas = 0;
        boolean estabaDescargado = this.isDescargado();
        
        // Recorremos el tambor y retiramos las balas y los casquillos
        for(int i=0; i<this.Tambor.length; i++){
            // Si el orificio tiene bala o casquillo
            if(this.Tambor[i]==Estado.BALA || this.Tambor[i]==Estado.CASQUILLO){
                if(this.Tambor[i] == Estado.BALA){
                    balasDescargadas++;
                }
                totalDescargas++;
                this.Tambor[i] = Estado.VACIO;
            }
        }
        
        // Actualizamos los atributos de clase
        if(!estabaDescargado && this.isDescargado()){
            Revolver.numRevolveresDescargados++;
        } 
        return balasDescargadas;
    }
    
    
    /**
     * Se dispara el revólver pulsando el gatillo. Si el orificio del tambor que
     * había en ese momento ante el percutor contenía un proyectil completo, se
     * producirá un disparo efectivo. Si el orificio estaba vacío o contenía un
     * casquillo, no se producirá un disparo efectivo. En cuaqluier caso el
     * tambor girará de izquierda a derecha una posición y se colocará el
     * siguiente orificio ante el percutor.
     *
     * @return si el disparo ha sido o no efectivo
     */
    public boolean disparar(){
        boolean disparoEfectivo = this.Tambor[this.posicion] == Estado.BALA;
        boolean estabaDescargado = this.isDescargado();
        
        if(disparoEfectivo){
            this.Tambor[this.posicion] = Estado.CASQUILLO;
            this.posicion = (this.posicion + 1) % this.getCapacidad(); // Recorre el bucle infinitamente
            // this.posicion++;
            // this.posicion = ((this.posicion+1) == this.getCapacidad() ? 0 : this.posicion+1);
            this.disparos++; //Actualiza la variable de objeto
            Revolver.numTotalDisparos++; //Actualiza la variable de clase
        }
        
        if(!estabaDescargado && this.isDescargado()){
            Revolver.numRevolveresDescargados++;
        }
        
        return disparoEfectivo;
    }
    
    
    
    /**
     * Devuelve una cadena que representa el estado de un revolver. El resultado
     * devuelto representará el contenido del tambor y tendrá la siguiente
     * estructura:
     * <ol>
     * <li>un inicio de bloque o llave (carácter '{');</li>
     * <li>un carácter de tipo '_', 'X' o 'x' por cada orificio del tambor:
     * <ul>
     * <li>si el orificio está vacío aparecerá el carácter '_' (guion bajo o
     * "subrayado");</li>
     * <li>si el orificio contiene una bala completa (no disparada), aparecerá
     * el carácter 'X' (equis mayúscula);</li>
     * <li>si el orificio contiene un casquillo (bala disparada), aparecerá el
     * carácter 'x' (equis minúscula);</li>
     * </ul></li>
     * <li>un fin de bloque o llave (carácter '}').</li>
     * </ol>
     * <p>
     * Además, el orificio que se encuentre en ese momento delante del percutor
     * deberá aparecerá encerrado entre corchetes (caracteres '[' y ']'). Así
     * quedará claro el orificio sobre el que va a impactar el percutor la
     * próxima vez que se apriete el gatillo al disparar.</p>
     * <p>
     * Aquí tienes un posible ejemplo de salida:
     *  <code>{ x [X] X  X  _  _  _  _ }</code>, donde observamos que: </p>
     * <ul>
     * <li>se trata de un revólver con un tambor de capacidad para ocho
     * proyectiles;</li>
     * <li>en el primer orificio hay un casquillo;</li>
     * <li>en los orificios segundo, tercero y cuarto hay balas que aún no han
     * sido disparadas;</li>
     * <li>los orificios del quinto al octavo se encuentran aún vacíos;</li>
     * <li>el percutor se encuentra sobre el segundo orificio.</li>
     * </ul>
     *
     *
     * @return una cadena que representa el estado del tambor del revolver
     */
    @Override
    public String toString(){
        return this.estadoTambor();
    }
    
    // ------------------------------------------------------------
    //                    MÉTODOS PRIVADOS
    // ------------------------------------------------------------
    /**
     * Este es el método que realmente genera la cadena de estado del tambor.
     *
     * @return cadena con el estado del tambor
     */
    private String estadoTambor() {
        StringBuilder stringTambor = new StringBuilder("{");
        for(int i=0; i<this.Tambor.length; i++){
            String orificio = "";
            switch(this.Tambor[i]){
                case BALA:
                    orificio = "X";
                    break;
                case CASQUILLO:
                    orificio = "x";
                    break;
                case VACIO:
                    orificio = "_";
                    break;
            }
            
            if(i == this.posicion){
                stringTambor.append("{").append(orificio).append("}");
            } else {
                stringTambor.append(" ").append(orificio).append(" ");
            }
        }
        stringTambor.append("}");
        return stringTambor.toString();
    }
    
    /**
     * Enum privado con los distintos estados de un orificio del tambor.
     */

    private enum Estado {
        /**
         * Vacio
         */
        VACIO,
        /**
         * Bala
         */
        BALA,
        /**
         * Casquillo
         */
        CASQUILLO;
    }    
    
}
