#include "asm.h"

int main()
{
	int arr[] = {6, 1, 30, 10, 0};

	int num = sizeof arr / sizeof(int);
	
	int res = mediana(arr, num);
	
	printf("Result: %d\n", res);
}
