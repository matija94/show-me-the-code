section .data ;data segment
	userMsg db 'Please enter a number: '
	lenUserMsg equ $-userMsg
	dispMsg db 'You have entered: '
	lenDispMsg equ $-dispMsg

section .bss ; unitialized data segment
	num resb 5

section .text ; code segment
	global _start
_start:
	; prompt user
	mov eax,4 ;(sys_write)
	mov ebx,1 ;(file descriptor - stdout)
	mov ecx,userMsg ;( store usermsg )
	mov edx,lenUserMsg ; (store len usermsg)
	int 80h ;(kernel interrupt)

	;read and store the user input
	mov eax,3 ;(sys_read)
	mov ebx,2 ; (file descriptor - stdin)
	mov ecx,num
	mov edx,5 ; 5 bytes(numeric, 1 for sign) of that information
	int 80h	

	;output the message
	mov eax,4 ;(sys_write)
	mov ebx,1 ;(stdout)
	mov ecx,dispMsg ;(store disp msg)
	mov edx,lenDispMsg
	int 80h
	
	;ouput the number entered
	mov eax,4
	mov ebx,1
	mov ecx,num
	mov edx, 5
	int 80h

	;exit code
	mov eax, 1
	mov ebx, 0
	int 80h
