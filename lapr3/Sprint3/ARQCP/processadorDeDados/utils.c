#include "header_pd.h"

void get_sub_data(char **sub_data, char *str, char *c)
{
	char *token = strtok(str, c);

	for (int i = 0; i < SUB_DATA_N_TOKENS; i++)
	{
		strcpy(sub_data[i], token);
		token = strtok(NULL, c);
	}
}

void verify_directory(char *dir)
{
	mkdir(dir, 0777);
}

void get_filename_timed(char *filename)
{
	time_t t = time(NULL);
	struct tm tm = *localtime(&t);

	snprintf(filename, FILENAME_PD_SIZE, "%d%02d%02d%02d%02d%02d_sensors.txt", tm.tm_year + 1900, tm.tm_mon + 1, tm.tm_mday, tm.tm_hour, tm.tm_min, tm.tm_sec);

}

char *get_complete_path(char *dir, char *file)
{
	int mid = strlen(dir);
	char *str = alloc_str(mid + strlen(file) + 1);

	strcpy(str, dir);
	strcpy(&str[mid], file);
	
	return str;
}

void init_circ_buf(s_circular_buffer *buf, int len)
{
	buf->len = len;
	buf->read = 0;
	buf->write = 0;
	buf->arr = alloc_int_array(len);
}

char **alloc_matrix(int rows, int cols)
{
	char **matrix = (char **) malloc(sizeof(char *) * rows);
	
	if (matrix == NULL)
		exit(3);

	for (int i = 0; i < rows; i++)
		matrix[i] = alloc_str(cols);
	return matrix;
}

void dealloc_matrix(char **matrix, int rows)
{
	for (int i = 0; i < rows; i++)
		free(matrix[i]);
	free(matrix);
}

void dealloc_sensors(s_sensor *s, int n_sensors)
{
	for (int i = 0; i < n_sensors; i++)
	{
		free(s[i].type);
		free(s[i].unit);
		free(s[i].buf.arr);
	}

	free(s);
}

int *alloc_int_array(int size)
{
	int *arr = (int *) malloc(sizeof(int) * size);

	if (arr == NULL)
		exit(3);
	return arr;
}

char *alloc_str(int size)
{
	char *str = (char *) calloc(sizeof(char), size);

	if (str == NULL)
		exit(3);
	return str;
}

FILE *open_file(char *file, char *mode)
{
	FILE *fd = fopen(file, mode);

	if (fd == NULL)
		exit(2);
	return fd;
}
