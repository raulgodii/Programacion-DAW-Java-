#include <stdlib.h>
#include <stdio.h>

int main() {
    int n1, n2, n3, aux;
    aux=0;

    printf("Introduce a continuacion 3 numeros");
    
    printf("\n Numero 1: ");
    scanf("%d", &n1);

    printf("\n Numero 2: ");
    scanf("%d", &n2);
    
    printf("\n Numero 3: ");
    scanf("%d", &n3);

    if(n1>n2){
        aux = n1;
        n1 = n2;
        n2 = aux;
    }

    if (n1>n3){
        aux = n1;
        n1 = n3;
        n3 = aux;
    }

    if(n2>n3){
        aux = n2;
        n2 = n3;
        n3 = aux;
    }
    

    printf("\n Numeros ordenados: %d %d %d \n", n1, n2, n3);

    return 0;
}
