/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arma;

/**
 *
 * @author franc
 */
public class Utilidades {
    
    /**
     * Muestra por pantalla los atributos públicos de la clase <code>Revolver</code>.
     */
    public static void consultaAtributosPublicos(){
        System.out.println(" --> Consulta de los atributos públicos");
        System.out.println("Capacidad máxima del tambor: " + Revolver.MAXIMA_CAPACIDAD);
        System.out.println("Capacidad mínima del tambor: " + Revolver.MINIMA_CAPACIDAD);
        System.out.println("Capacidad por omisión del tambor: " + Revolver.DEFAULT_CAPACIDAD);
        System.out.println("Máximo número de serie admitido por año: " + Revolver.MAXIMO_NUM_SERIE);
    }
    
    /**
     * Muestra por pantalla los atributos privados de la clase <code>Revolver</code>.
     */
    public static void consultaInformacionClase(){
        System.out.println(" --> Consulta de los atributos privados");
        System.out.println("Cantidad total de disparos realizados por todos los revolveres: " + Revolver.getNumTotalDisparos());
        System.out.println("Cantidad total de revólvers descargados: " + Revolver.getNumRevolveresDescargados());
    }
    
    /**
     * Muestra por pantalla el estado del objeto de la clase <code>Revolver</code>.
     * @param r objeto revólver cuyo estado se desea mostrar por pantalla
     */
    public static void consultaInformacionObjeto(Revolver r){
        System.out.println(" --> Consulta de información de objeto Revolver");
        System.out.println("Número de serie: " + r.getNumSerie());
        System.out.println("Capacidad del tambor: " + r.getNumSerie());
        System.out.println("Número de balas cargadas: " + r.getNumBalas());
        System.out.println("Número total de disparos: " + r.getNumDisparos());
        System.out.println("¿Está descargado?: " + (r.isDescargado() ? "Si":"No"));
        System.out.println("Estado actual del tambor: " + r.toString());
    }
    /**
     * Carga un revólver con un número de balas usando el método <code>cargar</code> de la clase <code>Revolver</code>.
     * @param r objeto de la clase Revolver que se va a cargar
     * @param numBalas número de balas con las que se va a cargar
     */
    public static void cargarRevolver(Revolver r, int numBalas){
        int balas;
        System.out.println(" --> Cargamos el arma con " + numBalas);
        System.out.println("Estado del Revolver antes de cargar: " + r.toString());
        System.out.println("Cargando...");
        
        balas = r.cargar(numBalas);
        System.out.println("Cargado. Balas cargadas efectivamente: " + balas);
        System.out.println("Estado del Revolver después de cargar: " + r.toString());
    }
    
    /**
     * Carga un revólver ccompletamente usando el método <code>cargar</code> de la clase <code>Revolver</code>.
     * @param r objeto de la clase Revolver que se va a cargar
     */
    public static void cargarRevolverCompletamente(Revolver r){
        int balas;
        System.out.println(" --> Cargamos el arma completamente");
        System.out.println("Estado del Revolver antes de cargar: " + r.toString());
        System.out.println("Cargando...");
        
        balas = r.cargar();
        System.out.println("Cargado. Balas cargadas efectivamente: " + balas);
        System.out.println("Estado del Revolver después de cargar: " + r.toString());
    }
    
    /**
     * Dispara un revólver una cantidad de veces indicada por parámetro usando el método <code>disparar</code> de la clae <code>Revolve</code>
     * @param r objeto Revolver que se va a disparar
     * @param numDisparos cantidad dd disparos que se desean realizar
     */
    public static void dispararRevolver(Revolver r, int numDisparos){
        boolean disparoEfectivo;
        
        System.out.println(" --> Disparamos el arma " + numDisparos + " ve" + (numDisparos==1 ? "z" : "ces"));
        for(int i=1; i<=numDisparos; i++){
            System.out.println("\nDisparo " + i + ":");
            System.out.println("Estado del revolver antes de disparar: " + r.toString());
            disparoEfectivo = r.disparar();
            System.out.println("¿Disparo efectivo?: " + (disparoEfectivo ? "Si" : "No"));
            System.out.println("Estado del revolver después de disparar: " + r.toString());
        }
    }
    
    /**
     * Descarga un revólver usando el método <code>descargar</code> de la clase <code>Revolver</code>. Muestra por pantalla el estado antes y después de cargar
     * @param r objeto Revolver que se va a disparar
     */
    public static void descargarRevolver(Revolver r){
        System.out.println(" --> Vaciamos el tambor");
        System.out.println("Estado del revolver antes de descargar: " + r.toString());
        System.out.println("Balas descargadas: " + r.descargar());
        System.out.println("Estado del revolver después de descargar: " + r.toString());
    }
}
