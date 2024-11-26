#include "header_pd.h"

// argv[1] = path_to_file
// argv[2] = config_file
// argv[3] = dir_output
// argv[4] = n_read

int main(int argc, char **argv)
{
	if (argc != 5)
		exit(1);
	
	verify_directory(argv[3]);

	int n_sensors;
	s_sensor *sensors = set_up_sensors(argv[2], &n_sensors);

	start_cicle(sensors, n_sensors, argv[1], argv[3], atoi(argv[4]));

	dealloc_sensors(sensors, n_sensors);
}

// USAC07
s_sensor *set_up_sensors(char *config_file, int *n_s)
{
	int j;
	char **sub_data = alloc_matrix(SUB_DATA_N_TOKENS, TOKENS_LEN);
	char **data = alloc_matrix(MAX_FILE_LINES, MAX_FILE_LINE_LENGTH);

	FILE *fd = open_file(config_file, "r");

	*n_s = 0;

	while (fgets(data[*n_s], MAX_FILE_LINE_LENGTH, fd))
		(*n_s)++;
	
	s_sensor *s = (s_sensor *) calloc(sizeof(s_sensor), *n_s);

	if (s == NULL)
		exit(3);

	for (int i = 0; i < *n_s; i++)
	{
		get_sub_data(sub_data, data[i], "#");

		j = atoi(sub_data[0]) - 1;

		s[j].sensor_id = j + 1;

		s[j].type = strdup(sub_data[1]);
		s[j].unit = strdup(sub_data[2]);

		init_circ_buf(&s[j].buf, atoi(sub_data[3]));
		
		s[j].median.window_len = atoi(sub_data[4]);
		s[j].median.value = 0;

		s[j].timestamp = 0;
		s[j].timeout = atoi(sub_data[5]);
		s[j].write_counter = 0;
	}

	dealloc_matrix(sub_data, SUB_DATA_N_TOKENS);
	dealloc_matrix(data, MAX_FILE_LINES);

	fclose(fd);

	return s;
}

// USAC11
void start_cicle(s_sensor *s, int n_sensors, char *file, char *dir, int n_read){
	FILE *fd = open_file(file, "r");

	int i = -1;

	while (++i < MAX_INFINITE_LOOP_ITER)
	{
		read_and_save_info(fd, s, n_read);
		
		calc_median(s, n_sensors);

		send_info(s, n_sensors, dir);
	}

	fclose(fd);
}

// USAC08
void read_and_save_info(FILE *fd, s_sensor *s, int n_read)
{
	int reads = -1;
	char *str = alloc_str(MAX_FILE_LINE_LENGTH);

	while (++reads < n_read)
	{
		fgets(str, MAX_FILE_LINE_LENGTH, fd);

		save_info(s, str);

		fgets(str, MAX_FILE_LINE_LENGTH, fd);
	}

	free(str);
}

// USAC09
void save_info(s_sensor *s, char *str)
{
	static int old_timestamp = 0;

	char delim[] = ":#";
	char *token = strtok(str, delim);

	s_sensor *s1 = &s[atoi(strtok(NULL, delim)) - 1];

	token = strtok(NULL, delim);
	token = strtok(NULL, delim);

	token = strtok(NULL, delim);
	token = strtok(NULL, delim);
	
	enqueue_value(s1->buf.arr, s1->buf.len, &s1->buf.read, &s1->buf.write, atoi(token));

	token = strtok(NULL, delim);
	token = strtok(NULL, delim);

	s1->timestamp = atoi(token);

	if (s1->timestamp - old_timestamp > s1->timeout)
		s1->median.value = -1;

	old_timestamp = s1->timestamp;
}

// USAC11
void calc_median(s_sensor *s, int n_sensors)
{
	int *vec;

	for (int i = 0; i < n_sensors; i++)
	{
		vec = alloc_int_array(s[i].median.window_len);
		
		if (s[i].median.value != -1)
		{
			if (!move_num_vec(s[i].buf.arr, s[i].buf.len, &s[i].buf.read, &s[i].buf.write, s[i].median.window_len, vec))
				s[i].median.value = -1;
			else
				s[i].median.value = mediana(vec, s[i].median.window_len);
		}

		s[i].write_counter++;
		free(vec);
	}
}

// USAC10
void send_info(s_sensor *s, int n_sensors, char *dir)
{
	char filename[FILENAME_PD_SIZE];

	get_filename_timed(filename);

	char *full_path = get_complete_path(dir, filename);

	FILE *out_file = open_file(full_path, "w");

	free(full_path);

	write_to_file(out_file, s, n_sensors);

	fclose(out_file);
}

// USAC10
void write_to_file(FILE *fd, s_sensor *s, int n_sensors)
{	
	for (int i = 0; i < n_sensors; i++)
	{
		if (s[i].median.value == -1)
			fprintf(fd, "%d,%d,%s,%s,error#\n", s[i].sensor_id, s[i].write_counter, s[i].type, s[i].unit);
		else
			fprintf(fd, "%d,%d,%s,%s,%d#\n", s[i].sensor_id, s[i].write_counter, s[i].type, s[i].unit, s[i].median.value);
	}
}

