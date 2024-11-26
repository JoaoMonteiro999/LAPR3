NAME = processadorDeDados
CC = gcc
FLAGS = -Wall -Wextra -Werror -fanalyzer
RM = rm -rf

ARGS = /dev/ttyACM0 ../config_file.txt ../PD_OUT/ 50

GDB = gdb
GDB_FLAGS = -tui

all: $(NAME)

$(NAME): processadorDeDados.o utils.o extract_token.o enqueue_value.o move_num_vec.o sort_array.o mediana.o
	$(CC) $^ $(FLAGS) -g -z noexecstack -o $@
	
processadorDeDados.o: processadorDeDados.c
	$(CC) -g $(FLAGS) -c $^ -o $@

utils.o: utils.c
	$(CC) -g $(FLAGS) -c $^ -o $@

extract_token.o: extract_token.s
	$(CC) -g $(FLAGS) -c $^ -o $@

enqueue_value.o: enqueue_value.s
	$(CC) -g $(FLAGS) -c $^ -o $@

move_num_vec.o: move_num_vec.s
	$(CC) -g $(FLAGS) -c $^ -o $@

sort_array.o: sort_array.s
	$(CC) -g $(FLAGS) -c $^ -o $@

mediana.o: mediana.s
	$(CC) -g $(FLAGS) -c $^ -o $@

run: $(NAME)
	./$^ $(ARGS)

debug: $(NAME)
	$(GDB) --args $^ $(ARGS)

fclean: clean
	$(RM) $(NAME)

clean:
	$(RM) processadorDeDados.o utils.o extract_token.o enqueue_value.o move_num_vec.o sort_array.o mediana.o

re: fclean all

.PHONY: all fclean clean re
