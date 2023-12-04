.model small
.stack 

.data
	a db ?
	b db "resultado:",10,13,'$' 
	t0 db ?


.code

	main PROC

		MOV ax, @data
		MOV ds, ax

		MOV al, 3
		ADD al, 6

		MOV t0, al
		MOV a, al
 		mov dx, offset b
		mov ah, 9 
		int 21h
		MOV dl, a
		add dl,30h
		MOV ah, 02h
		int 21h

		;AAA 
		;MOV cx, ax
		;MOV cx, 3030h
		;MOV ah, 09h
		;MOV ah, 02h
		;MOV dl, ch
		;int 21h
		;MOV ah, 02h
		;MOV dl, cl
		;int 21h
		.exit

	main ENDP

end main

