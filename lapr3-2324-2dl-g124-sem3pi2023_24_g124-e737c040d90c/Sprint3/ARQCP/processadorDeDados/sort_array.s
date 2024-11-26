.section .data
.section .text

.global sort_array

sort_array:
	pushq %rbp
	movq %rsp, %rbp

	movl %esi, %r8d
	decl %r8d

	xorq %rdx, %rdx

loop:
	cmpl %edx, %r8d
	jle end

	movl 4(%rdi, %rdx, 4), %eax
	cmpl (%rdi, %rdx, 4), %eax
	jl swaps

	incl %edx
	jmp loop
	
swaps:
	movl (%rdi, %rdx, 4), %r11d
	movl 4(%rdi, %rdx, 4), %r10d
	movl %r11d, 4(%rdi, %rdx, 4)
	movl %r10d, (%rdi, %rdx, 4)

	xor %rdx, %rdx
	jmp loop

end:
	leave
	ret
