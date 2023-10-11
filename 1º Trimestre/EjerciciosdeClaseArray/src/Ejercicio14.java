
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * 
 *14. Mejora el juego "Busca el tesoro" de tal forma que si hay una mina a una
 *    casilla de distancia, el programa avise diciendo ¡Cuidado! ¡Hay una mina cerca!
 * 
 * @author franc
 */
public class Ejercicio14 {
    public static void main (String [] args){
        char m[][]= new char[4][5];
        int ftesoro=0, fbomba=0, ctesoro=0, cbomba=0, fin=1;
        Scanner leer = new Scanner(System.in);
        
        //Tesoro
        ftesoro=(int)(Math.random()*4);
        ctesoro=(int)(Math.random()*5);
        System.out.printf("Coordenadas tesoro: m[%d][%d]\n", ftesoro, ctesoro);
        
        //Bomba
        do{
            fbomba=(int)(Math.random()*4);
            cbomba=(int)(Math.random()*5);
        } while((fbomba==ftesoro) && (cbomba==ctesoro));
        System.out.printf("Coordenadas bomba: m[%d][%d]\n\n", fbomba, cbomba);
        
        int fil, col;
        do{
            System.out.println("          Columna 0      Columna 1      Columna 2      Columna 3      Columna 4");
            for(int nfil=0; nfil<4; nfil++){
            System.out.printf("Fila %d", nfil);
                for(int ncol=0; ncol<5; ncol++){
                    System.out.printf("        %c       ", m[nfil][ncol]);
                }
                System.out.println();
            }
            do{
                System.out.print("\n-->Introduce una fila: ");
                fil=leer.nextInt();
                System.out.print("-->Introduce una columna: ");
                col=leer.nextInt();
            }while(fil<0 || fil>3 || col<0 || col>4);
            m[fil][col]='X';
            if((fil==ftesoro)&&(col==ctesoro)){
                System.out.println("\n  |||||| Tesoro encontrado ||||||");
                return;
            }
            if((fil==fbomba)&&(col==cbomba)){
                System.out.println("\n  |||||| Bomba Encontrada! FIN DEL JUEGO ||||||");
                return;
            }
            for(int i=1; i>=-1;i--){
                for(int j=1; j>=-1; j--){
                    if((fbomba+j==fil)&&(cbomba+i==col)){
                        System.out.println("\n|||| CUIDADO, HAY UNA BOMBA CERCA ||||\n");
                    }
                }
            }
        }while(fin==1);  
    }
}
