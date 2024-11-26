#include "header_sd.h"

// argv[1] = dirInput
// argv[2] = dirOutput
// argv[3] = period

// USAC12 / 13
int main(int argc, char **argv)
{
	if (argc != 4)
		exit(1);
	
	verify_dir(argv[2]);
	verify_dir(DEF_DIR);

	int lines;
	int n_files;
	char *in_path;
	char *out_path;
	char *move_path;
	char **data = alloc_matrix(MAX_LINES, MAX_LINE_SIZE);
	char **filenames; 

	while (1)
	{
		filenames = alloc_matrix(MAX_FILES, MAX_NAME_LEN);
		n_files = get_filenames(&filenames, argv[1]);

		for (int i = 0; i < n_files; i++)
		{
			if (!strcmp(filenames[i], ".") || !strcmp(filenames[i], ".."))
				continue;

			in_path = get_complete_path(argv[1], filenames[i]);
			out_path = get_complete_path(argv[2], filenames[i]);
			move_path = get_complete_path(DEF_DIR, filenames[i]);

			lines = process_file(data, in_path);

			send_info(data, out_path, lines);
			rename(in_path, move_path);

			free(in_path);
			free(out_path);
			free(move_path);
		}

		dealloc_matrix(filenames, MAX_FILES);

		sleep(atoi(argv[3]) / 1000);
	}

	dealloc_matrix(data, MAX_LINES);

	return (0);
}

// USAC13
void send_info(char **data, char *out_path, int lines)
{
	FILE *fd = fopen(out_path, "w+");

	if (fd == NULL)
		exit(2);

	for (int i = 0; i < lines; i++)
		fprintf(fd, "%s", data[i]);

	fclose(fd);
}
  
//USAC13
int process_file(char **data, char *filename)
{
	int i = -1;
	int len;
	FILE *fd = fopen(filename, "r");

	if (fd == NULL)
		exit(2);

	while (fgets(data[++i], MAX_LINE_SIZE, fd))
	{
		len = strlen(data[i]);

		data[i][len - 2] = '.';
		data[i][len - 1] = '0';
		data[i][len] = '0';
		data[i][len + 1] = '#';
		data[i][len + 2] = '\n';
		data[i][len + 3] = '\0';
	}

	fclose(fd);
	return i;
}
