#ifndef HEADER_PD_H
# define HEADER_PD_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <dirent.h>
#include <time.h>

#define MAX_FILES 10000
#define MAX_NAME_LEN 100
#define MAX_LINE_SIZE 100
#define MAX_LINES 50
#define DEF_DIR "../filesRead/"

typedef struct {
	int sensor_id;
	int write_counter;
	char *type;
	char *unit;
	int value;
} s_sensor;

char **alloc_matrix(int rows, int cols);
char *get_complete_path(char *dir, char *file);
int process_file(char **data, char *filename);
int get_filenames(char ***filenames, char *dir);
void send_info(char **data, char *out_path, int lines);
void dealloc_matrix(char **matrix, int rows);
void verify_dir(char *dir);

#endif
