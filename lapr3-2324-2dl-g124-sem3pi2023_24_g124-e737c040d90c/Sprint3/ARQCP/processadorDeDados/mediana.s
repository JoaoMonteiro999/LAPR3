.section .data
.section .text

.global mediana

mediana:
	pushq %rbp
	movq %rsp, %rbp

	call sort_array

	xorq %rax, %rax

	cmpl %eax, %esi
	je end

	movl %esi, %eax
	movl $2, %ecx

	cltd
	idivl %ecx

	movl (%rdi, %rax, 4), %eax

end:
	leave
	ret
