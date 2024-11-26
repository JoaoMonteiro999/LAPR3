NAME = usac02
CC = gcc
AS = gcc
FLAGS = -Wall -Wextra -fanalyzer
RM = rm -f

GDB = gdb
GDB_FLAGS = -tui

all: $(NAME)

$(NAME): main.o asm.o
	$(CC) $^ -z noexecstack -o $@

main.o: main.c
	$(CC) -g $(FLAGS) -c $^ -o $@

asm.o: asm.s
	$(AS) -g $(FLAGS) -c $^ -o $@

run: $(NAME)
	./$^

debug: $(NAME)
	$(GDB) $(GDB_FLAGS) $^

fclean: clean
	$(RM) $(NAME)

clean:
	$(RM) main.o asm.o

re: fclean all

.PHONY: all fclean clean re run