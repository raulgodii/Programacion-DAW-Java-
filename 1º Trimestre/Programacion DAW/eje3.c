#include <stdlib.h>
#include <stdio.h>
#include <math.h>


int main(){

    int x, n;

    printf("\n Introduce un numero x: ");
    scanf("%d", &x);

    printf("\n Introduce un numero n: ");
    scanf("%d", &n);

    int res = pow(x, n);

    printf("\nEl resultado de %d elevado a %d es igual a: %d", x, n, res);

    return 0;
}