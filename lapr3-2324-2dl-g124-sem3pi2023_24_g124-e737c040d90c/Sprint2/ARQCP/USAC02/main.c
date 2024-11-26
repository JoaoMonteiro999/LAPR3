#include "asm.h"

int test_exec(int *test, int length, int *values, int len_values, int *expected, int read, int write)
{
	for (int i = 0; i < len_values; i++)
		enqueue_value(test, length, &read, &write, *(values + i));
	
	for (int i = 0; i < length; i++)
		if (*(test + i) != *(expected + i))
			return 1;
	return 0;
}

int test3()
{
	int test[] =	 {0, 0, 0};

	int values[] = 	 {5};

	int expected[] = {0, 0, 5};

	int length = sizeof test / sizeof(int);

	int len_values = sizeof values / sizeof(int);

	int read = 0;
	int write = 2;

	return test_exec(test, length, values, len_values, expected, read, write);
}

int test2()
{
	int test[] =	 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

	int values[] = 	 {2, 1, 2, 1, 2, 1, 2, 1, 2};

	int expected[] = {2, 1, 2, 1, 2, 1, 2, 1, 2, 9};

	int length = sizeof test / sizeof(int);

	int len_values = sizeof values / sizeof(int);

	int read = 0;
	int write = 0;

	return test_exec(test, length, values, len_values, expected, read, write);
}

int test1()
{
	int test[] =	 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

	int values[] = 	 {1, 1, 1, 1, 1};

	int expected[] = {1, 1, 1, 1, 1, 5, 6, 7, 8, 9};

	int length = sizeof test / sizeof(int);

	int len_values = sizeof values / sizeof(int);

	int read = 0;
	int write = 0;

	return test_exec(test, length, values, len_values, expected, read, write);
}

void test_caller(int (*test)(), int test_n)
{
	if (test())
		printf("Test %d: FAIL\n", test_n);
	else 
		printf("Test %d: PASS\n", test_n);
}

int main()
{
	int (*test_arr[])() = {&test1, &test2, &test3};

	int n_tests = sizeof test_arr / sizeof(int *);

	for (int i = 0; i < n_tests; i++)
		test_caller(*(test_arr + i), i + 1);
}

