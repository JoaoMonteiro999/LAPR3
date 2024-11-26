.section .data

.section .text
.global extract_token 			

extract_token:
	movq $0, %rax
    movq $0, %rcx
    movl $0, %r8d
	
    call token_loop

    cmpq $0, %rax
    je end


token_loop:
    cmpb $0, (%rdi)
    je end

    movb (%rdi), %al
    
    cmpb (%rsi, %rcx), %al 
    je detected

    movq $0, %rcx
    
    incq %rdi
    
    jmp token_loop

detected:
    incq %rcx
    
    cmpb $0, (%rsi, %rcx)
	je token_found
	
    incq %rdi
    
    jmp token_loop

token_found:
    incq %rdi
    
loop_values:
	cmpb $0,(%rdi)
	je end_values
	
	cmpb $'#',(%rdi)
	je end_values
	
	cmpb $'.',(%rdi)
	je increment
	
	cmpb $'0',(%rdi)
	jl end

	cmpb $'9',(%rdi)
	jg end
    
    movb (%rdi), %al
	subb $48, %al
    movzbl %al, %eax
    
    imull $10, %r8d
    addl %eax, %r8d
    
increment:
	incq %rdi
	
	jmp loop_values
	
end_values:
	movl %r8d, (%rdx)

end:
    ret
