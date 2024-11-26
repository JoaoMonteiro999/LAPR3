.section .data
.section .text

.global enqueue_value

# rdi = arr
# rsi = length
# rdx = read
# rcx = write
# r8  = value

enqueue_value:
	pushq %rbp
	movq %rsp, %rbp

	movl (%rcx), %r9d			# save actual write, to put data in memory

	jmp inc_write_pointer			# increment write, and get next position

test:						# tests if its needed to increment read position
	movl (%rcx), %eax			# copy new write to eax, to compare (cant compare two memory addresses)
	cmpl (%rdx), %eax			# if they are the same, read is incremented by one, to skip oldest element
	je inc_read_pointer			# if array full, increase read

cont:
	movl %r8d, (%rdi, %r9, 4)		# put value in old write position
	jmp end

inc_write_pointer:
	incl (%rcx)				# increment write pointer
	cmpl (%rcx), %esi			# if write is not maxLen
	jne test				# then jmp

reset_write_pointer:				# else reset pointer
	movl $0, (%rcx)				# to 0
	jmp test	

inc_read_pointer:				# increment read pointer
	incl (%rdx)				# else, increment
	cmpl (%rdx), %esi			# if read is not maxLen
	jne cont				# then jmp

reset_read_pointer:				# reset pointer
	movl $0, (%rdx)				# to array pointer
	jmp cont

end:
	leave
	ret

# {
#    next = inc_write(write, maxLen, arr)
#
#    if (read == next)
#        inc_read(read, maxLen, arr)
#
#    write = value
# }

