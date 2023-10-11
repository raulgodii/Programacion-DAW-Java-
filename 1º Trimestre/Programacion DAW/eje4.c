#include <stdlib.h>
#include <stdio.h>

int main(){

    int n, j;
    int cont=0;

    printf("\n Introduce un numero: ");
    scanf("%d", &n);

    for(int i=n; i!=0; i--){
        j=i;
        cont++;
        while(j!=0){
            printf("  \b");
            j--;
        }
        
        for(int a=0; a<cont; a++){
            printf("%d ", cont);
        }

        printf("\n");
    }

    return 0;
}