/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 10. Define un array de números enteros de 3 filas por 6 columnas con
 *     nombre "num" y asigna los valores según la siguiente tabla:
 *     num:
 *            Columna 0   Columna 1   Columna 2   Columna 3   Columna 4   Columna 5
 *     Fila 0     0          30           2                                   5
 *     Fila 1    75                                               0
 *     Fila 2                            -2           9                      11
 * 
 *    Muestra el contenido de todos los elementos del array dispuestos en
 *    forma de tabla como se muestra en la figura.
 * 
 * @author franc
 */
public class Ejercicio10 {
    public static void main (String [] args){
        int[][] m = new int[3][6];
        m[0][0] = 0;
        m[0][1] = 30;
        m[0][3] = 2;
        m[0][5] = 5;
        m[1][0] = 75;
        m[1][4] = 0;
        m[2][2] = -2;
        m[2][3] = 9;
        m[2][5] = 11;
        
        System.out.println("        Columna 0    Columna 1    Columna 2    Columna 3    Columna 4    Columna 5");
        
        for(int nfil=0; nfil<3; nfil++){
            System.out.printf("Fila %d", nfil);
            for(int ncol=0; ncol<6; ncol++){
                System.out.printf("      %d      ", m[nfil][ncol]);
            }
            System.out.println();
        }
    }
}
