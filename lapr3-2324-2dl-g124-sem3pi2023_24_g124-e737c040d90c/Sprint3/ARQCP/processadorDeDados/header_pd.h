#ifndef HEADER_PD_H
#define HEADER_PD_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <time.h>

#define MAX_INFINITE_LOOP_ITER 1000
#define MAX_FILE_LINES 100
#define MAX_FILE_LINE_LENGTH 100
#define SUB_DATA_N_TOKENS 6
#define TOKENS_LEN 30
#define FILENAME_PD_SIZE 27

typedef struct {
	int *arr;
	int len;
	int read;
	int write;
} s_circular_buffer;

typedef struct {
	int window_len;
	int value;
} s_median;

typedef struct {
	int sensor_id;
	char *type;
	char *unit;
	s_circular_buffer buf;
	s_median median;
	int timestamp;
	int timeout;
	int write_counter;
} s_sensor;

// processadorDeDados.c
s_sensor *set_up_sensors(char *config_file, int *n_s);
void start_cicle(s_sensor *s, int n_sensors, char *file, char *dir, int n_read);
void read_and_save_info(FILE *fd, s_sensor *s, int n_read);
void save_info(s_sensor *s, char *str);
void calc_median(s_sensor *s, int n_sensors);
void send_info(s_sensor *s, int n_sensors, char *dir);
void write_to_file(FILE *fd, s_sensor *s, int n_sensors);

// utils.c
void get_sub_data(char **sub_data, char *str, char *c);
void verify_directory(char *dir);
void get_filename_timed(char *filename);
char *get_complete_path(char *dir, char *file);
void init_circ_buf(s_circular_buffer *buf, int len);
char **alloc_matrix(int rows, int cols);
void dealloc_matrix(char **matrix, int rows);
void dealloc_sensors(s_sensor *sensor, int n_sensors);
int *alloc_int_array(int size);
char *alloc_str(int size);
FILE *open_file(char *file, char *mode);

// assembly files(.s)
int extract_token(char *input, char *token, int *output);
void enqueue_value(int *array, int length, int *read, int *write, int value);
int move_num_vec(int *array, int length, int *read, int *write, int num, int *vec);
int mediana(int *vec, int num);
void sort_array(int *vec, int num);

#endif
