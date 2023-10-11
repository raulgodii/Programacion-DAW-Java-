package pruebamaquina;
import java.util.Arrays;
import maquina.*;

/**
 * Clase de prueba de la biblioteca de clases maquina
 * @author Profesor PROG
 */
public class PruebaMaquina {

    /**
     * Programa  principal de prueba de la biblioteca maquina
     * @param args La lista de argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("Probando máquina");
        
        Maquina[] listaMaquinas;
        boolean error=false;
        
        //-------------------------------------------------------
        //             Pruebas de máquinas
        //-------------------------------------------------------

        Maquina artefacto;

        //-------------------------------------------------------
        //                CABECERA DE LAS PRUEBAS
        //-------------------------------------------------------
        System.out.println();
        System.out.println("+------------------------------------------------------+");
        System.out.println("| PRUEBAS DE LA JERARQUÍA DE CLASES DEL PAQUETE maquina |");
        System.out.println("+------------------------------------------------------+");
        System.out.println();        
        
        //-------------------------------------------------------
        //           Creación de elementos de juego
        //-------------------------------------------------------
        // Reservamos espacio para el array de elementos que vamos a usar de prueba
        listaMaquinas = new Maquina[9];
        int maquinasGuardadasEnArray=0;
        
        System.out.println ("CREACIÓN DE MÁQUINAS");
        System.out.println ("---------------------");        
        
        System.out.println ("\nPruebas de creación de BICICLETAS:");
        System.out.println ("------------------------------------");        
        artefacto= new Bicicleta ("", "");  //Bicicleta con nombre y marca vacíos  (CORRECTO)
           mostrarMaquinaCreada (artefacto);
        artefacto= new Bicicleta (null, null); // Bicicleta con nombre y marca nulos (CORRECTO)
           mostrarMaquinaCreada(artefacto);
        artefacto= new Bicicleta ("ORBEA", "Orca"); // Bicicleta con nombre y Marca (CORRECTO)
           mostrarMaquinaCreada (artefacto);
        
        System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName()); //solo el último, con marca y modelo significativos usando primer constructor
        listaMaquinas[maquinasGuardadasEnArray]= artefacto;
        maquinasGuardadasEnArray++;
        
        //otros intentos de crear bicicletas
       
        try{
           artefacto= new Bicicleta ("ORBEA", "Enana", Bicicleta.MIN_RADIO_RUEDA-1);  // Bicicleta con tamaño de radio menor que el mínimo (ERROR)
        }catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
         if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        }
         
        error=false;
        try{
           artefacto= new Bicicleta ("ORBEA", "Gigante", Bicicleta.MAX_RADIO_RUEDA+1);  // Bicicleta con tamaño de radio mayor que el máximo (ERROR)
        }catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
         if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;   
        }
         
       error=false;  
       try{
          artefacto= new Bicicleta ("ORBEA", "Rallon", (Bicicleta.MIN_RADIO_RUEDA +Bicicleta.MAX_RADIO_RUEDA)/2);  // Bicicleta con tamaño de radio medio (CORRECTO)
        }catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
         if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
             
        }
         
         
         System.out.println ("\nPruebas de creación de MOLINOS:");
         System.out.println ("---------------------------------");        
         
        artefacto= new Molino ("", "",Fuerza.ELECTRICIDAD);  //Molino Eléctrico con nombre y marca vacíos. (CORRECTO)
           mostrarMaquinaCreada (artefacto);
        artefacto= new Molino ("IBERDROLA", "Eficient",Fuerza.VIENTO); // Molino de viento con nombre y Marca.
           mostrarMaquinaCreada (artefacto);
        
        System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName()); //solo el último, con marca y modelo significativos usando primer constructor
        listaMaquinas[maquinasGuardadasEnArray]= artefacto;
        maquinasGuardadasEnArray++;
        
        //otros intentos de crear Molinos
        error=false;
        try{
           artefacto= new Molino ("IBERDROLA", "Eficient",null);  // Molino con un tipo de fuerza motriz erróneo por ser null (ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        }

        error=false;
        try{
           artefacto= new Molino ("IBERDROLA", "HidroPower",Fuerza.CORRIENTE_AGUA);  // Molino hidráulico con marca y modelo y fuerza motriz Corriente de agua. (CORRECTO)
        }catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
         if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;   
        }

         System.out.println ("\nPruebas de creación de COCHES ELÉCTRICOS:");
         System.out.println ("-------------------------------------------");        
        
        error=false;
        try{
           artefacto= new CocheElectrico ("", "",null,0,0,0);  // Coche Eléctrico con fuerza motriz nula. (ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new CocheElectrico ("HYUNDAY", "Kona",Fuerza.COMBUSTIBLE,0,0,0);  // Coche Eléctrico con Fuerza motriz errónea.(ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new CocheElectrico ("HYUNDAY", "Kona",Fuerza.ELECTRICIDAD,10,35,300);  // Coche Eléctrico con voltaje erróneo. (ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new CocheElectrico ("HYUNDAY", "Kona",Fuerza.ELECTRICIDAD,24,40,300);  // Coche Eléctrico con capacidad máxima errónea. (ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new CocheElectrico ("HYUNDAY", "Kona",Fuerza.ELECTRICIDAD,12,35,CocheElectrico.MIN_AUTONOMIA-1);  // Coche Eléctrico con autonomía menor de lo permitido. (ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new CocheElectrico ("HYUNDAY", "Kona",Fuerza.ELECTRICIDAD,12,35,CocheElectrico.MAX_AUTONOMIA+1);  // Coche Eléctrico con autonomía mayor de lo permitido. (ERROR)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new CocheElectrico ("HYUNDAY", "Kona",Fuerza.ELECTRICIDAD,12,35,(CocheElectrico.MIN_AUTONOMIA+CocheElectrico.MAX_AUTONOMIA)/2);  // Coche Eléctrico con autonomía media. (CORRECTO)
        } catch (NullPointerException | IllegalArgumentException npe){
            System.out.printf(npe.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        }      

         System.out.println ("\nPruebas de creación de BATIDORAS:");
         System.out.println ("-------------------------------------------");        
        
        error=false;
        try{
           artefacto= new Batidora ("", "",Batidora.DEFAULT_VOLTAJE_BATIDORA,Batidora.DEFAULT_POTENCIA_BATIDORA);  // Batidora sin marca ni modelo, y valores por defecto para voltaje y potencia (CORRECTO)
        } catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new Batidora ("PHILIPS", "Promix",Batidora.DEFAULT_VOLTAJE_BATIDORA,900.0);  // Batidora con potencia  errónea.(ERROR)
        } catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        error=false;
        try{
           artefacto= new Batidora ("PHILIPS", "Promix",100,Batidora.DEFAULT_POTENCIA_BATIDORA);  // Batidora con voltaje erróneo.(ERROR)
        } catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new Batidora ("PHILIPS", "Promix",Batidora.DEFAULT_VOLTAJE_BATIDORA,Batidora.DEFAULT_POTENCIA_BATIDORA);  // Batidora con voltaje  y potencia por defecto.(CORRECTO)
        } catch (IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
             
 
        System.out.println ("\nPruebas de creación de CALCULADORAS:");
         System.out.println ("-------------------------------------------");        
        
        error=false;
        try{
           artefacto= new Calculadora ("", "",null);  // Calculadora con un tipo de pila nulo. (ERROR)
        } catch (NullPointerException | IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new Calculadora ("CASIO","FX-991SPXI",TipoPila.C_1_5V);  // Calculadora con el tipo de pila erróneo(ERROR)
        } catch (NullPointerException | IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
        error=false;
        try{
           artefacto= new Calculadora ("CASIO","FX-991SPXI",Calculadora.DEFAULT_TIPO_PILA);  // Calculadora con tipo de pila correcto(CORRECTO)
        } catch (NullPointerException | IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray] = artefacto;
             maquinasGuardadasEnArray++;
        }       
        
       
        error=false;
        try{
           artefacto= new Calculadora ("CASIO","FX-991SPXI",Calculadora.DEFAULT_TIPO_PILA, false);  // Calculadora con tipo de pila correcto, pero con la pila agotada(CORRECTO)
        } catch (NullPointerException | IllegalArgumentException iae){
            System.out.printf(iae.getMessage()+"\n");
            error=true;
        }
        if(!error){
             mostrarMaquinaCreada (artefacto);  // si no diera error, se guardaría, y se incrementaría el contador de máquinas
             System.out.printf ("\t Guardamos artefacto tipo %s en el array.\n", artefacto.getClass().getSimpleName());
             listaMaquinas[maquinasGuardadasEnArray]= artefacto;
             maquinasGuardadasEnArray++;
        } 
        
         // Mostramos el contenido del array de máquinas
        int indice=0;
        System.out.println ("\n--------------------------------"); 
        System.out.println ("MÁQUINAS INCLUIDAS EN EL ARRAY:");
        System.out.println ("--------------------------------"); 
        for (Maquina maq: listaMaquinas) {
            System.out.printf ("MÁQUINA %d: %s\n",
                    indice++, contenidoMaquina(maq));
        }
        
        System.out.println ("\n-----------------------------------"); 
        System.out.printf("TOTAL DE MÁQUINAS INTRODUCIDAS: %d \n",maquinasGuardadasEnArray);
        System.out.println ("-----------------------------------"); 
      
        //-------------------------------------------------------
        //       Prueba de métodos de la interfaz Desplazable
        //-------------------------------------------------------
        System.out.println ("\n----------------------------------------");
        System.out.println ("PRUEBA DE MÉTODOS DE LA INTERFAZ Desplazable");
        System.out.println ("----------------------------------------");
        indice=0;
        for (Maquina maq: listaMaquinas) {
            System.out.printf ("\nMáguina %d: %s\n",
                    indice++, contenidoMaquina(maq));
            try {
                System.out.printf ("Kilómetros recorridos sin repostar: %.2f, ", ((Desplazable)maq).getKilometrosSinRepostar());
                System.out.printf ("Total Kilómetros: %.2f, ", ((Desplazable)maq).getTotalKilometrosRecorridos());
                System.out.print ( "Desplazar 40.5 km., ");
                ((Desplazable)maq).desplazar(40.5);
                System.out.printf ("Kilómetros recorridos sin repostar: %.2f, ", ((Desplazable)maq).getKilometrosSinRepostar());
                System.out.printf ("Total Kilómetros: %.2f, ", ((Desplazable)maq).getTotalKilometrosRecorridos());
            } catch (IllegalArgumentException | IllegalStateException | ClassCastException ex) {
                String cadenaAMostrar=ex.getMessage();
                cadenaAMostrar=cadenaAMostrar.substring(0,cadenaAMostrar.indexOf("(")-1)+".";
                System.out.printf ("\nError. %s\n",
                        cadenaAMostrar);
            }
        }      
        
        //-------------------------------------------------------
        //       Prueba de métodos de la interfaz Recargable
        //-------------------------------------------------------
        System.out.println ("\n----------------------------------------");
        System.out.println ("PRUEBA DE MÉTODOS DE LA INTERFAZ Recargable");
        System.out.println ("----------------------------------------");
        indice=0;
        for (Maquina maq: listaMaquinas) {
            System.out.printf ("\nMáguina %d: %s\n",
                    indice++, contenidoMaquina(maq));
            try {
                System.out.printf ("Consumir batería (para 50 unidades de consumo).  Restante: %.2f. ", ((Recargable)maq).usarBateria(50.0));
                System.out.printf ("Estado: %s, ", ((Recargable)maq).toString());
                System.out.printf ("Cargar batería:");
                ((Recargable)maq).cargar();
                System.out.printf ("Estado: %s, ", ((Recargable)maq).toString());
            } catch (IllegalArgumentException | IllegalStateException | ClassCastException ex) {
               String cadenaAMostrar=ex.getMessage();
                cadenaAMostrar=cadenaAMostrar.substring(0,cadenaAMostrar.indexOf("(")-1)+".";
                System.out.printf ("\nError. %s\n",
                        cadenaAMostrar);
            }
        }        
        //-------------------------------------------------------
        //       Prueba de métodos de la interfaz Enchufable
        //-------------------------------------------------------
        System.out.println ("\n----------------------------------------");
        System.out.println ("PRUEBA DE MÉTODOS DE LA INTERFAZ Enchufable");
        System.out.println ("----------------------------------------");
        indice=0;
        for (Maquina maq: listaMaquinas) {
            System.out.printf ("\nMáguina %d: %s\n",
                    indice++, contenidoMaquina(maq));
            try {
                System.out.printf ("Voltaje; %3d. ",((Enchufable)maq).getVoltaje()); 
                System.out.printf ("Países compatibles: %s ", Arrays.toString(((Enchufable)maq).getPaisesCompatibles()));

            } catch (IllegalArgumentException | IllegalStateException | ClassCastException ex) {
                String cadenaAMostrar=ex.getMessage();
                cadenaAMostrar=cadenaAMostrar.substring(0,cadenaAMostrar.indexOf("(")-1)+".";
                System.out.printf ("\nError. %s\n",
                        cadenaAMostrar);
            }
        }        
        
 //==========================================================================       
    }
    
     static void mostrarMaquinaCreada (Maquina artefacto) {
        System.out.printf ("Máquina creada %s\n", 
                contenidoMaquina (artefacto));        
    }
    
    static String contenidoMaquina (Maquina artefacto) {
        String resultado;
        if (artefacto != null) {                
            resultado= String.format ("%-15s %s",
                artefacto.getClass().getSimpleName()+":", artefacto);
        } else {
            resultado= "null";
        }
        return resultado;
    }
}
