#include "asm.h"

void print_array(int *arr, int num)
{
	for (int i = 0; i < num; i++)
		printf("%d ", *(arr + i));
	printf("\n");
}

int main()
{
	int arr[] = {1, 2, 4, 3, 5, -1, -6};

	int num = sizeof arr / sizeof(int);

	printf("Before: \n");
	print_array(arr, num);

	sort_array(arr, num);

	printf("After: \n");
	print_array(arr, num);
}


