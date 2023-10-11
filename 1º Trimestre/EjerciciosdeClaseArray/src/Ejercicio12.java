/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 12. Realiza un programa que rellene un array de 6 filas por 10 columnas
 *     con números enteros positivos comprendidos entre 0 y 1000 (ambos incluidos).
 *     A continuación, el programa deberá dar la posición tanto del máximo como 
 *     del mínimo.
 * 
 * @author franc
 */
public class Ejercicio12 {
    public static void main (String [] args){
        int m[][] = new int[6][10];
        int mayor=Integer.MIN_VALUE;
        int menor=Integer.MAX_VALUE;
        int fmayor=0, fmenor=0, cmayor=0, cmenor=0;
        
        System.out.println("          Columna 0      Columna 1      Columna 2      Columna 3      Columna 4      Columna 5      Columna 6      Columna 7      Columna 8      Columna 9");
        
        for(int nfil=0; nfil<6; nfil++){
            System.out.printf("Fila %d", nfil);
            for(int ncol=0; ncol<10; ncol++){
                m[nfil][ncol]=(int)(Math.random()*1001);
                System.out.printf("      %d      ", m[nfil][ncol]);
                if(m[nfil][ncol]<menor){
                    menor=m[nfil][ncol];
                    fmenor=nfil;
                    cmenor=ncol;
                }
                if(m[nfil][ncol]>mayor){
                    mayor=m[nfil][ncol];
                    fmayor=nfil;
                    cmayor=ncol;
                }
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.printf("--> Posicion del mayor(%d): m[%d][%d] \n", mayor, fmayor, cmayor);
        System.out.printf("--> Posicion del menor(%d): m[%d][%d] \n", menor, fmenor, cmenor);
    }
}
