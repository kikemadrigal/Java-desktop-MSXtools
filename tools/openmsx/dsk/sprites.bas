10200 screen 2,2
10210 for i=0 to 2:sp$=""
	10220 for j=0 to 31
		10230 read a$
		10240 sp$=sp$+chr$(val(a$))
	10250 next J
	10260 sprite$(i)=sp$
10270 next i
10280 rem sprites data definitions
10290 rem data definition sprite 0, name: avion
10290 data 0,0,0,248,240,255,31,255
10300 data 255,255,254,252,248,224,128,0
10310 data 0,0,0,0,0,192,240,248
10320 data 254,192,0,0,0,0,0,0
10330 rem data definition sprite 1, name: arbol
10330 data 31,63,63,63,31,31,15,3
10340 data 3,3,3,3,3,3,255,255
10350 data 240,248,248,248,240,224,128,128
10360 data 128,128,128,128,128,128,255,255
10370 rem data definition sprite 2, name: casa
10370 data 0,0,0,6,6,15,31,127
10380 data 63,63,63,63,63,0,0,0
10390 data 0,112,32,32,32,240,248,248
10400 data 248,248,248,200,248,0,0,0
10410 rem data atrubutes sprite 0, name: avion
10410 put sprite 0,(20*0,100),10,0
10420 rem data atrubutes sprite 1, name: arbol
10420 put sprite 1,(20*1,100),9,1
10430 rem data atrubutes sprite 2, name: casa
10430 put sprite 2,(20*2,100),6,2
10440 goto 10440
