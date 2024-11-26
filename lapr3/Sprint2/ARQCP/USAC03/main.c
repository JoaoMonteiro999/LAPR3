#include "asm.h"

int main()
{	
	int array[] = {1, 0, 0};
	int length = sizeof(array) / sizeof(int);

	int read = 0;
	int write = 1;

	int num = 1;
	int vec[num];

	printf("\n----- %d -----\n", move_num_vec(array, length, &read, &write, num, vec));

	return 0;
}