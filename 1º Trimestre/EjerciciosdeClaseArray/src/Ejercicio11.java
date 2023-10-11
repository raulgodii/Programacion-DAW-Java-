
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 11. Escribe un programa que pida 20 números enteros. Estos números se deben 
 *     introducir en un array de 4 filas por 5 columnas. El programa mostrará 
 *     las sumas parciales de filas y columnas igual que si de una hoja de 
 *     cálculo se tratara. 
 *     La suma total debe aparecer en la esquina inferior derecha.
 * 
 * @author franc
 */
public class Ejercicio11 {
    public static void main (String [] args){
        int m[][] = new int[4][5];
        Scanner leer = new Scanner(System.in);
        
        for(int nfil=0; nfil<4; nfil++){
            for(int ncol=0; ncol<5; ncol++){
                System.out.printf("M[%d][%d]= ", nfil,ncol);
                m[nfil][ncol]=leer.nextInt();
                System.out.println();
            }
        }
        
        System.out.println("        Columna 0    Columna 1    Columna 2    Columna 3    Columna 4    Total");
        
        int suma = 0;
        for(int nfil=0; nfil<4; nfil++){
            suma=0;
            System.out.printf("Fila %d", nfil);
            for(int ncol=0; ncol<5; ncol++){
                System.out.printf("      %d      ", m[nfil][ncol]);
                suma+=m[nfil][ncol];
            }
            System.out.println("   "+suma);
        }
        
        System.out.print("Total ");
        
        for(int ncol=0; ncol<5; ncol++){
            suma=0;
            for(int nfil=0; nfil<4; nfil++){
                suma+=m[nfil][ncol];
            }
            System.out.print("      "+suma+"      ");
        }
    }
}
