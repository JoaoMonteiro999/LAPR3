#include "header_sd.h"

int get_filenames(char ***filenames, char *dirname)
{
	int i = 0;
	DIR *d;
	struct dirent *dir;

	d = opendir(dirname);
	
	if (d == NULL)
		exit(2);
	while ((dir = readdir(d)) != NULL)
		strcpy((*filenames)[i++], dir->d_name);

	closedir(d);

	return i;
}

void verify_dir(char *dir)
{
	mkdir(dir, 0777);
}

char **alloc_matrix(int rows, int cols)
{
	char **matrix = (char **) malloc(sizeof(char *) * rows);
	
	if (matrix == NULL)
		exit(3);

	for (int i = 0; i < rows; i++)
	{
		matrix[i] = (char *) malloc(sizeof(char) * cols);

		if (matrix[i] == NULL)
			exit(3);
	}
	return matrix;
}

void dealloc_matrix(char **matrix, int rows)
{
	for (int i = 0; i < rows; i++)
		free(matrix[i]);
	free(matrix);
}

char *get_complete_path(char *dir, char *file)
{
	int mid = strlen(dir);
	char *str = calloc(sizeof(char), mid + strlen(file) + 1);

	if(str == NULL)
		exit(3);

	strcpy(str, dir);
	strcpy(&str[mid], file);
	
	return str;
}

