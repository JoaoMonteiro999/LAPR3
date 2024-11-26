.section .data
	.equ SUCCESS, 1

.section .text

.global move_num_vec

# rdi = array
# rsi = length
# rdx = read
# rcx = write
# r8  = num
# r9 = vec

move_num_vec:
	pushq %rbp				    # Prologue
	movq %rsp, %rbp

	xorq %rax, %rax			    # Clear rax
	xorq %r10, %r10			    # Index of r9

	cmpl %esi, %r8d			    # Check if the number of numbers to be copied
	jge end					    # Is bigger or equal then length, if so, return 0

loop:
	cmpl $0, %r8d			    # Stop condition
	je success

	movl (%rdx), %eax		    # Empty Buffer Check
	cmpl (%rcx), %eax
	je end

	movl (%rdi, %rax, 4), %r11d # Copy value from read to Vector
	movl %r11d, (%r9, %r10, 4)

	jmp inc_read_pointer        # Increment read pointer

cont:
	incl %r10d                  # Increment index of Vector
	decl %r8d                   # Decrements number of numbers to be copied

	jmp loop

inc_read_pointer:               # Increment read pointer
	incl (%rdx)
	cmpl (%rdx), %esi           # Check if read pointer reached the limit
	jne cont

reset_read_pointer:             # If so, moves 0 to read pointer
	movl $0, (%rdx)
	jmp cont

success:
	movl $SUCCESS, %eax	        # Success return

end:
	leave					    # Epilogue
	ret
