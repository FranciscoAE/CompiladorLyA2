.model small
.stack 


.data 
    msg db "hello world!!! ",10,13,'$'
	msg2 db "Si ",10,13,'$'
	msg3 db "No ",10,13,'$'
	num db 1
	num2 db 2

; segemento de codigo    
.code

    ; procedimiento principal main
	main PROC

        ; carga en memoria las variables del semento de datos
    	MOV ax, @data
    	MOV ds, ax  


        ; impresion por pantalla
		mov dx, offset msg
		mov ah, 9
    	int 21h  


		mov dl, num
		add dl, 30h
		mov ah,02
		int 21h

		
		

    	.exit   

	main ENDP   

end main