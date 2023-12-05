.model small
.stack 

.data
	d db 30
	e db 4
	f db 5
	g db ?
	caca db "Hola",10,13,'$' 
	t2 db ?


.code

	main PROC

		MOV ax, @data
		MOV ds, ax

		MOV d, 3
 		MOV e, 4
 	L0:

		MOV al, 30
		CMP al, d

		JE L1

		MOV al, d
		CMP al, e

		JNE L2

		MOV f, 5
 		MOV dl, f
		ADD dl, 30h
		MOV ah, 02h
		int 21h
		MOV d, 30
 		JMP L3

	L2:

		MOV al, 4
		MOV bl, 2

		MUL bl
		MOV t2, al
		MOV bl , t2
 		MOV g, bl 
		mov dx, offset caca
		mov ah, 9 
		int 21h
		MOV dl, g
		ADD dl, 30h
		MOV ah, 02h
		int 21h
		MOV d, 30
 	L3:

		JMP L0

	L1:

		.exit

	main ENDP

end main

