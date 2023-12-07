Segmento Data 
 DECLARE int d 30 
 DECLARE int e 4 
 DECLARE int f 5 
 DECLARE int g ? 
 DECLARE string caca "Hola resultado" 
 DECLARE dd t2 ? 


Segmento Code 
 ASIGNAR d , 3
 ASIGNAR e , 4
 Label L0:
 COMPARAR 30 , d
 JE 
 while Saltar  Falso L1
 COMPARAR d , e
 JNE 
 if Saltar  Falso L2
 ASIGNAR f , 5
 Print f ASIGNAR d , 30
 goto L3
 Label L2:
 MUL 4 , 2
 ASIGNAR t2 , MUL
 ASIGNAR g , t2
 Print caca Print g ASIGNAR d , 30
 Label L3:
 goto L0
 Label L1:
