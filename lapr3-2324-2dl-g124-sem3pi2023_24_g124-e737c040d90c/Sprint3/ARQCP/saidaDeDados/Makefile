NAME = saidaDeDados
CC = gcc
FLAGS = -Wall -Wextra -Werror -fanalyzer
RM = rm -rf

ARGS = ../PD_OUT/ ../SD_OUT/ 10000

GDB = gdb
GDB_FLAGS = -tui

all: $(NAME)

$(NAME): saidaDeDados.o utils.o
	$(CC) $^ -o $@
	
saidaDeDados.o: saidaDeDados.c
	$(CC) -g $(FLAGS) -c $^ -o $@

utils.o: utils.c
	$(CC) -g $(FLAGS) -c $^ -o $@

run: $(NAME)
	./$^ $(ARGS)

debug: $(NAME)
	$(GDB) $(GDB_FLAGS) --args $^ $(ARGS)

fclean: clean
	$(RM) $(NAME)

clean:
	$(RM) saidaDeDados.o utils.o

re: fclean all

.PHONY: all fclean clean re run debug
