#include <stdlib.h>
#include <stdio.h>

int main(){

    int cont=0;
    int n;

    printf("\n Introduce un numero: ");
    scanf("%d", &n);

    if(n==10){
        cont++;
    }

    while (n!=-1){
        printf("\n Introduce un numero: ");
        scanf("%d", &n);

        if(n==10){
        cont++;
        }
    }

    printf("\n Fin del programa, numero de veces que ha aparecido el numero 10: %d \n", cont);

    return 0;
}